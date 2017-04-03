package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.Authority;
import domain.Actor;
import domain.Folder;
import domain.Message;


@Service
@Transactional
public class MessageService {
	// Managed repository -------------------------------
	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private SpamService spamService;

	@Autowired
	private FolderService folderService;
	
	@Autowired
	private ActorService actorService;

	// Constructor --------------------------------------
	public MessageService() {
		super();
	}

	// Simple CRUD methods ------------------------------
	public Message create() {
		
		Message m = new Message();
		Actor sender=actorService.findByPrincipal();
		m.setSender(sender.getEmailAddress());

		m.setFolder(folderService.findOneFolderByNameActor(sender.getId(),
				"outbox",true));
		
		m.setPriority("NEUTRAL");
		m.setMoment(new Date());

		return m;
	}
	
	private Message changeFolder(Message m,String folder,boolean system)
	{
		return changeFolder(m,folderService.findOneFolderByNameActor(actorService.findByPrincipal().getId(), folder, system).getId());
	}

	public Message changeFolder(Message m, Integer folder)
	{
		Assert.notNull(m);
		Assert.isTrue(m.getId()!=0);
		Assert.notNull(folder);
		Actor connected=actorService.findByPrincipal();
		Folder newf=folderService.findOne(folder);
		Assert.isTrue(connected.equals(m.getFolder().getActor()) && newf.getActor().equals(connected));
		Assert.notNull(newf);
		Message result=null;

			Assert.isTrue(newf!=m.getFolder());
			Folder f=m.getFolder();
			folderService.deleteMessage(f,m);
			m.setFolder(newf);
			folderService.saveMessage(newf, m);
			result=messageRepository.save(m);
			
		return result;
	}
	
	public Message save(Message mm) {
		checkPrincipal();
		Message m=null;
		if(mm.getId()!=0)
		{
			m=messageRepository.findOne(mm.getId());
			if((!m.getFolder().equals(mm.getFolder())) && actorService.findByPrincipal().equals(mm.getFolder().getActor()))
			{
				Folder f=m.getFolder();
				Assert.isTrue(!f.equals(mm.getFolder()));
				folderService.deleteMessage(f, m);
				m.setFolder(mm.getFolder());
				m=messageRepository.save(m);
				folderService.saveMessage(f, m);
			}
		}
		else
		{
			Assert.isTrue(mm.getId()==0);
			Assert.notNull(mm);
			Actor sender=actorService.findByPrincipal();
			Assert.isTrue(sender.getEmailAddress().equals(mm.getSender()));
			Assert.isTrue(mm.getFolder().equals(folderService.findOneFolderByNameActor(sender.getId(), "outbox", true)));
			Actor recipient=actorService.findByEmail(mm.getRecipient());
			Assert.notNull(recipient);	
			mm.setMoment(new Date());
			if (isSpam(mm)) {
				mm.setSpam(true);
			}
			m=messageRepository.save(mm);
			if (isSpam(mm)) {
				mm.setFolder(folderService.findOneFolderByNameActor(recipient.getId(), "spambox",true));
			}
			else
			{
				mm.setFolder(folderService.findOneFolderByNameActor(recipient.getId(), "inbox",true));
			}
			Message m2=messageRepository.save(mm);
			
			Folder fin=m.getFolder();
			Folder fout=m2.getFolder();
			
			Collection<Message> in=fin.getMessages();
			Collection<Message> out=fout.getMessages();
			
			if(!out.contains(m2))
			{
				out.add(m2);
				fout.setMessages(out);
			}
			if(!in.contains(m))
			{
				in.add(m);
				fin.setMessages(in);
			}
			
			folderService.saveMessage(fin,m);
			folderService.saveMessage(fout,m2);
		}
		return m;
	}
	
	public void deleteWR(Message m)
	{
		messageRepository.delete(m.getId());
	}
 
	public void delete(Message m) {
		checkPrincipal();
		Actor connected=actorService.findByPrincipal();
		Assert.isTrue(m.getFolder().getActor().equals(connected));

			Folder f=m.getFolder();
			if(f.getName().toLowerCase().equals("trashbox") && f.getSystemFolder())
			{
				folderService.deleteMessage(f,m);
				messageRepository.delete(m);
			}
			else
			{
				changeFolder(m, "trashbox",true);
			}
		
		
		
		
	}

	private boolean isSpam(Message m) {
		boolean spam = false;
		Assert.notNull(m.getBody());
		String[] keyWords = spamService.findOne().getSpamWords().split(",");
		for (String s : keyWords) {
			if (m.getBody().toLowerCase().contains(s.toLowerCase())) {
				spam = true;
				break;
			}
		}
		return spam;
	}

	public Collection<Message> findAllByActorAndFolder(Integer actor, String folder) {
		return messageRepository.findAllByActorAndFolder(folder, actor);
	}
	
	public Collection<Message> findAllByActorAndFolder(Integer actor, Integer id) {
		return messageRepository.findAllByActorAndFolder(id, actor);
	}
	
	// Other Message methods ---------------------------
	
	public void checkPrincipal() {
		Actor c = actorService.findByPrincipal();
		for (Authority b : c.getUserAccount().getAuthorities()) {
			Assert.isTrue(b.getAuthority().equals("USER") || b.getAuthority().equals("NUTRITIONIST") || b.getAuthority().equals("COOK") || b.getAuthority().equals("ADMINISTRATOR") || b.getAuthority().equals("SPONSOR"));
		}

	}

	public Collection<Message> findByPrincipal() {
		// TODO Auto-generated method stub
		Collection<Message> result=new ArrayList<Message>();
		Actor actor=actorService.findByPrincipal();
		
		result=messageRepository.findByActor(actor.getEmailAddress(),actor);
		return result;
	}

	public Message findOne(int id) {
		// TODO Auto-generated method stub
		return messageRepository.findOne(id);
	}
}

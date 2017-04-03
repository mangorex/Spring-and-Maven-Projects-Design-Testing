package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Folder;
import domain.Message;

import repositories.FolderRepository;

@Service
@Transactional
public class FolderService {

	// Managed repository -------------------------------
	@Autowired
	private FolderRepository folderRepository;
	@Autowired
	private ActorService actorService;
	@Autowired
	private MessageService messageService;

	// Constructor --------------------------------------
	public FolderService() {
		super();

	}

	// Simple CRUD methods ------------------------------

	public Folder create(Actor a) {
		Folder f = new Folder();
		f.setSystemFolder(false);
		f.setActor(a);
		f.setMessages(new ArrayList<Message>());
		return f;
	}

	public Folder save(Folder f) {
		Assert.notNull(f);
		Assert.isTrue(!(f.getSystemFolder() == true && f.getId() != 0));

		Folder saved = folderRepository.save(f);

		Actor b = saved.getActor();
		Collection<Folder> cf = b.getFolders();
		cf.add(saved);
		b.setFolders(cf);
		actorService.save(b);

		return saved;
	}
	
	public Folder saveMessage(Folder f,Message m) {
		Assert.notNull(f);
		Assert.notNull(m);
		Assert.isTrue(m.getId()!=0);
		Collection<Message> cm=new HashSet<Message>();

			cm.addAll(f.getMessages());
			cm.add(m);
			f.setMessages(cm);


		
		
		
		Folder saved = folderRepository.save(f);

		Actor b = saved.getActor();
		Collection<Folder> cf = b.getFolders();
		cf.add(saved);
		b.setFolders(cf);
		actorService.save(b);

		return saved;
	}

	public void delete(Folder f) {

		Assert.isTrue(f.getSystemFolder() != true);
		Assert.notNull(f);
		Actor b = f.getActor();
		if((!(f.getName().equals("trashbox") && f.getSystemFolder()))&& b.getFolders().contains(this.findOneFolderByNameActor(b.getId(), "trashbox", true)))
		{
			if(f.getMessages()!=null && !f.getMessages().isEmpty())
			{
				for(Message m:f.getMessages())
				{
					messageService.delete(m);
				}
			}
		}
		else
		{
			if(f.getMessages()!=null && !f.getMessages().isEmpty())
			{
				for(Message m:f.getMessages())
				{
					messageService.delete(m);
				}
			}
		}
		Collection<Folder> cf = b.getFolders();
		cf.remove(f);
		b.setFolders(cf);
		actorService.save(b);

		folderRepository.delete(f);
	}
	
	public void delete(Folder f, Actor a){
		Assert.notNull(f);
		Assert.notNull(a);
		
		Actor b = a;
		if((!(f.getName().equals("trashbox") && f.getSystemFolder()))&& b.getFolders().contains(this.findOneFolderByNameActor(b.getId(), "trashbox", true)))
		{
			if(f.getMessages()!=null && !f.getMessages().isEmpty())
			{
				for(Message m:f.getMessages())
				{
					messageService.delete(m);
				}
			}
		}
		else
		{
			if(f.getMessages()!=null && !f.getMessages().isEmpty())
			{
				for(Message m:f.getMessages())
				{
					messageService.delete(m);
				}
			}
		}
		Set<Folder> cf = new HashSet<Folder>(b.getFolders());
		cf.remove(f);
		b.setFolders(cf);
		actorService.save(b);

		folderRepository.delete(f);
		
	}
	
	public void deleteMessage(Folder f, Message m){
		Assert.notNull(f);
		Assert.notNull(m);
		Assert.isTrue(m.getId()!=0);
		
		Collection<Message> cf = new HashSet<Message>();

		cf=f.getMessages();


		cf.remove(m);

		f.setMessages(cf);


		folderRepository.save(f);

		
		
	}

	// Other Folder methods ---------------------------

	public Collection<Folder> findAll() {

		return folderRepository.findAll();
	}
	
	public Collection<Folder> findAllByActor(Integer id){
		return folderRepository.findAllByActor(id);
	}

	public Folder findOneFolderByNameActor(Integer a, String fname, boolean system) {
		return folderRepository.findOneFolderByNameActor(a, fname, system);
	}
	
	public Folder findOne(Integer id)
	{
		return folderRepository.findOne(id);
	}

}

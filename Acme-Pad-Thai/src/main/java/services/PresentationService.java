package services;

import java.util.HashSet;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PresentationRepository;
import domain.LearningMaterial;
import domain.MasterClass;
import domain.Presentation;

@Service
@Transactional
public class PresentationService{
	// Managed repository -------------------------------
	@Autowired
	private PresentationRepository presentationRepository;
	@Autowired
	private ActorService actorService;
	@Autowired
	private MasterClassService masterClassService;

	// Constructor --------------------------------------

	public PresentationService() {
		super();
	}

	// Simple CRUD methods ------------------------------
	public Presentation create() {
		Presentation p = new Presentation();
		p.setMasterClasses(new HashSet<MasterClass>());
		p.setAttachments(new HashSet<String>());
		return p;
	}

	public Presentation create(String path) {
		Assert.notNull(path);
		Presentation p = new Presentation();
		Collection<MasterClass> cmc = new HashSet<MasterClass>();
		p.setPath(path);
		p.setMasterClasses(cmc);
		p.setAttachments(new HashSet<String>());
		return p;
	}

	public Presentation create(MasterClass mc, String path) {
		Assert.notNull(mc);
		Assert.notNull(path);
		Presentation p = new Presentation();
		Collection<MasterClass> cmc = new HashSet<MasterClass>();
		cmc.add(mc);
		p.setPath(path);
		p.setMasterClasses(cmc);
		checkPrincipal(p);
		p.setAttachments(new HashSet<String>());
		return p;
	}

	public Presentation save(Presentation pp){
		Assert.notNull(pp);
		if(!pp.getAttachments().isEmpty())
		{
			for(String s:pp.getAttachments())
			{
				Assert.isTrue(isUrl(s));
			}
		}
		Presentation p=presentationRepository.save(pp);
		return p;
	}
	
	public Presentation save2(Presentation pp){
		Assert.notNull(pp);
		if(!pp.getAttachments().isEmpty())
		{
			for(String s:pp.getAttachments())
			{
				Assert.isTrue(isUrl(s));
			}
		}
		Presentation p=presentationRepository.save(pp);
		Collection<LearningMaterial> lm = new HashSet<LearningMaterial>();
		
		if(p.getId()!=0)
		{
			if(presentationRepository.findOne(p.getId()).getMasterClasses().size()>p.getMasterClasses().size()){
				Collection<MasterClass> cmcDB=presentationRepository.findOne(p.getId()).getMasterClasses();
				cmcDB.removeAll(p.getMasterClasses());
				if(cmcDB.size()!=0)
				{
					Collection<LearningMaterial> clm=new HashSet<LearningMaterial>();
					for(MasterClass mcDB:cmcDB)
					{
						clm=mcDB.getLearningMaterials();
						clm.remove(p);
						mcDB.setLearningMaterials(clm);
						masterClassService.save(mcDB);
					}
				}
			}
		}
		if(p.getMasterClasses().size()!=0){
			for(MasterClass a: p.getMasterClasses()){
				lm=a.getLearningMaterials();
				if(!lm.contains(p))
				{
					lm.add(p);
					a.setLearningMaterials(lm);
				}
				masterClassService.save(a);
			}
		}
		return p;
	}

	public void delete(Presentation p) {
		Collection<LearningMaterial> lm = new HashSet<LearningMaterial>();
		if (p.getMasterClasses().size() != 0) {
			for (MasterClass a : p.getMasterClasses()) {
				lm = a.getLearningMaterials();
				lm.remove(p);
				a.setLearningMaterials(lm);
				masterClassService.save(a);
			}
		}
		presentationRepository.delete(p);
	}

	public Presentation findOnePresentation(int p) {
		Assert.notNull(p);
		Presentation result=presentationRepository.findOne(p);
		return result;
	}
	
	public List<Presentation> findAll(){
		return presentationRepository.findAll();
	}
	
	public void checkPrincipal(Presentation p) {
		if(!p.getMasterClasses().isEmpty())
		{
			MasterClass mc=(MasterClass)p.getMasterClasses().toArray()[0];
			Assert.notNull(mc.getCook());
			Assert.isTrue(mc.getCook().equals(actorService.findByPrincipal()));
		}
	}
	
	private static boolean isUrl(String s) {
	    String regex = "^(https?://)?(([\\w!~*'().&=+$%-]+: )?[\\w!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([\\w!~*'()-]+\\.)*([\\w^-][\\w-]{0,61})?[\\w]\\.[a-z]{2,6})(:[0-9]{1,4})?((/*)|(/+[\\w!~*'().;?:@&=+$,%#-]+)+/*)$";
	 
	    try {
	        Pattern patt = Pattern.compile(regex);
	        Matcher matcher = patt.matcher(s);
	        return matcher.matches();
	 
	    } catch (RuntimeException e) {
	        return false;
	    }
	}
}

package services;

import java.util.HashSet;
import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MasterClassRepository;
import domain.Actor;
import domain.Cook;
import domain.LearningMaterial;
import domain.MasterClass;
import domain.Presentation;
import domain.Text;
import domain.Video;

@Service
@Transactional
public class MasterClassService {

	// Managed repository
	@Autowired
	private MasterClassRepository masterClassRepository;

	@Autowired
	private CookService cookService;
	
	@Autowired 
	private VideoService videoService;
	@Autowired
	private TextService textService;
	
	@Autowired
	private PresentationService presentationService;
	
	@Autowired
	private ActorService actorService;
	//Constructor --------------------------------------
	public MasterClassService(){
		super();
	}
	
	//Simple CRUD methods ------------------------------
	
	public MasterClass create(Cook c){
		MasterClass mc = new MasterClass();
		mc.setCook(c);
		mc.setLearningMaterials(new HashSet<LearningMaterial>());
		mc.setActors(new HashSet<Actor>());
		mc.setPromoted(false);
		return mc;
	}
	public MasterClass save(MasterClass mc) {
		Assert.notNull(mc);
		checkPrincipal(mc);
		Cook cook = cookService.findByPrincipal();
		if (cook.getManagedMasterClasses().contains(mc)) {
			Collection<MasterClass> cmc = cook.getManagedMasterClasses();
			cmc.remove(mc);
			cook.setManagedMasterClasses(cmc);
		}
		for (LearningMaterial lm : mc.getLearningMaterials()) {
			if (lm.getMasterClasses().equals(mc)) {
				lm.getMasterClasses().add(mc);
				learningMaterialSave(lm);
			}

		}

		MasterClass saved = masterClassRepository.save(mc);
		Collection<MasterClass> cmc = cook.getManagedMasterClasses();
		cmc.add(saved);
		cook.setManagedMasterClasses(cmc);
		cookService.save(cook);

		return saved;
	}

	public MasterClass save(MasterClass mc, Actor a) {
		Assert.notNull(mc);
		Assert.notNull(a);
		checkPrincipal1(mc);

		Collection<Actor> la = mc.getActors();
		la.add(a);
		mc.setActors(la);
		MasterClass saved = masterClassRepository.save(mc);
		Collection<MasterClass> cm = a.getMasterClasses();
		cm.add(saved);
		a.setMasterClasses(cm);
		actorService.save(a);

		return saved;

	}
	public void delete(MasterClass mc){
		Assert.notNull(mc);
		checkPrincipal(mc);
		Cook cook = cookService.findByPrincipal();
		Collection<MasterClass> cmc = cook.getManagedMasterClasses();
		cmc.remove(mc);
		cook.setManagedMasterClasses(cmc);
		masterClassRepository.delete(mc);
		
	}

public void addLearningMaterial(MasterClass mc,LearningMaterial lm)
	{
		Collection<LearningMaterial> clm=mc.getLearningMaterials();
		if(!clm.contains(lm))
		{
			clm.add(lm);
		}
		mc.setLearningMaterials(clm);
		masterClassRepository.save(mc);
	}
	
	public void checkPrincipal(MasterClass mc){
		Cook cook = cookService.findByPrincipal();
		Assert.isTrue(cook.equals(mc.getCook()));
	}
	
	public void checkPrincipal1(MasterClass mc){
		Actor cook = actorService.findByPrincipal();
		Assert.isTrue(!cook.equals(mc.getCook()));
	}
	
	public Collection<MasterClass> findAll(){
		return masterClassRepository.findAll();
	}
	
	public MasterClass findOne(Integer id){
		return masterClassRepository.findOne(id);
	}
	//Other MasterClass methods ---------------------------

	private LearningMaterial learningMaterialSave(LearningMaterial lm){
		LearningMaterial lmm = null;
		
		if(lm instanceof Video){
			lmm = videoService.save((Video) lm);
		}else if(lm instanceof Text){
			lmm = textService.save((Text) lm);
		
		}else if(lm instanceof Presentation){
			lmm = presentationService.save((Presentation) lm);
		}
		return lmm;
	}
	
	public Collection<Object[]> findNameTitleDescriptionMasterClass(){
		return masterClassRepository.findNameTitleDescriptionMasterClass();
	}
	
	public Collection<Object[]>  calculateMinMaxStddevNumberOfMasterClassPerCook(){
		return masterClassRepository.calculateMinMaxStddevNumberOfMasterClassPerCook();
		
	}
	
	public Collection<Object[]> calculateAvgPromotedDemotedMasterClassPerCook(){
		return masterClassRepository.calculateAvgPromotedDemotedMasterClassPerCook();
		
	}
	
	public Integer calculateNumberMasterClassPromoted(){
		return masterClassRepository.calculateNumberMasterClassPromoted();
	}

	
	public void register(MasterClass masterClass,Actor actor) {
		Collection<Actor> ca = masterClass.getActors();
		ca.add(actor);
		masterClass.setActors(ca);
		masterClassRepository.save(masterClass);
		
		Collection<MasterClass> cmc = actor.getMasterClasses();
		cmc.add(masterClass);
		actor.setMasterClasses(cmc);
		actorService.save(actor);
		
	}
	public void unregister(MasterClass masterClass, Actor actor){
		Collection<Actor> ca = masterClass.getActors();
		Collection<MasterClass> cmc = actor.getMasterClasses();
		ca.remove(actor);
		masterClass.setActors(ca);
		cmc.remove(masterClass);
		actor.setMasterClasses(cmc);
		masterClassRepository.save(masterClass);
		actorService.save(actor);
		
	}
	
	public MasterClass promote(MasterClass mc) {
		Assert.notNull(mc);
		mc.setPromoted(true);
		MasterClass a = masterClassRepository.save(mc);
		
		return a;
	}
	
	public MasterClass demote(MasterClass mc) {
		Assert.notNull(mc);
		mc.setPromoted(false);
		MasterClass a = masterClassRepository.save(mc);
		
		return a;
	}


	
	
	
}

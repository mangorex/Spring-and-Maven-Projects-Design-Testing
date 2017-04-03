package controllers.cook;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CookService;
import services.LearningMaterialService;
import services.MasterClassService;
import services.MessageService;
import services.PresentationService;
import services.TextService;
import services.VideoService;
import controllers.AbstractController;
import domain.Actor;
import domain.Cook;
import domain.LearningMaterial;
import domain.MasterClass;
import domain.Message;
import domain.Presentation;
import domain.Text;
import domain.Video;

@Controller
@RequestMapping("/masterClass/cook")
public class MasterClassCookController extends AbstractController {

	// Services ---------------------------
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private TextService textService;
	
	@Autowired
	private PresentationService presentationService;
	
	@Autowired
	private LearningMaterialService learningMaterialService;
	
	@Autowired
	private MasterClassService masterClassService;

	@Autowired
	private CookService cookService;
	@Autowired
	private MessageService messageService;
	
	// Constructors ------------------------
	public MasterClassCookController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Cook c = cookService.findByPrincipal();
		Collection<MasterClass> cmc = c.getManagedMasterClasses();

		result = new ModelAndView("masterClass/cook/list");
		result.addObject("masterClasses", cmc);

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Cook c = cookService.findByPrincipal();
		MasterClass mc = masterClassService.create(c);

		result = new ModelAndView("masterClass/edit");
		result.addObject("masterClass", mc);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid MasterClass masterClass,
			BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(masterClass);
		} else {
			try {
				masterClassService.save(masterClass);
				result = new ModelAndView("redirect:list.do");

			} catch (Throwable oops) {
				result = createEditModelAndView(masterClass,
						"masterClass.commit.error.save");
			}
		}
		return result;
	}

	@RequestMapping(value="/edit", method = RequestMethod.POST, params="delete")
	public ModelAndView delete(MasterClass masterClass, BindingResult binding){
		ModelAndView result = null;
		try{
            masterClassService.delete(masterClass);
            Message m = messageService.create();
            for(Actor a: masterClass.getActors()){
            	m.setRecipient(a.getEmailAddress());
            	m.setSubject("Delete MasterClass");
            	m.setBody("The MasterClass " + masterClass.getTitle() + " will be deleted. Sorry for the inconveniences");
            	messageService.save(m);			
            }
            
            result = new ModelAndView("redirect:list.do");
		}catch(Throwable oops){
			result = createEditModelAndView(masterClass,"masterClass.commit.error.delete");
			
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@Valid int masterClassId) {
		ModelAndView result;
		MasterClass masterClass = masterClassService.findOne(masterClassId);

		result = createEditModelAndView(masterClass);

		return result;
	}
	
	@RequestMapping(value="/learningMaterial", method = RequestMethod.GET)
	public ModelAndView learningMaterial2(String masterClassId){	
		ModelAndView result;
		if(masterClassId==null || masterClassId.isEmpty() || masterClassService.findOne(Integer.parseInt(masterClassId))==null)
		{
			result=new ModelAndView("redirect:../cook/list.do");
		}
		else
		{
			result=learningMaterial(masterClassId);
		}
		return result;
	}

	@RequestMapping(value="/learningMaterial", method = RequestMethod.POST, params = "edit")
	public ModelAndView learningMaterial(String masterClass){
	 	ModelAndView result;
	 	MasterClass mc=masterClassService.findOne(Integer.parseInt(masterClass));
	 	Collection<LearningMaterial> clm=learningMaterialService.findAll();
	 	Collection<LearningMaterial> mclm=learningMaterialService.findAllLMByMC(mc.getId());
	 	boolean[] b=new boolean[clm.size()];
	 	int i=0;
	 	for(LearningMaterial lm:clm)
	 	{
	 		b[i]=mclm.contains(lm);
	 		i=i+1;
	 	}
	 	result=new ModelAndView("masterClass/lm");
	 	result.addObject("masterClass",masterClass);
	 	result.addObject("learningMaterials",clm);
	 	result.addObject("selected",b);
	 	result.addObject("size",clm.size()-1);
	 	
		return result;
	}
	
	@RequestMapping(value="/learningMaterial", method = RequestMethod.POST, params = "lm")
	public ModelAndView learningMaterial(String masterClass,String[] learningMaterial){
	 	ModelAndView result;
	 	MasterClass mc=masterClassService.findOne(Integer.parseInt(masterClass));
	 	Collection<LearningMaterial> clmselected=new ArrayList<LearningMaterial>();
	 	Collection<MasterClass> c=new ArrayList<MasterClass>();
	 	try{
	 		if(learningMaterial!=null)
	 		{
		 		for(int i=0;i<learningMaterial.length;i++)
			 	{
			 		clmselected.add(learningMaterialService.findOneLM(Integer.parseInt(learningMaterial[i])));
			 	}
	 		}
	 		if(!clmselected.isEmpty())
	 		{
		 		for(LearningMaterial l:clmselected)
		 		{
		 			c=l.getMasterClasses();
		 			if(!c.contains(mc))
		 			{
		 				c.add(mc);
		 			}
		 			l.setMasterClasses(c);
		 			if(l instanceof Video)
		 			{
		 				videoService.save((Video)l);
		 			}
		 			else if(l instanceof Text)
		 			{
		 				textService.save((Text)l);
		 			}
		 			else if(l instanceof Presentation)
		 			{
		 				presentationService.save((Presentation)l);
		 			}
		 		}
	 		}
	 		Collection<LearningMaterial> unselect=mc.getLearningMaterials();
	 		if(!clmselected.isEmpty())
	 		{
	 			unselect.removeAll(clmselected);
	 		}
	 		if(!unselect.isEmpty())
	 		{
		 		for(LearningMaterial l:unselect)
		 		{
		 			c=l.getMasterClasses();
		 			c.remove(mc);
		 			l.setMasterClasses(c);
		 			if(l instanceof Video)
		 			{
		 				videoService.save((Video)l);
		 			}
		 			else if(l instanceof Text)
		 			{
		 				textService.save((Text)l);
		 			}
		 			else if(l instanceof Presentation)
		 			{
		 				presentationService.save((Presentation)l);
		 			}
		 		}
	 		}
	 		if(clmselected.size()>0)
	 		{
	 			mc.setLearningMaterials(clmselected);
	 		}
	 		else
	 		{
	 			mc.setLearningMaterials(new ArrayList<LearningMaterial>());
	 		}
	 		//mc=masterClassService.save(mc);
	 		result=list();
	 		result.addObject("messageERROR","masterClass.commit.ok");
	 	}catch(Throwable oops)
	 	{
	 		Collection<LearningMaterial> clm=learningMaterialService.findAll();
		 	Collection<LearningMaterial> mclm=learningMaterialService.findAllLMByMC(mc.getId());
		 	boolean[] b=new boolean[clm.size()];
		 	int i=0;
		 	for(LearningMaterial lm:clm)
		 	{
		 		b[i]=mclm.contains(lm);
		 		i=i+1;
		 	}
		 	result=new ModelAndView("masterClass/lm");
		 	result.addObject("masterClass",masterClass);
		 	result.addObject("learningMaterials",clm);
		 	result.addObject("selected",b);
		 	result.addObject("size",clm.size()-1);
		 	result.addObject("messageERROR","masterClass.commit.error");
			
	 	}
	 	return result;
	}

	private ModelAndView createEditModelAndView(MasterClass masterClass) {

		return createEditModelAndView(masterClass, null);
	}

	private ModelAndView createEditModelAndView(MasterClass masterClass,
			String message) {
		ModelAndView result;
		result = new ModelAndView("masterClass/edit");
		result.addObject("masterClass", masterClass);

		result.addObject("messageERROR", message);

		return result;
	}
}

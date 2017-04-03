package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CookService;
import services.LearningMaterialService;
import domain.Cook;
import domain.LearningMaterial;

@Controller
@RequestMapping("/learningMaterial")
public class LearningMaterialController extends AbstractController {

	// Services ------------------------------
	@Autowired
	private LearningMaterialService learningMaterialService;
	
	@Autowired
	private CookService cookService;
	// Constructors ---------------------------
	public LearningMaterialController() {
		super();
	}

	// Listing --------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView access(@Valid int masterClassId) {
		ModelAndView result;
		Collection<LearningMaterial> lm = learningMaterialService
				.findAllLMByMC(masterClassId);

		result = new ModelAndView("learningMaterial/list");
		result.addObject("learningMaterials", lm);
		result.addObject("masterClassId",masterClassId);
		
		return result;

	}
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
	
		result = new ModelAndView("learningMaterial/edit");
		
		
		return result;
	}
	
}

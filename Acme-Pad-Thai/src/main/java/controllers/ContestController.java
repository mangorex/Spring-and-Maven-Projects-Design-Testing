package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ContestService;

import domain.Contest;

@Controller
@RequestMapping("/contest")
public class ContestController extends AbstractController{

	
	//Services ---------------------------
	
	@Autowired
	private ContestService contestService;
	
	//Constructors ----------------------
	
	public ContestController(){
		super();
	}
	
	//Listing --------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		
		Collection<Contest> contests;
		
		contests = contestService.findAll();
		
		result = new ModelAndView("contest/list");
		result.addObject("contests", contests);
		result.addObject("requestUri", "contest/list.do");
		
		return result;
	}

}

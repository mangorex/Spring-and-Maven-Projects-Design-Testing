package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MasterClassService;
import controllers.AbstractController;
import domain.MasterClass;


@Controller
@RequestMapping("/masterClass")
public class MasterClassAdministratorController extends AbstractController{

		//Services -------------------------
		
		@Autowired
		private MasterClassService masterClassService;
	
		
		
		//Constructors -----------------------
		public MasterClassAdministratorController(){
			super();
		}
			
				
		@RequestMapping(value="/edit", method = RequestMethod.GET)
		public ModelAndView edit(@Valid int masterClassId){
			ModelAndView result;
			MasterClass masterClass = masterClassService.findOne(masterClassId);
		
			result = createEditModelAndView(masterClass);
				
				return result;
		}
		@RequestMapping(value="/edit", method = RequestMethod.POST, params="promote")
		public ModelAndView promote(@Valid MasterClass masterClass,BindingResult binding){
			ModelAndView result;
			if(binding.hasErrors()){
				result = createEditModelAndView(masterClass);
			}else{
				try{
					@SuppressWarnings("unused")
					MasterClass a = masterClassService.promote(masterClass);
					result = new ModelAndView("redirect:list.do");
					
				}catch(Throwable oops){
					result = createEditModelAndView(masterClass, "masterClass.commit.error.save");
				}
					}
					return result;
				}
				
				@RequestMapping(value="/edit", method = RequestMethod.POST, params="demote")
				public ModelAndView demote(@Valid MasterClass masterClass,BindingResult binding){
					ModelAndView result;
					if(binding.hasErrors()){
						result = createEditModelAndView(masterClass);
					}else{
						try{
							@SuppressWarnings("unused")
							MasterClass a = masterClassService.demote(masterClass);
							result = new ModelAndView("redirect:list.do");
							
						}catch(Throwable oops){
							result = createEditModelAndView(masterClass, "masterClass.commit.error.save");
						}
					}
					return result;
				}

					
					private ModelAndView createEditModelAndView(MasterClass masterClass)
					{
						return createEditModelAndView(masterClass, null);
					}
					
					private ModelAndView createEditModelAndView(MasterClass masterClass,String message)
					{
						ModelAndView result;
						
						result=new ModelAndView("masterClass/edit");
						result.addObject("masterClass",masterClass);
						
						
							
							result.addObject("messageERROR",message);
						return result;
					}
		}

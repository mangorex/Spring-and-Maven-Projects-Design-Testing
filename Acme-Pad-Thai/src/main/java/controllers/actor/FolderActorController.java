package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Folder;
import domain.Message;
import forms.FolderList;

@Controller
@RequestMapping("/folder/actor")
public class FolderActorController extends AbstractController{
	// Services ---------------------------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private MessageService messageService;
	
	// Constructors -----------------------------------------------------------
	public FolderActorController() {
		super();
	}

	// Create ----------------------------------------------------------------
		@RequestMapping(value = "/create", method=RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Folder folder= folderService.create(actorService.findByPrincipal());
			
			result = new ModelAndView("folder/edit");
			result.addObject("folder", folder);

			return result;
		}
		
		// Edit ----------------------------------------------------------------
		@RequestMapping(value = "/edit", method=RequestMethod.GET)
		public ModelAndView edit(@RequestParam String selectedFolder) {
			ModelAndView result;
			Folder folder=folderService.findOne(Integer.parseInt(selectedFolder));
			
			result=createEditModelAndView(folder);

			return result;
		}
	
		// Save ----------------------------------------------------------------
		@RequestMapping(value = "/edit", method=RequestMethod.POST, params="save")
		public ModelAndView save(@Valid Folder folder, BindingResult binding) {
			ModelAndView result;
			if(binding.hasErrors()){
				System.out.println(binding.toString());
				result=createEditModelAndView(folder);
			}
			else{
				try{
					
				folderService.save(folder);				
				Collection<Message> messages;
				messages = messageService.findByPrincipal();
				Collection<Folder> folders;

				folders = folderService.findAllByActor(actorService.findByPrincipal()
						.getId());

				result = new ModelAndView("message/list");
				result.addObject("messages", messages);
				result.addObject("folders", folders);
				result.addObject("folderList", new FolderList());
				result.addObject("selectedFolder", 0);
				result.addObject("sysFolder",1);
				
				}catch(Throwable oops)
				{
					System.out.println(oops.toString());
					result=createEditModelAndView(folder,"folder.commit.error");
				}
			}
			return result;
		}
		
		// Delete ----------------------------------------------------------------
		@RequestMapping(value = "/edit", method=RequestMethod.POST,params="delete")
		public ModelAndView delete(@Valid Folder folder, BindingResult binding) {
			ModelAndView result;
			if(binding.hasErrors()){
				result=createEditModelAndView(folder);
			}
			else{
				try{
				folderService.delete(folder);
				Collection<Message> messages;
				messages = messageService.findByPrincipal();
				Collection<Folder> folders;

				folders = folderService.findAllByActor(actorService.findByPrincipal()
						.getId());

				result = new ModelAndView("message/list");
				result.addObject("messages", messages);
				result.addObject("folders", folders);
				result.addObject("folderList", new FolderList());
				result.addObject("selectedFolder", 0);
				result.addObject("sysFolder",1);
				}catch(Throwable oops)
				{
					result=createEditModelAndView(folder,"folder.commit.error");
				}
			}
			return result;
		}
		
		private ModelAndView createEditModelAndView(Folder folder)
		{
			return createEditModelAndView(folder, null);
		}
		
		private ModelAndView createEditModelAndView(Folder folder,String message)
		{
			ModelAndView result;
			
			result=new ModelAndView("folder/edit");
			result.addObject("folder",folder);
			result.addObject("messageERROR",message);
			
			return result;
		}
}

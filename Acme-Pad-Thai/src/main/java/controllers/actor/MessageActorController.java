/* CurriculumCustomerController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers.actor;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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

// TODO: implement this controller from scratch. 

/**
 * @author Student
 *
 */
@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MessageService messageService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private FolderService folderService;

	// Constructors -----------------------------------------------------------

	public MessageActorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Message> messages;

		messages = messageService.findByPrincipal();

		result = new ModelAndView("message/list");
		Collection<Folder> folders;

		folders = folderService.findAllByActor(actorService.findByPrincipal()
				.getId());

		result.addObject("messages", messages);
		result.addObject("folders", folders);
		result.addObject("folderList", new FolderList());
		result.addObject("selectedFolder", 0);
		result.addObject("sysFolder",1);

		return result;
	}

	@RequestMapping(value = "/view", method = RequestMethod.POST, params = "view")
	public ModelAndView list(@RequestParam String fols,
			@ModelAttribute("folderList") FolderList folderList,
			BindingResult binding) throws Exception {
		ModelAndView result;
		Collection<Message> messages;
		Folder f = folderService.findOne(Integer.parseInt(fols));
		
		if (!fols.equals("0")) {
			

			messages = messageService.findAllByActorAndFolder(actorService
					.findByPrincipal().getId(), f.getId());
		} else {
			messages = messageService.findByPrincipal();
		}

		result = new ModelAndView("message/list");
		Collection<Folder> folders;

		folders = folderService.findAllByActor(actorService.findByPrincipal()
				.getId());

		if(fols.equals("0") || f.getSystemFolder()){
			result.addObject("sysFolder",1);
		}
		else{
			result.addObject("sysFolder",0);
		}
		
		result.addObject("messages", messages);
		result.addObject("folders", folders);
		result.addObject("folderList", new FolderList());
		result.addObject("selectedFolder", fols);
		

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "edit")
	public ModelAndView changeFolder(@RequestParam String message,
			@RequestParam String whereAmI, @RequestParam String fols,
			@ModelAttribute("folderList") FolderList folderList,
			BindingResult binding) throws Exception {
		ModelAndView result;
		Collection<Message> messages;
		result = new ModelAndView("message/list");
		Collection<Folder> folders;
		/** TODO CAMBIAR EL MENSAJE */
		String error = "message.commit.error";
		folders = folderService.findAllByActor(actorService.findByPrincipal()
				.getId());
		result.addObject("folders", folders);
		result.addObject("folderList", new FolderList());
		if (!whereAmI.equals("0")) {
			Folder f = folderService.findOne(Integer.parseInt(whereAmI));

			messages = messageService.findAllByActorAndFolder(actorService
					.findByPrincipal().getId(), f.getId());
		} else {
			messages = messageService.findByPrincipal();
		}
		if (binding.hasErrors()) {
			result.addObject("messages", messages);
			result.addObject("selectedFolder", whereAmI);
			result.addObject("messageERROR", error);
		} else {
			try {
				Message m = messageService.findOne(Integer.parseInt(message));
				Folder f = folderService.findOne(Integer.parseInt(fols));

				if (f.getActor() == actorService.findByPrincipal()) {
					if (!m.getFolder().equals(f)) {
						messageService.changeFolder(m, f.getId());
						if (!fols.equals("0")) {
							Folder f2 = folderService.findOne(Integer
									.parseInt(fols));

							messages = messageService.findAllByActorAndFolder(
									actorService.findByPrincipal().getId(),
									f2.getId());
						} else {
							messages = messageService.findByPrincipal();
						}
						result.addObject("messages", messages);
						result.addObject("selectedFolder", fols);
					} else {

						/** TODO CAMBIAR EL MENSAJE */

						result.addObject("messages", messages);
						result.addObject("selectedFolder", whereAmI);
						result.addObject("messageERROR", error);
					}
				} else {
					/** TODO CAMBIAR EL MENSAJE */

					result.addObject("messages", messages);
					result.addObject("selectedFolder", whereAmI);
					result.addObject("messageERROR", error);
				}

			} catch (Throwable oops) {
				result.addObject("messages", messages);
				result.addObject("selectedFolder", whereAmI);
				result.addObject("messageERROR", oops.getStackTrace());
			}
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@RequestParam String message,
			@RequestParam String whereAmI) throws Exception {
		ModelAndView result;
		Collection<Message> messages;
		result = new ModelAndView("message/list");
		Collection<Folder> folders;
		/** TODO CAMBIAR EL MENSAJE */
		String error = "message.commit.error";
		folders = folderService.findAllByActor(actorService.findByPrincipal()
				.getId());
		result.addObject("folders", folders);
		result.addObject("folderList", new FolderList());
		if (!whereAmI.equals("0")) {
			Folder f = folderService.findOne(Integer.parseInt(whereAmI));

			messages = messageService.findAllByActorAndFolder(actorService
					.findByPrincipal().getId(), f.getId());
		} else {
			messages = messageService.findByPrincipal();
		}
		/*
		 * if(binding.hasErrors()){ result.addObject("messages",messages);
		 * result.addObject("selectedFolder",whereAmI);
		 * result.addObject("message", error); } else{
		 */
		try {
			Message m = messageService.findOne(Integer.parseInt(message));
			Folder f = m.getFolder();
			Folder trash = folderService.findOneFolderByNameActor(actorService
					.findByPrincipal().getId(), "trashbox", true);

			if (f.getActor() == actorService.findByPrincipal()) {
				messageService.delete(m);
				messages = messageService.findAllByActorAndFolder(actorService
						.findByPrincipal().getId(), trash.getId());
				result.addObject("messages", messages);
				result.addObject("selectedFolder", trash.getId());
			} else {
				/** TODO CAMBIAR EL MENSAJE */

				result.addObject("messages", messages);
				result.addObject("selectedFolder", whereAmI);
				result.addObject("messageERROR", error);
			}

		} catch (Throwable oops) {
			result.addObject("messages", messages);
			result.addObject("selectedFolder", whereAmI);
			result.addObject("messageERROR", oops.getStackTrace());
		}
		// }
		return result;
	}

	// Read -------------------------------------------------------------------

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public ModelAndView read(@RequestParam int id) {
		ModelAndView result;
		Message message;
		if (id != 0
				&& messageService.findOne(id) != null
				&& messageService.findOne(id).getFolder().getActor() == actorService
						.findByPrincipal()) {
			message = messageService.findOne(id);

			result = new ModelAndView("message/edit");

			result.addObject("message", message);
		} else {
			Collection<Message> messages;

			messages = messageService.findByPrincipal();
			Collection<Folder> folders;

			folders = folderService.findAllByActor(actorService
					.findByPrincipal().getId());

			result = new ModelAndView("message/list");
			result.addObject("messages", messages);
			result.addObject("folders", folders);
			result.addObject("folderList", new FolderList());
			result.addObject("selectedFolder", 0);
		}
		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message message = messageService.create();

		result = new ModelAndView("message/edit");

		result.addObject("message", message);
		result.addObject("create", true);

		return result;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "save")
	public ModelAndView send(@Valid Message message, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("message/edit");

			result.addObject("message", message);
			result.addObject("create", true);
		} else {
			message.setId(0);
			message.setMoment(new Date());
			message.setFolder(folderService.findOneFolderByNameActor(actorService.findByPrincipal().getId(), "outbox", true));
			message.setSender(actorService.findByPrincipal().getEmailAddress());
			if (actorService.findByEmail(message.getRecipient()) == null) {
				result = new ModelAndView("message/edit");

				result.addObject("message", message);
				result.addObject("create", true);
				result.addObject("messageERROR", "message.actor.notfound");
			} else {
				messageService.save(message);
				result = new ModelAndView("message/list");
				Folder f = folderService.findOneFolderByNameActor(actorService.findByPrincipal().getId(), "outbox", true);
				Collection<Message> messages;
				messages = messageService.findAllByActorAndFolder(actorService.findByPrincipal().getId(), f.getId());
				Collection<Folder> folders;
				folders = folderService.findAllByActor(actorService.findByPrincipal().getId());
				result.addObject("folders", folders);
				result.addObject("folderList", new FolderList());
				result.addObject("messages", messages);
				result.addObject("selectedFolder", f.getId());
			}

		}

		return result;
	}
}
package controllers.cook;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.VideoService;
import controllers.AbstractController;
import domain.Video;

@Controller
@RequestMapping("/video")
public class VideoCookController extends AbstractController{

	//Services ---------------------------
	
	@Autowired
	private VideoService videoService;
	
	
	
	//Constructors ------------------------
	public VideoCookController(){
		super();
	}
	
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(){
		
		Video video = videoService.create();
		video.setIdentifier("https://www.youtube.com/watch?v=");
		
		
		return createEditModelAndView(video);
	}
	
	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Video video,BindingResult binding){
		ModelAndView result;
		if(binding.hasErrors()){
			result = createEditModelAndView(video);
		}else{
			try{
				videoService.save(video);
				result = new ModelAndView("redirect:learningMaterial/list.do");
				
			}catch(Throwable oops){
				result = createEditModelAndView(video, "video.commit.error.save");
			}
		}
		return result;
	}
	
	
	
	
	

	private ModelAndView createEditModelAndView(Video video) {
		
		return createEditModelAndView(video,null);
	}

	private ModelAndView createEditModelAndView(Video video,
			String message) {
		ModelAndView result;
		result = new ModelAndView("video/edit");
		result.addObject("video", video);

		result.addObject("messageERROR", message);

		return result;
	}
}

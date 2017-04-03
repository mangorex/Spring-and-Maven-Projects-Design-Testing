package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SpamService;
import controllers.AbstractController;
import domain.Spam;
import forms.FolderList;


@Controller
@RequestMapping("/spam/administrator")
public class SpamAdministratorController  extends AbstractController{

	//Services ---------------------------------------
	
	@Autowired
	private SpamService spamService;

	//Constructors -----------------------------------
	
	public SpamAdministratorController(){
		super();
	}
	
	// Deleting ----------------------------------
			@RequestMapping(value = "/manage", method = RequestMethod.POST, params="delete")
			public ModelAndView deleting(@Valid String fols){
				ModelAndView result;
				fols=fols.toLowerCase();
				
				
				Collection<String> words = spamService.findAll();
				result = new ModelAndView("spam/manage");
				
				if(fols!="" && !words.contains(fols))
				{
					result.addObject("messageERROR", "spam.commit.error");
				}
				else
				{
					words.remove(fols);
					Spam s=spamService.findOne();
					s.setSpamWords(StringUtils.join(words, ","));
					spamService.save(s);
				}
				
				String spamkeywords=StringUtils.join(words, ",");
				
//				if(words.contains("sex") && words.contains("viagra") && words.contains("cialis") && words.contains("love"))
//				{
//					words.remove("sex");
//					words.remove("viagra");
//					words.remove("cialis");
//					words.remove("love");
//				}
				
				
				
				result.addObject("spamkeywords", spamkeywords);
				result.addObject("words", words);
				result.addObject("folderList",new FolderList());
				
				return result;
			}
	
	// Adding ----------------------------------
		@RequestMapping(value = "/manage", method = RequestMethod.POST, params="save")
		public ModelAndView adding(@Valid String fols){
			ModelAndView result;
			fols=fols.toLowerCase();
			
			
			Collection<String> words = spamService.findAll();
			result = new ModelAndView("spam/manage");
			
			if(fols!="" && !words.contains(fols))
			{
				Spam s=spamService.findOne();
				s.setSpamWords(s.getSpamWords().concat(","+fols));
				spamService.save(s);
				words=spamService.findAll();
			}
			else
			{
				result.addObject("messageERROR", "spam.commit.error");
			}
			
			String spamkeywords=StringUtils.join(words, ",");
			
//			if(words.contains("sex") && words.contains("viagra") && words.contains("cialis") && words.contains("love"))
//			{
//				words.remove("sex");
//				words.remove("viagra");
//				words.remove("cialis");
//				words.remove("love");
//			}
			
			
			
			result.addObject("spamkeywords", spamkeywords);
			result.addObject("words", words);
			result.addObject("folderList",new FolderList());
			
			return result;
		}
	
	// Listing ----------------------------------
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		
		Collection<String> words = spamService.findAll();
		
		String spamkeywords=StringUtils.join(words, ",");
		
//		words.remove("sex");
//		words.remove("viagra");
//		words.remove("cialis");
//		words.remove("love");
		
		
		
		result = new ModelAndView("spam/manage");
		result.addObject("spamkeywords", spamkeywords);
		result.addObject("words", words);
		result.addObject("folderList",new FolderList());
		
		return result;
	}
	
	
	
}

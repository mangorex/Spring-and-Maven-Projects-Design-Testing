/* WelcomeController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CampaignService;
import services.MasterClassService;
import domain.Campaign;
import domain.MasterClass;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
	@Autowired	
	private CampaignService campaignService;
	@Autowired	
	private MasterClassService masterClassService;

	// Constructors -----------------------------------------------------------
	
	public WelcomeController() {
		super();
	}
		
	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required=false) String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		Collection<Campaign> cc=new HashSet<Campaign>();
		cc.addAll(campaignService.findAll());
		
		String banner=bannerAleatorio(getActiveBanners(cc));
		
		
		
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
				
		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);
		result.addObject("banner", banner);
		result.addObject("masterClasses", finPromotedMasterClasses());

		return result;
	}
	
	private String bannerAleatorio(Collection<String> activeBanners) {
		String b="";
		Random rnd = new Random();
		LinkedList<String> ls=new LinkedList<String>();
		ls.addAll(activeBanners);
		
		int num=(int)(rnd.nextDouble() * ls.size() + 0);
		if(ls.size()>0){
			b=ls.get(num);
		}
		else{	b="ERROR";}
		
		bannerDisplayed(b);
		return b;
	}

	private Collection<String> getActiveBanners(Collection<Campaign> cc){
		Collection<String> cs= new LinkedList<String>();
		Date d= new Date();
		
		for(Campaign c: cc){
			if(c.getEndDate().after(d) && c.getStar() && c.getMaxBannersDisplayed()>c.getNumBanners()){
				cs.addAll(Arrays.asList(c.getBanners().split(",")));
			}
		}
		
		return cs;
	}
	
	private void bannerDisplayed(String banner){
		Collection<Campaign> cc=new HashSet<Campaign>();
		cc.addAll(campaignService.findAll());
		
		for(Campaign c:cc){
			if(containBanner(c,banner)){
				c.setNumBanners(c.getNumBanners()+1);
				campaignService.save(campaignService.findOne(c.getId()));
			}
		}
	}
	
	private boolean containBanner(Campaign c,String banner){
		boolean res=false;
		Collection<String> cs= new LinkedList<String>();
		cs.addAll(Arrays.asList(c.getBanners().split(",")));
		
		for(String s:cs){
			if(s.equals(banner)){
				res=true;
			}
		}
		return res;
	}
	
	// auxiliar method for promoted masterclases
	private Collection<MasterClass> finPromotedMasterClasses(){
		Collection<MasterClass> cm= new HashSet<MasterClass>();
		
				
		for(MasterClass m:masterClassService.findAll()){
			if(m.getPromoted()){
				cm.add(m);
			}
		}			
		return cm;
		}
}
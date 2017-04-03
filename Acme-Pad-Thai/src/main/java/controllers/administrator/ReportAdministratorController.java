package controllers.administrator;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BillService;
import services.CampaignService;
import services.ContestService;
import services.CookService;
import services.LearningMaterialService;
import services.MasterClassService;
import services.RecipeService;
import services.SponsorService;
import services.StepService;
import services.UserService;
import controllers.AbstractController;
import domain.Contest;
import domain.LearningMaterial;
import domain.Sponsor;
import domain.User;

@Controller
@RequestMapping("/report/administrator")
public class ReportAdministratorController extends AbstractController {

	// Services ----------------
	@Autowired
	private SponsorService sponsorService;
	@Autowired
	private CampaignService campaignService;
	@Autowired
	private UserService userService;
	@Autowired
	private RecipeService recipeService;
	@Autowired
	private BillService billService;
	@Autowired
	private ContestService contestService;
	@Autowired
	private StepService stepService;
	@Autowired
	private CookService cookService;
	@Autowired
	private MasterClassService masterclassService;
	@Autowired
	private LearningMaterialService learningMaterialService;

	// Constructors ------------------

	public ReportAdministratorController() {
		super();
	}

	// Listing ------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Sponsor> sponsors;

		Object[] rpu = recipeService.calculateMinAvgMaxRecipePerUser();
		Collection<User> umr = userService.findUserMaxRecipes();
		Object[] rc = recipeService.calculateMinAvgMaxQualifiedsForContest();
		Collection<Contest> cq = contestService
				.findContestMaxRecipesQualifieds();
		Object[] sr = stepService.calculateAvgStdRecipe();
		Object[] ir = recipeService.calculateAvgStddevIngredientsOfRecipe();
		Collection<User> up = userService.findUserOrderByFollowers();
		Collection<User> ul = userService.findUserRegardingAvgLikesAndDislikes();
		
		sponsors = sponsorService
				.findSponsorNotManagedCampaignForTheLastThreeMonths();
		Double[] campaign = campaignService
				.calculateMinAvgMaxCampaignsPerSponsor();
		Double[] active = campaignService
				.calculateMinAvgMaxNumberCampaignsActivePerSponsor();

		Collection<Sponsor> ranking, rankingBill;

		result = new ModelAndView("report/dashboard");
		if(rpu.length<3)
		{
			result.addObject("rpsMin", 0);
			result.addObject("rpsAvg", 0);
			result.addObject("rpsMax", 0);
		}
		else
		{
			result.addObject("rpsMin", rpu[0]);
			result.addObject("rpsAvg", rpu[1]);
			result.addObject("rpsMax", rpu[2]);	
		}
		result.addObject("usersmorerecipes", umr);
		if(rc.length<3)
		{
			result.addObject("rcMin", 0);
			result.addObject("rcAvg", 0);
			result.addObject("rcMax", 0);	
		}
		else
		{
			result.addObject("rcMin", rc[0]);
			result.addObject("rcAvg", rc[1]);
			result.addObject("rcMax", rc[2]);
		}
		result.addObject("contestmore", cq);
		if(sr.length<2)
		{
			result.addObject("srAvg", 0);
			result.addObject("srDv", 0);	
		}
		else
		{
			result.addObject("srAvg", sr[0]);
			result.addObject("srDv", sr[1]);
		}
		if(ir.length<2)
		{
			result.addObject("irAvg", 0);
			result.addObject("irDv", 0);
		}
		else
		{
			result.addObject("irAvg", ir[0]);
			result.addObject("irDv", ir[1]);
		}
		result.addObject("userPop", up);
		result.addObject("userlikes", ul);
		
		result.addObject("sponsors", sponsors);
		if(campaign.length<3)
		{
			result.addObject("cpsMin", 0);
			result.addObject("cpsAvg", 0);
			result.addObject("cpsMax", 0);
		}
		else
		{
			result.addObject("cpsMin", campaign[0]);
			result.addObject("cpsAvg", campaign[1]);
			result.addObject("cpsMax", campaign[2]);
		}
		if(active.length<3)
		{
			result.addObject("acpsMin", 0);
			result.addObject("acpsAvg", 0);
			result.addObject("acpsMax", 0);
		}
		else
		{
			result.addObject("acpsMin", active[0]);
			result.addObject("acpsAvg", active[1]);
			result.addObject("acpsMax", active[2]);
		}
		ranking = sponsorService.findRankingCompaniesNumberOfCampaigns();
		rankingBill = sponsorService.findRankingCompaniesMonthlyBill();

		result.addObject("ranking", ranking);
		result.addObject("rankingBill", rankingBill);
		if(ranking.size()==0)
		{
			result.addObject("rankinglength", ranking.size());
		}
		else
		{
			result.addObject("rankinglength", ranking.size() - 1);
		}
		if(rankingBill.size()==0)
		{
			result.addObject("rankingBilllenght", rankingBill.size());
		}
		else
		{
			result.addObject("rankingBilllenght", rankingBill.size() - 1);
		}
		
		Object[] bill = billService.calculateAvgStd().toArray();
		if(bill.length<2)
		{
			Object[] aux={0,0};
			result.addObject("billPaid", aux);
			result.addObject("billUnpaid", aux);
		}
		else
		{
			result.addObject("billPaid", bill[0]);
			result.addObject("billUnpaid", bill[1]);
		}
		Collection<Sponsor> sponsorAvg = sponsorService
				.findCompaniesHaveSpentLessAvgInCampaigns();
		result.addObject("sponsorAvg", sponsorAvg);

		Collection<Sponsor> sponsorSpend = sponsorService
				.findCompanyNameSpentAtLeastNinetyPerCentMaxAmountCompanyOnCampaign();
		result.addObject("sponsorSpend", sponsorSpend);
		
		result=consultaCalculateMinMaxStddevNumberOfMasterClassPerCook(result);
		
		result.addObject("numMCPromoted",masterclassService.calculateNumberMasterClassPromoted());
		
		LinkedList<Object []> res1=new LinkedList<Object []>();
		res1.addAll(learningMaterialService.calculateAvgFromMClass());
		Collection<Double> cd1= new LinkedList<Double>();
		Collection<String> cd2= new LinkedList<String>();
		
		for(Object[] o:res1){
			cd1.add((Double) o[0]);
			cd2.add((((LearningMaterial) o[1]).getClass()+"").substring(13));
		}
		
		result.addObject("consulta21", cd1);
		result.addObject("consulta22", cd2);
		
		result=consultacalculateAvgPromotedDemotedMasterClassPerCook(result);
		
		result=findCooksSortedAccordingToNumberOfPromotedMasterClasses(result);

		return result;
	}
	
	
	private ModelAndView findCooksSortedAccordingToNumberOfPromotedMasterClasses(ModelAndView result) {
		LinkedList<Object []> res=new LinkedList<Object []>();
		res.addAll(cookService.findCooksSortedAccordingToNumberOfPromotedMasterClasses());
		
		LinkedList<String> nombre= new LinkedList<String>();
		LinkedList<String> apellido= new LinkedList<String>();
		LinkedList<Long> num= new LinkedList<Long>();
		
		for(Object[] o:res){
			nombre.add((String) o[0]);
			apellido.add((String) o[1]);
			num.add((Long) o[2]);

		}
		result.addObject("consulta41", nombre);
		result.addObject("consulta42", apellido);
		result.addObject("consulta43", num);
		
		return result;
	}

	// Auxiliar methods -----------------------------------------------------
	private ModelAndView consultacalculateAvgPromotedDemotedMasterClassPerCook(ModelAndView result){
		LinkedList<Object []> res=new LinkedList<Object []>();
		res.addAll(masterclassService.calculateAvgPromotedDemotedMasterClassPerCook());

		LinkedList<Double> avg= new LinkedList<Double>();
		LinkedList<String> nombre= new LinkedList<String>();
		
		for(Object[] o:res){
			avg.add((Double) o[0]);
			nombre.add((String) o[1]);

		}
		result.addObject("consulta51", avg);
		result.addObject("consulta52", nombre);
		
		return result;
	}
	
	private ModelAndView consultaCalculateMinMaxStddevNumberOfMasterClassPerCook(ModelAndView result){
		LinkedList<Object []> res=new LinkedList<Object []>();
		res.addAll(masterclassService.calculateMinMaxStddevNumberOfMasterClassPerCook());
		LinkedList<Integer> min= new LinkedList<Integer>();
		LinkedList<Integer> max= new LinkedList<Integer>();
		LinkedList<Double> avg= new LinkedList<Double>();
		LinkedList<Double> stdv= new LinkedList<Double>();
		LinkedList<String> nombre= new LinkedList<String>();
		
		for(Object[] o:res){
			min.add((Integer) o[0]);
			max.add((Integer) o[1]);
			avg.add((Double) o[2]);
			stdv.add((Double) o[3]);
			nombre.add((String) o[4]);

		}
		result.addObject("consulta11", min);
		result.addObject("consulta12", max);
		result.addObject("consulta13", avg);
		result.addObject("consulta14", stdv);
		result.addObject("consulta15", nombre);
		
		return result;
	}

}

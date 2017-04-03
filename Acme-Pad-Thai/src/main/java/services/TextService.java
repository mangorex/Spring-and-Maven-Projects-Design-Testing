package services;

import java.util.HashSet;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TextRepository;
import domain.LearningMaterial;
import domain.MasterClass;
import domain.Text;

@Service
@Transactional
public class TextService{
	// Managed repository -------------------------------
	@Autowired
	private TextRepository textRepository;
	@Autowired
	private ActorService actorService;
	@Autowired
	private MasterClassService masterClassService;

	// Constructor --------------------------------------

	public TextService() {
		super();
	}

	// Simple CRUD methods ------------------------------
	public Text create() {
		Text t = new Text();
		t.setMasterClasses(new HashSet<MasterClass>());
		t.setAttachments(new HashSet<String>());
		return t;
	}

	public Text create(String body) {
		Assert.notNull(body);
		Text t = new Text();
		Collection<MasterClass> cmc = new HashSet<MasterClass>();
		t.setBody(body);
		t.setMasterClasses(cmc);
		t.setAttachments(new HashSet<String>());
		return t;
	}

	public Text create(MasterClass mc, String body) {
		Assert.notNull(mc);
		Assert.notNull(body);
		Text t = new Text();
		Collection<MasterClass> cmc = new HashSet<MasterClass>();
		cmc.add(mc);
		t.setBody(body);
		t.setMasterClasses(cmc);
		checkPrincipal(t);
		t.setAttachments(new HashSet<String>());
		return t;
	}

	public Text save(Text tt) {
		Assert.notNull(tt);
		if(!tt.getAttachments().isEmpty())
		{
			for(String s:tt.getAttachments())
			{
				Assert.isTrue(isUrl(s));
			}
		}
		Text t=textRepository.save(tt);
		
		
		return t;
	}

	public Text save2(Text tt) {
		Assert.notNull(tt);
		if(!tt.getAttachments().isEmpty())
		{
			for(String s:tt.getAttachments())
			{
				Assert.isTrue(isUrl(s));
			}
		}
		Text t=textRepository.save(tt);
		
		Collection<LearningMaterial> lm = new HashSet<LearningMaterial>();
		
		if(t.getId()!=0)
		{
			if(textRepository.findOne(t.getId()).getMasterClasses().size()>t.getMasterClasses().size()){
				Collection<MasterClass> cmcDB=textRepository.findOne(t.getId()).getMasterClasses();
				cmcDB.removeAll(t.getMasterClasses());
				if(cmcDB.size()!=0)
				{
					Collection<LearningMaterial> clm=new HashSet<LearningMaterial>();
					for(MasterClass mcDB:cmcDB)
					{
						clm=mcDB.getLearningMaterials();
						clm.remove(t);
						mcDB.setLearningMaterials(clm);
						masterClassService.save(mcDB);
					}
				}
			}
		}
		if(t.getMasterClasses().size()!=0){
			for(MasterClass a: t.getMasterClasses()){
				lm=a.getLearningMaterials();
				if(!lm.contains(t))
				{
					lm.add(t);
					a.setLearningMaterials(lm);
				}
				masterClassService.save(a);
			}
		}
		return t;
	}

	public void delete(Text t) {
		Collection<LearningMaterial> lm = new HashSet<LearningMaterial>();
		if (t.getMasterClasses().size() != 0) {
			for (MasterClass a : t.getMasterClasses()) {
				lm = a.getLearningMaterials();
				lm.remove(t);
				a.setLearningMaterials(lm);
				masterClassService.save(a);
			}
		}
		textRepository.delete(t);
	}

	public Text findOneText(int t) {
		Assert.notNull(t);
		Text result=textRepository.findOne(t);
		return result;
	}
	
	public Collection<Text> findAll(){
		return textRepository.findAll();
	}
	
	public void checkPrincipal(Text t) {
		if(!t.getMasterClasses().isEmpty())
		{
			MasterClass mc=(MasterClass)t.getMasterClasses().toArray()[0];
			Assert.notNull(mc.getCook());
			Assert.isTrue(mc.getCook().equals(actorService.findByPrincipal()));
		}
	}
	
	private static boolean isUrl(String s) {
	    String regex = "^(https?://)?(([\\w!~*'().&=+$%-]+: )?[\\w!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([\\w!~*'()-]+\\.)*([\\w^-][\\w-]{0,61})?[\\w]\\.[a-z]{2,6})(:[0-9]{1,4})?((/*)|(/+[\\w!~*'().;?:@&=+$,%#-]+)+/*)$";
	 
	    try {
	        Pattern patt = Pattern.compile(regex);
	        Matcher matcher = patt.matcher(s);
	        return matcher.matches();
	 
	    } catch (RuntimeException e) {
	        return false;
	    }
	}
}

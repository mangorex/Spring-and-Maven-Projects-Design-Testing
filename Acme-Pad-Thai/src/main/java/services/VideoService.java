package services;

import java.util.HashSet;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.VideoRepository;
import domain.LearningMaterial;
import domain.MasterClass;
import domain.Video;

@Service
@Transactional
public class VideoService{
	// Managed repository -------------------------------
	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private ActorService actorService;
	@Autowired
	private MasterClassService masterClassService;
	
	// Constructor --------------------------------------
	
	public VideoService()
	{
		super();
	}
	
	// Simple CRUD methods ------------------------------
	public Video create(){
		Video v=new Video();
		v.setMasterClasses(new HashSet<MasterClass>());
		v.setAttachments(new HashSet<String>());
		return v;
	}
	
	public Video create(String url){
		Assert.notNull(url);
		Video v = new Video();
		Collection<MasterClass> cmc=new HashSet<MasterClass>();
		v.setIdentifier(url);
		v.setMasterClasses(cmc);
		v.setAttachments(new HashSet<String>());
		return v;
	}
	
	public Video create(MasterClass mc,String url){
		Assert.notNull(mc);
		Assert.notNull(url);
		Video v = new Video();
		Collection<MasterClass> cmc=new HashSet<MasterClass>();
		cmc.add(mc);
		v.setIdentifier(url);
		v.setMasterClasses(cmc);
		checkPrincipal(v);
		v.setAttachments(new HashSet<String>());
		Collection<LearningMaterial> clm=mc.getLearningMaterials();
		clm.add(v);
		mc.setLearningMaterials(clm);
		return v;
	}
	
	public Video save(Video vv){
		Assert.notNull(vv);
		if(!vv.getAttachments().isEmpty())
		{
			for(String s:vv.getAttachments())
			{
				Assert.isTrue(isUrl(s));
			}
		}
		Video v=videoRepository.save(vv);
		return v;
	}
	
	public Video save2(Video vv){
		Assert.notNull(vv);
		if(!vv.getAttachments().isEmpty())
		{
			for(String s:vv.getAttachments())
			{
				Assert.isTrue(isUrl(s));
			}
		}
		Video v=videoRepository.save(vv);
		Collection<LearningMaterial> lm = new HashSet<LearningMaterial>();
		if(v.getId()!=0)
		{
			if(videoRepository.findOne(v.getId()).getMasterClasses().size()>v.getMasterClasses().size()){
				Collection<MasterClass> cmcDB=videoRepository.findOne(v.getId()).getMasterClasses();
				cmcDB.removeAll(v.getMasterClasses());
				if(cmcDB.size()!=0)
				{
					Collection<LearningMaterial> clm=new HashSet<LearningMaterial>();
					for(MasterClass mcDB:cmcDB)
					{
						clm=mcDB.getLearningMaterials();
						clm.remove(v);
						mcDB.setLearningMaterials(clm);
						masterClassService.save(mcDB);
					}
				}
			}
		}
		if(v.getMasterClasses().size()!=0){
			for(MasterClass a: v.getMasterClasses()){
				lm=a.getLearningMaterials();
				if(!lm.contains(v))
				{
					lm.add(v);
					a.setLearningMaterials(lm);
				}
				masterClassService.save(a);
			}
		}
		return v;
	}
	
	public void delete(Video v){
		Collection<LearningMaterial> lm = new HashSet<LearningMaterial>();
		if(v.getMasterClasses().size()!=0){
			for(MasterClass a: v.getMasterClasses()){
				lm=a.getLearningMaterials();
				lm.remove(v);
				a.setLearningMaterials(lm);
				masterClassService.save(a);
			}
		}
		videoRepository.delete(v);
	}
	
	public Video findOneVideo(int v){
		Assert.notNull(v);
		Video result=videoRepository.findOne(v);
		return result;
	}
	
	public Collection<Video> findAll(){
		return videoRepository.findAll();
	}
	
	public void checkPrincipal(Video v) {
		if(!v.getMasterClasses().isEmpty())
		{
			MasterClass mc=(MasterClass)v.getMasterClasses().toArray()[0];
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

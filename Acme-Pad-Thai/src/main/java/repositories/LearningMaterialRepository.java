package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LearningMaterial;

@Repository
public interface LearningMaterialRepository extends JpaRepository<LearningMaterial, Integer> {
	@Query("select lm from LearningMaterial lm join lm.masterClasses mc where mc.id=?1")
	Collection<LearningMaterial> findAllLMByMClass(int mc);
	
	@Query("select avg(mc.learningMaterials.size),lm from MasterClass mc join mc.learningMaterials lm group by lm.class")
	Collection<Object[]> calculateAvgFromMClass();
}

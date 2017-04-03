package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

}

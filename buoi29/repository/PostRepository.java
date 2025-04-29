package vn.scrip.buoi29.repository;
import vn.scrip.buoi29.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
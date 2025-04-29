package vn.scrip.buoi29.repository;

import vn.scrip.buoi29.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Page<Review> findByMovie_Id(Integer movieId, Pageable pageable);
}
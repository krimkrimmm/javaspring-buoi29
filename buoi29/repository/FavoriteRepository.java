package vn.scrip.buoi29.repository;

import vn.scrip.buoi29.entity.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    boolean existsByUser_IdAndMovie_Id(Integer userId, Integer movieId);

    // Tìm favorite theo user và movie
    Optional<Favorite> findByUser_IdAndMovie_Id(Integer userId, Integer movieId);

    // Tìm tất cả favorites của 1 user, có phân trang
    Page<Favorite> findByUser_Id(Integer userId, Pageable pageable);

    // Hoặc nếu muốn xóa hàng loạt, có thể dùng
    @Transactional
    void deleteAllByUser_Id(Integer userId);

    void deleteByUser_IdAndMovie_Id(Integer userId, Integer movieId);
}
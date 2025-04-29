package vn.scrip.buoi29.repository;

import vn.scrip.buoi29.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
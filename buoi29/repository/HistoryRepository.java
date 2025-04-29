package vn.scrip.buoi29.repository;

import vn.scrip.buoi29.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
public interface HistoryRepository extends JpaRepository<History, Integer> {
}
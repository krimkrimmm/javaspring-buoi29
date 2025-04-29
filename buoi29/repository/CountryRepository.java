package vn.scrip.buoi29.repository;

import vn.scrip.buoi29.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CountryRepository extends JpaRepository<Country, Integer> {
}
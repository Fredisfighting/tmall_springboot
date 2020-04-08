package tmall.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tmall.pojo.Category;
import tmall.pojo.Property;

public interface PropertyDAO extends JpaRepository<Property, Integer> {
	Page<Property> findByCategory(Category category,Pageable pageable);
}
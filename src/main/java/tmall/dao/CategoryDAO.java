package tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tmall.pojo.Category;

public interface CategoryDAO extends 
	JpaRepository<Category, Integer> {

}

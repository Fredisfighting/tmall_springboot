package tmall.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tmall.pojo.Category;
import tmall.pojo.Property;
import tmall.pojo.PropertyValue;
import tmall.pojo.Product;

public interface PropertyValueDAO extends JpaRepository<PropertyValue, Integer> {
    List<PropertyValue> findByProductOrderByIdDesc(Product product);
    PropertyValue getByPropertyAndProduct(Property property, Product product);
}
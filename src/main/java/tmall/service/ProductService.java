package tmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tmall.dao.ProductDAO;
import tmall.pojo.Category;
import tmall.pojo.Product;
import tmall.util.Page4Navigator;

@Service
public class ProductService {

	@Autowired ProductDAO productDAO;
	@Autowired CategoryService categoryService;
	
	public void add(Product bean) {
		productDAO.save(bean);
	}

	 public void delete(int id) {
		 productDAO.delete(id);
	 }
	 
	 public Product get(int id) {
		 return productDAO.findOne(id);
	 }
	 
	 public void update(Product bean) {
		 productDAO.save(bean);
	 }
	 public Page4Navigator<Product> list(int cid,int start,int size,int navigatePages){
		  Category category = categoryService.get(cid);
		  Sort sort = new Sort(Sort.Direction.DESC,"id");
		  Pageable pageable = new PageRequest(start, size, sort);
		  Page<Product> pageFromJPA = productDAO.findByCategory(category, pageable);
		  return new Page4Navigator<Product>(pageFromJPA,navigatePages);
	 }
}

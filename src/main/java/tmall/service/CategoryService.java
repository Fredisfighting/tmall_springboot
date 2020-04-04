package tmall.service;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import tmall.dao.CategoryDAO;
import tmall.pojo.Category;
import tmall.util.Page4Navigator;

//看不懂
//现在看懂了
@Service
public class CategoryService {

	@Autowired CategoryDAO categoryDAO;

	public Page4Navigator<Category> list(int start,int size,int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC,"id");
		PageRequest pageable =new PageRequest(start,size,sort);
		Page pageFromJPA = categoryDAO.findAll(pageable);
		
		return new Page4Navigator<>(pageFromJPA,navigatePages);
	}

	public List<Category> list(){
		Sort sort = new Sort(Sort.Direction.DESC,"id");
		return categoryDAO.findAll(sort);
	}
	
	public void add(Category bean) {
		categoryDAO.save(bean);
	}
	
	public void delete(int id) {
		categoryDAO.delete(id);
	}
	
	public Category get(int id) {
		Category c = categoryDAO.findOne(id);
		return c;
	}
	
	public void update(Category bean) {
		categoryDAO.save(bean);
	}
}

package tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tmall.dao.ReviewDAO;
import tmall.pojo.Product;
import tmall.pojo.Review;

@Service
public class ReviewService {
	@Autowired ReviewDAO reviewDAO;
	@Autowired ProductService productService;
	
	public void add(Review review) {
		reviewDAO.save(review);
	}
	
	public List<Review> list(Product product){
		return reviewDAO.findByProductOrderByIdDesc(product);
	}

	public int getCount(Product product) {
		return reviewDAO.countByProduct(product);
	}
}
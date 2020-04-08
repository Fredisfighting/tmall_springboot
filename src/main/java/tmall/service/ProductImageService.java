package tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tmall.dao.ProductImageDAO;
import tmall.pojo.Product;
import tmall.pojo.ProductImage;

@Service
public class ProductImageService {
	@Autowired ProductImageDAO productImageDAO;
	@Autowired ProductService productService;
	
	public static final String type_single = "single";
	public static final String type_detail = "detail";
	private static final int Product = 0;
	
	public void add(ProductImage bean) {
		productImageDAO.save(bean);
	}
	
	public void delete(int id) {
		productImageDAO.delete(id);
	}
	
	public ProductImage get(int id) {
		return productImageDAO.findOne(id);
	}
	
	public List<ProductImage> listSingleProductImages(Product product){
		return productImageDAO.findByProductAndTypeOrderByIdDesc(product, type_single);
	}
	
	public List<ProductImage> listDetailProductImages(Product product){
		return productImageDAO.findByProductAndTypeOrderByIdDesc(product, type_detail);
	}
	
	public void setFirstProductImage(Product product) {
		List<ProductImage> singleImages = listSingleProductImages(product);
		if(!singleImages.isEmpty()) {
			product.setFirstProductImage(singleImages.get(0));
		}
		else {
			product.setFirstProductImage(new ProductImage());
		}
	}
	
	public void setFirstProductImages(List<Product> products) {
		for(Product product:products) {
			setFirstProductImage(product);
		}
	}
}

package tmall.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tmall.pojo.Product;
import tmall.service.CategoryService;
import tmall.service.ProductImageService;
import tmall.service.ProductService;
import tmall.util.Page4Navigator;

@RestController
public class ProductController {
	@Autowired ProductService productService;
    @Autowired CategoryService categoryService;
    @Autowired ProductImageService productImageService;
	
	@GetMapping("/categories/{cid}/products")
	public Page4Navigator<Product> list(@PathVariable("cid") int cid,
			@RequestParam(value="start",defaultValue = "0") int start,
			@RequestParam(value="size",defaultValue="5") int size) throws Exception{
			start = start<0?0:start;
			Page4Navigator<Product> page = productService.list(cid, start, size, 5);
			
			//性能可能较低，后续优化
			productImageService.setFirstProductImages(page.getContent());
			
			return page;
	}
	
    @GetMapping("/products/{id}")
    public Product get(@PathVariable("id") int id) throws Exception {
    	Product bean=productService.get(id);
    	return bean;
    }
    
    @PostMapping("/products")
    public Object add(@RequestBody Product bean) throws Exception {
        productService.add(bean);
        return bean;
    }
    
    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        productService.delete(id);
        return null;
    }
    
    @PutMapping("/products")
    public Object update(@RequestBody Product bean) throws Exception {
        productService.update(bean);
        return bean;
    }
}
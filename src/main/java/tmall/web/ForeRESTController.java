package tmall.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import tmall.comparator.ProductAllComparator;
import tmall.comparator.ProductDateComparator;
import tmall.comparator.ProductPriceComparator;
import tmall.comparator.ProductReviewComparator;
import tmall.comparator.ProductSaleCountComparator;
import tmall.pojo.Category;
import tmall.pojo.OrderItem;
import tmall.pojo.Product;
import tmall.pojo.ProductImage;
import tmall.pojo.PropertyValue;
import tmall.pojo.Review;
import tmall.pojo.User;
import tmall.service.CategoryService;
import tmall.service.OrderItemService;
import tmall.service.ProductImageService;
import tmall.service.ProductService;
import tmall.service.PropertyService;
import tmall.service.PropertyValueService;
import tmall.service.ReviewService;
import tmall.service.UserService;
import tmall.util.Result;

@RestController
public class ForeRESTController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired 
    PropertyService propertyService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    OrderItemService orderItemService;
    
    @GetMapping("/forehome")
    public Object home() {
        List<Category> cs= categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
        categoryService.removeCategoryFromProduct(cs);
        return cs;
    } 
    
    @PostMapping("/foreregister")
    public Object register(@RequestBody User user) {
        String name =  user.getName();
        String password = user.getPassword();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);
             
        if(exist){
            String message ="用户名已经被使用,不能使用";
            return Result.fail(message);
        }
         
        user.setPassword(password);
        userService.add(user);
        return Result.success();
    }   
    
    @PostMapping("/forelogin")
    public Object login(@RequestBody User userParam,HttpSession session) {
    	String name = userParam.getName();
    	name = HtmlUtils.htmlEscape(name);
    	
    	User user = userService.get(name, userParam.getPassword());
    	if(null==user) {
    		String message = "账号密码错误";
    		return Result.fail(message);
    	}
    	else {
    		session.setAttribute("user", user);
    		return Result.success();
    	}
    }
    
    @GetMapping("/foreproduct/{pid}")
    public Object product(@PathVariable("pid") int pid) {
    	Product product = productService.get(pid);

    	productImageService.setFirstProductImage(product);
    	productService.setSaleAndReviewNumber(product);

    	List<ProductImage> productSingleImages = productImageService.listSingleProductImages(product);
    	List<ProductImage> productDetailImages = productImageService.listDetailProductImages(product);
    	product.setProductSingleImages(productSingleImages);
    	product.setProductDetailImages(productDetailImages);
    	
    	List<PropertyValue> pvs = propertyValueService.list(product);
    	List<Review> reviews = reviewService.list(product);
    	
    	Map<String,Object> map = new HashMap<>();
    	map.put("product", product);
    	map.put("pvs", pvs);
    	map.put("reviews", reviews);
    	
    	return Result.success(map);
    }
    
    @GetMapping("/forecheckLogin")
    public Object checkLogin(HttpSession session) {
    	if(null != session.getAttribute("user")) {
    		return Result.success();
    	}
    	else
    		return Result.fail("未登录");
    }
    
    @GetMapping("forecategory/{cid}")
    public Object category(@PathVariable int cid,String sort) {
    	Category c = categoryService.get(cid);
    	productService.fill(c);
    	productService.setSaleAndReviewNuber(c.getProducts());
    	categoryService.removeCategoryFromProduct(c);
    	
    	if(null!=sort) {
    		switch(sort) {
    		case "review":
    			Collections.sort(c.getProducts(), new ProductReviewComparator());
    			break;
            case "date" :
                Collections.sort(c.getProducts(),new ProductDateComparator());
                break;
 
            case "saleCount" :
                Collections.sort(c.getProducts(),new ProductSaleCountComparator());
                break;
 
            case "price":
                Collections.sort(c.getProducts(),new ProductPriceComparator());
                break;
 
            case "all":
                Collections.sort(c.getProducts(),new ProductAllComparator());
                break;
                
            default:
            	Collections.sort(c.getProducts(),new ProductAllComparator());
    		}
    	}
    	return c;
    }
    
    @PostMapping("foresearch")
    public Object search(String keyword) {
    	if(null==keyword)
    		keyword="";
    	List<Product> products = productService.search(keyword, 0, 20);
    	productImageService.setFirstProductImages(products);
    	productService.setSaleAndReviewNuber(products);
    	return products;
    }
    
    @GetMapping("forebuyone")
    public Object buyone(int pid, int num, HttpSession session) {
        return buyoneAndAddCart(pid,num,session);
    }
    private int buyoneAndAddCart(int pid, int num, HttpSession session) {
        Product product = productService.get(pid);
        int oiid = 0;
 
        User user =(User)  session.getAttribute("user");
        boolean found = false;
        List<OrderItem> ois = orderItemService.listByUser(user);
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId()==product.getId()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                oiid = oi.getId();
                break;
            }
        }
 
        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setProduct(product);
            oi.setNumber(num);
            orderItemService.add(oi);
            oiid = oi.getId();
        }
        return oiid;
    }
    
    @GetMapping("forebuy")
    public Object buy(String[] oiid,HttpSession session){
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;

        for (String strid : oiid) {
            int id = Integer.parseInt(strid);
            OrderItem oi= orderItemService.get(id);
            total +=oi.getProduct().getPromotePrice()*oi.getNumber();
            orderItems.add(oi);
        }

        productImageService.setFirstProdutImagesOnOrderItems(orderItems);

        session.setAttribute("ois", orderItems);

        Map<String,Object> map = new HashMap<>();
        map.put("orderItems", orderItems);
        map.put("total", total);
        return Result.success(map);
    }
    
    @GetMapping("foreaddCart")
    public Object addCart(int pid,int num,HttpSession session) {
    	buyoneAndAddCart(pid, num, session);
    	return Result.success();
    }
    
    @GetMapping("forecart")
    public Object cart(HttpSession session) {
    	User user = (User) session.getAttribute("user");
    	List<OrderItem> ois = orderItemService.listByUser(user);
    	productImageService.setFirstProdutImagesOnOrderItems(ois);
    	return ois;
    }
}
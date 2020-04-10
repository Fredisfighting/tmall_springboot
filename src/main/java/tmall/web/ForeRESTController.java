package tmall.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import tmall.pojo.Category;
import tmall.pojo.User;
import tmall.service.CategoryService;
import tmall.service.ProductService;
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
            String message ="�û����Ѿ���ʹ��,����ʹ��";
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
    		String message = "�˺��������";
    		return Result.fail(message);
    	}
    	else {
    		session.setAttribute("user", user);
    		return Result.success();
    	}
    }
}
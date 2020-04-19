package tmall.web;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
 
@Controller
//此Controller为前端控制器，一个html页面一个Controller
//所有返回值均为html页面地址或跳转地址
public class AdminPageController {
    @GetMapping(value="/admin")
    public String admin(){
        return "redirect:admin_category_list";
    }
 
    @GetMapping(value="/admin_category_list")
    public String listCategory(){
    	//注解：此处返回值意思是html文件地址
        return "admin/listCategory";
    }
 
    @GetMapping(value="/admin_category_edit")
    public String editCategory(){
        return "admin/editCategory";
 
    }
 
    @GetMapping(value="/admin_order_list")
    public String listOrder(){
        return "admin/listOrder";
 
    }
 
    @GetMapping(value="/admin_product_list")
    public String listProduct(){
        return "admin/listProduct";
 
    }
 
    @GetMapping(value="/admin_product_edit")
    public String editProduct(){
        return "admin/editProduct";
 
    }
    @GetMapping(value="/admin_productImage_list")
    public String listProductImage(){
        return "admin/listProductImage";
 
    }
 
    @GetMapping(value="/admin_property_list")
    public String listProperty(){
        return "admin/listProperty";
 
    }
 
    @GetMapping(value="/admin_property_edit")
    public String editProperty(){
        return "admin/editProperty";
 
    }
 
    @GetMapping(value="/admin_propertyValue_edit")
    public String editPropertyValue(){
        return "admin/editPropertyValue";
 
    }
 
    @GetMapping(value="/admin_user_list")
    public String listUser(){
        return "admin/listUser";
 
    }
 
}
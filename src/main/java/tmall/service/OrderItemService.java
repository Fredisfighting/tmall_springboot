package tmall.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tmall.dao.OrderItemDAO;
import tmall.pojo.Order;
import tmall.pojo.OrderItem;
import tmall.pojo.Product;
import tmall.pojo.User;

@Service
public class OrderItemService {
   @Autowired OrderItemDAO orderItemDAO;
   @Autowired ProductImageService productImageService;

   public OrderItem get(int id) {
	   return orderItemDAO.findOne(id);
   }
   public void add(OrderItem oi) {
	   orderItemDAO.save(oi);
   }
   
   public void update(OrderItem oi) {
	   orderItemDAO.save(oi);
   }
   
   public void delete(int id) {
	   orderItemDAO.delete(id);
   }
   
   public void fill(List<Order> orders) {
       for (Order order : orders)
           fill(order);
   }

   public void fill(Order order) {
       List<OrderItem> orderItems = listByOrder(order);
       float total = 0;
       int totalNumber = 0;           
       for (OrderItem oi :orderItems) {
           total+=oi.getNumber()*oi.getProduct().getPromotePrice();
           totalNumber+=oi.getNumber();
           productImageService.setFirstProductImage(oi.getProduct());
       }
       order.setTotal(total);
       order.setOrderItems(orderItems);
       order.setTotalNumber(totalNumber);     
   }
    
   public List<OrderItem> listByOrder(Order order) {
       return orderItemDAO.findByOrderOrderByIdDesc(order);
   }
    
   public List<OrderItem> listByProduct(Product product){
	   return orderItemDAO.findByProduct(product);
   }
   
   public int getSaleCount(Product product) {
	   List<OrderItem> items = listByProduct(product);
	   int result = 0;
	   for(OrderItem item:items) {
		   if(null != item.getOrder()) {
			   if(null != item.getOrder().getPayDate()) {
				   result += item.getNumber();
			   }
		   }
	   }
	   return result;
   }
   
   public List<OrderItem> listByUser(User user){
	   return orderItemDAO.findByUserAndOrderIsNull(user);
   }
}
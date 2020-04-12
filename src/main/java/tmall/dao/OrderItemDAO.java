package tmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tmall.pojo.OrderItem;
import tmall.pojo.Product;
import tmall.pojo.User;
import tmall.pojo.Order;

public interface OrderItemDAO extends JpaRepository<OrderItem,Integer>{
    List<OrderItem> findByOrderOrderByIdDesc(Order order);
    List<OrderItem> findByProduct(Product product);
    List<OrderItem> findByUserAndOrderIsNull(User user);
}
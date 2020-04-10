package tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tmall.pojo.Order;

public interface OrderDAO extends JpaRepository<Order,Integer>{
}

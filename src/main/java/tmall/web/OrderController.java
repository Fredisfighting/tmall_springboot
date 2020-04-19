package tmall.web;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tmall.pojo.Order;
import tmall.service.OrderItemService;
import tmall.service.OrderService;
import tmall.util.Page4Navigator;
import tmall.util.Result;

@RestController
public class OrderController {
	@Autowired OrderService orderService;
	@Autowired OrderItemService orderItemService;

	@GetMapping("/orders")
	public Page4Navigator<Order> list(
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "5") int size
			){
		start = start<0?0:start;
		Page4Navigator<Order> page = orderService.list(start,size,5);
		orderItemService.fill(page.getContent());
		orderService.removeOrderFromOrderItem(page.getContent());
		return page;
	}
	
	@PutMapping("deliveryOrder/{oid}")
	public Object deliveryOrder(@PathVariable int oid)
		throws IOException{
		Order order = orderService.get(oid);
		order.setDeliveryDate(new Date());
		order.setStatus(orderService.waitConfirm);
		orderService.update(order);
		return Result.success();
	}
}

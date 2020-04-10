package tmall.service;

import tmall.dao.UserDAO;
import tmall.pojo.User;
import tmall.util.Page4Navigator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	UserDAO userDAO;
	
	public Page4Navigator<User> list(int start,int size,int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC,"id");
		Pageable pageable = new PageRequest(start,size,sort);
		Page pageFromJPA = userDAO.findAll(pageable);
		return new Page4Navigator<User>(pageFromJPA, navigatePages);
	}
	
	public boolean isExist(String name) {
		User foreUser = userDAO.findByName(name);
		return foreUser != null;
	}
	
	public User getByName(String name) {
		return userDAO.findByName(name);
	}
	
	public void add(User user) {
		userDAO.save(user);
	}
	
	public User get(String name,String password) {
		return userDAO.getByNameAndPassword(name, password);
	}
}

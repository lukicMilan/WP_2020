package dao;
import java.util.HashMap;
import java.util.Map;

import model.User;
import model.UserAdministrator;
import model.UserGender;
import model.UserType;

public class UserDAO {
	private String contextPath;
	private Map<String, User> users = new HashMap<>();
	
	public UserDAO(String contextPath) {
		User admin = new UserAdministrator("admin", "admin", "Milan", "Lukic", UserGender.MALE, UserType.ADMINISTRATOR);
		users.put(admin.getUsername(), admin);
	}
	
	public User getUserByUsername(String username) {
		return this.users.get(username);
	}
}

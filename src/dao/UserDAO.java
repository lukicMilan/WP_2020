package dao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import model.Apartment;
import model.Reservation;
import model.User;
import model.UserAdministrator;
import model.UserGender;
import model.UserGuest;
import model.UserHost;
import model.UserType;

public class UserDAO {
	private String contextPath;
	private Map<String, User> users = new HashMap<>();
	
	public UserDAO(String contextPath) {
		this.contextPath = contextPath;
		User admin = new UserAdministrator("admin", "admin", "Milan", "Lukic", UserGender.MALE, UserType.ADMINISTRATOR);
		UserHost host = new UserHost("host", "host", "Ana-Marija", "Buhmiler", UserGender.FEMALE, UserType.HOST, new ArrayList<Apartment>());
		UserGuest guest = new UserGuest("guest", "guest", "Vladimir", "Popovic", UserGender.MALE, UserType.GUEST, new ArrayList<Apartment>(), new ArrayList<Reservation>());
		users.put(admin.getUsername(), admin);
		users.put(host.getUsername(), host);
		users.put(guest.getUsername(), guest);
		saveUsers();
		
		loadUsers();
	}
	
	public User getUserByUsername(String username) {
		return this.users.get(username);
	}
	
	public Map<String, User> getAllUsers() {
		return this.users;
	}
	
	public boolean credentialOk(String username, String password) {
		User user = getUserByUsername(username);
		if(user != null && user.getPassword() != password) {
			return false;
		}
		return true;
	}
	
	public void saveUsers() {
		ObjectMapper objectMapper = new ObjectMapper();
		File file = null;
		FileWriter fileWriter = null;
		try {
			file = new File(contextPath + "//users.json");
			fileWriter = new FileWriter(file);
			
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			String outputString = objectMapper.writeValueAsString(users);

			fileWriter.write(outputString);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fileWriter != null) {
				try {
					fileWriter.close();
					System.out.println("Map USERS saved.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, User> loadUsers() {
		Map<String, User> users = null;
		
		ObjectMapper objectMapper = new ObjectMapper();
		File file = null;
		
		try {
			file = new File(contextPath + "//users.json");
			
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
           
			this.users = objectMapper.readValue(file, new TypeReference<HashMap<String, User>>() {});
			System.out.println("Baja ucitao sledece:");
			System.out.println(this.users.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return users;
	}
}

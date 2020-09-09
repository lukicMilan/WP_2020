package dao;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import dto.UserDTO;
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
		
		//loadUsers();
		saveUsers();
	}
	
	public User getUserByUsername(String username) {
		return this.users.get(username);
	}
	
	public Map<String, User> getAllUsers() {
		return this.users;
	}
	
	public ArrayList<UserDTO> getAllUsersDTO() {
		ArrayList<UserDTO> users = new ArrayList<>();
		
		loadUsers();
		for (Map.Entry<String, User> userEntry : this.users.entrySet()) {
			users.add(new UserDTO(userEntry.getValue()));
		}
		
		return users;
	}
	
	public boolean usernameExists(String username) {
		return users.containsKey(username);
	}
	
	public boolean changeUsername(String oldUsername, String newUsername) {
		if(users.containsKey(newUsername)) {
			return false;
		}
		
		
		User user = users.get(oldUsername);
		user.setUsername(newUsername);
		users.put(newUsername, user);
		
		users.remove(oldUsername);
		System.out.println("Username changed from " + oldUsername + " to "+ newUsername);
		saveUsers();
		
		return true;
	}
	
	public boolean changeUserDetails(UserDTO userDTO) {
		if(!users.containsKey(userDTO.getUsername())) {
			return false;
		}
		
		users.put(userDTO.getUsername(), UserDTO.toUser(userDTO));
		System.out.println("User " + userDTO.getUsername() + " updated profile info.");
		saveUsers();
		
		return true;
	}
	
	public boolean addUser(UserDTO userDTO) {
		
		if (users.containsKey(userDTO.getUsername())) {
			return false;
		}
		System.out.println(userDTO.getUserType());
//		userDTO.setUserType(UserType.GUEST.toString());
		users.put(userDTO.getUsername(), UserDTO.toUser(userDTO));

		System.out.println("User " + userDTO.getUsername() + " added");
		saveUsers();
		
		return true;
	}
	
	public boolean removeUser(String username) {
		if(!users.containsKey(username)) {
			return false;
		}
		
		users.remove(username);
		System.out.println("User " + username + " removed");
		saveUsers();
		
		return true;
	}
	
	public boolean credentialOk(String username, String password) {
		User user = getUserByUsername(username);
	
		if(user == null || !user.getPassword().equals(password)) {
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
	
	public Map<String, User> loadUsers() {
		Map<String, User> users = new HashMap<String, User>();
		
		ObjectMapper objectMapper = new ObjectMapper();
		File file = null;
		
		try {
			file = new File(contextPath + "//users.json");
			
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
           
			this.users = objectMapper.readValue(file, new TypeReference<HashMap<String, User>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return users;
	}
}

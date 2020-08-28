package dto;

import java.util.ArrayList;

import model.User;
import model.UserAdministrator;
import model.UserGender;
import model.UserGuest;
import model.UserHost;
import model.UserType;
import model.Apartment;
import model.Reservation;

public class UserDTO {
	private String username;
	private String password;
	private String name;
	private String surname;
	private String gender;
	private String userType;
	
	public UserDTO(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.gender = user.getGender().toString();
		this.userType = user.getUserType().toString();
	}
	
	public static User toUser(UserDTO userDTO) {
		switch (userDTO.userType) {
		case "ADMINISTRATOR": 
			return new UserAdministrator(userDTO.username, userDTO.password, userDTO.name, userDTO.surname, UserGender.valueOf(userDTO.gender), UserType.ADMINISTRATOR);
		case "HOST":
			return new UserHost(userDTO.username, userDTO.password, userDTO.name, userDTO.surname, UserGender.valueOf(userDTO.gender), UserType.HOST, new ArrayList<Apartment>());
		case "GUEST":
			return new UserGuest(userDTO.username, userDTO.password, userDTO.name, userDTO.surname, UserGender.valueOf(userDTO.gender), UserType.HOST, new ArrayList<Apartment>(), new ArrayList<Reservation>());
		default:
			return null;
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", password=" + password + ", name=" + name + ", surname=" + surname
				+ ", gender=" + gender + ", userType=" + userType + "]";
	}
	
	
}

package model;

import com.fasterxml.jackson.annotation.JsonTypeName;

//@JsonTypeName("administrator")
public class UserAdministrator extends User{

	public UserAdministrator() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserAdministrator(String username, String password, String name, String surname, UserGender userGender, UserType userType) {
		super(username, password, name, surname, userGender, userType);
	}

	@Override
	public String toString() {
		return "UserAdministrator [username=" + username + ", password=" + password + ", name=" + name + ", surname="
				+ surname + ", gender=" + gender + ", userType=" + userType + "]";
	}
	
	
}

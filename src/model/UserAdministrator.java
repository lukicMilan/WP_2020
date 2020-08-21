package model;

public class UserAdministrator extends User{

	public UserAdministrator() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserAdministrator(String username, String password, String name, String surname, UserGender userGender, UserType userType) {
		super(username, password, name, surname, userGender, userType);
	}
}

package model;

public abstract class User implements WorkaroundInterface{
	String username;
	String password;
	String name;
	String surname;
	UserGender gender;
	UserType userType;
	
	public User() {
		
	}
	
	public User(String username, String password, String name, String surname, UserGender userGender, UserType userType) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = userGender;
		this.userType = userType;
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
	public UserGender getGender() {
		return gender;
	}
	public void setGender(UserGender gender) {
		this.gender = gender;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", name=" + name + ", surname=" + surname
				+ ", gender=" + gender + ", userType=" + userType + "]";
	}
}

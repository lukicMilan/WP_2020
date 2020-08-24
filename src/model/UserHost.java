package model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonTypeName;

//@JsonTypeName("host")
public class UserHost extends User {
	private List<Apartment> apartments;

	public UserHost() {
		super();
		this.apartments = new ArrayList<Apartment>();
	}
	public UserHost(String username, String password, String name, String surname, UserGender userGender, UserType userType, List<Apartment> apartments) {
		super(username, password, name, surname, userGender, userType);
		this.apartments = apartments;
	}

	public UserHost(List<Apartment> apartments) {
		super();
		this.apartments = apartments;
	}

	public List<Apartment> getApartments() {
		return apartments;
	}

	public void setApartments(List<Apartment> apartments) {
		this.apartments = apartments;
	}
	
	public void addApartment(Apartment apartment) {
		this.apartments.add(apartment);
	}
	@Override
	public String toString() {
		return "UserHost [apartments=" + apartments + ", username=" + username + ", password=" + password + ", name="
				+ name + ", surname=" + surname + ", gender=" + gender + ", userType=" + userType + "]";
	}
	
	
}

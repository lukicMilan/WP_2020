package model;

import java.util.List;

public class UserHost extends User {
	List<Apartment> apartments;

	public UserHost() {
		super();
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

	@Override
	public String toString() {
		return "UserHost [apartments=" + apartments + "]";
	}
	
	
}

package model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonTypeName;

//@JsonTypeName("guest")
public class UserGuest extends User{
	List<Apartment> reservedApartments;
	List<Reservation> reservations;
	
	public UserGuest() {
		super();
		this.reservations = new ArrayList<Reservation>();
		this.reservedApartments = new ArrayList<Apartment>();
	}
	
	public UserGuest(String username, String password, String name, String surname, UserGender userGender, UserType userType, List<Apartment> reservedApartments, List<Reservation> reservations) {
		super(username, password, name, surname, userGender, userType);
		this.reservedApartments = reservedApartments;
		this.reservations = reservations;
	}
	
	public UserGuest(List<Apartment> reservedApartments, List<Reservation> reservations) {
		super();
		this.reservedApartments = reservedApartments;
		this.reservations = reservations;
	}

	public List<Apartment> getReservedApartments() {
		return reservedApartments;
	}

	public void setReservedApartments(List<Apartment> reservedApartments) {
		this.reservedApartments = reservedApartments;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	public void addReservation(Reservation reservation) { 
		this.reservations.add(reservation);
	}
	
	public void addReservedApartment(Apartment apartment) {
		this.reservedApartments.add(apartment);
	}

	@Override
	public String toString() {
		return "UserGuest [reservedApartments=" + reservedApartments + ", reservations=" + reservations + ", username="
				+ username + ", password=" + password + ", name=" + name + ", surname=" + surname + ", gender=" + gender
				+ ", userType=" + userType + "]";
	}
	
	
}

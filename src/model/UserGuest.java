package model;

import java.util.List;

public class UserGuest extends User{
	List<Apartment> reservedApartments;
	List<Reservation> reservations;
	
	public UserGuest() {
		super();
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

	@Override
	public String toString() {
		return "UserGuest [reservedApartments=" + reservedApartments + ", reservations=" + reservations + "]";
	}
}

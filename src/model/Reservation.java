package model;

import java.util.Date;

public class Reservation {
	Apartment apartment;
	Date date;
	int nights = 1;
	float totalPrice;
	String welcomeNote;
	UserGuest guest;
	ReservationStatus status;
	
	public Reservation() {
		super();
	}
	
	

	public Reservation(Apartment apartment, Date date, int nights, float totalPrice, String welcomeNote,
			UserGuest guest, ReservationStatus status) {
		super();
		this.apartment = apartment;
		this.date = date;
		this.nights = nights;
		this.totalPrice = totalPrice;
		this.welcomeNote = welcomeNote;
		this.guest = guest;
		this.status = status;
	}



	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNights() {
		return nights;
	}

	public void setNights(int nights) {
		this.nights = nights;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getWelcomeNote() {
		return welcomeNote;
	}

	public void setWelcomeNote(String welcomeNote) {
		this.welcomeNote = welcomeNote;
	}

	public UserGuest getGuest() {
		return guest;
	}

	public void setGuest(UserGuest guest) {
		this.guest = guest;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}



	@Override
	public String toString() {
		return "Reservation [apartment=" + apartment + ", date=" + date + ", nights=" + nights + ", totalPrice="
				+ totalPrice + ", welcomeNote=" + welcomeNote + ", guest=" + guest + ", status=" + status + "]";
	}
	
	
}

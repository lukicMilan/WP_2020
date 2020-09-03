package model;

import java.util.Date;

public class Reservation {
	long reservationId;
	long apartmentId;
	Date date;
	int nights = 1;
	float totalPrice;
	String welcomeNote;
	String guestUsername;
	ReservationStatus status;
	
	public Reservation() {
		super();
	}
	
	

	public Reservation(long reservationId, long apartmentId, Date date, int nights, float totalPrice, String welcomeNote,
			String guestUsername, ReservationStatus status) {
		super();
		this.reservationId = reservationId;
		this.apartmentId = apartmentId;
		this.date = date;
		this.nights = nights;
		this.totalPrice = totalPrice;
		this.welcomeNote = welcomeNote;
		this.guestUsername = guestUsername;
		this.status = status;
	}


	public long getReservationId() {
		return reservationId;
	}

	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}

	public long getApartmentId() {
		return apartmentId;
	}

	public void setApartment(long apartmentId) {
		this.apartmentId = apartmentId;
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

	public String getGuestUsername() {
		return guestUsername;
	}

	public void setGuest(String guestUsername) {
		this.guestUsername = guestUsername;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}



	@Override
	public String toString() {
		return "Reservation [apartment=" + apartmentId + ", date=" + date + ", nights=" + nights + ", totalPrice="
				+ totalPrice + ", welcomeNote=" + welcomeNote + ", guest=" + guestUsername + ", status=" + status + "]";
	}
	
	
}

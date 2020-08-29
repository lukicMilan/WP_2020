package dto;

import java.util.Date;

import model.Reservation;
import model.ReservationStatus;

public class ReservationDTO {
	long reservationId;
	long apartmentId;
	Date date;
	int nights = 1;
	float totalPrice;
	String welcomeNote;
	String guestUsername;
	ReservationStatus status;
	
	ReservationDTO() {
		super();
	}
	
	public ReservationDTO(Reservation reservation) {
		super();
		this.reservationId = reservation.getReservationId();
		this.apartmentId = reservation.getApartmentId();
		this.date = reservation.getDate();
		this.nights = reservation.getNights();
		this.totalPrice = reservation.getTotalPrice();
		this.welcomeNote = reservation.getWelcomeNote();
		this.guestUsername = reservation.getGuestUsername();
		this.status = reservation.getStatus();
	}

	public ReservationDTO(long reservationId, long apartmentId, Date date, int nights, float totalPrice,
			String welcomeNote, String guestUsername, ReservationStatus status) {
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

//	public Reservation getReservation() {
//		return new Reservation(this.getReservationId(), this.apartmentId, this.getDate(), this.getNights(),
//				this.getTotalPrice(), this.getWelcomeNote(), this.getGuestUsername(), this.getStatus());
//	}

	public long getReservationId() {
		return reservationId;
	}

	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}

	public long getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(long apartmentId) {
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

	public void setGuestUsername(String guestUsername) {
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
		return "ReservationDTO [reservationId=" + reservationId + ", apartmentId=" + apartmentId + ", date=" + date
				+ ", nights=" + nights + ", totalPrice=" + totalPrice + ", welcomeNote=" + welcomeNote
				+ ", guestUsername=" + guestUsername + ", status=" + status + "]";
	}
	
	
}

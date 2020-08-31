package dto;

import model.ReservationStatus;

public class ReservationStatusDTO {
	private long reservationId;
	private ReservationStatus reservationStatus;
	
	public ReservationStatusDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReservationStatusDTO(long reservationId, ReservationStatus reservationStatus) {
		super();
		this.reservationId = reservationId;
		this.reservationStatus = reservationStatus;
	}
	public long getReservationId() {
		return reservationId;
	}
	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}
	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}
	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	@Override
	public String toString() {
		return "ReservationStatusDTO [reservationId=" + reservationId + ", reservationStatus=" + reservationStatus
				+ "]";
	}
}

package model;

public enum ReservationStatus {
	CREATED, DENIDED, CANCELED, ACCEPTED, PASSED;
	
	public ReservationStatus fromString(String reservationStatus) {
		switch(reservationStatus) {
			case "CREATED":
				return ReservationStatus.CREATED;
			case "DENIED" :
				return ReservationStatus.DENIDED;
			case "CANCELED":
				return ReservationStatus.CANCELED;
			case "ACCEPTED":
				return ReservationStatus.ACCEPTED;
			case "PASSED":
				return ReservationStatus.PASSED;
			default:
				return null;
		}
	}
}

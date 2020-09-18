package dto;

public class ReservedDatesDTO {
	long apartmentId;
	String date;
	int nights = 1;
	
	public ReservedDatesDTO(ReservationDTO reservationDTO) {
		this.apartmentId = reservationDTO.getApartmentId();
		this.date = reservationDTO.getDate();
		this.nights = reservationDTO.getNights();
	}
	
	public ReservedDatesDTO() {
		super();
	}

	public long getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(long apartmentId) {
		this.apartmentId = apartmentId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getNights() {
		return nights;
	}

	public void setNights(int nights) {
		this.nights = nights;
	}
	
	
} 

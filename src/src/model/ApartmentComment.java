package src.model;

public class ApartmentComment {
	UserGuest guest;
	Apartment apartment;
	String comment;
	int stars;
	
	public ApartmentComment() {
		super();
	}

	public ApartmentComment(UserGuest guest, Apartment apartment, String comment, int stars) {
		super();
		this.guest = guest;
		this.apartment = apartment;
		this.comment = comment;
		this.stars = stars;
	}

	public UserGuest getGuest() {
		return guest;
	}

	public void setGuest(UserGuest guest) {
		this.guest = guest;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	@Override
	public String toString() {
		return "ApartmentComment [guest=" + guest + ", apartment=" + apartment + ", comment=" + comment + ", stars="
				+ stars + "]";
	}
	
	
}

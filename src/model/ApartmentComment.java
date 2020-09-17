package model;

public class ApartmentComment {
	long id;
	String username;
	long apartment;
	String comment;
	long stars;
	boolean visible;
	
	public ApartmentComment() {
		super();
	}

	public ApartmentComment(String username, long apartment, String comment, long stars) {
		super();
		this.username = username;
		this.apartment = apartment;
		this.comment = comment;
		this.stars = stars;
	}
	
	public ApartmentComment(long id, String username, long apartment, String comment, long stars, boolean visible) {
		super();
		this.id = id;
		this.username = username;
		this.apartment = apartment;
		this.comment = comment;
		this.stars = stars;
		this.visible = visible;
	}

	public ApartmentComment(String username, long apartment, String comment, long stars, boolean visible) {
		super();
		this.username = username;
		this.apartment = apartment;
		this.comment = comment;
		this.stars = stars;
		this.visible = visible;
	}

	public ApartmentComment(long id, String username, long apartment, String comment, long stars) {
		super();
		this.id = id;
		this.username = username;
		this.apartment = apartment;
		this.comment = comment;
		this.stars = stars;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsernames(String username) {
		this.username = username;
	}

	public long getApartment() {
		return apartment;
	}

	public void setApartment(long apartment) {
		this.apartment = apartment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getStars() {
		return stars;
	}

	public void setStars(long stars) {
		this.stars = stars;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "ApartmentComment [guest=" + username + ", apartment=" + apartment + ", comment=" + comment + ", stars="
				+ stars + "]";
	}
	
	
}

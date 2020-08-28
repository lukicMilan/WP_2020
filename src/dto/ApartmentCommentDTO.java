package dto;

import model.Apartment;
import model.ApartmentComment;
import model.User;
import model.UserGuest;

public class ApartmentCommentDTO {
	private long id;
	private String username;
	private long apartment;
	private String comment;
	private long stars;
	
	public ApartmentCommentDTO(long id,String username, long apartment, String comment, long stars) {
		super();
		this.id = id;
		this.username = username;
		this.apartment = apartment;
		this.comment = comment;
		this.stars = stars;
	}
	
	public ApartmentCommentDTO(ApartmentComment ac) {
		this.id = ac.getId();
		this.username = ac.getUser();
		this.apartment = ac.getApartment();
		this.comment = ac.getComment();
		this.stars = ac.getStars();
	}
	
	public static ApartmentComment toApartmentComment(ApartmentCommentDTO acDTO) {
		ApartmentComment ac = new ApartmentComment();
		ac.setId(acDTO.id);
		ac.setUser(acDTO.username);
		ac.setApartment(acDTO.apartment);
		ac.setComment(acDTO.comment);
		ac.setStars(acDTO.stars);
		return ac;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUser() {
		return username;
	}

	public void setUser(String username) {
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
	
}

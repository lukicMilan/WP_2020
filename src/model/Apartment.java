package model;

import java.awt.Image;
import java.util.Date;
import java.util.List;

public class Apartment {
	long id;
	ApartmentType type;
	int roomNumber;
	int guestNumber;
	List<Date> rentDates;
	List<Date> freeDates;
	String hostUsername;
	List<ApartmentComment> comments;
	List<String> imageList;
	long price;
	int entryTime = 14;
	int leaveTime = 10;
	boolean active;
	List<Amenities> amenities;
	long latitude;
	long longitude;
	String street;
	int number;
	String city;
	int zipCode;
	
	public Apartment() {
		super();
	}

	public Apartment(ApartmentType type, int roomNumber, int guestNumber, List<Date> rentDates,
			List<Date> freeDates, String hostUsername, List<ApartmentComment> comments, List<String> imageList, long price,
			int entryTime, int leaveTime, boolean active, List<Amenities> amenities,	long latitude,
	long longitude,
	String street,
	int number,
	String city,
	int zipCode) {
		super();
		this.type = type;
		this.roomNumber = roomNumber;
		this.guestNumber = guestNumber;
		this.rentDates = rentDates;
		this.freeDates = freeDates;
		this.hostUsername = hostUsername;
		this.comments = comments;
		this.imageList = imageList;
		this.price = price;
		this.entryTime = entryTime;
		this.leaveTime = leaveTime;
		this.active = active;
		this.amenities = amenities;
		this.latitude = latitude;
		this.longitude = longitude;
		this.street = street;
		this.number = number;
		this.city =city;
		this.zipCode = zipCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ApartmentType getType() {
		return type;
	}

	public void setType(ApartmentType type) {
		this.type = type;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getGuestNumber() {
		return guestNumber;
	}

	public void setGuestNumber(int guestNumber) {
		this.guestNumber = guestNumber;
	}

	public List<Date> getRentDates() {
		return rentDates;
	}

	public void setRentDates(List<Date> rentDates) {
		this.rentDates = rentDates;
	}

	public List<Date> getFreeDates() {
		return freeDates;
	}

	public void setFreeDates(List<Date> freeDates) {
		this.freeDates = freeDates;
	}

	public String getHostUsername() {
		return hostUsername;
	}

	public void setHostUsername(String hostUsername) {
		this.hostUsername = hostUsername;
	}

	public List<ApartmentComment> getComments() {
		return comments;
	}

	public void setComments(List<ApartmentComment> comments) {
		this.comments = comments;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(int entryTime) {
		this.entryTime = entryTime;
	}

	public int getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(int leaveTime) {
		this.leaveTime = leaveTime;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Amenities> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Amenities> amenities) {
		this.amenities = amenities;
	}
	
	

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "Apartment [id=" + id + ", type=" + type + ", roomNumber=" + roomNumber + ", guestNumber=" + guestNumber
				+ ", rentDates=" + rentDates + ", freeDates=" + freeDates + ", hostUsername="
				+ hostUsername + ", comments=" + comments + ", imageList=" + imageList + ", price=" + price
				+ ", entryTime=" + entryTime + ", leaveTime=" + leaveTime + ", active=" + active + ", amenities="
				+ amenities + ", latitude=" + latitude + ", longitude=" + longitude + ", street=" + street + ", number="
				+ number + ", city=" + city + ", zipCode=" + zipCode + "]";
	}

	
}

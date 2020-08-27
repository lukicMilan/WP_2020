package model;

import java.awt.Image;
import java.util.Date;
import java.util.List;

public class Apartment {
	long id;
	ApartmentType type;
	int roomNumber;
	int guestNumber;
	Location location;
	List<Date> rentDates;
	List<Date> freeDates;
	UserHost host;
	List<ApartmentComment> comments;
	List<String> imageList;
	long price;
	int entryTime = 14;
	int leaveTime = 10;
	boolean active;
	List<Amenities> amenities;
	
	public Apartment() {
		super();
	}

	public Apartment(ApartmentType type, int roomNumber, int guestNumber, Location location, List<Date> rentDates,
			List<Date> freeDates, UserHost host, List<ApartmentComment> comments, List<String> imageList, long price,
			int entryTime, int leaveTime, boolean active, List<Amenities> amenities) {
		super();
		this.type = type;
		this.roomNumber = roomNumber;
		this.guestNumber = guestNumber;
		this.location = location;
		this.rentDates = rentDates;
		this.freeDates = freeDates;
		this.host = host;
		this.comments = comments;
		this.imageList = imageList;
		this.price = price;
		this.entryTime = entryTime;
		this.leaveTime = leaveTime;
		this.active = active;
		this.amenities = amenities;
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
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

	public UserHost getHost() {
		return host;
	}

	public void setHost(UserHost host) {
		this.host = host;
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

	@Override
	public String toString() {
		return "Apartment [type=" + type + ", roomNumber=" + roomNumber + ", guestNumber=" + guestNumber + ", location="
				+ location + ", rentDates=" + rentDates + ", freeDates=" + freeDates + ", host=" + host + ", comments="
				+ comments + ", imageList=" + imageList + ", price=" + price + ", entryTime=" + entryTime
				+ ", leaveTime=" + leaveTime + ", active=" + active + ", amenities=" + amenities + "]";
	}
}

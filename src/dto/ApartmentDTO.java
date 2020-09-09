package dto;

import java.util.List;

import model.Amenities;
import model.Apartment;
import model.ApartmentComment;
import model.ApartmentType;
import model.Location;
import model.UserHost;

public class ApartmentDTO {
	private long id;
	private String type;
	private int roomNumber;
	private int guestNumber;
	private Location location;
	private List<String> rentDates;
	private List<String> freeDates;
	private String hostUsername;
	private List<ApartmentComment> comments;
	private List<String> imageList;
	private long price;
	private int entryTime;
	private int leaveTime;
	private boolean active;
	private List<Amenities> amenities;
	
	public ApartmentDTO(long id, String type, int roomNumber, int guestNumber, Location location,
			List<String> rentDates, List<String> freeDates, String hostUsername, List<ApartmentComment> comments,
			List<String> imageList, long price, int entryTim, int leaveTime, boolean active,
			List<Amenities> amenities) {
		super();
		this.id = id;
		this.type = type;
		this.roomNumber = roomNumber;
		this.guestNumber = guestNumber;
		this.location = location;
		this.rentDates = rentDates;
		this.freeDates = freeDates;
		this.hostUsername = hostUsername;
		this.comments = comments;
		this.imageList = imageList;
		this.price = price;
		this.entryTime = entryTim;
		this.leaveTime = leaveTime;
		this.active = active;
		this.amenities = amenities;
	}
	
	public ApartmentDTO() {
		super();
	}

	public ApartmentDTO(Apartment ap) {
		this.id = ap.getId();
		this.type = ap.getType().toString();
		this.roomNumber = ap.getRoomNumber();
		this.guestNumber = ap.getGuestNumber();
//		this.rentDates = ap.getRentDates();
//		this.freeDates = ap.getFreeDates();
		this.hostUsername = ap.getHostUsername();
		this.comments = ap.getComments();
		this.imageList = ap.getImageList();
		this.price = ap.getPrice();
		this.entryTime = ap.getEntryTime();
		this.leaveTime = ap.getLeaveTime();
		this.active = ap.isActive();
		this.amenities = ap.getAmenities();
	}
	
	public static Apartment toApartment(ApartmentDTO apartmentDTO) {
		Apartment ap = new Apartment();
		ap.setId(apartmentDTO.id);
		ap.setType(ApartmentType.valueOf(apartmentDTO.type));
		ap.setRoomNumber(apartmentDTO.roomNumber);
		ap.setGuestNumber(apartmentDTO.guestNumber);
		ap.setLocation(apartmentDTO.location);
		//rent dates i free dates????
		ap.setHostUsername(apartmentDTO.hostUsername);
		ap.setComments(apartmentDTO.comments);
		ap.setImageList(apartmentDTO.imageList);
		ap.setPrice(apartmentDTO.price);
		ap.setEntryTime(apartmentDTO.entryTime);
		ap.setLeaveTime(apartmentDTO.leaveTime);
		ap.setActive(apartmentDTO.active);
		ap.setAmenities(apartmentDTO.amenities);
		return ap;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
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
	public List<String> getRentDates() {
		return rentDates;
	}
	public void setRentDates(List<String> rentDates) {
		this.rentDates = rentDates;
	}
	public List<String> getFreeDates() {
		return freeDates;
	}
	public void setFreeDates(List<String> freeDates) {
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
	public void setEntryTime(int entryTim) {
		this.entryTime = entryTim;
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
	
	
}

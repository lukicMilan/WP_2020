package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Amenities;
import model.Apartment;
import model.ApartmentComment;
import model.ApartmentType;

public class ApartmentDTO {
	private long id;
	private String type;
	private int roomNumber;
	private int guestNumber;
	private List<String> rentDates;
	private List<Date> fancyRentDates;
	private List<String> freeDates;
	private String hostUsername;
	private List<ApartmentComment> comments;
	private List<String> imageList;
	private long price;
	private int entryTime;
	private int leaveTime;
	private boolean active;
	private List<Amenities> amenities;
	long latitude;
	long longitude;
	String street;
	int number;
	String city;
	int zipCode;
	
	public ApartmentDTO(long id, String type, int roomNumber, int guestNumber,
			List<String> rentDates, List<String> freeDates, String hostUsername, List<ApartmentComment> comments,
			List<String> imageList, long price, int entryTim, int leaveTime, boolean active,
			List<Amenities> amenities,long latitude,
			long longitude,
			String street,
			int number,
			String city,
			int zipCode) {
		super();
		this.id = id;
		this.type = type;
		this.roomNumber = roomNumber;
		this.guestNumber = guestNumber;
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
		this.latitude = latitude;
		this.longitude = longitude;
		this.street = street;
		this.number = number;
		this.city =city;
		this.zipCode = zipCode;
	}
	
	public ApartmentDTO() {
		super();
	}

	@SuppressWarnings("deprecation")
	public ApartmentDTO(Apartment ap) {
		List<Date> dates = ap.getRentDates();
		List<String> datesString = new ArrayList<String>();
		if(dates != null) {
			for (Date date : dates) {
				int day = date.getDate();
				int month = date.getMonth()+1;
				int year = date.getYear()+1900;
				String dateString = Integer.toString(year) + "/";
				dateString += Integer.toString(month) + "/";
				dateString += Integer.toString(day);
				
				datesString.add(dateString);
			}
		}
		this.id = ap.getId();
		this.type = ap.getType().toString();
		this.roomNumber = ap.getRoomNumber();
		this.guestNumber = ap.getGuestNumber();
 		this.rentDates = datesString;
//		this.freeDates = ap.getFreeDates();
		this.hostUsername = ap.getHostUsername();
		this.comments = ap.getComments();
		this.imageList = ap.getImageList();
		this.price = ap.getPrice();
		this.entryTime = ap.getEntryTime();
		this.leaveTime = ap.getLeaveTime();
		this.active = ap.isActive();
		this.amenities = ap.getAmenities();
		this.longitude = ap.getLongitude();
		this.latitude = ap.getLatitude();
		this.street = ap.getStreet();
		this.number = ap.getNumber();
		this.city = ap.getCity();
		this.zipCode = ap.getZipCode();
	}
	
	public static Apartment toApartment(ApartmentDTO apartmentDTO) {
		Apartment ap = new Apartment();
		ap.setId(apartmentDTO.id);
		ap.setType(ApartmentType.valueOf(apartmentDTO.type));
		ap.setRoomNumber(apartmentDTO.roomNumber);
		ap.setGuestNumber(apartmentDTO.guestNumber);
		if(apartmentDTO.getFancyRentDates() != null) {
			ap.setRentDates(apartmentDTO.getFancyRentDates());
		}
		ap.setHostUsername(apartmentDTO.hostUsername);
		ap.setComments(apartmentDTO.comments);
		ap.setImageList(apartmentDTO.imageList);
		ap.setPrice(apartmentDTO.price);
		ap.setEntryTime(apartmentDTO.entryTime);
		ap.setLeaveTime(apartmentDTO.leaveTime);
		ap.setActive(apartmentDTO.active);
		ap.setAmenities(apartmentDTO.amenities);
		ap.setLatitude(apartmentDTO.getLatitude());
		ap.setLongitude(apartmentDTO.getLongitude());
		ap.setStreet(apartmentDTO.getStreet());
		ap.setNumber(apartmentDTO.getNumber());
		ap.setCity(apartmentDTO.getCity());
		ap.setZipCode(apartmentDTO.getZipCode());
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

	public List<Date> getFancyRentDates() {
		return fancyRentDates;
	}

	public void setFancyRentDates(List<Date> fancyRentDates) {
		this.fancyRentDates = fancyRentDates;
	}
	
	
}

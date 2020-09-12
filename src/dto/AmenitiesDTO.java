package dto;

import model.Amenities;

public class AmenitiesDTO {
	long id;
	String type;
	String name;
	boolean deleted = false;
	
	public AmenitiesDTO(long id, String type, String name, boolean deleted) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.deleted = deleted;
	}
	public AmenitiesDTO(long id, String type, String name) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
	}
	
	public AmenitiesDTO() {
		super();
	}

	public AmenitiesDTO(Amenities amenities) {
		this.id = amenities.getId();
		this.type = amenities.getType();
		this.name = amenities.getName();
	}
	
	public static Amenities toAmenities(AmenitiesDTO amenitiesDTO) {
		Amenities amenities = new Amenities();
		amenities.setId(amenitiesDTO.getId());
		amenities.setType(amenitiesDTO.getType());
		amenities.setName(amenitiesDTO.getName());
		return amenities;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}

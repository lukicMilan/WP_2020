package dto;

import model.Amenities;

public class AmenitiesDTO {
	long id;
	String type;
	String name;
	
	public AmenitiesDTO(long id, String type, String name) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
	}

	public AmenitiesDTO(Amenities amenities) {
		this.id = amenities.getId();
		this.type = amenities.getType();
		this.name = amenities.getName();
	}
	
	public static Amenities toAmenities(AmenitiesDTO amenitiesDTO) {
		Amenities amenities = null;
		amenities.setId(amenitiesDTO.id);
		amenities.setType(amenitiesDTO.type);
		amenities.setName(amenitiesDTO.name);
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
	
	
}

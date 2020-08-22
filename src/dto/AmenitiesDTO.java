package dto;

import model.Amenities;

public class AmenitiesDTO {
	long id;
	String type;
	String name;
	
	public AmenitiesDTO(Amenities amenities) {
		this.id = amenities.getId();
		this.type = amenities.getType();
		this.name = amenities.getName();
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

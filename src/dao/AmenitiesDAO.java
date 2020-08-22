package dao;

import java.util.HashMap;
import java.util.Map;

import model.Amenities;

public class AmenitiesDAO {
	private String contextPath;
	private Map<Long, Amenities> amenities = new HashMap<>();
	
	public AmenitiesDAO(String contextPath) {
		Amenities amenity = new Amenities(1, "Outside", "Hot tub");
		this.amenities.put(amenity.getId(), amenity);
	}
	
	public Amenities getAmenitiesById(long id) {
		return this.amenities.get(id);
	}
}
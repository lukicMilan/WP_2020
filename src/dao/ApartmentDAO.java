package dao;

import java.util.HashMap;
import java.util.Map;

import model.Apartment;
import model.ApartmentType;
import model.Location;


public class ApartmentDAO {
	private String contextPath;
	private Map<Long, Apartment> apartments = new HashMap<>();
	
	public ApartmentDAO(String contextPath) {
		Apartment ap = new Apartment();
		this.apartments.put(ap.getId(), ap);
	}
	
	public Apartment getApartmentById(long id) {
		return this.apartments.get(id);
	}
}

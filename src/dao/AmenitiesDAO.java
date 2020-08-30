package dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import dto.AmenitiesDTO;
import dto.ApartmentDTO;
import model.Amenities;
import model.Apartment;

public class AmenitiesDAO {
	private String contextPath;
	private Map<Long, Amenities> amenities = new HashMap<>();
	
	public AmenitiesDAO(String contextPath) {
		this.contextPath = contextPath;
		Amenities amenity = new Amenities("Outside", "Hot tub");
		amenity.setId(1);
		this.amenities.put(amenity.getId(), amenity);
		loadAmenities();
	}
	
	public Map<Long, Amenities> getAmenities() {
		return this.amenities;
	}
	
	public Amenities getAmenitiesById(long id) {
		return this.amenities.get(id);
	}
	
	public boolean amenityExists(long id) {
		return amenities.containsKey(id);
	}
	
	public boolean addAmenity(AmenitiesDTO amenitiesDTO) {
		amenitiesDTO.setId(getFreeId(1));
		amenities.put(amenitiesDTO.getId(), AmenitiesDTO.toAmenities(amenitiesDTO));
		saveAmenities();
		
		return true;
	}
	
	public boolean removeAmenity(long id) {
		if(!amenities.containsKey(id)) {
			return false;
		}
		
		amenities.remove(id);
		saveAmenities();
		
		return true;
	}
	
	public void saveAmenities() {
		ObjectMapper objectMapper = new ObjectMapper();
		File file = null;
		FileWriter fileWriter = null;
		try {
			file = new File(contextPath + "//amenities.json");
			fileWriter = new FileWriter(file);
			
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			String outputString = objectMapper.writeValueAsString(amenities);

			fileWriter.write(outputString);
		}  catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fileWriter != null) {
				try {
					fileWriter.close();
					System.out.println("Map AMENITIES saved.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void loadAmenities() {		
		File file = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			
			file = new File(contextPath + "//amenities.json");
			
			this.amenities = objectMapper.readValue(file, new TypeReference<HashMap<Long, Amenities>>() {});
		}catch (IOException e) {
			e.printStackTrace();
		} 
			
	}
	
	private long getFreeId(long id) {
		loadAmenities();
		
		for (Map.Entry<Long, Amenities> amenity : this.amenities.entrySet()) {
			if(amenity.getValue().getId() == id) {
				return getFreeId(id++);
			}
		}
		
		return id;
	}
}
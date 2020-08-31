package dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import dto.ApartmentDTO;
import model.Amenities;
import model.Apartment;
import model.ApartmentComment;
import model.ApartmentType;
import model.Location;


public class ApartmentDAO {
	private String contextPath;
	private Map<Long, Apartment> apartments = new HashMap<>();
	
	public ApartmentDAO(String contextPath) {
		this.contextPath = contextPath;
		Location location = new Location();
		List<Date> rentDates = new ArrayList<Date>();
		List<Date> freeDates = new ArrayList<Date>();
		List<ApartmentComment> apartmentComments = new ArrayList<ApartmentComment>();
		List<String> imageList = new ArrayList<String>();
		List<Amenities> amenities = new ArrayList<Amenities>();
		Amenities amenity = new Amenities("Inside", "Room service");
		//treba jos id dodati na amenity
		amenities.add(amenity);
		
		Apartment ap = new Apartment(ApartmentType.FULL, 1, 5, location, rentDates, freeDates, 
				"host", apartmentComments, imageList, (long) 50 , 14, 10, true, amenities);
		ap.setId(getFreeId(1));
		this.apartments.put(ap.getId(), ap);
		saveApartments();
	}
	
	public Apartment getApartmentById(long id) {
		return this.apartments.get(id);
	}
	
	public Map<Long, Apartment> getApartments() {
		return this.apartments;
	}
	
	public Map<Long, Apartment> getAllPostedBy(String username) {
		loadApartments();
		Map<Long, Apartment> aps = new HashMap<Long, Apartment>();
		
		for(Map.Entry<Long, Apartment> entry : this.apartments.entrySet()) {
			if(entry.getValue().getHostUsername() == username) {
				aps.put(entry.getKey(), entry.getValue());
			}
		}
		
		return aps;
	}
	
	public boolean addApartment(ApartmentDTO apartmentDTO) {
		apartmentDTO.setId(getFreeId(1));
		apartments.put(apartmentDTO.getId(), ApartmentDTO.toApartment(apartmentDTO));
		saveApartments();
		
		return true;
	}
	
	public boolean removeApartment(long id) {
		if(!apartments.containsKey(id)) {
			return false;
		}
		
		apartments.remove(id);
		saveApartments();
		
		return true;
	}
	
	public boolean apartmentExists(long id) {
		return apartments.containsKey(id);
	}
	
	public void saveApartments() {
		ObjectMapper objectMapper = new ObjectMapper();
		File file = null;
		FileWriter fileWriter = null;
		try {
			file = new File(contextPath + "//apartments.json");
			fileWriter = new FileWriter(file);
			
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			String outputString = objectMapper.writeValueAsString(apartments);

			fileWriter.write(outputString);
		}  catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fileWriter != null) {
				try {
					fileWriter.close();
					System.out.println("Map APARTMETNS saved.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void loadApartments() {		
		File file = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			
			file = new File(contextPath + "//apartments.json");
			
			this.apartments = objectMapper.readValue(file, new TypeReference<HashMap<Long, Apartment>>() {});
		}catch (IOException e) {
			e.printStackTrace();
		} 
			
	}
	
	//funkcija je rekurzivna
	//vraca prvi slobodan id
	//poziva se sa getFreeId(1)
	private long getFreeId(long id) {
		loadApartments();
		
		for (Map.Entry<Long, Apartment> apartment : this.apartments.entrySet()) {
			if(apartment.getValue().getId() == id) {
				return getFreeId(id++);
			}
		}
		
		return id;
	}
}

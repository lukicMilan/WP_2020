package dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import dto.ApartmentDTO;
import model.Apartment;
import model.ApartmentType;
import model.Location;


public class ApartmentDAO {
	private String contextPath;
	private Map<Long, Apartment> apartments = new HashMap<>();
	
	public ApartmentDAO(String contextPath) {
		Apartment ap = new Apartment();
		this.apartments.put(ap.getId(), ap);
		loadApartments();
	}
	
	public Apartment getApartmentById(long id) {
		return this.apartments.get(id);
	}
	
	public Map<Long, Apartment> getApartments() {
		return this.apartments;
	}
	
	public Map<Long, Apartment> getAllPostedBy(String username) {
		Map<Long, Apartment> aps = new HashMap<Long, Apartment>();
		
		for(Map.Entry<Long, Apartment> entry : this.apartments.entrySet()) {
			if(entry.getValue().getHost().getUsername() == username) {
				aps.put(entry.getKey(), entry.getValue());
			}
		}
		
		return aps;
	}
	
	public boolean addApartment(ApartmentDTO apartmentDTO) {
		if(this.apartments.containsKey(apartmentDTO.getId())) {
			return false;
		}
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
}

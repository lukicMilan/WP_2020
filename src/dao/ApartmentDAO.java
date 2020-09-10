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

import dto.AmenitiesDTO;
import dto.ApartmentDTO;
import model.Amenities;
import model.Apartment;
import model.ApartmentComment;
import model.ApartmentType;


public class ApartmentDAO {
	private String contextPath;
	private Map<Long, Apartment> apartments = new HashMap<>();
	
	public ApartmentDAO(String contextPath) {
		this.contextPath = contextPath;
		List<Date> rentDates = new ArrayList<Date>();
		List<Date> freeDates = new ArrayList<Date>();
		List<ApartmentComment> apartmentComments = new ArrayList<ApartmentComment>();
		List<String> imageList = new ArrayList<String>();
		List<Amenities> amenities = new ArrayList<Amenities>();
		Amenities amenity = new Amenities("Inside", "Room service");
		//treba jos id dodati na amenity
		amenities.add(amenity);
		
		Apartment ap = new Apartment(ApartmentType.FULL, 1, 5,  rentDates, freeDates, "host", apartmentComments, imageList, (long) 50 , 14, 10, true, amenities,  50,50,"Ulica",1, "Grad", 10000);
		ap.setId(this.apartments.size()+1);
		this.apartments.put(ap.getId(), ap);
		saveApartments();
	}
	
	public Apartment getApartmentById(long id) {
		return this.apartments.get(id);
	}
	
	public ArrayList<ApartmentDTO> getApartments() {
		ArrayList<ApartmentDTO> aps = new ArrayList<>();
		
		loadApartments();
		for (Map.Entry<Long, Apartment> a : this.apartments.entrySet()) {
			aps.add(new ApartmentDTO(a.getValue()));
		}
		
		return aps;
	}
	
	public ArrayList<ApartmentDTO> getAllPostedBy(String username) {
		loadApartments();
		Map<Long, Apartment> aps = new HashMap<Long, Apartment>();
		
		for(Map.Entry<Long, Apartment> entry : this.apartments.entrySet()) {
			if(entry.getValue().getHostUsername() == username) {
				aps.put(entry.getKey(), entry.getValue());
			}
		}
		
		ArrayList<ApartmentDTO> a = new ArrayList<>();
		
		for (Map.Entry<Long, Apartment> ap : aps.entrySet()) {
			a.add(new ApartmentDTO(ap.getValue()));
		}
		
		return a;
	}
	
	public ArrayList<ApartmentDTO> getAllActiveApartments()  {
		Map<Long, Apartment> aps = new HashMap<Long, Apartment>();
		
		for(Map.Entry<Long, Apartment> entry : this.apartments.entrySet()) {
			if(entry.getValue().isActive()) {
				aps.put(entry.getKey(), entry.getValue());
			}
		}
		
		ArrayList<ApartmentDTO> a = new ArrayList<>();
		
		for (Map.Entry<Long, Apartment> ap : aps.entrySet()) {
			a.add(new ApartmentDTO(ap.getValue()));
		}
		
		return a;
	}
	
	public ArrayList<ApartmentDTO> getAllActiveApartmentsPostedBy(String username)  {
		Map<Long, Apartment> aps = new HashMap<Long, Apartment>();
		
		for(Map.Entry<Long, Apartment> entry : this.apartments.entrySet()) {
			if(entry.getValue().isActive() && entry.getValue().getHostUsername() == username) {
				aps.put(entry.getKey(), entry.getValue());
			}
		}
		ArrayList<ApartmentDTO> a = new ArrayList<>();
		
		for (Map.Entry<Long, Apartment> ap : aps.entrySet()) {
			a.add(new ApartmentDTO(ap.getValue()));
		}
		
		return a;
	}
	
	public ArrayList<ApartmentDTO> getAllNonActiveApartments()  {
		Map<Long, Apartment> aps = new HashMap<Long, Apartment>();
		
		for(Map.Entry<Long, Apartment> entry : this.apartments.entrySet()) {
			if(!entry.getValue().isActive()) {
				aps.put(entry.getKey(), entry.getValue());
			}
		}
		ArrayList<ApartmentDTO> a = new ArrayList<>();
		
		for (Map.Entry<Long, Apartment> ap : aps.entrySet()) {
			a.add(new ApartmentDTO(ap.getValue()));
		}
		
		return a;
	}
	
	public ArrayList<ApartmentDTO> getAllNonActiveApartmentsPostedBy(String username)  {
		Map<Long, Apartment> aps = new HashMap<Long, Apartment>();
		
		for(Map.Entry<Long, Apartment> entry : this.apartments.entrySet()) {
			if(!entry.getValue().isActive() && entry.getValue().getHostUsername() == username) {
				aps.put(entry.getKey(), entry.getValue());
			}
		}
		ArrayList<ApartmentDTO> a = new ArrayList<>();
		
		for (Map.Entry<Long, Apartment> ap : aps.entrySet()) {
			a.add(new ApartmentDTO(ap.getValue()));
		}
		
		return a;
	}
	
	public boolean editApartment(ApartmentDTO apartmentDTO) {
		if(!apartments.containsKey(apartmentDTO.getId())) {
			return false;
		}
		apartments.put(apartmentDTO.getId(), ApartmentDTO.toApartment(apartmentDTO));
		saveApartments();
		return true;
	}
	
	public boolean addApartment(ApartmentDTO apartmentDTO) {
		apartmentDTO.setId(this.apartments.size()+1);
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

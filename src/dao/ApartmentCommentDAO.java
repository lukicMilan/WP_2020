package dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import dto.AmenitiesDTO;
import dto.ApartmentDTO;
import model.Apartment;
import model.ApartmentComment;

public class ApartmentCommentDAO {
	private String contextPath;
	private Map<Long, ApartmentComment> comments = new HashMap<>();
	
	public ApartmentCommentDAO(String contextPath) {
		this.contextPath = contextPath;
		ApartmentComment ac = new ApartmentComment("guest", 1, "Odlicna usluga.", 4, true);
		ac.setId(this.comments.size()+1);
		this.comments.put(ac.getId(), ac);
		
		ApartmentComment ac1 = new ApartmentComment("guest", 1, "Fujcina sram vas bilo.", 1, false);
		ac1.setId(this.comments.size()+1);
		this.comments.put(ac1.getId(), ac1);
		
		saveApartmentComments();
	}
	
	public List<ApartmentComment> getAllCommentsForApartment(long id) {
		loadApartmentComments();
		List<ApartmentComment> ac = new ArrayList<ApartmentComment>();
		for(Map.Entry<Long, ApartmentComment> comment : this.comments.entrySet()) {
			if(comment.getValue().getApartment() == id) {
				ac.add(comment.getValue());
			}
		}
		return ac;
	}
	
	public List<ApartmentComment> getComments() {
		loadApartmentComments();
		List<ApartmentComment> ac = new ArrayList<ApartmentComment>();
		for(Map.Entry<Long, ApartmentComment> comment : this.comments.entrySet()) {
			ac.add(comment.getValue());
		}
		return ac;
	}
	
	public boolean addApartmentComment(ApartmentComment ac) {
		ac.setId(this.comments.size()+1);
		ac.setVisible(true);
		comments.put(ac.getId(), ac);
		saveApartmentComments();
		
		return true;
	}
	
	public boolean editApartmentComment(ApartmentComment ac) {
		if(!comments.containsKey(ac.getId())) {
			return false;
		}
		comments.put(ac.getId(), ac);
		saveApartmentComments();
		return true;
	}
	
	public boolean removeApartmentComment(long id) {
		if(!comments.containsKey(id)) {
			return false;
		}
		
		comments.remove(id);
		saveApartmentComments();
		
		return true;
	}
	
	public boolean apartmentCommentExists(long id) {
		return comments.containsKey(id);
	}

	public void saveApartmentComments() {
		ObjectMapper objectMapper = new ObjectMapper();
		File file = null;
		FileWriter fileWriter = null;
		try {
			file = new File(contextPath + "//apartmentComments.json");
			fileWriter = new FileWriter(file);
			
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			String outputString = objectMapper.writeValueAsString(comments);

			fileWriter.write(outputString);
		}  catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fileWriter != null) {
				try {
					fileWriter.close();
					System.out.println("Map APARTMENT COMMENTS saved.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void loadApartmentComments() {		
		File file = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			
			file = new File(contextPath + "//apartmentComments.json");
			
			this.comments = objectMapper.readValue(file, new TypeReference<HashMap<Long, ApartmentComment>>() {});
		}catch (IOException e) {
			e.printStackTrace();
		} 
			
	}

}

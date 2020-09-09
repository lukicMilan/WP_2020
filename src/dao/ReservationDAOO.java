package dao;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import model.Reservation;
import model.ReservationStatus;

public class ReservationDAOO {
	private String contextPath;
	private Map<Long, Reservation> reservations = new HashMap<>();
	
	public ReservationDAOO(String contextPath) {
		this.contextPath = contextPath;
		
		Reservation reservation = new Reservation(1, 1, new Date(120, 12,1), 2, 30, "hello", "guest", ReservationStatus.CANCELED);
		
		reservations.put(reservation.getReservationId(), reservation);
		saveReservations();
	}
	
	public void saveReservations() {
		ObjectMapper objectMapper = new ObjectMapper();
		File file = null;
		FileWriter fileWriter = null;
		try {
			file = new File(contextPath + "//reservations.json");
			fileWriter = new FileWriter(file);
			
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			String outputString = objectMapper.writeValueAsString(reservations);

			fileWriter.write(outputString);
		}  catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fileWriter != null) {
				try {
					fileWriter.close();
					System.out.println("Map RESERVATIONS saved.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void loadReservations() {		
		File file = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			
			file = new File(contextPath + "//reservations.json");
			
			this.reservations = objectMapper.readValue(file, new TypeReference<HashMap<Long, Reservation>>() {});
		}catch (IOException e) {
			e.printStackTrace();
		} 
			
	}
}

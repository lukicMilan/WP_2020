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

import dto.ReservationDTO;
import model.Reservation;
import model.ReservationStatus;


//potrebna dodatna ogranicenja za sve(npr pri kreiranju nove rezervacije
public class ReservationDAO {
	private String contextPath;
	private Map<Long, Reservation> reservations = new HashMap<>();
	
	public ReservationDAO(String contextPath) {
		this.contextPath = contextPath;
		
		@SuppressWarnings("deprecation")
		Reservation reservation = new Reservation(getFreeId(1), 1, new Date(120, 11, 1), 2, 100, "Welcome to apartment", "guest", ReservationStatus.CREATED);
		
		reservations.put(reservation.getReservationId(), reservation);
		
		saveReservations();
	}
	
	public Reservation getById(long id) {
		return this.reservations.get(id);
	}
	
	public ArrayList<ReservationDTO> getAll() {
		ArrayList<ReservationDTO> reservationList = new ArrayList<>();
		
		for (Map.Entry<Long, Reservation> reservation : this.reservations.entrySet()) {
			ReservationDTO reservationDTO = new ReservationDTO(reservation.getValue());
			reservationList.add(reservationDTO);
		}
		
		return reservationList;
	}
	
	public ArrayList<ReservationDTO> getAllFromApartment(long id) {
		ArrayList<ReservationDTO> reservationList = new ArrayList<>();
		
		for (Map.Entry<Long, Reservation> reservation : this.reservations.entrySet()) {
			if(reservation.getValue().getApartmentId() == id) {
				reservationList.add(new ReservationDTO(reservation.getValue()));
			}
		}
		
		return reservationList;
	}
	
	public ArrayList<ReservationDTO> getAllFromGuest(String username) {
		ArrayList<ReservationDTO> reservationList = new ArrayList<>();
		
		for (Map.Entry<Long, Reservation> reservation : this.reservations.entrySet()) {
			if(reservation.getValue().getGuestUsername() == username) {
				reservationList.add(new ReservationDTO(reservation.getValue()));
			}
		}
		
		return reservationList;
	}
	
	public boolean changeStatus(long reservationId, ReservationStatus status) {
		Reservation reservation = reservations.get(reservationId);
		
		if(reservation == null) {
			return false;
		}
		
		reservation.setStatus(status);
		saveReservations();
		
		return true;
	}
	
	public boolean addReservation(ReservationDTO reservationDTO) {
		Reservation reservation = reservationDTO.getReservationClass();
		reservation.setReservationId(getFreeId(1));
		
		reservations.put(reservation.getReservationId(), reservation);
		
		saveReservations();
		
		return true;
	}
	
	public boolean deleteReservation(long id) {
		if(!reservations.containsKey(id)) {
			return false;
		}
		reservations.remove(id);
		saveReservations();
		
		return true;
	}
	
////////////////////////////////////////////////////	
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
		} catch (IOException e) {
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
	
	public Map<Long, Reservation> loadReservations() {
		Map<Long, Reservation> reservations = new HashMap<>();
		
		ObjectMapper objectMapper = new ObjectMapper();
		File file = null;
		
		try {
			file = new File(contextPath + "//reservations.json");
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			
			reservations = objectMapper.readValue(file, new TypeReference<HashMap<Long, Reservation>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return reservations;
	}
	
	private long getFreeId(long id) {
		//loadApartments();
		
		for (Map.Entry<Long, Reservation> reservation : this.reservations.entrySet()) {
			if(reservation.getValue().getReservationId() == id) {
				return getFreeId(id++);
			}
		}
		
		return id;
	}
}

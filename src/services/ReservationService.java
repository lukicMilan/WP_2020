package services;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.ReservationDAO;
import dto.ReservationDTO;
import dto.ReservationStatusDTO;

import model.User;
import model.UserType;

@Path("/reservation")
public class ReservationService {
	@Context
	ServletContext ctx;
	
	public ReservationService() {}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("reservationDAO")==null) {
			ctx.setAttribute("reservationDAO", new ReservationDAO(ctx.getRealPath("")));
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllReservations() {
		ReservationDAO reservationDAO = (ReservationDAO) ctx.getAttribute("reservationDAO");
		
		//fali validacija ko sme sta da uzme kao i svuda, ne zaboraviti
		
		return Response.status(200).entity(reservationDAO.getAll()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createNewReservation(ReservationDTO reservationDTO, @Context HttpServletRequest request) {
		ReservationDAO reservationDAO = (ReservationDAO) ctx.getAttribute("reservationDAO");
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		
		if(request.getSession().getAttribute("loggedInUser") != null) {
			return Response.status(403).build();
		}
		
		if(loggedInUser.getUserType() != UserType.GUEST) {
			System.out.println("Only guests can make reservations.");
			return Response.status(403).build();
		}
		
		reservationDAO.addReservation(reservationDTO);
		
		return Response.status(200).build();
	}
	
	@PUT
	@Path("/status")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeStatus(ReservationStatusDTO reservationStatusDTO) {
		ReservationDAO reservationDAO = (ReservationDAO) ctx.getAttribute("reservationDAO");
		
		if(!reservationDAO.changeStatus(reservationStatusDTO.getReservationId(), reservationStatusDTO.getReservationStatus())) {
			return Response.status(404).build();
		}
		
		return Response.status(200).build();
	}
	
	@GET
	@Path("/{reservationId}") 
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReservationById(@PathParam(value="reservationId") long reservationId){
		ReservationDAO reservationDAO = (ReservationDAO) ctx.getAttribute("reservationDAO");
		
		if(reservationDAO.getById(reservationId) == null) {
			return Response.status(404).build();
		}
		
		ReservationDTO reservationDTO = new ReservationDTO(reservationDAO.getById(reservationId));
		
		return Response.status(200).entity(reservationDTO).build();
	}
	
	@GET
	@Path("/apartment/{apartmentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReservationByApartmentId(@PathParam(value="apartmentId") long apartmentId) {
		ReservationDAO reservationDAO = (ReservationDAO) ctx.getAttribute("reservationDAO");
		
		ArrayList<ReservationDTO> reservationDTOList = reservationDAO.getAllFromApartment(apartmentId);
		
		if(reservationDTOList.size() == 0) {
			return Response.status(404).build();
		}
		
		return Response.status(200).entity(reservationDTOList).build();
	}
	
	@GET
	@Path("/guest/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReservationByGuestUsername(@PathParam(value="username") String username) {
		ReservationDAO reservationDAO = (ReservationDAO) ctx.getAttribute("reservationDAO");
		
		ArrayList<ReservationDTO> reservationDTOList = reservationDAO.getAllFromGuest(username);
		
		if(reservationDTOList.size() == 0) {
			return Response.status(404).build();
		}
		
		return Response.status(200).entity(reservationDTOList).build();
	}
	
	@GET
	@Path("/host/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReservationByHostUsername(@PathParam(value="username") String username) {
		ReservationDAO reservationDAO = (ReservationDAO) ctx.getAttribute("reservationDAO");
		
		//fali validacija da li je ulogovan
		
		ArrayList<ReservationDTO> reservationDTOList = reservationDAO.getAllFromHost(username);
	
		
		return Response.status(200).entity(reservationDTOList).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteReservation(@PathParam(value="id") long id, @Context HttpServletRequest request) {
		ReservationDAO reservationDAO = (ReservationDAO) ctx.getAttribute("reservationDAO");
		
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		
		if(loggedInUser.getUserType()!=UserType.ADMINISTRATOR) {
			return Response.status(401).build();
		}
		
		if(!reservationDAO.deleteReservation(id)) {
			return Response.status(404).build();
		}
		
		return Response.status(200).build();
	}
}

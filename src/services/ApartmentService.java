package services;

import java.util.HashMap;
import java.util.Map;

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

import dao.ApartmentDAO;
import dto.ApartmentDTO;
import model.Apartment;
import model.User;
import model.UserType;

@Path("/apartment")
public class ApartmentService {
	@Context
	ServletContext ctx;

	public ApartmentService() {
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("apartmentDAO")== null) {
			ctx.setAttribute("apartmentDAO", new ApartmentDAO(ctx.getRealPath("")));
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApartments(@Context HttpServletRequest request) {
		ApartmentDAO apartmentDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		
		Map<Long, Apartment> aps = new HashMap<Long, Apartment>();
		if(loggedInUser == null) {
			aps = apartmentDAO.getAllActiveApartments();
		}
		else if(loggedInUser.getUserType()==UserType.GUEST) {
			aps = apartmentDAO.getAllActiveApartments();
		}else if(loggedInUser.getUserType()==UserType.HOST){
			aps = apartmentDAO.getAllPostedBy(loggedInUser.getUsername());
		}
		else {
			aps = apartmentDAO.getApartments();
		}
		return Response.status(200).entity(aps).build();
	}
	
	@GET
	@Path("/nonActive")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNonActiveApartments(@Context HttpServletRequest request) {
		ApartmentDAO apartmentDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		
		Map<Long, Apartment> aps = new HashMap<Long, Apartment>();
		if(loggedInUser.getUserType() == UserType.HOST) {
			aps = apartmentDAO.getAllNonActiveApartmentsPostedBy(loggedInUser.getUsername());
			return Response.status(200).entity(aps).build();
		} else if (loggedInUser.getUserType() == UserType.ADMINISTRATOR){
			aps = apartmentDAO.getAllNonActiveApartments();
			return Response.status(200).entity(aps).build();
		}else {
			System.out.println("Only hosts and admins can access apartments that are not active.");
			return Response.status(403).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addApartment(ApartmentDTO apartmentDTO)  {
		
		ApartmentDAO apartmentDAO =(ApartmentDAO) ctx.getAttribute("apartmentDAO");
		
		if(apartmentDAO.apartmentExists(apartmentDTO.getId())) {
			return Response.status(409).build();
		}
		
		apartmentDAO.addApartment(apartmentDTO);		
		return Response.status(200).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editApartment(ApartmentDTO apartmentDTO, @Context HttpServletRequest request) {
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		ApartmentDAO apartmentDAO =(ApartmentDAO) ctx.getAttribute("apartmentDAO");
		
		if(loggedInUser.getUsername() != apartmentDTO.getHostUsername() || 
				loggedInUser.getUserType() == UserType.ADMINISTRATOR) {
			apartmentDAO.editApartment(apartmentDTO);
			return Response.status(200).build();
		}else {
			System.out.println("Only the apartment host and the administrator can make changes.");
			return Response.status(403).build();
		}
		
	}
	
	//izmeni ako ti se ne svidja doslo je do konflikta jer se oba zavrsavaju sa /{} pa nece da inicijalizuje
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApartmentById(@PathParam(value = "id") long id, @Context HttpServletRequest request) {
		ApartmentDAO apDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		
		Apartment ap = apDAO.getApartmentById(id);
		
		if(ap == null) {
			return Response.status(204).build();
		}
		
		return Response.status(200).entity(new ApartmentDTO(ap)).build();
	}
	
	@GET
	@Path("/user/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPostedBy(@PathParam(value = "username") String username, @Context HttpServletRequest request) {
		ApartmentDAO apDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		
		Map<Long, Apartment> aps = apDAO.getAllPostedBy(username);
		
		if(aps == null) {
			return Response.status(204).build();
		}
		
		return Response.status(200).entity(new HashMap<Long, Apartment>(aps)).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteApartment(ApartmentDTO apDTO, @Context HttpServletRequest request) {
		ApartmentDAO apDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		if(loggedInUser.getUserType() == UserType.ADMINISTRATOR ||
				(loggedInUser.getUsername() == apDTO.getHostUsername() && 
				loggedInUser.getUserType() == UserType.HOST)) {
			apDAO.removeApartment(apDTO.getId());
			return Response.status(200).build();
		}else {
			System.out.println("Only the apartment host and the administrator can remove an apartment.");
			return Response.status(403).build();
		}
	}
	
}






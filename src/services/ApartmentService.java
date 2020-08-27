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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.ApartmentDAO;
import dao.UserDAO;
import dto.ApartmentDTO;
import dto.UserCredentials;
import dto.UserDTO;
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
		
		Map<Long, Apartment> aps = new HashMap<Long, Apartment>();
		aps = apartmentDAO.getApartments();
		
		return Response.status(200).entity(aps).build();
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
	@Path("/{username}")
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
		
		apDAO.removeApartment(apDTO.getId());
		
		return Response.status(200).build();
	}
	
	
}






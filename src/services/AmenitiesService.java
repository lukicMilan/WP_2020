package services;

import java.util.ArrayList;
import java.util.List;

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

import dao.AmenitiesDAO;
import dao.ApartmentDAO;
import dto.AmenitiesDTO;
import dto.ApartmentDTO;
import dto.DeleteAmenitiesDTO;
import model.Amenities;
import model.User;

@Path("/amenities")
public class AmenitiesService {
	@Context
	ServletContext ctx;
	
	public AmenitiesService() {
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("amenitiesDAO")== null) {
			ctx.setAttribute("amenitiesDAO", new AmenitiesDAO(ctx.getRealPath("")));
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAmenities(@Context HttpServletRequest request) {
		AmenitiesDAO amenitiesDAO = (AmenitiesDAO) ctx.getAttribute("amenitiesDAO");
		
		ArrayList<AmenitiesDTO> ams = new ArrayList<>();
		ams = amenitiesDAO.getAmenities();
		
		return Response.status(200).entity(ams).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAmenities(AmenitiesDTO amenitiesDTO, @Context HttpServletRequest request)  {
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		
		AmenitiesDAO amenitiesDAO =(AmenitiesDAO) ctx.getAttribute("amenitiesDAO");
		
//		if(amenitiesDAO.amenityExists(amenitiesDTO.getId())) {
//			return Response.status(409).build();
//		}
//		if(loggedInUser.getUserType() != UserType.ADMINISTRATOR) {
//			System.out.println("Only the system administrator can add new amenities.");
//			return Response.status(403).build();
//		}
		AmenitiesDTO amsDTO = amenitiesDAO.addAmenity(amenitiesDTO);		
		return Response.status(200).entity(amsDTO).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAmenityById(@PathParam(value = "id") long id, @Context HttpServletRequest request) {
		AmenitiesDAO amDAO = (AmenitiesDAO) ctx.getAttribute("amenitiesDAO");
		
		Amenities amenity = amDAO.getAmenitiesById(id);
		
		if(amenity == null) {
			return Response.status(204).build();
		}
		
		return Response.status(200).entity(new AmenitiesDTO(amenity)).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editAmenity(AmenitiesDTO amenitiesDTO, @Context HttpServletRequest request) {
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		AmenitiesDAO amDAO = (AmenitiesDAO) ctx.getAttribute("amenitiesDAO");
//		
//		if(loggedInUser.getUserType() != UserType.ADMINISTRATOR) {
//			System.out.println("Only the system administrator can make changes to amenities.");
//			return Response.status(403).build();
//		}
		System.out.println("udje u edit");
		amDAO.editAmeniy(amenitiesDTO);
		return Response.status(200).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteAmenity(@PathParam(value = "id") long id, @Context HttpServletRequest request) {
		AmenitiesDAO amenitiesDAO = (AmenitiesDAO) ctx.getAttribute("amenitiesDAO");
		amenitiesDAO.removeAmenity(id);
		amenitiesDAO.saveAmenities();
		return Response.status(200).build();
	}

}

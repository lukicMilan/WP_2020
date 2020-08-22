package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.ApartmentDAO;
import dao.UserDAO;
import model.Apartment;

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
		
		Apartment ap = apartmentDAO.getApartmentById(1);
		
		return Response.status(200).entity(ap).build();
	}
	
	
}

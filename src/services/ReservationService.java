package services;

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
		
		return Response.status(200).entity(reservationDAO.getAll()).build();
	}
}

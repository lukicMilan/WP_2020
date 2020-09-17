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
import dao.ApartmentCommentDAO;
import dao.ApartmentDAO;
import dto.AmenitiesDTO;
import dto.ApartmentDTO;
import model.ApartmentComment;
import model.User;

@Path("/apartmentComment")
public class ApartmentCommentService {
	@Context
	ServletContext ctx;

	public ApartmentCommentService() {
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("apartmentCommentDAO")== null) {
			ctx.setAttribute("apartmentCommentDAO", new ApartmentCommentDAO(ctx.getRealPath("")));
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApartmentComments(@Context HttpServletRequest request) {
		ApartmentCommentDAO apartmentCommentDAO = (ApartmentCommentDAO) ctx.getAttribute("apartmentCommentDAO");
		
		List<ApartmentComment> comments = new ArrayList<>();
		comments = apartmentCommentDAO.getComments();
		
		return Response.status(200).entity(comments).build();
	}
	
	@GET
	@Path("/apartment/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCommentsForApartment(@PathParam(value = "id") long id, @Context HttpServletRequest request) {
		ApartmentCommentDAO acDAO = (ApartmentCommentDAO) ctx.getAttribute("apartmentCommentDAO");
		List<ApartmentComment> acs = new ArrayList<ApartmentComment>();
		acs = acDAO.getAllCommentsForApartment(id);
		
		return Response.status(200).entity(acs).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComment(ApartmentComment apartmentComment)  {
		
		ApartmentCommentDAO apartmentCommentDAO =(ApartmentCommentDAO) ctx.getAttribute("apartmentCommentDAO");
		
		if(apartmentCommentDAO.apartmentCommentExists(apartmentComment.getId())) {
			return Response.status(409).build();
		}
		
		apartmentCommentDAO.addApartmentComment(apartmentComment);
		return Response.status(200).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteApartmentComment(ApartmentComment apartmentComment, @Context HttpServletRequest request) {
		ApartmentCommentDAO apartmentCommentDAO = (ApartmentCommentDAO) ctx.getAttribute("apartmentCommentDAO");
		
		apartmentCommentDAO.removeApartmentComment(apartmentComment.getId());
		
		return Response.status(200).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editApartmentComment(ApartmentComment apartmentComment, @Context HttpServletRequest request) {
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		ApartmentCommentDAO acDAO =(ApartmentCommentDAO) ctx.getAttribute("apartmentCommentDAO");
		
//		if(loggedInUser.getUsername() != apartmentDTO.getHostUsername() || 
//				loggedInUser.getUserType() == UserType.ADMINISTRATOR) {
//			apartmentDAO.editApartment(apartmentDTO);
//			return Response.status(200).build();
//		}else {
//			System.out.println("Only the apartment host and the administrator can make changes.");
//			return Response.status(403).build();
//		}
		acDAO.editApartmentComment(apartmentComment);
		return Response.status(200).build();
		
	}
	
}

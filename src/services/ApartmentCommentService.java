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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.ApartmentCommentDAO;
import model.ApartmentComment;

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
	@Path("/apartment/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCommentsForApartment(@PathParam(value = "id") long id, @Context HttpServletRequest request) {
		ApartmentCommentDAO acDAO = (ApartmentCommentDAO) ctx.getAttribute("apartmentCommentDAO");
		List<ApartmentComment> acs = new ArrayList<ApartmentComment>();
		acs = acDAO.getAllCommentsForApartment(id);
		
		return Response.status(200).entity(new ArrayList(acs)).build();
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
	
}

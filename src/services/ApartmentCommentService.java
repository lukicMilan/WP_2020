package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import dao.ApartmentCommentDAO;


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
	
	
}

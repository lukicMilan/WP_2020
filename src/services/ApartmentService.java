package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
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
import javax.ws.rs.core.Response.ResponseBuilder;

import dao.AmenitiesDAO;
import dao.ApartmentDAO;
import dto.AmenitiesDTO;
import dto.ApartmentDTO;
import dto.ImagePathDTO;
import model.Amenities;
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

		ArrayList<ApartmentDTO> aps = new ArrayList<>();
//		if(loggedInUser == null) {
//			aps = apartmentDAO.getAllActiveApartments();
//		}
//		else if(loggedInUser.getUserType()==UserType.GUEST) {
//			aps = apartmentDAO.getAllActiveApartments();
//		}else if(loggedInUser.getUserType()==UserType.HOST){
//			aps = apartmentDAO.getAllPostedBy(loggedInUser.getUsername());
//		}
//		else {
//			aps = apartmentDAO.getApartments();
//		}
		aps = apartmentDAO.getApartments();
		return Response.status(200).entity(aps).build();
	}

	@GET
	@Path("/active")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActiveApartments(@Context HttpServletRequest request) {
		ApartmentDAO apartmentDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		
		ArrayList<ApartmentDTO> aps = new ArrayList<>();
//		if(loggedInUser.getUserType() == UserType.HOST) {
//			aps = apartmentDAO.getAllNonActiveApartmentsPostedBy(loggedInUser.getUsername());
//			return Response.status(200).entity(aps).build();
//		} else if (loggedInUser.getUserType() == UserType.ADMINISTRATOR){
//			aps = apartmentDAO.getAllNonActiveApartments();
//			return Response.status(200).entity(aps).build();
//		}else {
//			System.out.println("Only hosts and admins can access apartments that are not active.");
//			return Response.status(403).build();
//		}
		
		aps = apartmentDAO.getAllActiveApartments();
		
		return Response.status(200).entity(aps).build();
	}
	
	@PUT
	@Path("/deletedAmenity/{id}")
	public Response deletedAmenity(@PathParam(value = "id") long id, @Context HttpServletRequest request) {
//		AmenitiesDAO amenitiesDAO = (AmenitiesDAO) ctx.getAttribute("amenitiesDAO");
		ApartmentDAO apartmentDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
//		AmenitiesDTO amenitiesDTO = new AmenitiesDTO(amenitiesDAO.getAmenitiesById(id));
//		
//		List<Amenities> ams = new ArrayList<>();
//
//		System.out.println("brisem ameniti" + amenitiesDTO.getId() );
//		
//		List<ApartmentDTO> apartments = new ArrayList<>();
//		apartments = apartmentDAO.getApartments();
//		
//		for (int i = 0; i < apartments.size(); i++) {
//			ApartmentDTO apDTO = apartments.get(i);
//			if(apDTO.getAmenities().stream().filter(a-> a.getId()==amenitiesDTO.getId()).findFirst().isPresent()) {
//				System.out.println("iz apartmana" + apartments.get(i).getId());
//				ams = apDTO.getAmenities();
//				ams.remove(AmenitiesDTO.toAmenities(amenitiesDTO));
//				apDTO.setAmenities(ams);
//				apartmentDAO.editApartment(apDTO);
//			}
//		}
		apartmentDAO.deleteAmenity(id);
		return Response.status(200).build();
	}
	
	@GET
	@Path("/nonActive")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNonActiveApartments(@Context HttpServletRequest request) {
		ApartmentDAO apartmentDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		
		ArrayList<ApartmentDTO> aps = new ArrayList<>();
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
		System.out.println("usao");
		if(apartmentDAO.apartmentExists(apartmentDTO.getId())) {
			return Response.status(409).build();
		}
		
		return Response.status(200).entity(apartmentDAO.addApartment(apartmentDTO)).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editApartment(ApartmentDTO apartmentDTO, @Context HttpServletRequest request) {
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		ApartmentDAO apartmentDAO =(ApartmentDAO) ctx.getAttribute("apartmentDAO");
		
//		if(loggedInUser.getUsername() != apartmentDTO.getHostUsername() || 
//				loggedInUser.getUserType() == UserType.ADMINISTRATOR) {
//			apartmentDAO.editApartment(apartmentDTO);
//			return Response.status(200).build();
//		}else {
//			System.out.println("Only the apartment host and the administrator can make changes.");
//			return Response.status(403).build();
//		}
		apartmentDAO.editApartment(apartmentDTO);
		return Response.status(200).build();
		
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
		
		ArrayList<ApartmentDTO> aps = apDAO.getAllPostedBy(username);
		
		if(aps == null) {
			return Response.status(204).build();
		}
		
		return Response.status(200).entity(aps).build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteApartment(@PathParam(value = "id") long id, @Context HttpServletRequest request) {
		ApartmentDAO apDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
//		if(loggedInUser.getUserType() == UserType.ADMINISTRATOR ||
//				(loggedInUser.getUsername() == apDTO.getHostUsername() && 
//				loggedInUser.getUserType() == UserType.HOST)) {
//			apDAO.removeApartment(apDTO.getId());
//			return Response.status(200).build();
//		}else {
//			System.out.println("Only the apartment host and the administrator can remove an apartment.");
//			return Response.status(403).build();
//		}
		apDAO.removeApartment(id);
		return Response.status(200).build();
	}
	


	@GET
	@Path("/{apartmentId}/image/{imageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({"image/png", "image/jpg", "image/gif"})
	public Response getImage(@PathParam(value="apartmentId")long aparmtentId, @PathParam(value="imageId") long imageId, @Context HttpServletRequest request) {
		ApartmentDAO apartmentDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		
		Apartment apartment = apartmentDAO.getApartmentById(aparmtentId);
		
		File file = new File("C:\\Users\\msila\\Desktop\\ApartmentImages\\" + imageId + ".jpg");
		 
        ResponseBuilder responseBuilder = Response.ok((Object) file);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"" + imageId +"\"");
        return responseBuilder.build();
	}
	
	@POST
	@Path("/{apartmentId}/image")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadImage(@PathParam(value="apartmentId")long apartmentId, @Context HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException, ServletException {
		ApartmentDAO apartmentDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		
		Apartment apartment = apartmentDAO.getApartmentById(apartmentId);
		final String path = "C:\\Users\\msila\\Desktop\\ApartmentImages\\";
	    final Part filePart = request.getPart("file");
	    final String fileName = apartmentDAO.getImageIndex() + ".jpg";
		
	    OutputStream out = null;
	    InputStream filecontent = null;
	    final PrintWriter writer = response.getWriter();
	    
	    try {
	    	out = new FileOutputStream(new File(path + File.separator
	                + fileName));
	        filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        writer.println("New file " + fileName + " created at " + path);
	        
		} catch (Exception e) {
			writer.println("You either did not specify a file to upload or are "
	                + "trying to upload a file to a protected or nonexistent "
	                + "location.");
	        writer.println("<br/> ERROR: " + e.getMessage());


		} finally {
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	        if (writer != null) {
	            writer.close();
	        }
	    }
	    
		return Response.status(200).build();
	}
}






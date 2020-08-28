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

import dao.UserDAO;
import dto.UserCredentials;
import dto.UserDTO;
import model.User;
import model.UserType;

@Path("/user")
public class UserService {
	@Context
	ServletContext ctx;
	
	public UserService() {
	}
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("userDAO")== null) {
			ctx.setAttribute("userDAO", new UserDAO(ctx.getRealPath("")));
		}
	}
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers(@PathParam(value = "username") String username, @Context HttpServletRequest request) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		
		User user = userDAO.getUserByUsername(username);
		
		if(user == null) {
			return Response.status(204).build();
		}
		
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		
		if(loggedInUser.getUserType() != UserType.ADMINISTRATOR) {
			return Response.status(403).build();
		}

		
		return Response.status(200).entity(new UserDTO(user)).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(UserDTO userDTO) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		
		if(userDAO.usernameExists(userDTO.getUsername())) {
			return Response.status(409).build();
		}
		
		userDAO.addUser(userDTO);
		return Response.status(200).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeUserInfo(UserDTO userDTO, @Context HttpServletRequest request) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		
		User oldUserInfo = (User) request.getSession().getAttribute("loggedInUser");
		
		if(oldUserInfo == null){
			return Response.status(401).build();
		}
		
		if(userDAO.usernameExists(userDTO.getUsername())) {
			return Response.status(409).build();
		}
		
		if(oldUserInfo.getUsername() != userDTO.getUsername()) {
			userDAO.changeUsername(oldUserInfo.getUsername(), userDTO.getUsername());
		}
		
		userDAO.changeUserDetails(userDTO);
		
		return Response.status(200).build();
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteUser(UserCredentials userCredentials, @Context HttpServletRequest request) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		
		if((userDAO.credentialOk(userCredentials.getUsername(), userCredentials.getPassword()) 
				&& loggedInUser.getUsername() == userCredentials.getUsername()) 
				|| loggedInUser.getUserType() == UserType.ADMINISTRATOR) {
			userDAO.removeUser(userCredentials.getUsername());
		} else {
			return Response.status(401).build();
		}
		
		
		return Response.status(200).build();
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(UserCredentials userCredentials, @Context HttpServletRequest request) {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		
		if(!userDAO.credentialOk(userCredentials.getUsername(), userCredentials.getPassword())) {
			return Response.status(401).build();
		}
		
		request.getSession().setAttribute("loggedInUser", userDAO.getUserByUsername(userCredentials.getUsername()));
		
		return Response.status(200).build();
	}
	
	@POST
	@Path("/logout")
	public Response logout(@Context HttpServletRequest request) {
		request.getSession().removeAttribute("loggedInUser");
		return Response.status(200).build();
	}
}

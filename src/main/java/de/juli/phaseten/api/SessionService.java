package de.juli.phaseten.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.juli.phaseten.controller.Controller;
import de.juli.phaseten.exeption.NoPermissionExeption;
import de.juli.phaseten.model.PlaySession;
import de.juli.phaseten.model.Player;
import de.juli.phaseten.model.PlayerGroup;

@Path("/playsession")
public class SessionService extends RestService<PlaySession> {

	/**
	 * Alle Sessions
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValues(@QueryParam("hash") String hash) {
		return super.getValues(hash, PlaySession.class);				
	}

	/**
	 * Session einer bestimmten Id
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValueById(@QueryParam("hash") String hash, @PathParam("id") String id) {
		return super.getValueById(hash, id, PlaySession.class);
	}

	/**
	 * Anzahl der gespeicherten Datensaetze  
	 */
	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCount(@QueryParam("hash") String hash) {
		return super.getCount(hash, PlaySession.class);
	}
	
	/**
	 * Alle Sessions einer bestimmten Gruppe 
	 */
	@GET
	@Path("/playergroup/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSessionForGroup(@QueryParam("hash") String hash, @PathParam("id") String id) {
		Controller<PlayerGroup> controller = new Controller<>();
		String jsonResponse = "";
		try {
			super.checkPermission(hash);
			super.checkPermission(hash);
			List<PlaySession> models = controller.findById(stringToNumber(id), PlayerGroup.class).getSessions();
			jsonResponse = mapper.writeValueAsString(models);
		} catch (NoPermissionExeption e) {
			return noPermissionResult();	
		} catch (IllegalArgumentException  e) {
			return Response.status(Response.Status.NO_CONTENT).entity(String.format("No entry for [%s] with id [%s]", PlayerGroup.class.getName(), id)). build();			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse). build();
		}
		return createResponse(jsonResponse);
	}

	/**
	 * Alle Sessions eines Spielers 
	 */
	@GET
	@Path("/player/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSessionForPlayer(@QueryParam("hash") String hash, @PathParam("id") String id) {
		Controller<Player> controller = new Controller<>(); 
		String jsonResponse = "";
		try {
			super.checkPermission(hash);
			List<PlayerGroup> groups = controller.findById(stringToNumber(id), Player.class).getPlayerGroups();
			List<PlaySession> models = new ArrayList<>();
			for (PlayerGroup group : groups) {
				models.addAll(group.getSessions());
			}
			jsonResponse = mapper.writeValueAsString(models);
		} catch (NoPermissionExeption e) {
			return noPermissionResult();	
		} catch (IllegalArgumentException  e) {
			return Response.status(Response.Status.NO_CONTENT).entity(String.format("No entry for [%s] with id [%s]", PlayerGroup.class.getName(), id)). build();			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse). build();
		}
		return createResponse(jsonResponse);
	}

}

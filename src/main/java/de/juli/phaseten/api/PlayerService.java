package de.juli.phaseten.api;

import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.juli.phaseten.exeption.NoPermissionExeption;
import de.juli.phaseten.model.Player;
import de.juli.phaseten.model.PlayerGroup;

@Path("/player")
public class PlayerService extends RestService<Player> {

	/**
	 *  Alle Spieler
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValues(@QueryParam("hash") String hash) {
		return super.getValues(hash, Player.class);				
	}

	/**
	 * Spieler via id
	 */

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValueById(@QueryParam("hash") String hash, @PathParam("id") String id) {
		return super.getValueById(hash, id, Player.class);
	}
	
	/**
	 *  Alle Spieler einer Gruppe
	 */
	@GET
	@Path("/playergroup/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSessionsForPlayer(@QueryParam("hash") String hash, @PathParam("id") String id) {
		String jsonResponse = "";
		try {
			super.hasPermission(hash);
			List<PlayerGroup> models = super.findModel(id, Player.class).getPlayerGroups();
			jsonResponse = mapper.writeValueAsString(models);
		} catch (NoPermissionExeption e) {
			return noPermissionResult();	
		} catch (IllegalArgumentException  e) {
			return Response.status(Response.Status.NO_CONTENT).entity(String.format("No entry for [%s] with id [%s]", Player.class.getName(), id)). build();			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse). build();
		}
		return createResponse(jsonResponse);
	}
	

	/**
	 *  Anzahl der Spieler
	 */
	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCount(@QueryParam("hash") String hash) {
		return super.getCount(hash, Player.class);
	}
	
	/**
	 * Neuen Spieler anlegen
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/")
	public Response createNewModel(@QueryParam("hash") String hash, String jsonRequest) throws Exception {
		Player model = mapper.readValue(jsonRequest, Player.class);
		String jsonResponse = "";
		try {
			super.hasPermission(hash);
			Player mappedModel = checkExistence(model);
			if (mappedModel == null) {
				mappedModel = (Player) conrtroller.create(model);
			}
			jsonResponse = mapper.writeValueAsString(mappedModel);
		} catch (NoPermissionExeption e) {
			return noPermissionResult();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse).build();
		}
		return createResponse(jsonResponse);
	}

	private Player checkExistence(Player model) {
		if(model.getId() != null) {
			return model;
		}		
		try {
			model = (Player) super.conrtroller.findByName(Player.class, model.getName());
		} catch (NoResultException e) {
			return null;
		}
		return model;
	}

}

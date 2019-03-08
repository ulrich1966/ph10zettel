package de.juli.phaseten.api;

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
import de.juli.phaseten.model.Game;
import de.juli.phaseten.model.Player;
import de.juli.phaseten.model.Sheed;

@Path("/sheed")
public class SheedService extends RestService<Sheed> {

	/**
	 * Alle Zettel
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValues(@QueryParam("hash") String hash) {
		return super.getValues(hash, Sheed.class);
	}

	/**
	 * Zettel via id
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValueById(@QueryParam("hash") String hash, @PathParam("id") String id) {
		return super.getValueById(hash, id, Sheed.class);
	}

	
	/**
	 * Zettel eines Spiels
	 */
	@GET
	@Path("/game/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGamesForSession(@QueryParam("hash") String hash, @PathParam("id") String id) {
		Controller<Game> ctrl = new Controller<>();
		String jsonResponse = "";
		try {
			super.hasPermission(hash);
			Game game = ctrl.findById(stringToNumber(id), Game.class);
			List<Sheed> models = game.getSheeds();
			jsonResponse = mapper.writeValueAsString(models);
		} catch (NoPermissionExeption e) {
			return noPermissionResult();
		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.NO_CONTENT).entity(String.format("No entry for [%s] with id [%s]", Player.class.getName(), id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse).build();
		}
		return createResponse(jsonResponse);
	}
	
	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCount(@QueryParam("hash") String hash) {
		return super.getCount(hash, Sheed.class);
	}

}

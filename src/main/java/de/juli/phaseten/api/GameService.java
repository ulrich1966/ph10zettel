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
import de.juli.phaseten.model.Game;
import de.juli.phaseten.model.PlaySession;
import de.juli.phaseten.model.Player;
import de.juli.phaseten.model.PlayerGroup;

@Path("/game")
public class GameService extends RestService<Game> {

	/**
	 * Alle Spiele
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValues(@QueryParam("hash") String hash) {
		return super.getValues(hash, Game.class);
	}

	/**
	 * Spiel via id
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValueById(@QueryParam("hash") String hash, @PathParam("id") String id) {
		return super.getValueById(hash, id, Game.class);
	}

	/**
	 * Spiele einer Spielrunde
	 */
	@GET
	@Path("/playsession/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGamesForSession(@QueryParam("hash") String hash, @PathParam("id") String id) {
		Controller<PlaySession> ctrl = new Controller<>();
		String jsonResponse = "";
		try {
			super.hasPermission(hash);
			PlaySession session = ctrl.findById(stringToNumber(id), PlaySession.class);
			List<Game> models = super.conrtroller.findByPlaySession(session);
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

	/**
	 * Spiele einer Gruppe
	 */
	@GET
	@Path("/playergroup/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGamesForGroup(@QueryParam("hash") String hash, @PathParam("id") String id) {
		Controller<PlayerGroup> ctrl = new Controller<>();
		String jsonResponse = "";
		try {
			super.hasPermission(hash);
			List<PlaySession> sessions = ctrl.findById(stringToNumber(id), PlayerGroup.class).getSessions();
			List<Game> models = new ArrayList<>();
			for (PlaySession session : sessions) {
				models.addAll(session.getGames());
			}
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

	/**
	 * Spiele eines Spielers
	 */
	@GET
	@Path("/player/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSessionsForPlayer(@QueryParam("hash") String hash, @PathParam("id") String id) {
		Controller<Player> ctrl = new Controller<>();
		String jsonResponse = "";
		try {
			super.hasPermission(hash);
			List<PlayerGroup> groups = ctrl.findById(stringToNumber(id), Player.class).getPlayerGroups();
			List<Game> models = new ArrayList<>();
			for (PlayerGroup group : groups) {
				for (PlaySession session : group.getSessions()) {
					models.addAll(session.getGames());
				}
			}
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
		return super.getCount(hash, Game.class);
	}

}

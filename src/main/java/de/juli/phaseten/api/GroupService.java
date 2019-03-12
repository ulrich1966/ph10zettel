package de.juli.phaseten.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.RollbackException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.util.JSONPObject;

import de.juli.phaseten.controller.Controller;
import de.juli.phaseten.exeption.NoPermissionExeption;
import de.juli.phaseten.model.Player;
import de.juli.phaseten.model.PlayerGroup;

@Path("/playergroup")
public class GroupService extends RestService<PlayerGroup> {
	
	/**
	 *  Alle Gruppen
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValues(@QueryParam("hash") String hash) {
		return super.getValues(hash, PlayerGroup.class);
	}
	
	/**
	 *  Gruppe einer Id
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValueById(@QueryParam("hash") String hash, @PathParam("id") String id) {
		return super.getValueById(hash, id, PlayerGroup.class);
	}

	/**
	 *  Alle Gruppen eines Spielers
	 */
	@GET
	@Path("/player/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGroupsForPlayer(@QueryParam("hash") String hash, @PathParam("id") String id) {
		Controller<Player> controller = new Controller<>();
		String jsonResponse = "";
		try {
			super.hasPermission(hash);
			List<PlayerGroup> models = controller.findById(stringToNumber(id), Player.class).getPlayerGroups();
			jsonResponse = mapper.writeValueAsString(models);
		} catch (NoPermissionExeption e) {
			return noPermissionResult();
		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.NO_CONTENT).entity(String.format("Kein Eintrag für [ %s ] mit der ID [ %s ]", Player.class.getName(), id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse).build();
		}
		return createResponse(jsonResponse);
	}
	
	/**
	 *	Spieler einer Gruppe zuweisen 
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{gid}/player/{pid}")
	public Response addPlayerToGroup(@QueryParam("hash") String hash, @PathParam("gid") String gid, @PathParam("pid") String pid) throws Exception {
		//PlayerGroup model = mapper.readValue(jsonRequest, PlayerGroup.class);
		String jsonResponse = "";
		try {
			super.hasPermission(hash);
			PlayerGroup group = findModel(gid, PlayerGroup.class);
			
			if(group == null) {
				String msg = String.format("Spielergruppe mit ID: [ %s ] nicht gefunden!", gid);
				throw new IllegalArgumentException(msg);
			}
			
			Controller<Player> controller = new Controller<>();
			Player player = controller.findById(stringToNumber(pid), Player.class);
			if(player == null) {
				String msg = String.format("Spieler mit ID: [ %s ] nicht gefunden!", gid);
				throw new IllegalArgumentException(msg);
			}
			
			group.addPlayer(player);
			super.conrtroller.update(group);
			
			JSONPObject jsonGroup = new JSONPObject("group", mapper.writeValueAsString(group));
			JSONPObject jsonPlayer = new JSONPObject("player", mapper.writeValueAsString(player));
			Map<String, JSONPObject> map = new HashMap<>();
			map.put("group", jsonGroup);
			map.put("player", jsonPlayer);
			
			jsonResponse = mapper.writeValueAsString(map);
		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		} catch (NoPermissionExeption e) {
			return noPermissionResult();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse).build();
		}
		return createResponse(jsonResponse);
	}

	/**
	 *  Anzahl der Gruppen
	 */
	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCount(@QueryParam("hash") String hash) {
		return super.getCount(hash, PlayerGroup.class);
	}

	/**
	 * Neue Gruppe anlegen oder Aenderungen speichern
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/")
	public Response createGroup(@QueryParam("hash") String hash, String jsonRequest) throws Exception {
		PlayerGroup model = mapper.readValue(jsonRequest, PlayerGroup.class);
		String jsonResponse = "";
		try {
			super.hasPermission(hash);
			boolean exist = checkExistence(model);
			if (!exist) {
				model = (PlayerGroup) conrtroller.create(model);
				jsonResponse = mapper.writeValueAsString(model);
			} else {
				jsonResponse = String.format("Eine Spielergruppe mit dem Namen [ %s ] existeirt bereits!", model.getName());
				return Response.status(Response.Status.CONFLICT).entity(jsonResponse).build();
			}
		} catch (NoPermissionExeption e) {
			return noPermissionResult();
		} catch (RollbackException e) {
			String msg = String.format("Spielergruppe [ %s ] konnte nicht erzeugt werden! Vielleicht gibt es diesen Gruppennamen schon!?", model.getName());
			return Response.status(Response.Status.NO_CONTENT).entity(msg).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse).build();
		}
		return createResponse(jsonResponse);
	}
	
	/**
	 * Gruppe loeschen
	 */
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delGroup(@QueryParam("hash") String hash, @PathParam("id") String id) {
		String jsonResponse = "";
		boolean success = false;
		try {
			super.hasPermission(hash);
			PlayerGroup model = super.findModel(id, PlayerGroup.class);
			if(model != null) {
				model.getPlayers().clear();
//				for(PlaySession s : model.getSessions()) {
//					model.removeSession(s);
//				}
				success = super.conrtroller.delete(model);
			} else {
				throw new IllegalArgumentException();
			}
			if(success) {
				jsonResponse = mapper.writeValueAsString(String.format("Gruppe [ %s ] geloescht", model.getName()));				
			} else {
				jsonResponse = mapper.writeValueAsString(String.format("Gruppe [ %s ] konnte nicht geloescht werden. Es sind noch Daten mit dieser Gruppe verbunden", model.getName()));								
			}
		} catch (NoPermissionExeption e) {
			return noPermissionResult();
		} catch (RollbackException e) {
			return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.NO_CONTENT).entity(String.format("Kein Eintrag für [ %s ] mit der ID [ %s ]", Player.class.getName(), id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return createResponse(jsonResponse);
	}
	
	private boolean checkExistence(PlayerGroup model) {
		if(model.getId() != null) {
			return true;
		}
		model = (PlayerGroup) super.conrtroller.findByName(PlayerGroup.class, model.getName());
		if(model != null) {
			return true;
		}
		return false;
	}
}

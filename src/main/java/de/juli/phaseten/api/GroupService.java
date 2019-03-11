package de.juli.phaseten.api;

import java.util.List;

import javax.persistence.RollbackException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.juli.phaseten.controller.Controller;
import de.juli.phaseten.exeption.NoPermissionExeption;
import de.juli.phaseten.model.Player;
import de.juli.phaseten.model.PlayerGroup;

@Path("/playergroup")
public class GroupService extends RestService<PlayerGroup> {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValues(@QueryParam("hash") String hash) {
		return super.getValues(hash, PlayerGroup.class);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValueById(@QueryParam("hash") String hash, @PathParam("id") String id) {
		return super.getValueById(hash, id, PlayerGroup.class);
	}

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
		return super.getCount(hash, PlayerGroup.class);
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/")
	public Response postMessage(@QueryParam("hash") String hash, String jsonRequest) throws Exception {
		PlayerGroup model = mapper.readValue(jsonRequest, PlayerGroup.class);
		String jsonResponse = "";
		try {
			super.hasPermission(hash);
			if (model.getId() == null) {
				model = (PlayerGroup) conrtroller.create(model);
			} else {
				model = (PlayerGroup) conrtroller.update(model);
			}
			jsonResponse = mapper.writeValueAsString(model);
		} catch (NoPermissionExeption e) {
			return noPermissionResult();
		} catch (RollbackException e) {
			String msg = String.format("Spielergruppe [ %s ] konnte nicht erzeugt werden! Vielleicht gibt es diese Gruppe schon!?", model.getName());
			return Response.status(Response.Status.NO_CONTENT).entity(msg).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse).build();
		}
		return createResponse(jsonResponse);
	}
}

package de.juli.phaseten.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.juli.phaseten.exeption.NoPermissionExeption;
import de.juli.phaseten.model.Player;

@Path("/player")
public class PlayerService extends RestService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValues(@QueryParam("hash") String hash) {
		String jsonResponse = "";
		try {
			if(!super.checkPermission(hash)) {
				throw new NoPermissionExeption();
			}
			List<Player> all = conrtroller.findAll("Player");
			jsonResponse = mapper.writeValueAsString(all);
		} catch (NoPermissionExeption e) {
			return super.noPermissionResult();			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()). build();
		}
		return super.createResponse(jsonResponse);
				
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVAlueById(@QueryParam("hash") String hash, @PathParam("id") String id) {
		String jsonResponse = "";
		try {
			if(!super.checkPermission(hash)) {
				throw new NoPermissionExeption();
			}
			Player player = super.conrtroller.findById(super.stringToNumber(id), Player.class);
			jsonResponse = mapper.writeValueAsString(player);
		} catch (NoPermissionExeption e) {
			return super.noPermissionResult();	
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse). build();
		}
		return super.createResponse(jsonResponse);
	}
	
	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCount(@QueryParam("hash") String hash) {
		String jsonResponse = "";
		try {
			if(!super.checkPermission(hash)) {
				throw new NoPermissionExeption();
			}
			List<Player> all = conrtroller.findAll("Player");
			jsonResponse = ""+all.size();
		} catch (NoPermissionExeption e) {
			return super.noPermissionResult();			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse). build();
		}
		return super.createResponse(jsonResponse);
	}

}

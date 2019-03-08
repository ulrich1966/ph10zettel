package de.juli.phaseten.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	public Response getVAlueById(@QueryParam("hash") String hash, @PathParam("id") String id) {
		return super.getValueById(hash, id, PlayerGroup.class);
	}
	
	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCount(@QueryParam("hash") String hash) {
		return super.getCount(hash, PlayerGroup.class);
	}

}

package de.juli.phaseten.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
 
@Path("/")
public class Wellcome {
 
	@GET
	public Response getMsg(@PathParam("param") String msg) {
		String output = "Moin";
		return Response.status(200).entity(output).build();
	}
 
}

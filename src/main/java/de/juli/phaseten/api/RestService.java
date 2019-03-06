package de.juli.phaseten.api;

import java.util.Date;
import java.util.Locale;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.juli.phaseten.controller.Controller;
import de.juli.phaseten.model.Player;

abstract class RestService {
	protected Controller<Player> conrtroller = new Controller<Player>();
	protected ObjectMapper mapper = new ObjectMapper();
	
	protected Long stringToNumber(String id) {
		try {
			return new Long(id);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	protected Response nullResult() {
		return Response.status(401).entity("no result").build();
	}

	public boolean checkPermission(String hash) {
		return true;
	}

	public Response noPermissionResult() {
		return Response.status(Response.Status.FORBIDDEN).entity("no psermisson").build();
	}

	public Response createResponse(String jsonResponse) {
		return Response.ok(jsonResponse)
				.header(HttpHeaders.CONTENT_LENGTH, jsonResponse.length())
				.header(HttpHeaders.ACCEPT_ENCODING, "utf-8")				
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)				
				.header(HttpHeaders.HOST, "")
				.header(HttpHeaders.CONTENT_LANGUAGE, "de")
				.header(HttpHeaders.LAST_MODIFIED, new Date().toString())
				.header(HttpHeaders.LAST_MODIFIED, new Date().toString())
				.header("Allow:", "Allow:GET, HEAD, PUT")			
				.header("Content-Security-Policy", "default-src 'self'")			
				.header("X-Content-Security-Policy", "default-src 'self'")			
				.header("X-Webkit-CSP", "default-src 'self'")
				.build();
	}
}

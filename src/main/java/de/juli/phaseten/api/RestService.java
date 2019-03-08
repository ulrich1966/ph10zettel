package de.juli.phaseten.api;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.juli.phaseten.controller.Controller;
import de.juli.phaseten.exeption.NoPermissionExeption;
import de.juli.phaseten.model.Model;

abstract class RestService<T extends Model> {
	protected Controller<T> conrtroller = new Controller<T>();
	protected ObjectMapper mapper = new ObjectMapper();
	
	protected Response getValues(String hash, Class<T> clazz) {
		String jsonResponse = "";
		try {
			if(!checkPermission(hash)) {
				throw new NoPermissionExeption();
			}
			List<T> all = conrtroller.findAll(clazz);
			jsonResponse = mapper.writeValueAsString(all);
		} catch (NoPermissionExeption e) {
			e.printStackTrace();
			return noPermissionResult();			
		} catch (IllegalArgumentException  e) {
			e.printStackTrace();
			return Response.status(Response.Status.NO_CONTENT).entity(String.format("No entry for [%s]", clazz.getName())). build();			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()). build();
		}
		return createResponse(jsonResponse);
	}
	
	protected Response getValueById(String hash, String id, Class<T> clazz) {
		String jsonResponse = "";
		try {
			if(!checkPermission(hash)) {
				throw new NoPermissionExeption();
			}
			T model = conrtroller.findById(stringToNumber(id), clazz);
			jsonResponse = mapper.writeValueAsString(model);
		} catch (NoPermissionExeption e) {
			return noPermissionResult();	
		} catch (IllegalArgumentException  e) {
			return Response.status(Response.Status.NO_CONTENT).entity(String.format("No entry for [%s] with id [%s]", clazz.getName(), id)). build();			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse). build();
		}
		return createResponse(jsonResponse);
	}
	
	protected Response getCount(String hash, Class<T> clazz) {
		String jsonResponse = "";
		try {
			if(!checkPermission(hash)) {
				throw new NoPermissionExeption();
			}
			List<T> list = conrtroller.findAll(clazz);
			jsonResponse = ""+list.size();
		} catch (NoPermissionExeption e) {
			return noPermissionResult();
		} catch (IllegalArgumentException  e) {
			return Response.status(Response.Status.NO_CONTENT).entity(String.format("No entry for [%s]", clazz.getName())). build();			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jsonResponse). build();
		}
		return createResponse(jsonResponse);		
	}
	
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

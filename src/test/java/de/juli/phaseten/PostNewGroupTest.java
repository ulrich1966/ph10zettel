package de.juli.phaseten;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.juli.phaseten.model.PlayerGroup;

public class PostNewGroupTest {

	@Test
	public void test() throws Exception {
		final String POST_PARAMS = getGroupAsJson();
		System.out.println(POST_PARAMS);

		URL obj = new URL("http://localhost:8080/ph10zettel/api/playergroup/");
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);
		OutputStream os = connection.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();

		int responseCode = connection.getResponseCode();
		System.out.println("POST Response Code :  " + responseCode);
		System.out.println("POST Response Message : " + connection.getResponseMessage());
		
		ObjectMapper mapper = new ObjectMapper();
		PlayerGroup group = mapper.readValue(connection.getInputStream(), PlayerGroup.class);

		System.out.println(group.toString());
	}

	private String getGroupAsJson() throws Exception {
		PlayerGroup group = new PlayerGroup("test");
		String result = new ObjectMapper().writeValueAsString(group);
		return result;
	}

}

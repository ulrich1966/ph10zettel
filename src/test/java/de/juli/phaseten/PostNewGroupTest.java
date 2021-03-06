package de.juli.phaseten;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.juli.phaseten.model.PlayerGroup;
import de.juli.phaseten.util.ApiUrl;

public class PostNewGroupTest {

	@Test
	public void test() throws Exception {
		final String POST_PARAMS = getModelAsJson();
		System.out.println(POST_PARAMS);

		URL obj = new URL(ApiUrl.GROUPS);
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

	private String getModelAsJson() throws Exception {
		PlayerGroup group = new PlayerGroup("test");
		String result = new ObjectMapper().writeValueAsString(group);
		return result;
	}

}

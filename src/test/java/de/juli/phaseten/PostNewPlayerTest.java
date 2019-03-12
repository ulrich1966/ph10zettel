package de.juli.phaseten;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.juli.phaseten.model.Player;
import de.juli.phaseten.util.ApiUrl;

public class PostNewPlayerTest {

	@Test
	public void test() throws Exception {
		final String POST_PARAMS = getModelAsJson();
		System.out.println(POST_PARAMS);

		URL obj = new URL(ApiUrl.PLAYER);
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);
		
		OutputStream os = connection.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();

		try {
			int responseCode = connection.getResponseCode();
			System.out.println("POST Response Code :  " + responseCode);
			System.out.println("POST Response Message : " + connection.getResponseMessage());			
			ObjectMapper mapper = new ObjectMapper();
			Player model = mapper.readValue(connection.getInputStream(), Player.class);
			System.out.println(""+model.toString());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private String getModelAsJson() throws Exception {
		Player model = new Player("Weihnachtsmann", "Ist denn schon wieder Weihnachten?", null);
		String result = new ObjectMapper().writeValueAsString(model);
		return result;
	}

}

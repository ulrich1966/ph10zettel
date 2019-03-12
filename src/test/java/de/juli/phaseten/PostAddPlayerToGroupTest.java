package de.juli.phaseten;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.juli.phaseten.model.PlayerGroup;

public class PostAddPlayerToGroupTest {

	@Test
	public void test() throws Exception {
		final String POST_PARAMS = getGroupAsJson();
		System.out.println(POST_PARAMS);

		URL obj = new URL("http://localhost:8080/ph10zettel/api/playergroup/");
		HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		postConnection.setRequestMethod("POST");
		postConnection.setRequestProperty("Content-Type", "application/json");
		postConnection.setDoOutput(true);
		OutputStream os = postConnection.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();

		int responseCode = postConnection.getResponseCode();
		System.out.println("POST Response Code :  " + responseCode);
		System.out.println("POST Response Message : " + postConnection.getResponseMessage());

		// success
		BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
	}

	private String getGroupAsJson() throws Exception {
		PlayerGroup group = new PlayerGroup("test");
		String result = new ObjectMapper().writeValueAsString(group);
		return result;
	}

}

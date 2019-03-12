package de.juli.phaseten;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import de.juli.phaseten.util.ApiUrl;
import de.juli.phaseten.util.RandomModel;

public class AddPlayerToGroupTest {

	@Test
	public void test() throws Exception {

		Long gid = RandomModel.fetchRandomGroup().getId();
		Long pid = RandomModel.fetchRandomPlayer().getId();
		String url$ = String.format(ApiUrl.GROUP_ADD_PLAYER, gid, pid);
		System.out.println(url$);

		HttpURLConnection connection = (HttpURLConnection) new URL(url$).openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);

		int responseCode = connection.getResponseCode();
		System.out.println("Response Code :  " + responseCode);
		System.out.println("Response Message : " + connection.getResponseMessage());

		ObjectMapper mapper = new ObjectMapper();
		Map<String, JSONPObject> map = mapper.readValue(connection.getInputStream(), new TypeReference<Map<String, JSONPObject>>(){});
		
		System.out.println("DONE!");
	}
}

package de.juli.phaseten;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

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

		//ObjectMapper mapper = new ObjectMapper();
		
		System.out.println("DONE!");
	}
}

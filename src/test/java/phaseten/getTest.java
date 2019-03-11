package phaseten;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.juli.phaseten.model.PlayerGroup;

public class getTest {

	@Test
	public void test() throws Exception {
		URL url = new URL("http://localhost:8080/ph10zettel/api/playergroup/");
	    String readLine = null;
	    HttpURLConnection conection = (HttpURLConnection) url.openConnection();
	    conection.setRequestMethod("GET");
	    conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
	    int responseCode = conection.getResponseCode();

	    if (responseCode == HttpURLConnection.HTTP_OK) {
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(conection.getInputStream()));
	        StringBuffer response = new StringBuffer();
	        while ((readLine = in .readLine()) != null) {
	            response.append(readLine);
	        } in .close();
	        // print result
	        System.out.println("JSON String Result " + response.toString());
	        //GetAndPost.POSTRequest(response.toString());
	    } else {
	        System.out.println("GET NOT WORKED");
	    }
	}

	private String getGroupAsJson() throws Exception {
		PlayerGroup group = new PlayerGroup("test");
		String result = new ObjectMapper().writeValueAsString(group);
		return result;
	}

}

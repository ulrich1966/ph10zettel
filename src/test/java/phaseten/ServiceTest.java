package phaseten;

import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.juli.phaseten.api.PlayerService;
import de.juli.phaseten.model.Player;

public class ServiceTest {

	private List<Player> list;
	private static Long id;

	@Test
	public void test() {
		System.out.println("moin");
	}

	@Test
	public void playersTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		PlayerService service = new PlayerService();
		Response player = service.getValues("bla");
		
		String jsonString = player.getEntity().toString();
		System.out.println(jsonString+"\n");
		System.out.println(jsonString+"\n");
		System.out.println(jsonString+"\n");
		
		//Player[] array= mapper.readValue(gsonString, Player[].class);
		
		list = mapper.readValue(jsonString, new TypeReference<List<Player>>(){});
		list.forEach(m -> System.out.println(m.toString()));
		id = list.get(0).getId();
		
		System.out.println("DONE\n");
	}

	
	@Test
	public void countTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		PlayerService service = new PlayerService();
		Response count = service.getCount("bla");
		String jsonString = count.getEntity().toString();
		System.out.println(jsonString);
		System.out.println("DONE\n");
	}

	@Test
	public void playerTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		PlayerService service = new PlayerService();
		Response count = service.getVAlueById("bal", ""+id);
		String jsonString = count.getEntity().toString();
		System.out.println(jsonString);
		System.out.println("DONE\n");
	}
	

}

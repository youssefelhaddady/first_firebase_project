package ma.usf.examples.firebase.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import ma.usf.examples.firebase.entity.Person;

@Service
public class UserService {

	private List<Person> playersList;

	public UserService() {
		this.playersList = new ArrayList<>(Arrays.asList(
				new Person(1, "Lionel Messi", stringToDate("24-06-1987"), 1.7, "Argentina",
						Arrays.asList("FC Barcelona")),
				new Person(2, "Cristiano Ronaldo", stringToDate("05-02-1985"), 1.87, "Portugal",
						Arrays.asList("Sporting CP", "Manchester United", "Real Madrid", "Juventus")),
				new Person(3, "Ronaldo de Assis Moreira", stringToDate("21-03-1980"), 1.81, "Brazil", null)));

	}

	public List<Person> getAll() {
		return playersList;
	}

	public Person getPlayer(long id) {
		return playersList.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
	}

	public Person addPlayer(Person player) {
		long id = Collections.max(playersList, Comparator.comparing(Person::getId)).getId();
		player.setId(++id);
		playersList.add(player);
		return player;
	}

	public Person updatePlayer(long id, Person player) {
		Person modified = getPlayer(id);

		if (player.getName() != null && !player.getName().isEmpty())
			modified.setName(player.getName());

		if (player.getBorn() != null)
			modified.setBorn(player.getBorn());

		if (player.getHeight() > 1)
			modified.setHeight(player.getHeight());

		if (player.getCountry() != null && !player.getCountry().isEmpty())
			modified.setCountry(player.getCountry());

		if (player.getTeams() != null && !player.getTeams().isEmpty())
			modified.setTeams(player.getTeams());

		deletePlayer(id);
		modified.setId(id);
		addPlayer(modified);
		return modified;
	}

	public boolean deletePlayer(long id) {
		Iterator<Person> it = playersList.iterator();
		while (it.hasNext()) {
			long itId = it.next().getId();
			if (itId == id) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	private Date stringToDate(String str) {
		DateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}

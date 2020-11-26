package ma.usf.examples.firebase.service;

import java.util.List;

import ma.usf.examples.firebase.entity.Person;

public interface PlayerService {

	public List<Person> getAll();
	
	public Person getPlayer(long id);
	
	public Person addPlayer(Person player);
	
	public Person updatePlayer(long id, Person player);
	
	public boolean deletePlayer(long id);
}

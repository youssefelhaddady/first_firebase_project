package ma.usf.examples.firebase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.usf.examples.firebase.entity.Person;
import ma.usf.examples.firebase.service.PlayerFirebaseService;

@RestController()
@RequestMapping("/api/players")
public class PlayerController {

	@Autowired
//	private PlayerStaticService playerService;
	private PlayerFirebaseService playerService;

	@GetMapping
	public List<Person> all() {
		return playerService.getAll();
	}

	@GetMapping("/{id}")
	public Person getPlayer(@PathVariable Long id) {
		return playerService.getPlayer(id);
	}

	@PostMapping
	public Person createPlayer(@RequestBody Person person) {
		return playerService.addPlayer(person);
	}

	@PutMapping("/{id}")
	public Person updatePlayer(@PathVariable Long id, @RequestBody Person person) {
		return playerService.updatePlayer(id, person);
	}

	@DeleteMapping("/{id}")
	public boolean deletePlayer(@PathVariable Long id) {
		return playerService.deletePlayer(id);
	}

}

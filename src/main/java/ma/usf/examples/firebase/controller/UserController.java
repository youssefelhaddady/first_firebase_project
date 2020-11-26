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
import ma.usf.examples.firebase.service.UserService;

@RestController()
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<Person> all() {
		return userService.getAll();
	}

	@GetMapping("/{id}")
	public Person getPlayer(@PathVariable Long id) {
		return userService.getPlayer(id);
	}

	@PostMapping
	public Person createPlayer(@RequestBody Person person) {
		return userService.addPlayer(person);
	}

	@PutMapping("/{id}")
	public Person updatePlayer(@PathVariable Long id, @RequestBody Person person) {
		return userService.updatePlayer(id, person);
	}

	@DeleteMapping("/{id}")
	public boolean deletePlayer(@PathVariable Long id) {
		return userService.deletePlayer(id);
	}

}

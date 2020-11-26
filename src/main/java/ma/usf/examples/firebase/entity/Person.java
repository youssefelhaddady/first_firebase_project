package ma.usf.examples.firebase.entity;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data class Person {

	private long id;
	private String name;
	private Date born;
	private double height;
	private String country;
	private List<String> teams;
}

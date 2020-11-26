package ma.usf.examples.firebase.service;

import java.io.FileInputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseInitializer {

	@Value("${path.to.service-account-key}")
	private String pathToServiceAccountKey;

	@Value("${database.url}")
	private String databaseUrl;

	@PostConstruct
	public void init() {

		FileInputStream serviceAccount;
		try {
			serviceAccount = new FileInputStream(pathToServiceAccountKey);

			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount)).setDatabaseUrl(databaseUrl).build();

			FirebaseApp.initializeApp(options);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

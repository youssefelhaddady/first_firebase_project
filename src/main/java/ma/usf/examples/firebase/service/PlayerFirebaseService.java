package ma.usf.examples.firebase.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import ma.usf.examples.firebase.entity.Person;

@Service
public class PlayerFirebaseService implements PlayerService {

	private final String PLAYERS_COL = "players";
	private static long currentPlayerId = 1;

	@Override
	public List<Person> getAll() {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		CollectionReference collectionReference = dbFirestore.collection(PLAYERS_COL);
		ApiFuture<QuerySnapshot> future = collectionReference.get();

		try {
			QuerySnapshot query = future.get();

			if (!query.isEmpty()) {
				return query.toObjects(Person.class);
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Person getPlayer(long id) {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection(PLAYERS_COL).document("" + id);

		return getPlayerFromDocumentReference(documentReference);
	}

	private Person getPlayerFromDocumentReference(DocumentReference documentReference) {
		ApiFuture<DocumentSnapshot> future = documentReference.get();

		try {
			DocumentSnapshot document = future.get();

			if (document.exists()) {
				return document.toObject(Person.class);
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Person addPlayer(Person player) {
		player.setId(currentPlayerId++);
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection(PLAYERS_COL).document("" + player.getId());
//		ApiFuture<WriteResult> collectionsApiFuture = 
		documentReference.set(player);
		return getPlayerFromDocumentReference(documentReference);
	}

	@Override
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

		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection(PLAYERS_COL).document("" + id);
		documentReference.set(modified);
		return getPlayerFromDocumentReference(documentReference);
	}

	@Override
	public boolean deletePlayer(long id) {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> future = dbFirestore.collection(PLAYERS_COL).document("" + id).delete();
		try {
			if (future != null && future.get().getUpdateTime() != null)
				return true;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}

}

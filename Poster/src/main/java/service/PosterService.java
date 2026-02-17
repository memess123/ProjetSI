package service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import donnees.Poster;

public class PosterService {

	// Simulation de base de données en mémoire
	private Map<String, Poster> database = new ConcurrentHashMap<>();

	public PosterService() {
		// Données de test
		Poster p1 = new Poster("1", "Star Wars - A New Hope", "Poster classique 1977", LocalDate.of(1977, 5, 25),
				"movie1");
		Poster p2 = new Poster("2", "Empire Strikes Back", "Poster Hoth", LocalDate.of(1980, 5, 21), "movie1");
		database.put(p1.getIdPoster(), p1);
		database.put(p2.getIdPoster(), p2);
	}

	// Récupérer un poster par ID
	public Poster findById(String idPoster) {
		return database.get(idPoster);
	}

	// Récupérer tous les posters d'un film spécifique
	public List<Poster> findByMovieId(String movieId) {
		return database.values().stream().filter(p -> movieId.equals(p.getFilmId())).collect(Collectors.toList());
	}

	// Créer un poster
	public void create(Poster poster) {
		if (poster.getIdPoster() == null || poster.getIdPoster().isEmpty()) {
			throw new IllegalArgumentException("L'ID est requis");
		}
		database.put(poster.getIdPoster(), poster);
	}

	// Mettre à jour un poster
	public boolean update(String idPoster, Poster poster) {
		if (database.containsKey(idPoster)) {
			poster.setIdPoster(idPoster); // Sécurité : on force l'ID de l'URL
			database.put(idPoster, poster);
			return true;
		}
		return false;
	}

	// Supprimer un poster
	public boolean delete(String idPoster) {
		return database.remove(idPoster) != null;
	}
}
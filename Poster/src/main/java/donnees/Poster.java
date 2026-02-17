package donnees;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Poster {
	private String idPoster;
	private String titre;
	private String description;

	// Formatage automatique de la date pour le JSON (yyyy-MM-dd)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate datePublication;

	// J'ajoute filmId pour pouvoir lier un poster à un film (nécessaire pour la
	// logique métier)
	private String filmId;

	public Poster() {
	} // Constructeur vide obligatoire pour Jackson

	public Poster(String idPoster, String titre, String description, LocalDate datePublication, String filmId) {
		this.idPoster = idPoster;
		this.titre = titre;
		this.description = description;
		this.datePublication = datePublication;
		this.filmId = filmId;
	}

	// Getters et Setters
	public String getIdPoster() {
		return idPoster;
	}

	public void setIdPoster(String idPoster) {
		this.idPoster = idPoster;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDatePublication() {
		return datePublication;
	}

	public void setDatePublication(LocalDate datePublication) {
		this.datePublication = datePublication;
	}

	public String getFilmId() {
		return filmId;
	}

	public void setFilmId(String filmId) {
		this.filmId = filmId;
	}
}
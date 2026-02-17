package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import donnees.Poster;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PosterService;

import java.io.IOException;
import java.util.List;

// Ce servlet capture TOUTES les requêtes commençant par /api/
@WebServlet(name = "AppServlet", urlPatterns = { "/api/*" })
public class PosterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7926404763334347664L;
	private PosterService posterService;
	private ObjectMapper mapper;

	@Override
	public void init() throws ServletException {
		this.posterService = new PosterService();
		this.mapper = new ObjectMapper();
		// Module nécessaire pour gérer les LocalDate Java 8+
		this.mapper.registerModule(new JavaTimeModule());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo(); // ex: "/posters/12" ou "/movies/5/posters"
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		if (path == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		// Cas 1: /movies/{id}/posters
		if (path.startsWith("/movies/") && path.endsWith("/posters")) {
			// Extraction de l'ID du film
			String[] parts = path.split("/"); // ["", "movies", "{id}", "posters"]
			if (parts.length == 4) {
				String movieId = parts[2];
				List<Poster> posters = posterService.findByMovieId(movieId);
				mapper.writeValue(resp.getWriter(), posters);
				return;
			}
		}

		// Cas 2: /posters/{id_poster}
		if (path.startsWith("/posters/")) {
			String posterId = path.substring("/posters/".length());
			Poster poster = posterService.findById(posterId);

			if (poster != null) {
				mapper.writeValue(resp.getWriter(), poster);
			} else {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Poster non trouvé");
			}
			return;
		}

		resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL invalide");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();

		// Cas: /posters (Création)
		if ("/posters".equals(path) || "/posters/".equals(path)) {
			try {
				// Conversion JSON -> Objet Java
				Poster newPoster = mapper.readValue(req.getReader(), Poster.class);
				posterService.create(newPoster);

				resp.setStatus(HttpServletResponse.SC_CREATED);
				resp.getWriter().write("{\"message\": \"Poster créé avec succès\"}");
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Données invalides");
			}
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();

		// Cas: /posters/{id_poster}
		if (path != null && path.startsWith("/posters/")) {
			String posterId = path.substring("/posters/".length());

			try {
				Poster posterToUpdate = mapper.readValue(req.getReader(), Poster.class);
				boolean updated = posterService.update(posterId, posterToUpdate);

				if (updated) {
					resp.setStatus(HttpServletResponse.SC_OK);
					resp.getWriter().write("{\"message\": \"Poster mis à jour\"}");
				} else {
					resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Poster non trouvé");
				}
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Données invalides");
			}
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();

		// Cas: /posters/{id_poster}
		if (path != null && path.startsWith("/posters/")) {
			String posterId = path.substring("/posters/".length());
			boolean deleted = posterService.delete(posterId);

			if (deleted) {
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.getWriter().write("{\"message\": \"Poster supprimé\"}");
			} else {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Poster non trouvé");
			}
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
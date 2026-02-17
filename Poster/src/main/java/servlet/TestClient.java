package servlet;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

public class TestClient {

	// Assurez-vous que le port et le nom du contexte (poster-api) sont corrects
	// Si vous avez fait "Run on Server", Eclipse utilise souvent le nom du projet
	// comme contexte.
	private static final String BASE_URL = "http://localhost:8080/Poster/api/posters";

	public static void main(String[] args) {
		try {
			HttpClient client = HttpClient.newHttpClient();

			// --- TEST 1: POST (Création) ---
			String jsonInput = """
					{
					   "idPoster": "eclipse-test-1",
					   "titre": "Test Eclipse",
					   "description": "Ceci est un test depuis une classe Main Java",
					   "datePublication": "2023-10-20",
					   "filmId": "movie-eclipse"
					}
					""";

			HttpRequest postRequest = HttpRequest.newBuilder().uri(URI.create(BASE_URL))
					.header("Content-Type", "application/json").POST(BodyPublishers.ofString(jsonInput)).build();

			HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
			System.out.println("POST Status: " + postResponse.statusCode());
			System.out.println("POST Body: " + postResponse.body());

			// --- TEST 2: GET (Vérification) ---
			HttpRequest getRequest = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/eclipse-test-1")).GET()
					.build();

			HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
			System.out.println("GET Status: " + getResponse.statusCode());
			System.out.println("GET Body: " + getResponse.body());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
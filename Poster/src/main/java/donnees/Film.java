package donnees;

public class Film {
    private String id;
    private String titre;
    private String description;

    public Film() {}

    public Film(String id, String titre, String description) {
        this.id = id;
        this.titre = titre;
        this.description = description;
    }

    // Getters et Setters standard...
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
package domaine;

public class Offre {
    private int id;
    private String Code;
    private String libelle;
    private String description;
    private Entreprise entreprise;
	public Offre() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Entreprise getEntreprise() {
		return entreprise;
	}
	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}
	@Override
	public String toString() {
		return "Offre [id=" + id + ", Code=" + Code + ", libelle=" + libelle + ", description=" + description
				+ ", entreprise=" + entreprise + "]";
	}
    
}

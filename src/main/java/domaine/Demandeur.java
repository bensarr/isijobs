package domaine;


public class Demandeur extends Utilisateur{
    private int id;
    private String nom;
    private String prenom;
	public Demandeur() {
		super();
	}
	
	public Demandeur(String login, String password, String adresse, String telephone, String email, String role) {
		super(login, password, adresse, telephone, email, role);
	}

	public Demandeur(int id, String nom, String prenom) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Override
	public String toString() {
		return "Demandeur [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", getLogin()=" + getLogin()
				+ ", getPassword()=" + getPassword() + ", getRole()=" + getRole() + ", getAdresse()=" + getAdresse()
				+ ", getTelephone()=" + getTelephone() + ", getEmail()=" + getEmail() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}    
}

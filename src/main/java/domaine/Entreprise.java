package domaine;

public class Entreprise extends Utilisateur{
    private int id;
    private String ninea;
    private String denomination;
	public Entreprise() {
		super();
	}
	
	public Entreprise(String login, String password, String adresse, String telephone, String email, String role) {
		super(login, password, adresse, telephone, email, role);
	}

	public Entreprise(int id, String ninea, String denomination) {
		super();
		this.id = id;
		this.ninea = ninea;
		this.denomination = denomination;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNinea() {
		return ninea;
	}
	public void setNinea(String ninea) {
		this.ninea = ninea;
	}
	public String getDenomination() {
		return denomination;
	}
	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	@Override
	public String toString() {
		return "Entreprise [id=" + id + ", ninea=" + ninea + ", denomination=" + denomination + ", getLogin()="
				+ getLogin() + ", getPassword()=" + getPassword() + ", getRole()=" + getRole() + ", getAdresse()="
				+ getAdresse() + ", getTelephone()=" + getTelephone() + ", getEmail()=" + getEmail() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
    
}

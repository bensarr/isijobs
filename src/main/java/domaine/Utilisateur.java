package domaine;

public class Utilisateur {
    private int id;
    private String login;
    private String password;
    private String adresse;
    private String telephone;
    private String email;
    private String role;
	public Utilisateur() {
		super();
	}
	
	public Utilisateur(String role) {
		super();
		this.role = role;
	}
	
	public Utilisateur(String login, String password, String adresse, String telephone, String email, String role) {
		super();
		this.login = login;
		this.password = password;
		this.adresse = adresse;
		this.telephone = telephone;
		this.email = email;
		this.role = role;
	}

	public Utilisateur(int id, String login, String password, String adresse, String telephone, String email,
			String role) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.adresse = adresse;
		this.telephone = telephone;
		this.email = email;
		this.role = role;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean hasRole(String libelle)
	{
		boolean rep=false;
		if(this.role.equalsIgnoreCase(libelle))
			rep=true;
		return rep;
	}
	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", login=" + login + ", password=" + password + ", role=" + role + "]";
	}
    
    
}

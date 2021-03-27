package domaine;

public class Postuler {
    private int id;
    private Offre offre;
    private Demandeur demandeur;
    private boolean reponse;
	public Postuler() {
		super();
	}
	public Postuler(Offre offre, Demandeur demandeur) {
		super();
		this.offre = offre;
		this.demandeur = demandeur;
		this.reponse = false;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Offre getOffre() {
		return offre;
	}
	public void setOffre(Offre offre) {
		this.offre = offre;
	}
	public Demandeur getDemandeur() {
		return demandeur;
	}
	public void setDemandeur(Demandeur demandeur) {
		this.demandeur = demandeur;
	}
	public boolean isReponse() {
		return reponse;
	}
	public void setReponse(boolean reponse) {
		this.reponse = reponse;
	}
	@Override
	public String toString() {
		return "Postuler [id=" + id + ", offre=" + offre + ", demandeur=" + demandeur + ", reponse=" + reponse + "]";
	}
    
}

package repository;

import java.util.List;

import domaine.Demandeur;
import domaine.Entreprise;
import domaine.Offre;
import domaine.Postuler;

public interface IPostulerRepository extends IRepository<Postuler>{
	public List<Postuler> findAllByEntreprise(Entreprise entreprise);
	public List<Postuler> findAllByDemandeur(Demandeur demandeur);
	public List<Postuler> findAllByOffre(Offre offre);
	public Postuler findByOffreAndDemandeur(Offre offre, Demandeur demandeur);

}

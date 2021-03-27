package repository;

import java.util.List;

import domaine.Demandeur;
import domaine.Entreprise;
import domaine.Offre;

public interface IOffreRepository extends IRepository<Offre>{
	public List<Offre> findAllByEntreprise(Entreprise entreprise);
	public List<Offre> findAllByDemandeur(Demandeur demandeur);
}

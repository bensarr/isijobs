package repository;

import domaine.Utilisateur;


public interface IUtilisateurRepository extends IRepository<Utilisateur>{
    public Utilisateur connexion(String login,String password);
}

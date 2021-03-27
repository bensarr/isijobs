package service.console;

import domaine.Offre;
import domaine.Postuler;
import service.IDisplayService;

public class ConsoleDisplayService implements IDisplayService {

	@Override
	public void displayWelcome() {
		System.out.println("Bienvenu sur la plateforme ISI-Jobs");

	}

	@Override
	public void displayMenuPrincipal() {
		System.out.println("> l \t Se Connecter (Log in)");
		System.out.println("> s \t S'inscrire (Sign up)");
	}

	public void displayUnknowOption() {
		System.out.println("Choix Inconnu !!!");		
	}
	@Override
	public void displayUnknowUser() {
		System.out.println("Login ou mot de pass incorrect !!!");		
	}

	@Override
	public void displayMenuEntreprise() {
		System.out.println("> a \t Faire un Appel d'offre (Nouveau)");
		System.out.println("> c \t Consulter Candidatures (Les Demandes)");
		System.out.println("> p \t Mon Profil (Profil)");
		System.out.println("> x \t Se Déconnecter (Log Out)");
		
	}

	@Override
	public void displayMenuDemandeur() {
		System.out.println("> l \t Liste Appels d'offre (Liste)");
		System.out.println("> c \t Consulter Candidatures (Mes Demandes)");
		System.out.println("> p \t Mon Profil (Profil)");
		System.out.println("> x \t Se Déconnecter (Log Out)");		
	}
	@Override
	public void displayMenuAdmin() {
		System.out.println("> l \t Liste Utilisateurs (Liste)");
		System.out.println("> p \t Mon Profil (Profil)");
		System.out.println("> x \t Se Déconnecter (Log Out)");		
	}

	@Override
	public void displayLogin() {
		System.out.println("Formulaire CONNEXION");
		
	}
	@Override
	public void displaySignUp() {
		System.out.println("Formulaire d'Inscription");		
	}
	@Override
	public void displaySignUpSuccess() {
		System.out.println("Inscription Success");		
	}
	@Override
	public void displaySignUpError() {
		System.out.println("Inscription Error Vueiller Réessayer plus tard!");		
	}
	@Override
	public void displayPostulerSuccess() {
		System.out.println("Candidature Success");		
	}
	@Override
	public void displayPostulerError() {
		System.out.println("Candidature Error Vueiller Réessayer plus tard!");		
	}

	@Override
	public void displayMenuUser() {
		System.out.println("> e \t Entreprise ");
		System.out.println("> d \t Demandeur");
		System.out.println("> x \t Retour (Back)");		
	}
	@Override
	public void displayLabel(String label) {
		System.out.print(label+":\t");		
	}

	@Override
	public void displayOffre(Offre o) {
		System.out.println("> "+o.getId()+"**"+o.getCode()+"**"+o.getLibelle());		
	}
	@Override
	public void displayEmptyList(String classe) {
		System.out.println("\t Aucun Element "+classe+" n'a été trouvé!!!");		
	}

	@Override
	public void displayPostuler_Entreprise(Postuler p) {
		System.out.println("> "+p.getId()+"**"+p.getDemandeur().getNom()+"**"+p.getDemandeur().getPrenom()+"**"+this.displayResponse(p.isReponse()));
	}
	@Override
	public void displayPostuler_Demandeur(Postuler p) {
		System.out.println("> "+p.getId()+"**"+p.getOffre().getLibelle()+"**"+this.displayResponse(p.isReponse()));
	}
	private String displayResponse(boolean rep)
	{
		String reponse="NON";
		if(rep)
			reponse="OUI";
		return reponse;		
	}

	@Override
	public void displayQuestionYesOrNo() {
		System.out.println("> [oui/Non] \t Vouler postuler à cette Offre \n \t[oui/Non]:\t");
		
	}
	@Override
	public void displayQuestionYesOrNoCandidate() {
		System.out.println("> [oui/Non] \t Vouler Accepter cette candidature  \n \t[oui/Non]:\t");
		
	}

	@Override
	public void displayCandidate(Postuler p) {
		System.out.println("**************************************\n"
				+ "**\t"+p.getDemandeur().getNom()+"\t"+p.getDemandeur().getPrenom()+
				"\t**\n**\t"+p.getDemandeur().getEmail()+"\t"+p.getDemandeur().getTelephone()+
				"\t**\n**\t Réponse: "+this.displayResponse(p.isReponse())
				+"\t**\n**************************************");		
	}
	

}

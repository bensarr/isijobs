package service;

import domaine.Offre;
import domaine.Postuler;

public interface IDisplayService {
	void displayWelcome();

    void displayMenuPrincipal();
    
    void displayUnknowOption();
    void displayUnknowUser();
    
    void displayMenuEntreprise();

    void displayMenuDemandeur();

    void displayLogin();
    void displayLabel(String label);

	void displayMenuAdmin();

	void displaySignUp();

	public void displayPostulerSuccess();
	public void displayPostulerError();
	public void displayCandidate(Postuler p);
	void displayMenuUser();

	void displaySignUpSuccess();

	void displaySignUpError();

	void displayOffre(Offre o);
	
	void displayPostuler_Entreprise(Postuler p);
	
	void displayPostuler_Demandeur(Postuler p);

	void displayQuestionYesOrNo();

	void displayEmptyList(String classe);

	void displayQuestionYesOrNoCandidate();

}

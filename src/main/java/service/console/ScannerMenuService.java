package service.console;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import domaine.Demandeur;
import domaine.Entreprise;
import domaine.Offre;
import domaine.Postuler;
import domaine.Utilisateur;
import repository.jdbc.DataSource;
import repository.jdbc.DemandeurRepository;
import repository.jdbc.EntrepriseRepository;
import repository.jdbc.OffreRepository;
import repository.jdbc.PostgresSQLDataSource;
import repository.jdbc.PostulerRepository;
import repository.jdbc.UtilisateurRepository;
import service.IDisplayService;
import service.IMenuService;

public class ScannerMenuService implements IMenuService{
	private final IDisplayService displayService;
	private final Scanner scanner;
	private final UtilisateurRepository utilisateurRepository;
	private final DemandeurRepository demandeurRepository;
	private final EntrepriseRepository entrepriseRepository;
	private final OffreRepository offreRepository;
	private final PostulerRepository postulerRepository;
	private final DataSource dataSource;
	

	public ScannerMenuService(IDisplayService displayService) {
		this.displayService = displayService;
		this.scanner = new Scanner(System.in);
		this.dataSource=new PostgresSQLDataSource();
		this.utilisateurRepository=new UtilisateurRepository(dataSource);
		this.demandeurRepository=new DemandeurRepository(dataSource);
		this.entrepriseRepository=new EntrepriseRepository(dataSource);
		this.offreRepository=new OffreRepository(dataSource);
		this.postulerRepository=new PostulerRepository(dataSource);
	}

	private String scannerChoice() {
        return scanner.nextLine();
    }
	private void serviceBienvenu(String choice)
	{
		switch (choice) {
		case "l":
			this.serviceLogin();
			break;
		case "s":
			this.serviceSignUp();
			break;

		default:
			displayService.displayUnknowOption();
			this.displayMenu();
			break;
		}
		
	}
	@Override
	public void displayMenu(){
		String choice=this.scannerChoice();
		this.serviceBienvenu(choice);
	}
	private void serviceLogin()
	{
		displayService.displayLogin();
		displayService.displayLabel("Login");
		String login=this.scannerChoice();
		displayService.displayLabel("Password");
		String password=this.scannerChoice();
		Utilisateur u= this.utilisateurRepository.connexion(login, password);
		if (u != null) {
			switch (u.getRole()) {
			case "ROLE_ENTREPRISE":
				Entreprise e=this.entrepriseRepository.findById(u.getId());
				this.serviceEntreprise(e);
				break;
			case "ROLE_DEMANDEUR":
				Demandeur d=this.demandeurRepository.findById(u.getId());
				this.serviceDemandeur(d);
				
				break;
			case "ROLE_ADMIN":
				displayService.displayMenuAdmin();
				break;
			default:
				displayService.displayUnknowUser();
				displayService.displayMenuPrincipal();
				this.displayMenu();
				break;
			}
		} else {
			displayService.displayUnknowUser();
			displayService.displayMenuPrincipal();
			this.displayMenu();
		}
	}
	private void serviceDemandeur(Demandeur d)
	{
		displayService.displayMenuDemandeur();
		String choice=this.scannerChoice();
		switch (choice) {
		case "l":
			//postuler
			this.serviceCandidature(d);			
			break;
		case "c":
			//les offres postulée par le demandeur
			List<Postuler> postulers= this.postulerRepository.findAllByDemandeur(d);
			this.serviceListPostuler(postulers, d.getRole());			
			break;
		case "p":
			
			break;
		case "x":
			displayService.displayMenuPrincipal();
			this.displayMenu();
			break;

		default:
			displayService.displayUnknowOption();
			this.serviceDemandeur(d);
			break;
		}
		this.serviceDemandeur(d);
	}
	private void serviceEntreprise(Entreprise e)
	{
		displayService.displayMenuEntreprise();
		List<Offre> offres= this.offreRepository.findAllByEntreprise(e);
		String choice=this.scannerChoice();
		switch (choice) {
		case "a":
			if(this.serviceAddOffre(e)!=0)
				displayService.displaySignUpSuccess();
			else
				displayService.displaySignUpError();
			break;
		case "c":
			//les offres de l'entreprise
			this.serviceEntreprise_lesCandidatures(offres);
			break;
		case "p":
			
			break;
		case "x":
			displayService.displayMenuPrincipal();
			this.displayMenu();
			break;

		default:
			displayService.displayUnknowOption();
			this.serviceEntreprise(e);
			break;
		}
		this.serviceEntreprise(e);
	}
	private void serviceCandidature(Demandeur demandeur)
	{
		List<Offre> offres= this.offreRepository.findAll();
		this.serviceListOffre(offres);
		Offre offre=this.getOffreOnList(offres, this.scannerChoice());
		Postuler postuler=this.postulerRepository.findByOffreAndDemandeur(offre, demandeur);
		if(postuler!=null) 
		{	
			this.displayService.displayPostuler_Demandeur(postuler);
		}
		else{
			this.displayService.displayQuestionYesOrNo();
			String Choice=this.scannerChoice();
			switch (Choice) {
			case "oui":
				if(postulerRepository.create(new Postuler(offre,demandeur))!=0)
					this.displayService.displayPostulerSuccess();
				else
					this.displayService.displayPostulerError();
				break;

			default:
				break;
			}
			this.serviceDemandeur(demandeur);
		}
			
		
	}
	private void serviceEntreprise_lesCandidatures(List<Offre> offres) {
		this.serviceListOffre(offres);
		Offre offre=this.getOffreOnList(offres, this.scannerChoice());
		if(offre!=null)
		{
			this.serviceCandidature(offre);
		}			
		else {
			displayService.displayUnknowOption();
		}
		this.serviceEntreprise(offre.getEntreprise());
	}
	private void serviceCandidature(Offre offre)
	{
		List<Postuler> postulers=postulerRepository.findAllByOffre(offre);
		this.serviceListPostuler(postulers,offre.getEntreprise().getRole());
		String choice=this.scannerChoice();
		Postuler postuler=this.getPostulerOnList(postulers, choice);
		if(postuler!=null)
		{	// vérifier si la réponse est vraie avant
			if(postuler.isReponse()==true)
			{
				System.out.println("Cette candidature a déja été acceptée");
			}
			else
			{
				this.displayService.displayCandidate(postuler);
				this.displayService.displayQuestionYesOrNoCandidate();
				String Choice=this.scannerChoice();
				switch (Choice) {
				case "oui":
					if(postulerRepository.update(postuler)!=0)
						this.displayService.displayPostulerSuccess();
					else
						this.displayService.displayPostulerError();
					break;

				default:
					break;
				}			
			}
			
		}			
		else 
			displayService.displayUnknowOption();
		
	}
	private Postuler getPostulerOnList(List<Postuler> postulers,String choice)
	{
		return postulers.stream()
		.filter(
				o->Integer.parseInt(choice)==o.getId()
				)
		.findAny()
		.orElse(null);
	}
	private Offre getOffreOnList(List<Offre> offres,String choice)
	{
		return offres.stream()
		.filter(
				o->Integer.parseInt(choice)==o.getId()
				)
		.findAny()
		.orElse(null);
	}
	private void serviceSignUp()
	{
		displayService.displaySignUp();
		displayService.displayMenuUser();
		String choice=this.scannerChoice();
		switch (choice) {
		case "e":
			if(this.serviceSignUpEntreprise(this.serviceSignup())!=0)
				displayService.displaySignUpSuccess();
			else
				displayService.displaySignUpError();
			break;
		case "d":
			if(this.serviceSignUpDemandeur((this.serviceSignup()))!=0)
				displayService.displaySignUpSuccess();
			else
				displayService.displaySignUpError();
			break;
		default:
			displayService.displayWelcome();
			displayService.displayMenuPrincipal();
			this.displayMenu();
			break;
		}
		displayService.displayMenuPrincipal();
		this.displayMenu();
	}
	private int serviceSignUpEntreprise(String[] user)
	{	Entreprise u=new Entreprise(user[0],user[1],user[2],user[3],user[4],"ROLE_ENTREPRISE");
		displayService.displayLabel("Ninea");
		u.setNinea(this.scannerChoice());
		displayService.displayLabel("Dénomination");
		u.setDenomination(this.scannerChoice());
		return this.entrepriseRepository.create(u);
	}

	private int serviceSignUpDemandeur(String[] user)
	{	Demandeur u=new Demandeur(user[0],user[1],user[2],user[3],user[4],"ROLE_DEMANDEUR");
		displayService.displayLabel("Nom");
		u.setNom((this.scannerChoice()));
		displayService.displayLabel("Prénom");
		u.setPrenom((this.scannerChoice()));
		return this.demandeurRepository.create(u);
	}
	private String[] serviceSignup()
	{
		displayService.displayLabel("Adresse");
		String adresse=this.scannerChoice();
		displayService.displayLabel("Téléphone");
		String telephone=this.scannerChoice();
		displayService.displayLabel("Email");
		String email=this.scannerChoice();
		displayService.displayLabel("Login");
		String login=this.scannerChoice();
		displayService.displayLabel("Password");
		String password=this.scannerChoice();
		String[] tab={login,password,adresse,telephone,email};
		return tab;
	}
	private int serviceAddOffre(Entreprise entreprise)
	{
		Offre o=new Offre();
		displayService.displayLabel("Code");
		o.setCode(this.scannerChoice());
		displayService.displayLabel("Libelle");
		o.setLibelle(this.scannerChoice());
		displayService.displayLabel("Description");
		o.setDescription(this.scannerChoice());
		o.setEntreprise(entreprise);
		
		return offreRepository.create(o);
	}
	private void serviceListOffre(List<Offre> offres)
	{
		if(offres.isEmpty())
			displayService.displayEmptyList(Offre.class.getSimpleName());
		else
		{
			System.out.println("*************************************\n \tListe des offres\n*************************************");
			offres.stream().forEach(o->displayService.displayOffre(o));
			System.out.println("*************************************");
		}
	}
	private void serviceListPostuler(List<Postuler> postulers,String role)
	{
		if(postulers.isEmpty())
			displayService.displayEmptyList(Postuler.class.getSimpleName());
		else
		{
			System.out.println("*************************************\n \tListe des Candidatures\n*************************************");
			if(role.equalsIgnoreCase("ROLE_ENTREPRISE"))
				postulers.stream().forEach(p->displayService.displayPostuler_Entreprise(p));
			else
				postulers.stream().forEach(p->displayService.displayPostuler_Demandeur(p));
			System.out.println("*************************************");
		}
	}

}

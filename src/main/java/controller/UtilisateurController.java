package controller;

import service.IDisplayService;
import service.console.ConsoleDisplayService;
import service.console.ScannerMenuService;

public class UtilisateurController {
	private final ScannerMenuService menuService;
	private final IDisplayService displayService;

	public UtilisateurController() {
		displayService=new ConsoleDisplayService();
		menuService=new ScannerMenuService(displayService);
	}
	
	public void Index()
	{
		displayService.displayWelcome();
		displayService.displayMenuPrincipal();
		menuService.displayMenu();
	}
	
	

}

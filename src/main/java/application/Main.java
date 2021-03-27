package application;

import java.io.IOException;

import controller.UtilisateurController;

public class Main {
	public static void main(String[] args) throws InterruptedException, IOException{
		UtilisateurController utilisateurController=new UtilisateurController();
		utilisateurController.Index();
	}

}

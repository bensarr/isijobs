package repository.jdbc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import domaine.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UtilisateurRepositoryTest {

	UtilisateurRepository utilisateurRepository;
    DataSource dataSource;

	@BeforeEach
	void initialisation(){
        dataSource = mock(DataSource.class);
        utilisateurRepository= new UtilisateurRepository(dataSource);
        
    }
    @Test
    void testGetAll() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        ResultSet resultSet = mock(ResultSet.class);
        when(dataSource.executeSelect()).thenReturn(resultSet);//Matchers:anyString-when
        when(resultSet.next()).thenReturn(true, true, true, true, false);
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        //assertEquals(4, utilisateurs.size());
    }
    @Test
    void connexionFail() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        ResultSet resultSet = mock(ResultSet.class);
        when(dataSource.executeSelect()).thenReturn(resultSet);
        Utilisateur utilisateur = utilisateurRepository.connexion(anyString(),anyString());
        //assertThat(utilisateur).isNotNull();
        assertNotNull(utilisateur);
    }

}

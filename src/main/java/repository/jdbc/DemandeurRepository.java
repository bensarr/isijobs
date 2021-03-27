package repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import domaine.Demandeur;
import repository.IDemandeurRepository;

public class DemandeurRepository implements IDemandeurRepository {
	
	private final String SQL_INSERT_DEMANDEUR="INSERT INTO public.utilisateur(\r\n"
			+ "	login, password, role, adresse, telephone, email, nom, prenom)\r\n"
			+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	private final String SQL_SELECT_ONE="SELECT * FROM public.utilisateur\r\n"
    		+ "WHERE id = ?";
	
	private DataSource datasource;
	
	public DemandeurRepository(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public int create(Demandeur objet) {
		int result=0;
        
		datasource.initPS(SQL_INSERT_DEMANDEUR);
        try {
        	datasource.getPstm().setString(1,objet.getLogin());
        	datasource.getPstm().setString(2,objet.getPassword());
        	datasource.getPstm().setString(3,objet.getRole());
        	datasource.getPstm().setString(4,objet.getAdresse());
        	datasource.getPstm().setString(5,objet.getTelephone());
        	datasource.getPstm().setString(6,objet.getEmail());
        	datasource.getPstm().setString(7,objet.getNom());
        	datasource.getPstm().setString(8,objet.getPrenom());
            
            datasource.executeMaj();
            ResultSet rs=datasource.getPstm().getGeneratedKeys();
            if(rs.next()){
                result=rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DemandeurRepository.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              datasource.CloseConnection();
        }
      
        return result;
	}

	@Override
	public int update(Demandeur objet) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Demandeur> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Demandeur findById(int id) {
		Demandeur demandeur = null;
        datasource.initPS(SQL_SELECT_ONE);
        try {
        	datasource.getPstm().setInt(1, id);
            ResultSet rs = datasource.executeSelect();
            if(rs.next()){
            	demandeur=new Demandeur();
                demandeur.setId(rs.getInt("id"));
                demandeur.setLogin(rs.getString("login"));
                demandeur.setPassword(rs.getString("password"));
                demandeur.setRole(rs.getString("role"));
                demandeur.setAdresse(rs.getString("adresse"));
                demandeur.setTelephone(rs.getString("telephone"));
                demandeur.setEmail(rs.getString("email"));
                demandeur.setNom(rs.getString("nom"));
                demandeur.setPrenom(rs.getString("prenom"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DemandeurRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        datasource.CloseConnection();
        return demandeur;
	}

	

}

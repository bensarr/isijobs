package repository.jdbc;

import domaine.Utilisateur;
import repository.IUtilisateurRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtilisateurRepository implements IUtilisateurRepository{
	//String Requetes
	private final String SQL_INSERT_ADMIN="INSERT INTO public.utilisateur(\r\n"
			+ "	login, password, role, adresse, telephone, email)\r\n"
			+ "	VALUES (?, ?, ?, ?, ?, ?);";
    private final String SQL_SELECT_LOGIN_PASSWORD="SELECT * FROM public.utilisateur\r\n"
    		+ "WHERE login LIKE ?\r\n"
    		+ "AND password LIKE ?";
	
	private DataSource datasource;
	
	public UtilisateurRepository(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public Utilisateur connexion(String login, String password) {
		datasource.initPS(SQL_SELECT_LOGIN_PASSWORD);
        Utilisateur u=null;
     try { 
    	 datasource.getPstm().setString(1, login);
    	 datasource.getPstm().setString(2, password);
           ResultSet rs=datasource.executeSelect();
           if(rs.next()){
        	   u=new Utilisateur();
               u.setId(rs.getInt("id"));
               u.setLogin(rs.getString("login"));
               u.setPassword(rs.getString("password"));
               u.setRole(rs.getString("role"));
               u.setAdresse(rs.getString("adresse"));
               u.setTelephone(rs.getString("telephone"));
               u.setEmail(rs.getString("email"));
           }
     } catch (SQLException ex) {
         Logger.getLogger(UtilisateurRepository.class.getName()).log(Level.SEVERE, null, ex);
     }
     datasource.CloseConnection();
     return u;
	}

	@Override
	public int create(Utilisateur objet) {
		int result=0;
        
		datasource.initPS(SQL_INSERT_ADMIN);
        try {
        	datasource.getPstm().setString(1,objet.getLogin());
        	datasource.getPstm().setString(2,objet.getPassword());
        	datasource.getPstm().setString(3,objet.getRole());
        	datasource.getPstm().setString(4,objet.getAdresse());
        	datasource.getPstm().setString(5,objet.getTelephone());
        	datasource.getPstm().setString(6,objet.getEmail());
            
            datasource.executeMaj();
            ResultSet rs=datasource.getPstm().getGeneratedKeys();
            if(rs.next()){
                result=rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurRepository.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              datasource.CloseConnection();
        }
      
        return result;
	}

	@Override
	public int update(Utilisateur objet) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Utilisateur> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utilisateur findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}

package repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import domaine.Entreprise;
import repository.IEntrepriseRepository;

public class EntrepriseRepository implements IEntrepriseRepository {
	private final String SQL_INSERT_ENTREPRISE="INSERT INTO public.utilisateur(\r\n"
			+ "	login, password, role, adresse, telephone, email, ninea, denomination)\r\n"
			+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private final String SQL_SELECT_ONE="SELECT * FROM public.utilisateur\r\n"
    		+ "WHERE id = ?";
	
	private DataSource datasource;
	
	public EntrepriseRepository(DataSource datasource) {
		this.datasource = datasource;
	}

	@Override
	public int create(Entreprise objet) {
		int result=0;
        
		datasource.initPS(SQL_INSERT_ENTREPRISE);
        try {
        	datasource.getPstm().setString(1,objet.getLogin());
        	datasource.getPstm().setString(2,objet.getPassword());
        	datasource.getPstm().setString(3,objet.getRole());
        	datasource.getPstm().setString(4,objet.getAdresse());
        	datasource.getPstm().setString(5,objet.getTelephone());
        	datasource.getPstm().setString(6,objet.getEmail());
        	datasource.getPstm().setString(7,objet.getNinea());
        	datasource.getPstm().setString(8,objet.getDenomination());
            
            datasource.executeMaj();
            ResultSet rs=datasource.getPstm().getGeneratedKeys();
            if(rs.next()){
                result=rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EntrepriseRepository.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
              datasource.CloseConnection();
        }
      
        return result;
	}

	@Override
	public int update(Entreprise objet) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Entreprise> findAll() {
		// TODO Auto-generated method stub
				return null;
	}

	@Override
	public Entreprise findById(int id) {
		Entreprise entreprise=null;
		datasource.initPS(SQL_SELECT_ONE);
        try {
        	datasource.getPstm().setInt(1, id);
            ResultSet rs = datasource.executeSelect();
            if(rs.next()){
            	entreprise=new Entreprise();
            	entreprise.setId(rs.getInt("id"));
            	entreprise.setLogin(rs.getString("login"));
            	entreprise.setPassword(rs.getString("password"));
            	entreprise.setRole(rs.getString("role"));
            	entreprise.setAdresse(rs.getString("adresse"));
            	entreprise.setTelephone(rs.getString("telephone"));
            	entreprise.setEmail(rs.getString("email"));
            	entreprise.setNinea(rs.getString("ninea"));
            	entreprise.setDenomination(rs.getString("prenom"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DemandeurRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        datasource.CloseConnection();
        return entreprise;
	}

}

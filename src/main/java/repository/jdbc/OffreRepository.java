package repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import domaine.Demandeur;
import domaine.Entreprise;
import domaine.Offre;
import repository.IOffreRepository;

public class OffreRepository implements IOffreRepository {
	//String Requetes
	private final String SQL_INSERT="INSERT INTO public.offre(\r\n"
			+ "	code, libelle, description, entreprise_id)\r\n"
			+ "	VALUES (?, ?, ?, ?);";
	private final String SQL_SELECT_BY_ENTREPRISE="SELECT o.*\r\n"
			+ "	FROM public.offre o\r\n"
			+ "	JOIN public.utilisateur e\r\n"
			+ "	ON o.entreprise_id = e.id\r\n"
			+ "	WHERE o.entreprise_id= ?;";
	private final String SQL_SELECT_BY_DEMANDEUR="SELECT o.*\r\n"
			+ "	FROM public.offre o\r\n"
			+ "	JOIN public.postuler p\r\n"
			+ "	ON p.offre_id=o.id\r\n"
			+ "	JOIN public.utilisateur d\r\n"
			+ "	ON p.demandeur_id=d.id\r\n"
			+ "	WHERE p.demandeur_id= ?;";
    private final String SQL_SELECT_ALL="SELECT *FROM public.offre;";
	private final String SQL_SELECT_ONE="SELECT * FROM public.offre\r\n"
    		+ "WHERE id = ?";
	private DataSource dataSource;
	private EntrepriseRepository entrepriseRepository;
	
	public OffreRepository(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
		this.entrepriseRepository = new EntrepriseRepository(dataSource);
	}

	@Override
	public int create(Offre objet) {
		int result=0;
	        
		dataSource.initPS(SQL_INSERT);
        try {
        	dataSource.getPstm().setString(1,objet.getCode());
        	dataSource.getPstm().setString(2,objet.getLibelle());
        	dataSource.getPstm().setString(3,objet.getDescription());
        	dataSource.getPstm().setInt(4,objet.getEntreprise().getId());
            
        	dataSource.executeMaj();
            ResultSet rs=dataSource.getPstm().getGeneratedKeys();
            if(rs.next()){
                result=rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(OffreRepository.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
        	dataSource.CloseConnection();
        }
      
        return result;
	}

	@Override
	public int update(Offre objet) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Offre> findAll() {
		dataSource.initPS(SQL_SELECT_ALL);
        List<Offre> result=null;
        try {
         //Mapping  objet(Profil) avec Table Profil
          
           ResultSet rs=dataSource.executeSelect();
           result=new ArrayList<>();
           while(rs.next()){
        	   Offre o=new Offre();
               o.setId(rs.getInt("id"));
               o.setCode(rs.getString("code"));
               o.setLibelle(rs.getString("libelle"));
               o.setDescription(rs.getString("description"));
               Entreprise entreprise=entrepriseRepository.findById(rs.getInt("entreprise_id"));
               o.setEntreprise(entreprise);
               result.add(o);
           }
         

     } catch (SQLException ex) {
         Logger.getLogger(OffreRepository.class.getName()).log(Level.SEVERE, null, ex);
     }
     dataSource.CloseConnection();
     return result;
	}

	@Override
	public Offre findById(int id) {
		Offre offre = null;
        dataSource.initPS(SQL_SELECT_ONE);
        try {
        	dataSource.getPstm().setInt(1, id);
            ResultSet rs = dataSource.executeSelect();
            if(rs.next()){
            	offre=new Offre();
            	offre.setId(rs.getInt("id"));
            	offre.setCode(rs.getString("code"));
                offre.setLibelle(rs.getString("libelle"));
                offre.setDescription(rs.getString("description"));
                Entreprise entreprise=entrepriseRepository.findById(rs.getInt("entreprise_id"));
                offre.setEntreprise(entreprise);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OffreRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataSource.CloseConnection();
        return offre;
	}

	@Override
	public List<Offre> findAllByEntreprise(Entreprise entreprise) {
		dataSource.initPS(SQL_SELECT_BY_ENTREPRISE);
        List<Offre> result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
              dataSource.getPstm().setInt(1, entreprise.getId());
              ResultSet rs=dataSource.executeSelect();
              result=new ArrayList<>();
              while(rs.next()){
            	  Offre o=new Offre();
                  o.setId(rs.getInt("id"));
                  o.setCode(rs.getString("code"));
                  o.setLibelle(rs.getString("libelle"));
                  o.setDescription(rs.getString("description"));
                  o.setEntreprise(entreprise);
                  result.add(o);
              }
        } catch (SQLException ex) {
            Logger.getLogger(OffreRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataSource.CloseConnection();
        return result;
	}

	@Override
	public List<Offre> findAllByDemandeur(Demandeur demandeur) {
		dataSource.initPS(SQL_SELECT_BY_DEMANDEUR);
        List<Offre> result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
              dataSource.getPstm().setInt(1, demandeur.getId());
              ResultSet rs=dataSource.executeSelect();
              result=new ArrayList<>();
              while(rs.next()){
            	  Offre o=new Offre();
                  o.setId(rs.getInt("id"));
                  o.setCode(rs.getString("code"));
                  o.setLibelle(rs.getString("libelle"));
                  o.setDescription(rs.getString("description"));
                  o.setEntreprise(entrepriseRepository.findById(rs.getInt("entreprise_id")));
                  result.add(o);
              }
        } catch (SQLException ex) {
            Logger.getLogger(OffreRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataSource.CloseConnection();
        return result;
	}
}

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
import domaine.Postuler;
import repository.IPostulerRepository;

public class PostulerRepository implements IPostulerRepository {
	//String Requetes
	private final String SQL_INSERT="INSERT INTO public.postuler(\r\n"
			+ "	offre_id, demandeur_id, reponse)\r\n"
			+ "	VALUES (?, ?, ?);";
	private final String SQL_SELECT_ONE="SELECT * FROM public.postuler\r\n"
			+ "WHERE id = ?";
	private final String SQL_UPDATE="UPDATE public.postuler\r\n"
			+ "	SET reponse=?\r\n"
			+ "	WHERE id = ?;";
	private final String SQL_SELECT_BY_ENTREPRISE="SELECT p.*\r\n"
			+ "	FROM public.postuler p\r\n"
			+ "	JOIN public.offre o\r\n"
			+ "	ON p.offre_id = o.id\r\n"
			+ "	JOIN public.utilisateur d\r\n"
			+ "	ON o.entreprise_id = d.id\r\n"
			+ "	WHERE d.role LIKE ?\r\n"
			+ "	AND o.entreprise_id = ?;";
	private final String SQL_SELECT_BY_DEMANDEUR="SELECT p.*\r\n"
			+ "	FROM public.postuler p\r\n"
			+ "	JOIN public.utilisateur d\r\n"
			+ "	ON p.demandeur_id = d.id\r\n"
			+ "	WHERE d.role LIKE ? \r\n"
			+ "	AND p.demandeur_id = ? ;";
	private final String SQL_SELECT_BY_OFFRE="SELECT p.*\r\n"
			+ "	FROM public.offre o\r\n"
			+ "	JOIN public.postuler p\r\n"
			+ "	ON p.offre_id=o.id\r\n"
			+ "	JOIN public.utilisateur d\r\n"
			+ "	ON p.demandeur_id=d.id\r\n"
			+ "	WHERE p.offre_id= ?;";

	private final String SQL_SELECT_BY_OFFRE_AND_DEMANDEUR="SELECT p.*\r\n"
			+ "	FROM public.offre o\r\n"
			+ "	JOIN public.postuler p\r\n"
			+ "	ON p.offre_id=o.id\r\n"
			+ "	JOIN public.utilisateur d\r\n"
			+ "	ON p.demandeur_id=d.id\r\n"
			+ "	WHERE p.offre_id= ? \r\n"
			+ "	AND p.demandeur_id = ? ;";
	
	private DataSource dataSource;
	private OffreRepository offreRepository;
	private DemandeurRepository demandeurRepository;
	
	public PostulerRepository(DataSource dataSource) {
		this.dataSource = dataSource;
		this.offreRepository=new OffreRepository(dataSource);
		this.demandeurRepository=new DemandeurRepository(dataSource);
	}

	@Override
	public int create(Postuler objet) {
		int result=0;
        
		dataSource.initPS(SQL_INSERT);
        try {
        	dataSource.getPstm().setInt(1,objet.getOffre().getId());
        	dataSource.getPstm().setInt(2,objet.getDemandeur().getId());
        	dataSource.getPstm().setBoolean(3,false);
            
        	dataSource.executeMaj();
            ResultSet rs=dataSource.getPstm().getGeneratedKeys();
            if(rs.next()){
                result=rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(PostulerRepository.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
        	dataSource.CloseConnection();
        }
      
        return result;
	}

	@Override
	public int update(Postuler objet) {
		int result=0;
        dataSource.initPS(SQL_UPDATE);
        try {
        	objet.setReponse(true);
        	dataSource.getPstm().setBoolean(1,objet.isReponse());
        	dataSource.getPstm().setInt(2,objet.getId());
            result=dataSource.executeMaj();
        } catch (SQLException ex) {
            Logger.getLogger(PostulerRepository.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
        	dataSource.CloseConnection();
        }      
        return result;
	}

	@Override
	public List<Postuler> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Postuler findById(int id) {
		Postuler postuler = null;
        dataSource.initPS(SQL_SELECT_ONE);
        try {
        	dataSource.getPstm().setInt(1, id);
            ResultSet rs = dataSource.executeSelect();
            if(rs.next()){
            	postuler=new Postuler();
            	postuler.setId(rs.getInt("id"));
                postuler.setReponse(rs.getBoolean("reponse"));
                Offre offre=offreRepository.findById(rs.getInt("offre_id"));
                postuler.setOffre(offre);
                Demandeur demandeur=demandeurRepository.findById(rs.getInt("demandeur_id"));
                postuler.setDemandeur(demandeur);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OffreRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataSource.CloseConnection();
        return postuler;
	}

	@Override
	public List<Postuler> findAllByEntreprise(Entreprise entreprise) {
		dataSource.initPS(SQL_SELECT_BY_ENTREPRISE);
        List<Postuler> result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
            	dataSource.getPstm().setString(1, entreprise.getRole());
	            dataSource.getPstm().setInt(2, entreprise.getId());
	            ResultSet rs=dataSource.executeSelect();
	            result=new ArrayList<>();
	            while(rs.next()){
		            Postuler p=new Postuler();
	                p.setId(rs.getInt("id"));
	                p.setReponse(rs.getBoolean("reponse"));
	                Offre offre=offreRepository.findById(rs.getInt("offre_id"));
	                p.setOffre(offre);
	                Demandeur demandeur=demandeurRepository.findById(rs.getInt("demandeur_id"));
	                p.setDemandeur(demandeur);
	                result.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostulerRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataSource.CloseConnection();
        return result;
	}

	@Override
	public List<Postuler> findAllByDemandeur(Demandeur demandeur) {
		dataSource.initPS(SQL_SELECT_BY_DEMANDEUR);
        List<Postuler> result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
            	dataSource.getPstm().setString(1, demandeur.getRole());
	            dataSource.getPstm().setInt(2, demandeur.getId());
	            ResultSet rs=dataSource.executeSelect();
	            result=new ArrayList<>();
	            while(rs.next()){
		            Postuler p=new Postuler();
	                p.setId(rs.getInt("id"));
	                p.setReponse(rs.getBoolean("reponse"));
	                Offre offre=offreRepository.findById(rs.getInt("offre_id"));
	                p.setOffre(offre);
	                p.setDemandeur(demandeur);
	                result.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostulerRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataSource.CloseConnection();
        return result;
	}

	@Override
	public List<Postuler> findAllByOffre(Offre offre) {
		dataSource.initPS(SQL_SELECT_BY_OFFRE);
        List<Postuler> result=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
            	dataSource.getPstm().setInt(1, offre.getId());
	            ResultSet rs=dataSource.executeSelect();
	            result=new ArrayList<>();
	            while(rs.next()){
		            Postuler p=new Postuler();
	                p.setId(rs.getInt("id"));
	                p.setReponse(rs.getBoolean("reponse"));
	                p.setOffre(offre);
	                Demandeur demandeur=demandeurRepository.findById(rs.getInt("demandeur_id"));
	                p.setDemandeur(demandeur);
	                result.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostulerRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataSource.CloseConnection();
        return result;
	}
	@Override
	public Postuler findByOffreAndDemandeur(Offre offre,Demandeur demandeur) {
		dataSource.initPS(SQL_SELECT_BY_OFFRE_AND_DEMANDEUR);
        Postuler p=null;
        try {
            //Mapping  objet(Profil) avec Table Profil
            	dataSource.getPstm().setInt(1, offre.getId());
            	dataSource.getPstm().setInt(2, demandeur.getId());
	            ResultSet rs=dataSource.executeSelect();
	            if(rs.next()){
		            p=new Postuler();
	                p.setId(rs.getInt("id"));
	                p.setReponse(rs.getBoolean("reponse"));
	                p.setOffre(offre);
	                p.setDemandeur(demandeur);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostulerRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataSource.CloseConnection();
        return p;
	}

}

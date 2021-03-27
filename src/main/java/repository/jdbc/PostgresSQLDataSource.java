package repository.jdbc;

import java.sql.*;

public class PostgresSQLDataSource implements DataSource {
	private  Connection cnx;
    private  PreparedStatement pstm;
    private int ok;
    private ResultSet rs;
    
    private static final String DRIVER="org.postgresql.Driver";
    
	public static final String NOM_BASE = "jee_prototype_isijobs";
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "brandao37";
    @Override
    public Connection getConnection(){
        String protocole = "jdbc:postgresql:";
        // Adresse IP de l’hôte de la base et port
        String ip = "localhost";  // dépend du contexte
        String port = "5432";  // port Postgres par défaut
        // Nom de la base ;
        String nomBase = NOM_BASE;  // dépend du contexte
        // Chaîne de connexion
        String conString = protocole + "//" + ip + ":" + port + "/" + nomBase;
        // Identifiants de connexion et mot de passe
        String dbUser = DB_USER;  // dépend du contexte
        String dbPassword = DB_PASSWORD;  // dépend du contexte
        try { 
            Class.forName(DRIVER);
            cnx= DriverManager.getConnection(conString, dbUser, dbPassword);
            
        } catch (ClassNotFoundException ex) {
            System.err.println("Erreur de Chargement de Driver");
        } catch (SQLException ex) {
            System.err.println("Erreur de Connexion");
        }
        // Connexion
        return cnx;
    }
    public void initPS(String sql)
	{
		getConnection();
		try{
			  if(sql.toLowerCase().startsWith("insert"))
			     {
			    	pstm=cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS );
			     }
			else{
				   pstm=cnx.prepareStatement(sql);
		        }
		    }catch(Exception e)
		{
			e.printStackTrace();
		}
	}
    @Override
	public int executeMaj()
	{
		try {
			ok = pstm.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return ok;
	}
    @Override
	public ResultSet executeSelect()
	{
		try {

			rs=pstm.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();

		}
		return rs;
	}
    @Override
	public PreparedStatement getPstm()
	{
		return  this.pstm;

	}
   
    @Override
    public void CloseConnection(){
		try{
		if(cnx!=null){
			cnx.close();
		}
		}catch(Exception ex){
			ex.printStackTrace();

		}
	}

}

package repository.jdbc;

import domaine.Role;
import repository.IRoleRepository;

import java.util.ArrayList;
import java.util.List;

public class RoleRepository implements IRoleRepository{
	private List<Role> roles;
	
	
	public RoleRepository() {
		roles=new ArrayList<>();
		roles.add(new Role("ROLE_ADMIN"));
		roles.add(new Role("ROLE_ENTREPRISE"));
		roles.add(new Role("ROLE_DEMANDEUR"));
	}

	public List<Role> getAll() {
		return roles;
	}

	public Role getByLibelle(String libelle) {
		// TODO Auto-generated method stub
		return roles.stream()
				.filter(r->r.getLibelle().equalsIgnoreCase(libelle))
				.findAny().get();
	}
}

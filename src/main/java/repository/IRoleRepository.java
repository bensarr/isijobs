package repository;

import domaine.Role;

import java.util.List;

public interface IRoleRepository {
    public List<Role>getAll();
    public Role getByLibelle(String libelle);
}

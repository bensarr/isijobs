package repository;

import java.util.List;

public interface IRepository<T> {
	int  create(T objet);
	   int  update(T objet);
	   List<T> findAll();
	   T findById(int id);
}

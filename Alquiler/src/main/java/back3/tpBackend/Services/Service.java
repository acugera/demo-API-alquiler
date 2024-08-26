package back3.tpBackend.Services;

import java.util.List;

// Interface que define métodos para hacer CRUD de una entidad...
public interface Service<T,W>{
    void add(T entity);

    T getById(W id);

    List<T> getAll();

    T delete(W id);

    void update(T entity);

}

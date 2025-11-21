package crm.example.services;

import java.util.List;

import crm.example.exceptions.InvalidUniqueDataException;

public interface RestService <T, D> {    
        
    T save(D dto) throws InvalidUniqueDataException;
    
    T getById(Long id);

    List<T> getAll();

    void update(D dto);

    void deleteById(Long id);
}

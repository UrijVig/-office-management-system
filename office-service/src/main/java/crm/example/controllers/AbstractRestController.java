package crm.example.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import crm.example.exceptions.InvalidUniqueDataException;
import crm.example.services.RestService;
import jakarta.validation.Valid;

public abstract class AbstractRestController<T, G> {

    protected final RestService<T, G> restService;

    protected AbstractRestController(RestService<T,G> restService){
        this.restService = restService;
    }

    @GetMapping
    public ResponseEntity<List<T>> getAll() {
        List<T> items = restService.getAll();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable Long id) {
        return ResponseEntity.ok(restService.getById(id));
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody G dto) {
        restService.update(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        restService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected abstract ResponseEntity<T> save(G dto, UriComponentsBuilder uri)
            throws InvalidUniqueDataException;
}


package com.careermatch.controller;

import com.careermatch.model.Internship;
import com.careermatch.service.InternshipService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internships")
public class InternshipController {

    private final InternshipService service;

    public InternshipController(InternshipService service) {
        this.service = service;
    }

    @GetMapping
    public List<Internship> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Internship> getById(@PathVariable Long id) {

        Internship internship = service.getById(id);

        return internship == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(internship);
    }

    @PostMapping
    public Internship create(@RequestBody Internship internship) {
        return service.create(internship);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Internship> update(
            @PathVariable Long id,
            @RequestBody Internship internship) {

        Internship updated = service.update(id, internship);

        return updated == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

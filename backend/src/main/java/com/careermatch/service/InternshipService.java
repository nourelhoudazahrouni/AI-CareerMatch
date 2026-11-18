package com.careermatch.service;

import com.careermatch.model.Internship;
import com.careermatch.repository.InternshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternshipService {

    private final InternshipRepository repository;

    public InternshipService(InternshipRepository repository) {
        this.repository = repository;
    }

    public List<Internship> getAll() {
        return repository.findAll();
    }

    public Internship getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Internship create(Internship internship) {
        return repository.save(internship);
    }

    public Internship update(Long id, Internship internship) {

        Internship existing = getById(id);

        if (existing == null) {
            return null;
        }

        existing.setTitle(internship.getTitle());
        existing.setCompany(internship.getCompany());
        existing.setLocation(internship.getLocation());
        existing.setRequiredSkills(internship.getRequiredSkills());

        return repository.save(existing);
    }

    public boolean delete(Long id) {

        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}

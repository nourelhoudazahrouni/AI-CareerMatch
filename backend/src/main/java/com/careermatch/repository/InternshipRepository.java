package com.careermatch.repository;

import com.careermatch.model.Internship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternshipRepository
        extends JpaRepository<Internship, Long> {
}

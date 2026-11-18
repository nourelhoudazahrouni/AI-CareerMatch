package com.careermatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.careermatch.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}

package com.example.personTask.collection;

import com.example.personTask.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryClass extends JpaRepository<Person, Long> {
}

package com.example.tutorial.Data;

import com.example.tutorial.Model.Event;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
}

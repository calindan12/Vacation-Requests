package com.example.proiect;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface requestsRepo extends JpaRepository<Request , Integer> {
    List<Request> findByUserId(int userId);

}

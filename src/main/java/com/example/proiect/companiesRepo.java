package com.example.proiect;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface companiesRepo extends JpaRepository<Companies , Integer> {
    public Companies getCompaniesByName(String name);
    public Companies getCompaniesById(Integer id);

}

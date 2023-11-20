package com.example.proiect;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface userCompanyRepo extends JpaRepository<UserCompany, Integer> {

}

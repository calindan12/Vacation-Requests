package com.example.proiect;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface repo extends JpaRepository<UserDtls , Integer> {
    public boolean existsByEmail(String email);

    public UserDtls findByEmail(String email);

//    List<UserDtls>findByEmailNot(String email);
    List<UserDtls> findByEmailNotAndRoleNot(String email, String role);

    public UserDtls findByFirstNameAndLastName(String firstName , String lastName);
}

package com.example.proiect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class serviceImpl implements service{

    @Autowired
    private repo userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    @Autowired
    private requestsRepo requestsRepo;

    @Autowired
    private companiesRepo companiesRepo;

    @Override
    public UserDtls createUser(UserDtls user){
        user.setPassword(passwordEncode.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        return userRepo.save(user);
    }

    @Override
    public boolean checkEmail(String email) {
        return userRepo.existsByEmail(email);
    }


    public List<UserDtls> cautaObiecteCuEmailDiferitSiRol(UserDtls user) {
        String emailDeReferinta = user.getEmail();
        return userRepo.findByEmailNotAndRoleNot(emailDeReferinta , "SUPER_ADMIN");
    }

    public List<Companies> searchCompaniesByUser(UserDtls userDtls) {
        return userDtls.getCompanies();
    }

    public List<Companies> searchCompanies() {
        return companiesRepo.findAll();
    }

    public List<Request> getRequestsByUserId(int userId) {
        return requestsRepo.findByUserId(userId);
    }

}

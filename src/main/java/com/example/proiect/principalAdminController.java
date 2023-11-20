package com.example.proiect;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/principalAdmin/")
public class principalAdminController {
    @Autowired
    private repo userRepo;

    @Autowired
    private requestsRepo requestsRepo;

    @Autowired
    private serviceImpl service;



    @GetMapping("/")
    public String home(){
        return "superAdminHome";
    }

    @GetMapping("/seeUsers")
    public String seeRequests(){
        return "seeUsersSuperAdmin";
    }

    @ModelAttribute
    private void userDetails(Model m , Principal p){
        String email =  p.getName();
        UserDtls user = userRepo.findByEmail(email);
        m.addAttribute("user" , user);
    }



    @ModelAttribute
    public void getOtherUsers(Principal p , Model m){
        String email =  p.getName();
        UserDtls user = userRepo.findByEmail(email);
        List<UserDtls> otherUsers =new ArrayList<>(service.cautaObiecteCuEmailDiferitSiRol(user));
        m.addAttribute("otherUsers" , otherUsers);
    }
    @GetMapping("/edit-user-profile/{id}")
    public String editUserProfile(@PathVariable Integer id, Model model) {
        UserDtls user = userRepo.getById(id);
        model.addAttribute("user", user);
        return "superAdminEditProfile";
    }

    @PostMapping("/edit-user-profile/{id}")
    public String saveEditedUserProfile(@PathVariable Integer id, @ModelAttribute UserDtls user) {
        // Aici preia user-ul existent din baza de date sau serviciu
        UserDtls existingUser = userRepo.getById(id);

        // Actualizează câmpurile nume și prenume dacă au fost completate
        if (user.getFirstName() != null) {
            existingUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            existingUser.setLastName(user.getLastName());
        }
        if(user.getEmail() != null){
            existingUser.setEmail(user.getEmail());
        }

        // Actualizează rolul
        existingUser.setRole(user.getRole());

        // Salvează utilizatorul existent actualizat înapoi în baza de date
        userRepo.save(existingUser);

        return "redirect:/principalAdmin/";
    }


}

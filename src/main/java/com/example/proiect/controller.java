package com.example.proiect;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class controller {

    @Autowired
    private serviceImpl userService;

    @Autowired
    private userCompanyRepo userCompanyRepo;

    @Autowired
    private companiesRepo companiesRepo;

    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @GetMapping("/signin")
    public String login(){
        return "login";
    }

    @ModelAttribute("getCompanies")
    public List<Companies> getCompanies(Principal p){
        List<Companies>companies =new ArrayList<>(userService.searchCompanies());
        return companies;
    }

    @PostMapping("/createUser")
    public String createuser(@ModelAttribute UserDtls user , HttpSession session , @RequestParam("password") String password, @RequestParam("retypePassword") String retypePassword, @RequestParam("selectedCompanies")List<Integer>selectedCompanies){
        System.out.println(user);

//        if(user.getEmail() == "" || user.getFirstName() == "" || user.getLastName() == "" || user.getPassword() == "" || user.getPosition() == ""){
//            return "nu s a salvat";
//        }

        boolean f = userService.checkEmail(user.getEmail());
        if(f){
            session.setAttribute("msg" , "Email of user already exists");
        } else if (!password.equals(retypePassword)) {
            session.setAttribute("msg" , "Passwords don't match");
        } else {
            UserDtls userDtls = userService.createUser(user);

            for(Integer companyId : selectedCompanies){
                UserCompany userToCompany = new UserCompany();
                Companies company = companiesRepo.getCompaniesById(companyId);
                userToCompany.setCompany(company);
                userToCompany.setUser(userDtls);
                userCompanyRepo.save(userToCompany);
            }

            if (userDtls != null) {
                session.setAttribute("msg", "Regiser Successfully");
            } else {
                session.setAttribute("msg", "Something went wrong...");
            }
        }


//        userService.createUser(user)
        return "redirect:/register";
    }
}

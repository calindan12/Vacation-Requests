//package com.example.proiect;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class InitialUserSetup implements CommandLineRunner {
//
//    @Autowired
//    private repo userRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncode;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//            if (!userRepository.existsByEmail("admin@example.com")) {
//                UserDtls superAdmin = new UserDtls();
//                superAdmin.setFirstName("Admin");
//                superAdmin.setLastName("Admin");
//                superAdmin.setEmail("admin@example.com");
//                superAdmin.setPassword(passwordEncode.encode("admin"));
//                superAdmin.setPosition("Administrator");
//                superAdmin.setRole("SUPER_ADMIN");
//
//                userRepository.save(superAdmin);
//            }
//    }
//}
//

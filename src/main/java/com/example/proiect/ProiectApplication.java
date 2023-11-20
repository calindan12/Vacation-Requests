package com.example.proiect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProiectApplication {


    public static void main(String[] args) {
//         serviceImpl serviceExample = new serviceImpl();
//        if(serviceExample.checkIfTableExists()){
        SpringApplication.run(ProiectApplication.class, args);
//        }else{
//            System.out.println("Table does not exist");
//        }
    }
}


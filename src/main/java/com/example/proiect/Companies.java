package com.example.proiect;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "companies")
public class Companies {
    @Id
//<<<<<<< Updated upstream
//    @GeneratedValue
    @Column(name = "id", nullable = false)
//    private Long id;
//=======
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//>>>>>>> Stashed changes

    private String name;

    @ManyToMany(mappedBy = "companies")
    private List<UserDtls> companyUsers = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserDtls> getCompanyUsers() {
        return companyUsers;
    }

    public void setCompanyUsers(List<UserDtls> companyUsers) {
        this.companyUsers = companyUsers;
    }
}

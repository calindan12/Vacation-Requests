package com.example.proiect;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_company")
public class UserCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDtls user;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Companies company;

    public void setCompany(Companies company) {
        this.company = company;
    }

    public void setUser(UserDtls user) {
        this.user = user;
    }
}



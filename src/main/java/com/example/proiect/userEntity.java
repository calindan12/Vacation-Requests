package com.example.proiect;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class userEntity {
//    private String fName;
//    private String lName;

    private String name;

    private List<String> company;
    private String replacement;
//    private int number_of_days;
    private String from_date;
    private String to_date;
    private String year;
    private String date_of_completion;
    private String position;
    private int number_of_days;

    public userEntity(String name, List<String> company, String replacement, int number_of_days, String from_date, String to_date, String year, String date_of_completion, String position) {
        this.name = name;
        this.company = company;
        this.replacement = replacement;
        this.number_of_days = number_of_days;
        this.from_date = from_date;
        this.to_date = to_date;
        this.year = year;
        this.date_of_completion = date_of_completion;
        this.position = position;
    }

    public userEntity(){
        company = new ArrayList<>();
    }

//    public String getfName() {
//        return fName;
//    }
//    public String getlName() {
//        return lName;
//    }
    public String getName(){
        return name;
    }
    public void setName(){
        this.name = name;
    }

    public List<String> getCompany() {
        return company;
    }

    public void setCompany(List<String> company) {
        this.company = company;
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

//    public int getNumber_of_days() {
//        return number_of_days;
//    }

//    public void setNumber_of_days(int number_of_days) {
//        this.number_of_days = number_of_days;
//    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate_of_completion() {
        return date_of_completion;
    }

    public void setDate_of_completion(String date_of_completion) {
        this.date_of_completion = date_of_completion;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
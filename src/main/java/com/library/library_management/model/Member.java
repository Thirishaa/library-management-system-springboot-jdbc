package com.library.library_management.model;

import java.time.LocalDate;

public class Member {
    private int id;
    private String name;
    private String phone;
    private LocalDate registeredDate;

    public Member() {}

    public Member(int id, String name, String phone, LocalDate registeredDate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.registeredDate = registeredDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDate getRegisteredDate() { return registeredDate; }
    public void setRegisteredDate(LocalDate registeredDate) { this.registeredDate = registeredDate; }
}

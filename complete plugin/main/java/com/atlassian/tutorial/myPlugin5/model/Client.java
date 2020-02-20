package com.atlassian.tutorial.myPlugin5.model;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

import java.util.Date;

public interface Client extends Entity {
    String getLastName();

    void setLastName(String lastName);

    String getFirstName();

    void setFirstName(String firstName);

    String getMiddleName();

    void setMiddleName(String middleName);

    Date getBirthday();

    void setBirthday(Date birthday);

    String getAddress();

    void setAddress(String address);

    String getPhone();

    void setPhone(String phone);

    String getEmail();

    void setEmail(String email);

    @OneToMany
    Dog[] getDogs();

    @OneToMany
    Paddock[] getPaddock();
}
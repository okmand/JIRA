package com.atlassian.tutorial.myPlugin5.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class WalkerImpl {
    @XmlElement
    private Integer id;

    @XmlElement
    private String lastName;

    @XmlElement
    private String firstName;

    @XmlElement
    private String middleName;

    @XmlElement
    private Date birthday;

    @XmlElement
    private String phone;

    @XmlElement
    private String email;

    public WalkerImpl() {
    }

    public WalkerImpl(Integer id, String lastName, String firstName,
                      String middleName, Date birthday, String phone,
                      String email) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
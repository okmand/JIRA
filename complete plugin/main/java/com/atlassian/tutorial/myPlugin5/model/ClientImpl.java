package com.atlassian.tutorial.myPlugin5.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class ClientImpl {
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
    private String address;

    @XmlElement
    private String phone;

    @XmlElement
    private String email;

    @XmlElement
    private Dog[] dogs;

    public ClientImpl() {
    }

    public ClientImpl(Integer id, String lastName, String firstName,
                      String middleName, Date birthday, String address,
                      String phone, String email) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public Integer getID() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Dog[] getDogs() {
        return dogs;
    }
}
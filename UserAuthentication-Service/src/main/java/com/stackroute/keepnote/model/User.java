package com.stackroute.keepnote.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/*
 * The class "User" will be acting as the data model for the User Table in the database. 
 * Please note that this class is annotated with @Entity annotation. 
 * Hibernate will scan all package for any Java objects annotated with the @Entity annotation. 
 * If it finds any, then it will begin the process of looking through that particular 
 * Java object to recreate it as a table in your database.
 */
@Entity
public class User {

    /*
     * This class should have five fields (userId,firstName,lastName,
     * userPassword,userRole,userAddedDate). Out of these five fields, the field
     * userId should be the primary key. This class should also contain the getters
     * and setters for the fields, along with the no-arg , parameterized constructor
     * and toString method.The value of userAddedDate should not be accepted from
     * the user but should be always initialized with the system date
     */



    @Id
    private String userId;
    private  String firstName;
    private  String lastName;
    private String userPassword;
    private  String userRole;
    private  Date userAddedDate;

    public User() {
    }

    public User(String userId, String firstName, String lastName, String userPassword, String userRole, Date userAddedDate) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userAddedDate = userAddedDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userAddedDate=" + userAddedDate +
                '}';
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String  string) {
        this.userId=string;

    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String  string) {
        this.firstName= string;

    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String  string) {
        this.lastName= string;

    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String  string) {
        this.userPassword= string;

    }

    public String getUserRole() {
        return this.userRole;
    }

    public void setUserRole(String  string) {
        this.userRole= string;

    }


    public Date getUserAddedDate() {
        return this.userAddedDate;
    }

    public void setUserAddedDate(Date date) {
        this.userAddedDate= date;

    }




}

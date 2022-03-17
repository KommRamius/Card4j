/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ascpd.cardbook4j;

/**
 *
 * @author andrea
 */
public class Contatto {
    private String email;
    private String FirstName;
    private String LastName;
    private String Organization;

    public String getOrganization() {
        return Organization;
    }

    public void setOrganization(String Organization) {
        this.Organization = Organization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }
    
    
    
}

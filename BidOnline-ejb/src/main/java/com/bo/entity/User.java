package com.bo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 2/25/13
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Integer userId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String userName;
    @NotNull
    private String email;
    @NotNull
    @Size(min = 6,message = "password too weak")
    private String password;
    @NotNull
    private String country;
    @NotNull
    private String presentAddress;
    @NotNull
    private String permanentAddress;

    private String phoneNumber;

    private int role;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private List<Item> items;

    //    @JoinTable(name="Offer", joinColumns={@JoinColumn(name="userId",referencedColumnName = "userId")},
//            inverseJoinColumns={@JoinColumn(name="itemId",referencedColumnName = "itemId")})

//    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JoinTable(name="User_Offer", joinColumns={@JoinColumn(name = "userId",referencedColumnName = "userId")},
//    inverseJoinColumns={@JoinColumn(name = "itemId",referencedColumnName = "itemId")})
//    private List<Item> watchList;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private List<Bid> bids;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name="User_Offer", joinColumns={@JoinColumn(name = "userId",referencedColumnName = "userId")},
            inverseJoinColumns={@JoinColumn(name = "itemId",referencedColumnName = "itemId")})
    private List<Item> bidItems;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "buyerId")
    private List<Item> boughtItems;
//    private ArrayList <BuyList> buyLists;
//    private ArrayList <Customer> customers;
//    private ArrayList <Seller> sellLists;


    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;

    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public List<Item> getBidItems() {
        return bidItems;
    }

    public void setBidItems(List<Item> bidItems) {
        this.bidItems = bidItems;
    }
}

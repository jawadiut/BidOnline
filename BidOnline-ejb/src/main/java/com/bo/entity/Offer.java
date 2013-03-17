package com.bo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/5/13
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Offer {
    @Id
    @GeneratedValue
    private Integer offerId;
    private Integer itemId;
    private String offerStatus;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> bidders;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Bid> bids;
    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public List<User> getBidders() {
        return bidders;
    }

    public void setBidders(List<User> bidders) {
        this.bidders = bidders;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
}

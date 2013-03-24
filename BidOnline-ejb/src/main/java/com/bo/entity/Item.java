package com.bo.entity;

//import java.sql.Blob;
import org.hibernate.annotations.ForeignKey;
import org.primefaces.model.StreamedContent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/2/13
 * Time: 8:45 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Item implements Serializable {

    @Id
    @GeneratedValue
    private Integer itemId;

    private String itemTitle;

    private String itemSubtitle;

    private Integer itemStartingPrice;

    private Integer itemFixedPrice;

    private String itemCondition;

    private String itemDescription;

    private String itemType;

    private int itemBidHistory;

    @Temporal(TemporalType.DATE)
    private Date itemUploadDate;

    @Temporal(TemporalType.DATE)
    private Date itemExpireDate;

    @Lob
    private byte[] itemImage;

    private String itemStatus;

    private Integer itemLatestBid;

    private Integer userId;

    private Integer buyerId;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private List<Bid> bids;

    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "bidItems")
    private List<User> bidders;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemSubtitle() {
        return itemSubtitle;
    }

    public void setItemSubtitle(String itemSubtitle) {
        this.itemSubtitle = itemSubtitle;
    }

    public Integer getItemStartingPrice() {
        return itemStartingPrice;
    }

    public void setItemStartingPrice(Integer itemStartingPrice) {
        this.itemStartingPrice = itemStartingPrice;
    }

    public Integer getItemFixedPrice() {
        return itemFixedPrice;
    }

    public void setItemFixedPrice(Integer itemFixedPrice) {
        this.itemFixedPrice = itemFixedPrice;
    }

    public String getItemCondition() {
        return itemCondition;
    }

    public void setItemCondition(String itemCondition) {
        this.itemCondition = itemCondition;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getItemBidHistory() {
        return itemBidHistory;
    }

    public void setItemBidHistory(int itemBidHistory) {
        this.itemBidHistory = itemBidHistory;
    }

    public Date getItemUploadDate() {
        return itemUploadDate;
    }

    public void setItemUploadDate(Date itemUploadDate) {
        this.itemUploadDate = itemUploadDate;
    }

    public Date getItemExpireDate() {
        return itemExpireDate;
    }

    public void setItemExpireDate(Date itemExpireDate) {
        this.itemExpireDate = itemExpireDate;
    }

    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Integer getItemLatestBid() {
        return itemLatestBid;
    }

    public void setItemLatestBid(Integer itemLatestBid) {
        this.itemLatestBid = itemLatestBid;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public List<User> getBidders() {
        return bidders;
    }

    public void setBidders(List<User> bidders) {
        this.bidders = bidders;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }


}

package com.bo.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/6/13
 * Time: 12:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Bid {
    @Id
    @GeneratedValue
    private Integer bidId;
    @Temporal(TemporalType.DATE)
    private Date bidDate;
    private Integer bidPrice;
    private Integer userId;
    private Integer itemId;
//    private Integer userId;
//    private Integer offerId;

    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Date getBidDate() {
        return bidDate;
    }

    public void setBidDate(Date bidDate) {
        this.bidDate = bidDate;
    }

    public Integer getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Integer bidPrice) {
        this.bidPrice = bidPrice;
    }



//    public Integer getBidPrice() {
//        return bidPrice;
//    }
//
//    public void setBidPrice(Integer bidPrice) {
//        this.bidPrice = bidPrice;
//    }
}

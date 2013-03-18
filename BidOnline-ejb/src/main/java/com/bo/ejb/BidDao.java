package com.bo.ejb;

import com.bo.entity.Bid;

import javax.ejb.Local;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/6/13
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface BidDao {
     public void updateBid(Bid bid);
     public List<Bid> getBidByOfferId(Integer offerId);
     public Bid getBidByItemAndUserId(Integer userId,Integer itemId);
    //public Bid getBidByOffer(Integer offerId, Integer userId);
}

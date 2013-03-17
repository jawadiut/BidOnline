package com.bo.ejb;

import com.bo.entity.Bid;
import com.bo.entity.Item;
import com.bo.entity.Offer;

import javax.ejb.Local;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/5/13
 * Time: 4:26 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface OfferDao {

    //public void saveOffer(Integer userId, Offer offer, Bid bid, Item item);
    public List<Integer> getAvailableOffers();
    public List<Offer> getOfferDetails();
    public Offer getOfferByItemId(int itemId);
    public Offer getOfferWithBidders(Integer offerId);
}

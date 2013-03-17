package com.bo.ejb;

import com.bo.entity.Bid;
import com.bo.entity.Item;
import com.bo.entity.Offer;
import com.bo.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/5/13
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class OfferDaoImpl implements OfferDao {
    @PersistenceContext(unitName = "persistBD")
    private EntityManager entityManager;



//    @Override
//    public void saveOffer(Integer userId, Offer offer, Bid bid,Item item) {
//
//        Offer offer1 = entityManager.find(Offer.class, offer.getOfferId());
//        User user = entityManager.find(User.class, userId);
//
//        offer1.getBids().add(bid);
//
//        user.getBids().add(bid);
//
//        offer1.getBidders().add(user);
//        System.out.println("Number of offer1-bidders..."+offer1.getBidders().size());
//        //System.out.println("offer-bidList: "+offer1.getBids());
//        user.getOffers().add(offer1);
//        System.out.println("user-bidList: " + user.getBids());
//        entityManager.persist(user);
//        entityManager.persist(offer1);
//    }


    @Override
    public List<Integer> getAvailableOffers() {
        return entityManager.createQuery("SELECT o.itemId from Offer o")
                .getResultList();
    }

    @Override
    public List<Offer> getOfferDetails() {
        return entityManager.createQuery("SELECT o from Offer o")
                .getResultList();
    }

    @Override
    public Offer getOfferByItemId(int itemId) {
        System.out.println("entered into offerDao....");
        return (Offer) entityManager.createQuery("SELECT o from Offer o where o.itemId=:dd ")
                .setParameter("dd", itemId)
                .getSingleResult();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Offer getOfferWithBidders(Integer itemId){
        Offer offer;
        try {
            offer = (Offer) entityManager.createQuery("SELECT o FROM Offer o JOIN FETCH o.bidders WHERE o.itemId=:itemId ORDER BY o.itemId DESC")
                    .setParameter("itemId", itemId).getSingleResult();
        } catch (NoResultException ex) {
            //offer = entityManager.find(Offer.class, offerId);
            offer = (Offer) entityManager.createQuery("SELECT o FROM Offer o WHERE o.itemId=:itemId ORDER BY o.itemId DESC ")
                    .setParameter("itemId",itemId).getSingleResult();
            offer.getBidders().size();
        }
        return offer;
    }


}

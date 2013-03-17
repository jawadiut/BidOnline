package com.bo.ejb;

import com.bo.entity.Bid;
import com.bo.entity.Item;
import com.bo.entity.Offer;
import com.bo.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/2/13
 * Time: 9:09 AM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class ItemDaoImpl implements ItemDao {

    protected final Log log = LogFactory.getLog(getClass());

    @PersistenceContext(unitName = "persistBD")
    private EntityManager entityManager;

    @Override
    public void uploadItem(Integer userId,Item item){
        User user = entityManager.find(User.class,userId);
        //System.out.println("user info: "+userId+","+user);
        user.getItems().add(item);
        System.out.println("In uploadItem......"+item.getItemTitle());

        entityManager.persist(user);
    }

    @Override
    public Item findItemById(int itemId){
        return entityManager.find(Item.class,itemId);
    }

    @Override
    public void deleteItem(int itemId){
        Item item = findItemById(itemId);
        entityManager.remove(item);
    }

    @Override
    public void updateItem(Item item){

        //Item item = entityManager.find(Item.class,itemId);
        entityManager.merge(item);
    }

    @Override
    public void updateBidInfo(Integer userId, Bid bid,Item item) {

        item= findItemById(item.getItemId());
        User user = entityManager.find(User.class, userId);
        item.getBids().add(bid);
        user.getBids().add(bid);
        item.getBidders().add(user);
        user.getBidItems().add(item);
        System.out.println("user-bidList: " + user.getBids());
        entityManager.persist(user);
        entityManager.persist(item);
    }

    @Override
    public Item getItem(int itemId) {
        System.out.println("In getItem method: ");
        Item item = entityManager.find(Item.class,itemId);
        System.out.println("iteminfo...."+item);
        return item;//To change body of implemented methods use File | Settings | File Templates.
    }
    @Override
    public List<Item> getItems(){
        return entityManager.createQuery("select i from Item i where i.itemStatus LIKE :active")
                .setParameter("active","active").getResultList();
    }
    @Override
    public List<Item> getPendingItems(){
        return entityManager.createQuery("select i from Item i where i.itemStatus LIKE :pending")
                .setParameter("pending","pending").getResultList();

    }

    @Override
    public Item getItemWithBidders(Integer itemId){
        Item item;
        try {
            item = (Item) entityManager.createQuery("SELECT i FROM Item i JOIN FETCH i.bidders WHERE i.itemId=:itemId ")
                    .setParameter("itemId", itemId).getSingleResult();
        } catch (NoResultException ex) {
            //offer = entityManager.find(Offer.class, offerId);
            item = entityManager.find(Item.class,itemId);
            item.getBidders().size();
        }
        return item;
    }

    @Override
    public Item getItemWithBids(Integer itemId) {
        Item item;
        try {
            item = (Item) entityManager.createQuery("SELECT i FROM Item i JOIN FETCH i.bids WHERE i.itemId=:itemId ")
                    .setParameter("itemId", itemId).getSingleResult();
        } catch (NoResultException ex) {
            //offer = entityManager.find(Offer.class, offerId);
            item = entityManager.find(Item.class,itemId);
            item.getBidders().size();
        }
        return item;
    }

    @Override
    public List<Item> getItemByCategory(String category) {
        return entityManager.createQuery("select i from Item  i where i.itemType LIKE :category and i.itemStatus LIKE :status")
                .setParameter("category",category)
                .setParameter("status","active")
                .getResultList();
    }
}

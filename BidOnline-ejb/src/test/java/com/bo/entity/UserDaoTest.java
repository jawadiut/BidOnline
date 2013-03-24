package com.bo.entity;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/20/13
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(Arquillian.class)
public class UserDaoTest {
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, User.class.getPackage(), Item.class.getPackage(), Bid.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

//    private static final String[] User_TITLES = {
//            "Super Mario Brothers",
//            "Mario Kart",
//            "F-Zero"
//    };

    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;

    // tests go here



    private void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Dumping old records...");
        em.createQuery("delete from User").executeUpdate();
        em.createQuery("delete from Item").executeUpdate();
        em.createQuery("delete from Bid").executeUpdate();
        utx.commit();
    }

    private void insertData() throws Exception {

        utx.begin();
        em.joinTransaction();
        System.out.println("Inserting records...");
        User user = new User();
        user.setUserName("Mango");
        user.setRole(1);
        user.setCountry("Bangladesh");
        user.setEmail("imon@therap.com");
        user.setPassword("therap");
        user.setFirstName("jguser");
        user.setLastName("ckuser");
        user.setPermanentAddress("a");
        user.setPresentAddress("b");
        user.setPhoneNumber("01813494949");
        em.persist(user);

        utx.commit();
        // clear the persistence context (first-level cache)
        em.clear();

    }

    private void startTransaction() throws Exception {
        utx.begin();
        em.joinTransaction();
    }

    private Item insertItem() throws Exception {


        Item item = new Item();
        item.setItemStatus("pending");
        item.setItemLatestBid(0);
        item.setItemBidHistory(0);
        item.setItemCondition("new");
        item.setItemImage(null);
        item.setItemDescription("Mitsubishi");
        item.setItemStartingPrice(1000);
        item.setItemFixedPrice(2000);
        item.setItemUploadDate(Calendar.getInstance().getTime());
        item.setItemExpireDate(Calendar.getInstance().getTime());
        item.setItemTitle("Mitsubishi");
        item.setItemSubtitle("Corolla");
        item.setItemType("New");

        return item;

    }

    private Bid insertBid() throws Exception {

        Bid bid = new Bid();
        bid.setBidDate(Calendar.getInstance().getTime());
        bid.setBidPrice(1500);
        return bid;
    }

    private void addItemToUser() throws Exception {
        utx.begin();
        User user = em.find(User.class, 1);
        user.getItems().add(insertItem());
        user.getItems().add(insertItem());
        em.persist(user);
        utx.commit();
    }

    private void addBidToUser() throws Exception{

        utx.begin();
        User user = em.find(User.class, 1);
        Item item = user.getItems().get(0);
        Bid bid = insertBid();
        user.getBids().add(bid);
        em.persist(user);
        utx.commit();
    }

    private void addBidListToUser() throws Exception{

        utx.begin();
        User user = em.find(User.class,1);
        user.getBidItems().add(insertItem());
        em.persist(user);
        utx.commit();
    }


    @Test
    @InSequence(value = 1)
    public void authenticateUserTest() throws Exception {


        clearData();
        insertData();

        User user2 = (User) em.createQuery("select u from User u where u.userName LIKE :UName and u.password LIKE :PSWord")
                .setParameter("UName", "Mango")
                .setParameter("PSWord", "therap")
                .getSingleResult();

        Assert.assertEquals(user2.getEmail(), "imon@therap.com");

    }

    @Test
    @InSequence(value = 2)
    public void findUserByIdTest() {

        User user = (User) em.find(User.class, 1);
        Assert.assertEquals(user.getUserName(), "Mango");
    }

    @Test
    @InSequence(value = 3)
    public void getUserWithOfferListTest() throws Exception {

        addItemToUser();
        User user1 ;
        user1 = (User) em.createQuery("SELECT u FROM User u JOIN FETCH u.items WHERE u.userId=:userId ORDER BY u.userId DESC")
                .setParameter("userId", 1).getSingleResult();
        Assert.assertEquals(user1.getItems().get(1).getItemTitle(), "Mitsubishi");

    }

    @Test
    @InSequence(value = 4)
    public void getUserWithBidsTest() throws Exception {


        addBidToUser();
        User user3 ;
        user3 = (User) em.createQuery("SELECT u FROM User u JOIN FETCH u.bids WHERE u.userId=:userId ORDER BY u.userId DESC")
                .setParameter("userId",1).getSingleResult();
        Assert.assertEquals((Object) user3.getBids().get(0).getBidPrice(),  1500);

    }

    @Test
    @InSequence(value = 5)
    public void getUserWithBidListTest() throws Exception{

        addBidListToUser();
        User user4;
        user4 = (User) em.createQuery("SELECT u FROM User u JOIN FETCH u.bidItems WHERE u.userId=:userId ORDER BY u.userId DESC")
                .setParameter("userId", 1).getSingleResult();
        Assert.assertEquals((Object) user4.getBidItems().get(0).getItemTitle(),  "Mitsubishi");
    }


}

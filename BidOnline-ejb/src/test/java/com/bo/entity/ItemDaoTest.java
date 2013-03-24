package com.bo.entity;

import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import com.bo.entity.Bid;
import com.bo.entity.Item;
import com.bo.entity.User;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ItemDaoTest {
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, Item.class.getPackage(), User.class.getPackage(),Bid.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }


    @PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;

    private void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Dumping old records...");
        em.createQuery("delete from User").executeUpdate();
        em.createQuery("delete from Item").executeUpdate();
        em.createQuery("delete from Bid").executeUpdate();
        utx.commit();
    }

//    private void insertItem() throws Exception{
//        System.out.println("inserting item....");
//        utx.begin();
//        em.joinTransaction();
//        Item item = new Item();
//        item.setItemType("Electronics");
//        item.setItemTitle("Core-I7");
//        item.setItemStatus("pending");
//        item.setItemLatestBid(0);
//        item.setItemBidHistory(0);
//        item.setItemCondition("new");
//        item.setItemImage(null);
//        item.setItemDescription("Mitsubishi");
//        item.setItemStartingPrice(1000);
//        item.setItemFixedPrice(2000);
//        item.setItemUploadDate(Calendar.getInstance().getTime());
//        item.setItemExpireDate(Calendar.getInstance().getTime());
//        item.setItemSubtitle("Corolla");
//        em.persist(item);
//        utx.commit();
//        em.clear();
//        //item.setUserId(1);
//    }

    private void insertUser() throws Exception {

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

    private Item insertItemAgain() throws Exception {


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
        item.setItemType("car");


        return item;
    }
    private Bid insertBid() throws Exception {
        System.out.println("in insertBId");
        Bid bid = new Bid();
        bid.setBidDate(Calendar.getInstance().getTime());
        bid.setBidPrice(18000);
        return bid;
    }

    @Test
    @InSequence(value = 1)
    public void uploadItemTest() throws Exception{
        clearData();
        insertUser();
        Item item = insertItemAgain();


        utx.begin();
//        Item item = em.find(Item.class,1);
        User user = em.find(User.class,1);
        user.getItems().add(item);
//        user.getItems().add(item);
        utx.commit();
        Assert.assertEquals((Object) user.getItems().size(),1);

    }

    @Test
    @InSequence(value = 2)
    public void findItemByIdTest() throws Exception {


        //utx.begin();

        Item item = em.find(Item.class,1);
        System.out.println("Item found: "+item.getItemId());
        //utx.commit();
        Assert.assertEquals((Object) item.getItemId(),1);
    }

//    @Test
//    @InSequence(value = 2)
//    public void deleteItemTest() throws Exception{
//        utx.begin();
//        Item item = em.find(Item.class,1);
//        em.remove(item);
//        utx.commit();
//        Assert.assertEquals((Object) item.getItemId(),1);
//    }

    @Test
    @InSequence(value = 3)
    public void updateItemTest() throws Exception {

        utx.begin();
        Item item = em.find(Item.class,1);
        item.setItemSubtitle("Mota");
        em.merge(item);
        utx.commit();
        Assert.assertEquals((Object) item.getItemSubtitle(),"Mota");

    }

    @Test
    @InSequence(value = 4)
    public void updateBidInfoTest() throws Exception {

//        clearData();
//
//        insertData();

        utx.begin();

        Item item = em.find(Item.class,1);

        User user = em.find(User.class, 1);

        Bid bid = insertBid();

        item.getBids().add(bid);

        user.getBids().add(bid);

        item.getBidders().add(user);

        user.getBidItems().add(item);

        em.persist(user);

        em.persist(item);

        item = (Item) em.createQuery("SELECT i FROM Item i JOIN FETCH i.bids WHERE i.itemId=:itemId")
                .setParameter("itemId", 1).getSingleResult();

        item.setItemBidHistory(item.getBids().size());

        bid = em.find(Bid.class,1);

        item.setItemLatestBid(bid.getBidPrice());

        em.merge(item);



        utx.commit();

        Assert.assertEquals(bid.getUserId(),bid.getItemId());
    }

    @Test
    @InSequence(value = 5)
    public void getPendingItemsTest(){
       List<Item>items = em.createQuery("select i from Item i where i.itemStatus LIKE :pending")
                .setParameter("pending","pending")
                .getResultList();
       Assert.assertEquals(items.size(),1);
    }

    @Test
    @InSequence(value = 6)
    public void getItemWithBiddersTest(){
        Item item;
        item = (Item)em.createQuery("SELECT i FROM Item i JOIN FETCH i.bidders WHERE i.itemId=:itemId ")
                .setParameter("itemId", 1)
                .getSingleResult();
        Assert.assertEquals(item.getBidders().get(0).getUserName(),"Mango");
    }

    @Test
    @InSequence(value = 7)
    public void getItemWithBidsTest(){
        Item item;
        item = (Item) em.createQuery("SELECT i FROM Item i JOIN FETCH i.bids WHERE i.itemId=:itemId")
                .setParameter("itemId", 1)
                .getSingleResult();
        Assert.assertEquals((Object) item.getBids().get(0).getUserId(),1);
    }

    @Test
    @InSequence(value = 8)
    public void getItemByCategoryTest(String category){
        List<Item> items;
        items = em.createQuery("select i from Item  i where i.itemType LIKE :category and i.itemStatus LIKE :status")
                .setParameter("category","car")
                .setParameter("status","active")
                .getResultList();
        Assert.assertTrue(items.isEmpty());
    }

    @Test
    @InSequence(value = 9)
    public void getItemByUploadDate(){
        List<Item> items;
        items = em.createQuery("select i from Item i ORDER BY i.itemUploadDate ASC ")
                .getResultList();
        Assert.assertFalse(items.isEmpty());
    }

    @Test
    @InSequence(value = 10)
    public void getItemByHighestBids(){
        List<Item> items;
        items = em.createQuery("select i from Item i ORDER BY i.itemBidHistory DESC ")
                .getResultList();
        Assert.assertEquals(items.get(0).getItemBidHistory(),1);
    }
    // tests go above
}

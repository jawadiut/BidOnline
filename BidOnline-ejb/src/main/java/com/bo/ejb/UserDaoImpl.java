package com.bo.ejb;

import com.bo.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 2/25/13
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */

@Stateless
public class UserDaoImpl implements UserDao{

    @PersistenceContext(unitName = "persistBD")
    private EntityManager em;

//    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void saveUser(User user){
        //user.setUserId(5);
        em.persist(user);
    }

    @Override
    public User authenticateUser(User user){
        return (User) em.createQuery("SELECT u from User u where u.userName LIKE :UName AND u.password LIKE :PSWord")
                .setParameter("UName",user.getUserName())
                .setParameter("PSWord",user.getPassword())
                .getSingleResult();
    }

    @Override
    public User findUserById(int userId){
        return (User)em.find(User.class,userId);
    }
    @Override
    public User getUserWithBidList(int userId) {
        User user;
        try {
            user = (User) em.createQuery("SELECT u FROM User u JOIN FETCH u.bidItems WHERE u.userId=:userId ORDER BY u.userId DESC")
                    .setParameter("userId", userId).getSingleResult();
        } catch (NoResultException ex) {
            user = em.find(User.class, userId);
            user.getBidItems().size();
        }
        return user;
    }

    public User getUserWithOfferList(int userId){
        User user;
        try {
            user = (User) em.createQuery("SELECT u FROM User u JOIN FETCH u.items WHERE u.userId=:userId ORDER BY u.userId DESC")
                    .setParameter("userId", userId).getSingleResult();
        } catch (NoResultException ex) {
            user = em.find(User.class, userId);
            user.getItems().size();
        }
        return user;
    }

    public User getUserWithBids(int userId){
        User user;
        try {
            user = (User) em.createQuery("SELECT u FROM User u JOIN FETCH u.bids WHERE u.userId=:userId ORDER BY u.userId DESC")
                    .setParameter("userId", userId).getSingleResult();
        } catch (NoResultException ex) {
            user = em.find(User.class, userId);
            user.getBids().size();
        }
        return user;
    }

//    @Override
//    public Integer getUserByItemId(int itemId){
//        return em.createQuery("select u.userId from User u where ")
//    }

}

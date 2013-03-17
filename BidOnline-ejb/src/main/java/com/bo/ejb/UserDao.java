package com.bo.ejb;

import com.bo.entity.User;

import javax.ejb.Local;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 2/25/13
 * Time: 10:19 AM
 * To change this template use File | Settings | File Templates.
 */

@Local
public interface UserDao {
    public void saveUser(User user);
    public User authenticateUser(User user);
    public User findUserById(int userId);
    public User getUserWithBidList(int userId);
    public User getUserWithOfferList(int userId);
    public User getUserWithBids(int userId);
//    public Integer getUserByItemId(int itemId);
}

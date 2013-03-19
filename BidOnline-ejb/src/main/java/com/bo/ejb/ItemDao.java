package com.bo.ejb;

import com.bo.entity.Bid;
import com.bo.entity.Item;


import javax.ejb.Local;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/2/13
 * Time: 9:09 AM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface ItemDao {
    public void uploadItem(Integer userId, Item item);
    public Item findItemById(int itemId);
    public void deleteItem(int itemId);
    public Item getItem(int itemId);
    public void updateItem(Item item);
    public List<Item> getItems();
    public List<Item> getItemByUploadDate();
    public List<Item> getItemByHighestBids();
    public List<Item> getPendingItems();
    public void updateBidInfo(Integer userId, Bid bid,Item item);
    public Item getItemWithBidders(Integer itemId);
    public List<Item> getItemByCategory(String category);
    public Item getItemWithBids(Integer itemId);
}

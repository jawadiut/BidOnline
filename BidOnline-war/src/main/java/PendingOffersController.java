import com.bo.ejb.ItemDao;
import com.bo.ejb.UserDao;
import com.bo.entity.Item;
import com.bo.entity.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/16/13
 * Time: 9:50 AM
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class PendingOffersController {

    private User user;
    private Item item;
    private List<Item> itemList;
    //private List<NewItem> newItemList;


    @EJB
    private UserDao userDao;

    @EJB
    private ItemDao itemDao;

    @PostConstruct
    public void init() {
        item = new Item();
        itemList = itemDao.getPendingItems();
    }

    public User getUser() {
        return user;
    }

    public Item getItem() {
        return item;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }


//    public List<NewItem> getNewItemList() {
//        return newItemList;
//    }
//
//    public void setNewItemList(List<NewItem> newItemList) {
//        this.newItemList = newItemList;
//    }

//    public NewItem getNewItem() {
//        return newItem;
//    }
//
//    public void setNewItem(NewItem newItem) {
//        this.newItem = newItem;
//    }




    public String offeredItemDetails(int itemId) {
        //item = itemDao.getItem(itemId);
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);

        if (httpSession.getAttribute("loggedUserId") != null) {

            httpSession.setAttribute("loggedItemId", itemId);

            //httpSession.setAttribute("loggedOfferId",offerId);

            return "bidRequest.xhtml?faces-redirect=true";
        }
        return "mustLogin.xhtml?faces-redirect=true";
    }

    public String activateOffer(int itemId){

        item = itemDao.getItem(itemId);
        item.setItemStatus("active");
        itemDao.updateItem(item);

        return "pendingOffers.xhtml?faces-redirect=true";
    }

    public String seeUserProfile(int userId){
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);

        httpSession.setAttribute("userProfileId",userId);
        return "userProfile.xhtml?faces-redirect=true";
    }
}

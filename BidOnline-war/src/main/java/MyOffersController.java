import com.bo.ejb.ItemDao;
import com.bo.ejb.OfferDao;
import com.bo.ejb.UserDao;
import com.bo.entity.Item;
import com.bo.entity.Offer;
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
 * Date: 3/11/13
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class MyOffersController {
    private User user;
    private Item item;
    private String itemCategory;
    private String loggedUserId;
    private List<Item> items;
    //    private List<Offer> offers;
    @EJB
    private ItemDao itemDao;
    @EJB
    private UserDao userDao;

    //    @EJB
//    private OfferDao offerDao;
    @PostConstruct
    public void init() {
        //offers = new ArrayList<Offer>();
        //System.out.println("inside init....");
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);

        user = userDao.getUserWithOfferList((Integer) httpSession.getAttribute("loggedUserId"));
        items = user.getItems();

        System.out.println("items..." + items);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getLoggedUserId() {
        return loggedUserId;
    }

    //    public List<Offer> getOffers() {
//        return offers;
//    }
//
//    public void setOffers(List<Offer> offers) {
//        this.offers = offers;
//    }

    public String showBidList(Integer itemId) {
        System.out.println("bidItemId: " + itemId);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        httpSession.setAttribute("bidItemId", itemId);
        return "itemWiseBidList.xhtml?faces-redirect=true";
    }


    public String showItemDetails(Integer itemId) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        httpSession.setAttribute("loggedItemId", itemId);
        if (httpSession.getAttribute("loggedUserId") != null) {
            return "bidRequest.xhtml?faces-redirect=true";
        }
        return "mustLogin.xhtml?faces-redirect=true";
    }

    public String showItemList(String itemType){
        System.out.println("itemType...."+itemType);
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);

        httpSession.setAttribute("loggedItemCategory",itemType);
        return "offersByCategory.xhtml?faces-redirect=true";
    }

    public String logout(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        httpSession.invalidate();
        return "index.xhtml?faces-redirect=true";
    }
}

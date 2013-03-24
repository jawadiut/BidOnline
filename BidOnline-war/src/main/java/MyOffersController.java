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
    private Integer loggedUserId;
    private String loggedUserName;
    private List<Item> items;
    private List<Item> activeItems;
    //    private List<Offer> offers;
    @EJB
    private ItemDao itemDao;
    @EJB
    private UserDao userDao;

    @PostConstruct
    public void init() {

        activeItems = new ArrayList<Item>();
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        loggedUserId =(Integer)httpSession.getAttribute("loggedUserId");
        loggedUserName = (String)httpSession.getAttribute("loggedUserName");

        user = userDao.getUserWithOfferList(loggedUserId);
        items = user.getItems();
        for(Item i: items){
            if(i.getItemStatus().equals("active"))activeItems.add(i);
        }
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

    public Integer getLoggedUserId() {
        return loggedUserId;
    }

    public List<Item> getActiveItems() {
        return activeItems;
    }

    public String getLoggedUserName() {
        return loggedUserName;
    }

    public String showBidList(Integer itemId) {
        System.out.println("bidItemId: " + itemId);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        httpSession.setAttribute("bidItemId", itemId);
        return "itemWiseBidList.xhtml?faces-redirect=true";
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

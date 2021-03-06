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
 * Date: 3/17/13
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class MyPendingListController {
    private User user;
    private Item item;
    private Integer loggedUserId;
    private String loggedUserName;
    private String itemCategory;
    private List<Item> itemList;
    private List<Item> pendingItemList;
    @EJB
    private ItemDao itemDao;
    @EJB
    private UserDao userDao;

    @PostConstruct
    public void init(){

        pendingItemList = new ArrayList<Item>();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)context.getExternalContext().getSession(false);
        loggedUserId = (Integer)httpSession.getAttribute("loggedUserId");
        loggedUserName = (String)httpSession.getAttribute("loggedUserName");
        user = userDao.getUserWithOfferList(loggedUserId);
        itemList = user.getItems();
        for(Item i: itemList){
            if(i.getItemStatus().equals("pending"))pendingItemList.add(i);
        }
    }

    public User getUser() {
        return user;
    }

    public Item getItem() {
        return item;
    }

    public String getLoggedUserName() {
        return loggedUserName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public List<Item> getPendingItemList() {
        return pendingItemList;
    }

    public String cancelOffer(Integer itemId){
        itemDao.deleteItem(itemId);
        return "myPendingList.xhtml?faces-redirect=true";
    }

    public String showItemList(String itemType){
        System.out.println("itemType...."+itemType);
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);

        httpSession.setAttribute("loggedItemCategory",itemType);
        return "offersByCategory.xhtml?faces-redirect=true";
    }

    public String logout(){
        System.out.println("In logout :D");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);
        httpSession.invalidate();
        return "index.xhtml?faces-redirect=true";

    }
}

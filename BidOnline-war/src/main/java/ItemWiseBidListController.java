import com.bo.ejb.BidDao;
import com.bo.ejb.ItemDao;
import com.bo.ejb.OfferDao;
import com.bo.ejb.UserDao;
import com.bo.entity.Bid;
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
 * Date: 3/13/13
 * Time: 7:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class ItemWiseBidListController {
    private String loggedUserName;
    private Integer loggedUserId;
    private String itemCategory;
    private Item item;
    private Bid bid;
    //private Offer offer;
    private User user;
    private List<Bid> bidList;
    private List<User> bidderList;

    @EJB
    private ItemDao itemDao;
    @EJB
    private BidDao bidDao;
//    @EJB
//    private OfferDao offerDao;
    @EJB
    private UserDao userDao;

    @PostConstruct
    public void init(){
        bidderList = new ArrayList<User>();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        Integer itemId = (Integer)httpSession.getAttribute("bidItemId");

        loggedUserName = (String)httpSession.getAttribute("loggedUserName");
        loggedUserId = (Integer)httpSession.getAttribute("loggedUserId");
        item = itemDao.getItemWithBidders(itemId);
        bidderList = item.getBidders();

        item = itemDao.getItemWithBids(itemId);
        bidList = item.getBids();
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLoggedUserName() {
        return loggedUserName;
    }

    public void setLoggedUserName(String loggedUserName) {
        this.loggedUserName = loggedUserName;
    }

    public void setLoggedUserId(Integer loggedUserId) {
        this.loggedUserId = loggedUserId;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }


    public Integer getLoggedUserId() {
        return loggedUserId;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public List<Bid> getBidList() {
        return bidList;
    }

    public void setBidList(List<Bid> bidList) {
        this.bidList = bidList;
    }

    public List<User> getBidderList() {
        return bidderList;
    }

    public void setBidderList(List<User> bidderList) {
        this.bidderList = bidderList;
    }

    public String showUserProfile(Integer userId){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession =(HttpSession) context.getExternalContext().getSession(false);
        httpSession.setAttribute("userProfileId",userId);
        return "userProfile.xhtml?faces-redirect=true";
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

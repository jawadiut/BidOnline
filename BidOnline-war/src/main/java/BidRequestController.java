import com.bo.ejb.*;
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
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/7/13
 * Time: 11:19 AM
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class BidRequestController {
    private User user;
    private Item item;
    private Bid bid;
    private Integer loggedUserId;
    private Integer newBid;
    private List<User> bidders;
    @EJB
    private UserDao userDao;
    @EJB
    private ItemDao itemDao;
    @EJB
    private BidDao bidDao;

    @PostConstruct
    public void init(){
        newBid = 1;
        bid = new Bid();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)context.getExternalContext().getSession(false);
        Integer loggedItemId = (Integer) httpSession.getAttribute("loggedItemId");
        item = itemDao.getItemWithBidders(loggedItemId);
        loggedUserId = (Integer)httpSession.getAttribute("loggedUserId");
        user = userDao.findUserById(loggedUserId);
        bidders = item.getBidders();
        for(User u: bidders){
            if(u.getUserId() == loggedUserId){
                newBid = 0;
                bid = bidDao.getBidByItemAndUserId(loggedUserId,loggedItemId);
                break;
            }
        }
    }

    public String bidForThisItem(){

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)context.getExternalContext().getSession(false);
        bid.setBidDate(Calendar.getInstance().getTime());
        itemDao.updateBidInfo(user.getUserId(),bid,item);
        //item = itemDao.getItemWithBidders((Integer)httpSession.getAttribute("loggedItemId"));
        item.setItemBidHistory(item.getBidders().size());
        item.setItemLatestBid(bid.getBidPrice());
        itemDao.updateItem(item);
        return "myBidList.xhtml?faces-redirect=true";

    }

    public String bidAgainForThisItem(){
        item.setItemLatestBid(bid.getBidPrice());
        bid.setBidDate(Calendar.getInstance().getTime());
        bidDao.updateBid(bid);
        itemDao.updateItem(item);
        return "myBidList.xhtml?faces-redirect=true";
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

    public Integer getNewBid() {
        return newBid;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<User> getBidders() {
        return bidders;
    }

    //    public Offer getOffer() {
//        return offer;
//    }
//
//    public void setOffer(Offer offer) {
//        this.offer = offer;
//    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public Integer getLoggedUserId() {
        return loggedUserId;
    }
}

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
    @EJB
    private UserDao userDao;
    @EJB
    private ItemDao itemDao;
    @EJB
    private BidDao bidDao;

    @PostConstruct
    public void init(){

        bid = new Bid();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)context.getExternalContext().getSession(false);
        item = itemDao.getItem((Integer)httpSession.getAttribute("loggedItemId"));
        loggedUserId = (Integer)httpSession.getAttribute("loggedUserId");
        user = userDao.findUserById(loggedUserId);
    }

    public String bidForThisItem(){

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)context.getExternalContext().getSession(false);
        bid.setBidDate(Calendar.getInstance().getTime());
        itemDao.updateBidInfo(user.getUserId(),bid,item);
        item = itemDao.getItemWithBidders((Integer)httpSession.getAttribute("loggedItemId"));
        item.setItemBidHistory(item.getBidders().size());
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

    public void setItem(Item item) {
        this.item = item;
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

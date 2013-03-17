import com.bo.ejb.BidDao;
import com.bo.ejb.ItemDao;
import com.bo.ejb.UserDao;
import com.bo.entity.Bid;
import com.bo.entity.Item;
import com.bo.entity.Offer;
import com.bo.entity.User;
import org.primefaces.model.StreamedContent;

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
 * Date: 3/7/13
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class MyBidListController {
    private User user;
    private Item item;
    private Bid bid;
    private List<Item> itemList;
    private List<Bid> bidList;
    //private List<Offer> offerList;
    private StreamedContent streamedContent;
    @EJB
    private UserDao userDao;
    @EJB
    private ItemDao itemDao;
    @EJB
    private BidDao bidDao;
    @PostConstruct
    public void init() {
        //item = new Item();
        //itemList = new ArrayList<Item>();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        user = userDao.getUserWithBidList((Integer) httpSession.getAttribute("loggedUserId"));
        itemList = user.getBidItems();
        user = userDao.getUserWithBids((Integer)httpSession.getAttribute("loggedUserId"));
        bidList = user.getBids();
        System.out.println("bidList..."+bidList);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public List<Bid> getBidList() {
        return bidList;
    }

    public void setBidList(List<Bid> bidList) {
        this.bidList = bidList;
    }

    //    public List<Offer> getOfferList() {
//        return offerList;
//    }
//
//    public void setOfferList(List<Offer> offerList) {
//        this.offerList = offerList;
//    }

    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }
}

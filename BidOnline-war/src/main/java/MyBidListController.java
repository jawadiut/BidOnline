import com.bo.ejb.BidDao;
import com.bo.ejb.ItemDao;
import com.bo.ejb.UserDao;
import com.bo.entity.Bid;
import com.bo.entity.Item;

import com.bo.entity.User;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
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
    private String itemCategory;
    private List<Item> itemList;
    private List<Bid> bidList;
    private Integer loggedUserId;
    private String loggedUserName;
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
        loggedUserId = (Integer) httpSession.getAttribute("loggedUserId");
        loggedUserName=(String) httpSession.getAttribute("loggedUserName");
        user = userDao.getUserWithBidList(loggedUserId);
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

    public Integer getLoggedUserId() {
        return loggedUserId;
    }

    public void setLoggedUserId(Integer loggedUserId) {
        this.loggedUserId = loggedUserId;
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

    public String getLoggedUserName() {
        return loggedUserName;
    }

    public void setLoggedUserName(String loggedUserName) {
        this.loggedUserName = loggedUserName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String showItemList(String itemType){

        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);

        httpSession.setAttribute("loggedItemCategory",itemType);
        return "offersByCategory.xhtml?faces-redirect=true";
    }

    public String offeredItemDetails(int itemId) {
        //item = itemDao.getItem(itemId);
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);

        if (loggedUserId != null) {

            httpSession.setAttribute("loggedItemId", itemId);

            //httpSession.setAttribute("loggedOfferId",offerId);
            //loggedUserName = (String)httpSession.getAttribute("loggedUserName");

            return "bidRequest.xhtml?faces-redirect=true";
        }
        return "mustLogin.xhtml?faces-redirect=true";
    }

    public String logout(){

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);
        httpSession.invalidate();
        return "index.xhtml?faces-redirect=true";
//        System.out.println(user.getUserName()+);
    }
    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }
}

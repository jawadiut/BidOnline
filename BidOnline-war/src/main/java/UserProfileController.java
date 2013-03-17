import com.bo.ejb.ItemDao;
import com.bo.ejb.UserDao;
import com.bo.entity.Bid;
import com.bo.entity.Item;
import com.bo.entity.User;

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
 * Date: 3/14/13
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class UserProfileController {
    private User user;
    private Item item;
    private Integer userProfileId;
    private Integer loggedUserId;
    private Integer listSize;
    private List<Bid> bidList;
    @EJB
    private UserDao userDao;
    @EJB
    private ItemDao itemDao;
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)context.getExternalContext().getSession(false);
        userProfileId = (Integer) httpSession.getAttribute("userProfileId");
        loggedUserId = (Integer)httpSession.getAttribute("loggedUserId");
        user = userDao.getUserWithBids(userProfileId);
        listSize = user.getBids().size();
        bidList = user.getBids();
    }

    public User getUser() {
        return user;
    }

    public Integer getUserProfileId() {
        return userProfileId;
    }

    public Integer getLoggedUserId() {
        return loggedUserId;
    }

    public Integer getListSize() {
        return listSize;
    }

    public List<Bid> getBidList() {
        return bidList;
    }

    public void setBidList(List<Bid> bidList) {
        this.bidList = bidList;
    }

    public String showItemDetails(Integer itemId){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)context.getExternalContext().getSession(false);
        httpSession.setAttribute("loggedItemId",itemId);
        return "bidRequest.xhtml?faces-redirect=true";
    }

    public void editUser(){

    }
}

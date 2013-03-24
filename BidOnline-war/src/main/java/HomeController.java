import com.bo.ejb.ItemDao;
import com.bo.ejb.UserDao;
import com.bo.entity.Item;
import com.bo.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
 * Date: 2/27/13
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class HomeController {
    private User user;
    private Integer userRole;
    private Integer loggedUserId;
    private String loggedUserName;
    private String itemCategory;
    private List<Item> itemList;
    protected final Log log = LogFactory.getLog(getClass());

    @EJB
    private UserDao userDao;
    @EJB
    private ItemDao itemDao;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public String getLoggedUserName() {
        return loggedUserName;
    }

    public void setLoggedUserId(Integer loggedUserId) {
        this.loggedUserId = loggedUserId;
    }

    public void setLoggedUserName(String loggedUserName) {
        this.loggedUserName = loggedUserName;
    }

    public Integer getLoggedUserId() {
        return loggedUserId;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    @PostConstruct
    public void init(){

        System.out.println("user entered home....: ");

        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);

        loggedUserId =(Integer) httpSession.getAttribute("loggedUserId");

        loggedUserName = (String) httpSession.getAttribute("loggedUserName");

        user = userDao.findUserById(loggedUserId);
        userRole = user.getRole();
        itemList = itemDao.getItems();
        System.out.println("user info:\n"+user.getUserName()+"\n"+user.getEmail());
        System.out.println("user name: "+loggedUserName);

    }

    public String logout(){

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        httpSession.invalidate();
        return "index.xhtml?faces-redirect=true";

    }

    public String showItemList(String itemType){

        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);

        httpSession.setAttribute("loggedItemCategory",itemType);
        return "offersByCategory.xhtml?faces-redirect=true";
    }

    public String showUserProfile(Integer userId){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession =(HttpSession) context.getExternalContext().getSession(false);
        httpSession.setAttribute("userProfileId",userId);
        return "userProfile.xhtml?faces-redirect=true";
    }

    public String offeredItemDetails(int itemId) {
        //item = itemDao.getItem(itemId);
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);

        if (loggedUserId != null) {

            httpSession.setAttribute("loggedItemId", itemId);

            return "bidRequest.xhtml?faces-redirect=true";
        }
        return "mustLogin.xhtml?faces-redirect=true";
    }

    public String recent(){
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);

        httpSession.setAttribute("recent","recent");
        return "availableOffers.xhtml?faces-redirect=true";
    }

    public String top(){
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);

        httpSession.setAttribute("top","top");
        return "availableOffers.xhtml?faces-redirect=true";
    }

}

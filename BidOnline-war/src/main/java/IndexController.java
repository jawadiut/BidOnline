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
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 2/23/13
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class IndexController {
    protected final Log log = LogFactory.getLog(getClass());
    private User user;
    private String keyWord;
    private String[] items={"car","motor-cycle","auto-rickshaw","others","house","land","shirt","baby-wearings"
    ,"shari","shoes","diamond","silver","gold","food"};
    private List<Item> itemList;
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

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String[] getItems() {
        return items;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostConstruct
    public void init(){
        itemList = new ArrayList<Item>();
        if(user==null){
            user = new User();
            itemList = itemDao.getItems();
        }
    }

    public String loginUser(){

        User loggedUser = userDao.authenticateUser(user);

        if(loggedUser != null){

            FacesContext context = FacesContext.getCurrentInstance();

            HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);

            httpSession.setAttribute("loggedUserId",loggedUser.getUserId());
            httpSession.setAttribute("loggedUserName",loggedUser.getUserName());

            return "home.xhtml?faces-redirect=true";
        }

        return "login not successful";
    }

    public String showItemList(String itemType){
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);

        httpSession.setAttribute("loggedItemCategory",itemType);
        return "offersByCategory.xhtml?faces-redirect=true";
    }

    public String seeUserProfile(Integer userId){
        return "mustLogin.xhtml?faces-redirect=true";
    }

    public String offeredItemDetails(Integer itemId){
        return "mustLogin.xhtml?faces-redirect=true";
    }

}

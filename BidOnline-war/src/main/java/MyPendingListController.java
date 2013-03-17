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
    private List<Item> itemList;
    @EJB
    private ItemDao itemDao;
    @EJB
    private UserDao userDao;
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)context.getExternalContext().getSession(false);
        Integer loggedUserId = (Integer)httpSession.getAttribute("loggedUserId");
        user = userDao.getUserWithOfferList(loggedUserId);
    }

    public User getUser() {
        return user;
    }

    public Item getItem() {
        return item;
    }

    public List<Item> getItemList() {
        return itemList;
    }
}

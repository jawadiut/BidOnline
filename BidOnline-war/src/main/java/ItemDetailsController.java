import com.bo.ejb.ItemDao;
import com.bo.ejb.ItemDaoImpl;
import com.bo.ejb.OfferDao;
import com.bo.ejb.UserDao;
import com.bo.entity.Item;
import com.bo.entity.Offer;
import com.bo.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/1/13
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class ItemDetailsController {

    private User user;
    private Item item;

    protected final Log log = LogFactory.getLog(getClass());
    @EJB
    private ItemDao itemDao;
    @EJB
    private UserDao userDao;

    private List<Item> Items;
    private UploadedFile uploadedFile;
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

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    @PostConstruct
    public void init(){
        if(item == null){

            item = new Item();

            FacesContext context = FacesContext.getCurrentInstance();

            HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);

            Integer loggedUserId =(Integer) httpSession.getAttribute("loggedUserId");

            user = userDao.findUserById(loggedUserId);

            System.out.println("item available.................."+item);
            System.out.println("itemDao available..............."+itemDao);
        }
    }



    public String upload(){

        item.setItemUploadDate(Calendar.getInstance().getTime());
        item.setItemBidHistory(0);
        item.setItemLatestBid(0);
        item.setItemStatus("pending");
        itemDao.uploadItem(user.getUserId(),item);

        return "home.xhtml?faces-redirect=true";
    }


    public String bidItem(){
        return "home.xhtml?faces-redirect=true";
    }
    public String logout(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);
        httpSession.invalidate();
        return "index.xhtml?faces-redirect=true";

    }
}

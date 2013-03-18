import com.bo.ejb.ItemDao;
import com.bo.ejb.OfferDao;
import com.bo.ejb.UserDao;
import com.bo.entity.Item;
import com.bo.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/6/13
 * Time: 1:13 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class AvailableOffersController {
    protected final Log log = LogFactory.getLog(getClass());
    private List<Integer> offerList;
    private User user;
    private Item item;
    private Integer loggedUserId;
    private String loggedUserName;
    private String itemCategory;
    //private NewItem newItem;
    //private Offer offer;
    //private List<Offer> offers;

    private StreamedContent[] dbImage;
    private StreamedContent dbImg;
    private List<Item> itemList;
    //private List<NewItem> newItemList;
    private InputStream dbStream;
    @EJB
    private OfferDao offerDao;

    @EJB
    private UserDao userDao;

    @EJB
    private ItemDao itemDao;

    @PostConstruct
    public void init() {
        item = new Item();
        itemList = itemDao.getItems();
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);
        loggedUserId =(Integer) httpSession.getAttribute("loggedUserId");

        loggedUserName = (String) httpSession.getAttribute("loggedUserName");
    }

    public List<Integer> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<Integer> offerList) {
        this.offerList = offerList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public StreamedContent[] getDbImage() {
        return dbImage;
    }

    public void setDbImage(StreamedContent[] dbImage) {
        this.dbImage = dbImage;
    }

    public Integer getLoggedUserId() {
        return loggedUserId;
    }

    public void setLoggedUserId(Integer loggedUserId) {
        this.loggedUserId = loggedUserId;
    }

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

    public StreamedContent getDbImg() {
        return dbImg;
    }

    public void setDbImg(StreamedContent dbImg) {
        this.dbImg = dbImg;
    }

//    public List<NewItem> getNewItemList() {
//        return newItemList;
//    }
//
//    public void setNewItemList(List<NewItem> newItemList) {
//        this.newItemList = newItemList;
//    }

//    public NewItem getNewItem() {
//        return newItem;
//    }
//
//    public void setNewItem(NewItem newItem) {
//        this.newItem = newItem;
//    }




    public String offeredItemDetails(int itemId) {
        //item = itemDao.getItem(itemId);
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);

        if (httpSession.getAttribute("loggedUserId") != null) {

            httpSession.setAttribute("loggedItemId", itemId);
            return "bidRequest.xhtml?faces-redirect=true";
        }
        return "mustLogin.xhtml?faces-redirect=true";
    }

    public String seeUserProfile(int userId){
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);

        httpSession.setAttribute("userProfileId",userId);
        return "userProfile.xhtml?faces-redirect=true";
    }

    public String logout(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        httpSession.invalidate();
        return "index.xhtml?faces-redirect=true";
    }
}

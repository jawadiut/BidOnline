import com.bo.ejb.ItemDao;
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
 * Date: 3/16/13
 * Time: 5:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class OffersByCategoryController {
    private User user;
    private Item item;
    private String itemCategory;
    private List<Item> itemList;
    private Integer loggedUserId;
    private String loggedUserName;
    @EJB
    private ItemDao itemDao;

    @PostConstruct
    public void init(){

        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        loggedUserId = (Integer)httpSession.getAttribute("loggedUserId");
        loggedUserName = (String)httpSession.getAttribute("loggedUserName");
        itemCategory = (String)httpSession.getAttribute("loggedItemCategory");
        item = new Item();
        itemList = itemDao.getItemByCategory(itemCategory);

    }

    public User getUser() {
        return user;
    }

    public Item getItem() {
        return item;
    }

    public Integer getLoggedUserId() {
        return loggedUserId;
    }

    public List<Item> getItemList() {
        return itemList;
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

    public String logout(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        httpSession.invalidate();
        return "index.xhtml?faces-redirect=true";
    }

    public String showItemDetails(Integer itemId){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession)context.getExternalContext().getSession(false);
        httpSession.setAttribute("loggedItemId",itemId);
        if(httpSession.getAttribute("loggedUserId")!=null){
            return "bidRequest.xhtml?faces-redirect=true";
        }
        return "mustLogin.xhtml?faces-redirect=true";
    }

    public String showItemList(String itemType){
        System.out.println("itemType...."+itemType);
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);

        httpSession.setAttribute("loggedItemCategory",itemType);
        return "offersByCategory.xhtml?faces-redirect=true";
    }
}

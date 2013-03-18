import com.bo.ejb.UserDao;
import com.bo.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

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
    protected final Log log = LogFactory.getLog(getClass());

    @EJB
    private UserDao userDao;

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

    public Integer getLoggedUserId() {
        return loggedUserId;
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
        System.out.println("user info:\n"+user.getUserName()+"\n"+user.getEmail());
        System.out.println("user name: "+loggedUserName);

    }

    public String logout(){
        System.out.println("In logout :D");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);
        httpSession.invalidate();
        return "index.xhtml?faces-redirect=true";
//        System.out.println(user.getUserName()+);
    }

    public String showItemList(String itemType){
        System.out.println("itemType...."+itemType);
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);

        httpSession.setAttribute("loggedItemCategory",itemType);
        return "offersByCategory.xhtml?faces-redirect=true";
    }

}

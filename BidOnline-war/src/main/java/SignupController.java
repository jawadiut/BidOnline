import com.bo.ejb.UserDao;
import com.bo.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 2/25/13
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */

@Named
@RequestScoped
public class SignupController {
    private User user;

    protected final Log log = LogFactory.getLog(getClass());

    @EJB
    private UserDao userDao;

    @PostConstruct
    public void init(){
        System.out.println("init started....");
        if(user==null){
            user = new User();
            System.out.println(user);
            System.out.println("userdao-init-2..."+userDao);
        }
    }

    public String saveUser(){

        user.setRole(1);

        System.out.println("user being saved....:" + user.getUserName() + "," + user.getPermanentAddress() + "," + user.getUserId() + "," + user.getRole() + "," + user.getPresentAddress()
                + "," + user.getEmail() + "," + user.getPassword() + "," + user.getPhoneNumber());
        System.out.println(user);
        System.out.println("userdao..."+userDao);

        userDao.saveUser(user);

        return "index.xhtml?faces-redirect=true";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}

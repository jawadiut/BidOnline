/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/24/13
 * Time: 10:32 AM
 * To change this template use File | Settings | File Templates.
 */
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.bo.ejb.UserDao;
import com.bo.entity.User;
import junit.framework.Assert;
import org.easymock.EasyMock;
import org.glassfish.api.deployment.archive.Archive;
import org.junit.Before;
import org.junit.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class IndexControllerTest {
    private IndexController indexController;
    private FacesContext facesContext;
    private HttpSession httpSession;
    private ExternalContext externalContext;
    private UserDao userDao;
    private User user,user1;
//    @Deployment
//    public static Archive<?> createDeployment() {
//        Archive archive = ShrinkWrap.create(JavaArchive.class, "login-controller.jar")
//                .addPackage(User.class.getPackage());
//
//        System.out.println("LoginControllerTest: " + archive.toString(true));
//
//        return archive;
//    }


    @Before
    public void setup(){

        indexController = new IndexController();
        externalContext = EasyMock.createMock(ExternalContext.class);
        httpSession = EasyMock.createMock(HttpSession.class);
        userDao = EasyMock.createMock(UserDao.class);
        facesContext = ContextMocker.mockFacesContext();

        user = new User();
        //user.setUserId(1);
        user.setUserName("jawad");
        user.setPassword("therap");

        user1 = new User();
        user1.setUserId(12);
        user1.setCountry("Bangladesh");
        user1.setUserName("jawad");

        indexController.setUserDao(userDao);
        indexController.setUser(user);
    }

    @Test
    public void loginUserTest(){

        EasyMock.expect(userDao.authenticateUser(user)).andReturn(user1);
        EasyMock.replay(userDao);
        EasyMock.expect(facesContext.getExternalContext()).andReturn(externalContext);
        EasyMock.replay(facesContext);
        EasyMock.expect(externalContext.getSession(true)).andReturn(httpSession);
        EasyMock.replay(externalContext);
        System.out.println("User name:  >>>"+user1.getUserName());
        httpSession.setAttribute("loggedUserId",user1.getUserId());
        httpSession.setAttribute("loggedUserName",user1.getUserName());
        EasyMock.replay(httpSession);
        String response = indexController.loginUser();
        Assert.assertEquals(response,"home.xhtml?faces-redirect=true");
    }

    @Test
    public void showItemListTest(){
        EasyMock.expect(facesContext.getExternalContext()).andReturn(externalContext);
        EasyMock.replay(facesContext);
        EasyMock.expect(externalContext.getSession(false)).andReturn(httpSession);
        EasyMock.replay(externalContext);
        httpSession.setAttribute("loggedItemType","car");
        String response = indexController.showItemList("car");
        Assert.assertEquals(response,"offersByCategory.xhtml?faces-redirect=true");
    }
}

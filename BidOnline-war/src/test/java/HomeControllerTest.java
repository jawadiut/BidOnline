/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/24/13
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.bo.ejb.ItemDao;
import com.bo.ejb.UserDao;
import com.bo.entity.Item;
import com.bo.entity.User;
import junit.framework.Assert;
import org.easymock.EasyMock;
import org.glassfish.api.deployment.archive.Archive;
import org.junit.Before;
import org.junit.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class HomeControllerTest {
    private HomeController homeController;
    private HttpSession httpSession;
    private FacesContext facesContext;
    private ExternalContext externalContext;
    private ItemDao itemDao;
    private Item item;

    @Before
    public void setup(){

        homeController = new HomeController();
        externalContext = EasyMock.createMock(ExternalContext.class);
        httpSession = EasyMock.createMock(HttpSession.class);
        itemDao = EasyMock.createMock(ItemDao.class);
        facesContext = ContextMocker.mockFacesContext();

        item = new Item();
        item.setItemId(1);
        item.setItemTitle("Marcidize");
    }

    @Test
    public void offeredItemDetailsTest(){

        EasyMock.expect(facesContext.getExternalContext()).andReturn(externalContext);
        EasyMock.replay(facesContext);
        EasyMock.expect(externalContext.getSession(true)).andReturn(httpSession);
        EasyMock.replay(externalContext);

        //httpSession.setAttribute("loggedUserId",user.getUserId());
        httpSession.setAttribute("loggedItemId", item.getItemId());

        String response = homeController.offeredItemDetails(item.getItemId());
        Assert.assertEquals(response,"mustLogin.xhtml?faces-redirect=true");
    }
    @Test
    public void logoutTest(){

        EasyMock.expect(facesContext.getExternalContext()).andReturn(externalContext);
        EasyMock.replay(facesContext);
        EasyMock.expect(externalContext.getSession(false)).andReturn(httpSession);
        EasyMock.replay(externalContext);
        String response = homeController.logout();
        Assert.assertEquals(response,"index.xhtml?faces-redirect=true");
    }

    @Test
    public void recent(){
        EasyMock.expect(facesContext.getExternalContext()).andReturn(externalContext);
        EasyMock.replay(facesContext);
        EasyMock.expect(externalContext.getSession(false)).andReturn(httpSession);
        EasyMock.replay(externalContext);
        httpSession.setAttribute("recent","recent");
        String response = homeController.recent();
        Assert.assertEquals(response,"availableOffers.xhtml?faces-redirect=true");
    }
}

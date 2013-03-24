import com.bo.ejb.ItemDao;
import com.bo.ejb.UserDao;
import com.bo.entity.Item;
import com.bo.entity.User;
import junit.framework.Assert;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/24/13
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class AvailableOffersControllerTest {

    private AvailableOffersController availableOffersController;
    private HttpSession httpSession;
    private FacesContext facesContext;
    private ExternalContext externalContext;
    private ItemDao itemDao;
    private Item item;
    private UserDao userDao;
    private User user;

    @Before
    public void setup(){

        availableOffersController = new AvailableOffersController();
        externalContext = EasyMock.createMock(ExternalContext.class);
        httpSession = EasyMock.createMock(HttpSession.class);
        itemDao = EasyMock.createMock(ItemDao.class);
        facesContext = ContextMocker.mockFacesContext();

        item = new Item();
        item.setItemId(1);
        item.setItemTitle("Picachu");
        item.setItemType("pokemon");

        user = new User();
        user.setUserId(1);
        user.setUserName("bazlur");

    }

    @Test
    public void offeredItemDetailsTest(){

        EasyMock.expect(facesContext.getExternalContext()).andReturn(externalContext);
        EasyMock.replay(facesContext);
        EasyMock.expect(externalContext.getSession(false)).andReturn(httpSession);
        EasyMock.replay(externalContext);

        httpSession.setAttribute("loggedUserId",user.getUserId());
        httpSession.setAttribute("loggedItemId", item.getItemId());

        String response = availableOffersController.offeredItemDetails(item.getItemId());
        Assert.assertEquals(response,"mustLogin.xhtml?faces-redirect=true");
    }

    @Test
    public void seeUserProfile(){

        EasyMock.expect(facesContext.getExternalContext()).andReturn(externalContext);
        EasyMock.replay(facesContext);
        EasyMock.expect(externalContext.getSession(true)).andReturn(httpSession);
        EasyMock.replay(externalContext);

        httpSession.setAttribute("userProfileId", user.getUserId());
        String response = availableOffersController.seeUserProfile(1);
        Assert.assertEquals(response,"userProfile.xhtml?faces-redirect=true");

    }

    @Test
    public void top(){

        EasyMock.expect(facesContext.getExternalContext()).andReturn(externalContext);
        EasyMock.replay(facesContext);
        EasyMock.expect(externalContext.getSession(false)).andReturn(httpSession);
        EasyMock.replay(externalContext);
        httpSession.setAttribute("top","top");
        String response = availableOffersController.top();
        Assert.assertEquals(response,"availableOffers.xhtml?faces-redirect=true");

    }


}

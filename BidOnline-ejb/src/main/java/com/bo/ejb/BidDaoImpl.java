package com.bo.ejb;

import com.bo.entity.Bid;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jawad
 * Date: 3/6/13
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class BidDaoImpl implements BidDao {
    @PersistenceContext(unitName = "persistBD")
    private EntityManager entityManager;
    @Override
    public void saveBid() {

    }

    @Override
    public List<Bid> getBidByOfferId(Integer offerId) {
        return  entityManager.createQuery("select b from Bid b where b.offerId=:offerId")
                .setParameter("offerId",offerId)
                .getResultList();
    }
}

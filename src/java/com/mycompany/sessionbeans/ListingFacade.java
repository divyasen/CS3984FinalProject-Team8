/*
 * Created by Woo Jin Kye on 2017.04.19  * 
 * Copyright © 2017 Osman Balci. All rights reserved. * 
 */
package com.mycompany.sessionbeans;

import com.mycompany.entityclasses.Listing;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author woojinkye
 */
@Stateless
public class ListingFacade extends AbstractFacade<Listing> {

    @PersistenceContext(unitName = "CS3984FinalProject-Team8PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ListingFacade() {
        super(Listing.class);
    }
    
    
    public Listing getListing(int id) {
        
        // The find method is inherited from the parent AbstractFacade class
        return em.find(Listing.class, id);
    }
    
    public List<Listing> findListingsByUserID(Integer userID) {
        /*
        The following @NamedQuery definition is given in Listing.java entity class file:
        @NamedQuery(name = "Listing.findListingsByUserId", query = "SELECT u FROM Listing u WHERE u.userId.id = :userId")
        
        The following statement obtaines the results from the named database query.
         */
        List<Listing> userFiles = em.createNamedQuery("Listing.findListingsByUserId")
                .setParameter("userId", userID)
                .getResultList();

        return userFiles;
    }
    
}

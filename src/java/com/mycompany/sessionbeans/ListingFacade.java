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

    public List<Listing> browseCategoryQuery(String browseCategory) {
        // Place the % wildcard before and after the search string to search for it anywhere in the publicVideo name 
        browseCategory = "%" + browseCategory + "%";
        // Conduct the search in a case-insensitive manner and return the results in a list.
        return getEntityManager().createQuery("SELECT c FROM Listing c WHERE c.category LIKE :browseCategory").setParameter("browseCategory", browseCategory).getResultList();
    }

    public List<Listing> searchQuery(String searchString, String browseCategory) {
        // Place the % wildcard before and after the search string to search for it anywhere in the name 
        searchString = "%" + searchString + "%";
        
        if (browseCategory.equals("all")) {
            browseCategory = "%" + browseCategory + "%";
            return getEntityManager().createQuery("SELECT l FROM Listing l WHERE l.itemName LIKE :searchString OR l.description LIKE :searchString").setParameter("searchString", searchString).getResultList();
        }
        // Conduct the search in a case-insensitive manner and return the results in a list.
        return getEntityManager().createQuery("SELECT l FROM Listing l WHERE (l.itemName LIKE :searchString OR l.description LIKE :searchString) AND l.category LIKE :browseCategory").setParameter("searchString", searchString).setParameter("browseCategory", browseCategory).getResultList();
    }
    
    public List<Listing> browseQuery(String category) {
        category = "%" + category + "%";
        
        return getEntityManager().createQuery("SELECT l FROM Listing l WHERE l.categroy LIKE :category").setParameter("category", category).getResultList();
    }

}

/*
 * Created by Woo Jin Kye on 2017.04.19  * 
 * Copyright © 2017 Osman Balci. All rights reserved. * 
 */
package com.mycompany.sessionbeans;

import com.mycompany.entityclasses.ListingPhoto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author woojinkye
 */
@Stateless
public class ListingPhotoFacade extends AbstractFacade<ListingPhoto> {

    @PersistenceContext(unitName = "CS3984FinalProject-Team8PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ListingPhotoFacade() {
        super(ListingPhoto.class);
    }
    
}

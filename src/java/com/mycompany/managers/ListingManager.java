/*
 * Created by Divya Sengar on 2017.04.19  * 
 * Copyright © 2017 Divya Sengar. All rights reserved. * 
 */
package com.mycompany.managers;

import com.mycompany.entityclasses.Listing;

import com.mycompany.sessionbeans.ListingFacade;
import com.mycompany.sessionbeans.ListingPhotoFacade;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

@Named(value = "listingManager")

/**
 *
 * @author Divya Sengar
 */
@SessionScoped
public class ListingManager implements Serializable {

    private String itemName;

    private String description;

    private Date postingDate;

    private BigDecimal price;

    private Listing selected;

    @EJB
    private ListingFacade listingFacade;
    
    @EJB
    private ListingPhotoFacade listingPhotoFacade;
    
    public ListingManager() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Listing getSelected() {
        return selected;
    }

    public void setSelected(Listing selected) {
        this.selected = selected;
    }

    public ListingFacade getListingFacade() {
        return listingFacade;
    }

    public void setListingFacade(ListingFacade listingFacade) {
        this.listingFacade = listingFacade;
    }

    public ListingPhotoFacade getListingPhotoFacade() {
        return listingPhotoFacade;
    }

    public void setListingPhotoFacade(ListingPhotoFacade listingPhotoFacade) {
        this.listingPhotoFacade = listingPhotoFacade;
    }
    
    public String createListing() {
        Listing newListing = new Listing();
        
        newListing.setItemName(itemName);
        newListing.setDescription(description);
        newListing.setPostingDate(postingDate);
        newListing.setPrice(price);
        
        getListingFacade().create(newListing);
        
        return "Profile.xhtml?faces-redirect=true";
    }
    
}

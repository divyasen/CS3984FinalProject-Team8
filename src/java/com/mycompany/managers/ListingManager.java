/*
 * Created by Divya Sengar on 2017.04.19  * 
 * Copyright © 2017 Divya Sengar. All rights reserved. * 
 */
package com.mycompany.managers;

import com.mycompany.entityclasses.Listing;

import com.mycompany.sessionbeans.ListingFacade;
import com.mycompany.sessionbeans.ListingPhotoFacade;
import com.mycompany.sessionbeans.UserFacade;
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
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

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
    
    private String category;

    private Listing selected;

   private String statusMessage;

    
    private List<UploadedFile> photos;

    @EJB
    private UserFacade userFacade;
    
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
    
    public UserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
    
    public void handleFileUpload(FileUploadEvent event){
        photos.add(event.getFile());
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<UploadedFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<UploadedFile> photos) {
        this.photos = photos;
    }
    
    public String createListing() {
        Listing newListing = new Listing();
        
        newListing.setItemName(itemName);
        newListing.setDescription(description);
        newListing.setPostingDate(new Date());
        newListing.setPrice(price);
         newListing.setCategory(category);
         int userPrimaryKey;
        userPrimaryKey = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id");
        newListing.setUserId(getUserFacade().find(userPrimaryKey));
        
        getListingFacade().create(newListing);
        
       // selected = newListing;
        
        return "MyProfile.xhtml?faces-redirect=true";
    }
    
}

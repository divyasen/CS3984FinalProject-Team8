/*
 * Created by Divya Sengar on 2017.04.19  * 
 * Copyright © 2017 Divya Sengar. All rights reserved. * 
 */
package com.mycompany.managers;

import com.mycompany.entityclasses.Listing;
import com.mycompany.entityclasses.ListingPhoto;
import com.mycompany.entityclasses.User;

import com.mycompany.sessionbeans.ListingFacade;
import com.mycompany.sessionbeans.ListingPhotoFacade;
import com.mycompany.sessionbeans.UserFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
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

    private FileUploadEvent photo1;

    private FileUploadEvent photo2;

    private FileUploadEvent photo3;

    private FileUploadEvent photo4;

    private FileUploadEvent photo5;

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

    public void handleFileUpload(FileUploadEvent event) {
        if (photo1 == null) {
            photo1 = event;
        } else if (photo2 == null) {
            photo2 = event;
        } else if (photo3 == null) {
            photo3 = event;
        } else if (photo4 == null) {
            photo4 = event;
        } else if (photo5 == null) {
            photo5 = event;
        }

    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    /*
    public ArrayList<FileUploadEvent> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<FileUploadEvent> photos) {
        this.photos = photos;
    }
     */
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

        selected = newListing;
        int index = 0;
        try {
            if (photo1 != null) {
                uploadPhoto(photo1, index);
                index++;
            }
            if (photo2 != null) {
                uploadPhoto(photo2, index);
                index++;

            }
            if (photo3 != null) {
                uploadPhoto(photo3, index);
                index++;

            }
            if (photo4 != null) {
                uploadPhoto(photo4, index);
                index++;

            }
            if (photo5 != null) {
                uploadPhoto(photo5, index);
                index++;

            }
        } catch (IOException ex) {
            Logger.getLogger(ListingManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        photo1 = null;
        photo2 = null;
        photo3 = null;
        photo4 = null;
        photo5 = null;

        return "MyProfile.xhtml?faces-redirect=true";
    }

    public void uploadPhoto(FileUploadEvent event, int index) throws IOException {

        try {


            /*
            To associate the file to the user, record "userId_filename" in the database.
            Since each file has its own primary key (unique id), the user can upload
            multiple files with the same name.
             */
            String userId_filename = selected.getId() + "_" + index + "." + getExtension(event.getFile().getFileName());

            /*
            "The try-with-resources statement is a try statement that declares one or more resources. 
            A resource is an object that must be closed after the program is finished with it. 
            The try-with-resources statement ensures that each resource is closed at the end of the
            statement." [Oracle] 
             */
            try (InputStream inputStream = event.getFile().getInputstream();) {

                // The method inputStreamToFile given below writes the uploaded file into the CloudStorage/FileStorage directory.
                inputStreamToFile(inputStream, userId_filename);
                inputStream.close();
            }

            /*
            Create a new ListingPhoto object with attibutes: (See ListingPhoto table definition inputStream DB)
                <> id = auto generated as the unique Primary key for the user file object
                <> filename = userId_filename
                <> user_id = user
             */
            ListingPhoto newListingPhoto = new ListingPhoto(getExtension(event.getFile().getFileName()), selected);

            /*
            ==============================================================
            If the userId_filename was used before, delete the earlier file.
            ==============================================================
             */
            // List<ListingPhoto> filesFound = getListingPhotoFacade().findByFilename(userId_filename);

            /*
            If the userId_filename already exists in the database, 
            the filesFound List will not be empty.
             */
 /*
            if (!filesFound.isEmpty()) {

                // Remove the file with the same name from the database
                getListingPhotoFacade().remove(filesFound.get(0));
            }
             */
            //---------------------------------------------------------------
            //
            // Create the new ListingPhoto entity (row) in the CloudDriveDB
            getListingPhotoFacade().create(newListingPhoto);

            // This sets the necessary flag to ensure the messages are preserved.
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

            //getListingPhotoController().refreshFileList();
            //resultMsg = new FacesMessage("File(s) Uploaded Successfully!");
            //FacesContext.getCurrentInstance().addMessage(null, resultMsg);
            // After successful upload, show the ListingPhotos.xhtml facelets page
            //FacesContext.getCurrentInstance().getExternalContext().redirect("ListingPhotos.xhtml");
        } catch (IOException e) {
            //resultMsg = new FacesMessage("Something went wrong during file upload! See: " + e.getMessage());
            //FacesContext.getCurrentInstance().addMessage(null, resultMsg);
        }

    }

    private File inputStreamToFile(InputStream inputStream, String file_name)
            throws IOException {

        // Read the series of bytes from the input stream
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);

        // Write the series of bytes on uploadedFile.
        File targetFile = new File(Constants.LISTING_PHOTOS_ABSOLUTE_PATH, file_name);

        OutputStream outStream;
        outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);
        outStream.close();

        return targetFile;
    }

    /**
     * Used to return the file extension for a file.
     *
     * @param filename
     * @return
     */
    public static String getExtension(String filename) {

        if (filename == null) {
            return null;
        }
        int extensionPos = filename.lastIndexOf('.');

        int lastUnixPos = filename.lastIndexOf('/');
        int lastWindowsPos = filename.lastIndexOf('\\');
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);
        int index = lastSeparator > extensionPos ? -1 : extensionPos;

        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

    public String getSinglePhoto(int listingId) {
        ListingPhoto list = getListingPhotoFacade().findPhotosByListingID(listingId).get(0);
        String temp = Constants.LISTING_PHOTOS_RELATIVE_PATH + listingId + "_0." + list.getExtension();
        return temp;
    }

    public List<String> getPhotos(int listingId) {
        List<ListingPhoto> list = getListingPhotoFacade().findPhotosByListingID(listingId);
        List<String> answer = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            answer.add(Constants.LISTING_PHOTOS_RELATIVE_PATH + listingId + "_" + i + "." + list.get(i).getExtension());
        }
        return answer;
    }

    /*
    Update the signed-in user's account profile. Return "" if an error occurs;
    otherwise, upon successful account update, redirect to show the Profile page.
     */
    public String updateListing() {

        if (statusMessage == null || statusMessage.isEmpty()) {

            Listing listing = selected;

            try {

                listing.setItemName(this.selected.getItemName());
                listing.setCategory(this.selected.getCategory());
                listing.setDescription(this.selected.getDescription());
                listing.setPrice(this.selected.getPrice());

                // Store the changes in the CloudDriveDB database
                getListingFacade().edit(listing);

            } catch (EJBException e) {
                statusMessage = "Something went wrong while editing user's profile! See: " + e.getMessage();
                return "";
            }
            // Account update is completed, redirect to show the Profile page.
            return "MyProfile.xhtml?faces-redirect=true";
        }
        return "";

    }

    public String editListing(Listing listing) {
        setSelected(listing);
        return "EditListing.xhtml?faces-redirect=true";
    }
}

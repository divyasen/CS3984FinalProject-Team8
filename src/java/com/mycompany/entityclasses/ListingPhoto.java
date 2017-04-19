/*
 * Created by Woo Jin Kye on 2017.04.19  * 
 * Copyright © 2017 Osman Balci. All rights reserved. * 
 */
package com.mycompany.entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author woojinkye
 */
@Entity
@Table(name = "ListingPhoto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListingPhoto.findAll", query = "SELECT l FROM ListingPhoto l")
    , @NamedQuery(name = "ListingPhoto.findById", query = "SELECT l FROM ListingPhoto l WHERE l.id = :id")
    , @NamedQuery(name = "ListingPhoto.findByExtension", query = "SELECT l FROM ListingPhoto l WHERE l.extension = :extension")})
public class ListingPhoto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "extension")
    private String extension;
    @JoinColumn(name = "listing_id", referencedColumnName = "id")
    @ManyToOne
    private Listing listingId;

    public ListingPhoto() {
    }

    public ListingPhoto(Integer id) {
        this.id = id;
    }

    public ListingPhoto(Integer id, String extension) {
        this.id = id;
        this.extension = extension;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Listing getListingId() {
        return listingId;
    }

    public void setListingId(Listing listingId) {
        this.listingId = listingId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListingPhoto)) {
            return false;
        }
        ListingPhoto other = (ListingPhoto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entityclasses.ListingPhoto[ id=" + id + " ]";
    }
    
}

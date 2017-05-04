/*
 * Created by Woo Jin Kye on 2017.04.19  * 
 * Copyright © 2017 Osman Balci. All rights reserved. * 
 */
package com.mycompany.entityclasses;

import com.mycompany.managers.Constants;
import com.mycompany.jsfclasses.ListingController;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author woojinkye
 */
@Entity
@Table(name = "Listing")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Listing.findAll", query = "SELECT l FROM Listing l")
    , @NamedQuery(name = "Listing.findById", query = "SELECT l FROM Listing l WHERE l.id = :id")
    , @NamedQuery(name = "Listing.findByItemName", query = "SELECT l FROM Listing l WHERE l.itemName = :itemName")
    , @NamedQuery(name = "Listing.findListingsByUserId", query = "SELECT u FROM Listing u WHERE u.userId.id = :userId")
    , @NamedQuery(name = "Listing.findByPostingDate", query = "SELECT l FROM Listing l WHERE l.postingDate = :postingDate")
    , @NamedQuery(name = "Listing.findByPrice", query = "SELECT l FROM Listing l WHERE l.price = :price")
    , @NamedQuery(name = "Listing.findByCategory", query = "SELECT l FROM Listing l WHERE l.category = :category")
})
public class Listing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "item_name")
    private String itemName;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "posting_date")
    @Temporal(TemporalType.DATE)
    private Date postingDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private BigDecimal price;
    @OneToMany(mappedBy = "listingId")
    private Collection<ListingPhoto> listingPhotoCollection;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "category")
    private String category;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;

    public Listing() {
    }

    public Listing(Integer id) {
        this.id = id;
    }

    public Listing(Integer id, String itemName, String description, Date postingDate, BigDecimal price, String Category) {
        this.id = id;
        this.itemName = itemName;
        this.description = description;
        this.postingDate = postingDate;
        this.price = price;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @XmlTransient
    public Collection<ListingPhoto> getListingPhotoCollection() {
        return listingPhotoCollection;
    }

    public void setListingPhotoCollection(Collection<ListingPhoto> listingPhotoCollection) {
        this.listingPhotoCollection = listingPhotoCollection;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof Listing)) {
            return false;
        }
        Listing other = (Listing) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entityclasses.Listing[ id=" + id + " ]";
    }
    
    public void editListing() {
        
    }

}

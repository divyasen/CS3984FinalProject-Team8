package com.mycompany.jsfclasses;

import com.mycompany.entityclasses.Listing;
import com.mycompany.entityclasses.User;
import com.mycompany.entityclasses.User;
import com.mycompany.jsfclasses.util.JsfUtil;
import com.mycompany.jsfclasses.util.JsfUtil.PersistAction;
import com.mycompany.sessionbeans.ListingFacade;
import com.mycompany.sessionbeans.UserFacade;
import java.io.IOException;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;

@Named("listingController")
@SessionScoped
public class ListingController implements Serializable {

    private String searchString;
    private String searchField;
    private String category;
    
    @EJB
    private ListingFacade listingFacade;

    // selected = Selected Listing object
    private Listing selected;

    // items = list of Listing objects
    private List<Listing> items = null;
    private List<Listing> searchItems = null;
    private List<Listing> browseItems = null;
    
    @EJB
    private com.mycompany.sessionbeans.ListingFacade ejbFacade;

    private List<Listing> browseAppliances = null;
    private List<Listing> browseArtsAndCrafts = null;
    private List<Listing> browseBooks = null;
    private List<Listing> browseClothing = null;
    private List<Listing> browseCollectibles = null;
    private List<Listing> browseElectronics = null;
    private List<Listing> browseMiscellaneous = null;
    private List<Listing> browseSportsAndOutdoors = null;
    private List<Listing> browseToysAndGames = null;
    private List<Listing> browseVehicles = null;
    private List<Listing> myListings = null;

    @EJB
    private UserFacade userFacade;

    public ListingController() {
    }

    public ListingFacade getListingFacade() {
        return listingFacade;
    }

    public void setListingFacade(ListingFacade listingFacade) {
        this.listingFacade = listingFacade;
    }

    public UserFacade getUserFacade() {
        return userFacade;
    }
    
    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
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

    public List<Listing> getBrowseAppliances() {
        return getListingFacade().browseCategoryQuery("Appliances");
    }

    public List<Listing> getBrowseArtsAndCrafts() {
        return getListingFacade().browseCategoryQuery("ArtsAndCrafts");
    }

    public List<Listing> getBrowseBooks() {
        return getListingFacade().browseCategoryQuery("Books");
    }

    public List<Listing> getBrowseClothing() {
        return getListingFacade().browseCategoryQuery("Clothing");
    }

    public List<Listing> getBrowseCollectibles() {
        return getListingFacade().browseCategoryQuery("Collectibles");
    }

    public List<Listing> getBrowseElectronics() {
        return getListingFacade().browseCategoryQuery("Electronics");
    }

    public List<Listing> getBrowseMiscellaneous() {
        return getListingFacade().browseCategoryQuery("Miscellaneous");
    }

    public List<Listing> getBrowseSportsAndOutdoors() {
        return getListingFacade().browseCategoryQuery("SportsAndOutdoors");
    }

    public List<Listing> getBrowseToysAndGames() {
        return getListingFacade().browseCategoryQuery("ToysAndGames");
    }

    public List<Listing> getBrowseVehicles() {
        return getListingFacade().browseCategoryQuery("Vehicles");
    }

    public List<Listing> getMyListings() {
        // Obtain the username of the logged-in user
        String user_name = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("username");

        // Obtain the object reference of the logged-in User object
        User user = getUserFacade().findByUsername(user_name);
        
        return getFacade().findListingsByUserID(user.getId());
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ListingFacade getFacade() {
        return ejbFacade;
    }

    public Listing prepareCreate() {
        selected = new Listing();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ListingCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ListingUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ListingDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Listing> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    
    /*
    Return the list of object references of all those Listings where 
    the search string 'searchString' entered by the user is contained in the searchField.
     */
    public List<Listing> getSearchItems() {
        return getListingFacade().searchQuery(searchString, searchField);
    }
    
    /*Return the list of object references of all those Listings where
    their category is 'category'
    */
    public List<Listing> getBrowseItems() {
        if (category.equals("all")) {
            return getItems();
        }
        return getListingFacade().browseCategoryQuery(category);
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Listing getListing(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Listing> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Listing> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Listing.class)
    public static class ListingControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ListingController controller = (ListingController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "listingController");
            return controller.getListing(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Listing) {
                Listing o = (Listing) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Listing.class.getName()});
                return null;
            }
        }

    }
    

    /**
     * @SessionScoped enables to preserve the values of the instance variables for the SearchResults.xhtml page to access.
     *
     * @param actionEvent refers to clicking the Submit button
     * @throws IOException if the page to be redirected to cannot be found
     */
    public void search(ActionEvent actionEvent) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("SearchResults.xhtml");
    }

    public void browse(ActionEvent actionEvent) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("Browse.xhtml");
    }
}

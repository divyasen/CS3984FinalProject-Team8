/*
 * Created by Woo Jin Kye on 2017.05.04  * 
 * Copyright © 2017 Woo Jin Kye. All rights reserved. * 
 */
package com.mycompany.managers;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/*
========================================
PrimeFaces HTML Text Editor UI Component
========================================
Adapted from http://www.primefaces.org/showcase/ui/input/editor.xhtml 
The @Named class annotation designates the bean object created in this class 
as a Contexts and Dependency Injection (CDI) named bean. The value attribute 
defines the logical name of the bean that can be used in JSF facelets pages. 
 */
@Named(value = "editorViewManager")

/* 
The @SessionScoped annotation indicates that this CDI-managed bean will be
maintained (i.e., its property values will be kept) across multiple HTTP requests 
as long as the user's established HTTP session is active. 
*/
@SessionScoped

public class EditorViewManager implements Serializable {

    private String text;
    private String sellerEmail;
    
    // Constructor method to provide initial content
    public EditorViewManager() {
        text = "";
        sellerEmail = "";
    }
    
    public String getText() {
        return text;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }
    
    public String setText(String itemName, String price, String postingDate, String email, String phone, String sellerEmail) {
        this.text = "<div align=\"center\">I am interested in buying the following item: " 
                + "<br /><br /><b>" + itemName + "</b><br /><br />"
                + "<div align=\"center\"><table>"
                + "<tr><td align=\"right\"><i>Price: </i></td><td>" + price + "</td></tr><tr></tr>"
                + "<tr><td align=\"right\"><i>Posting Date: </i></td><td>" + postingDate + "</td></tr><tr></tr>"
                + "</table></div></br></br>"
                + "Below is my contact information: "
                + "<div align=\"center\"><table>"
                + "<tr><td align=\"right\"><i>Email: </i></td><td>" + email + "</td></tr><tr></tr>"
                + "<tr><td align=\"right\"><i>Phone: </i></td><td>" + phone + "</td></tr><tr></tr>"
                + "</table></div>"
                + "</div>";
        
        this.sellerEmail = sellerEmail;
        
        return "ContactSeller.xhtml?faces-redirect=true";
    }

    public void setText(String text) {
        this.text = text;
    }
}
/*
 * Created by Travis Weissenberger on 2017.04.19  * 
 * Copyright © 2017 Travis Weissenberger. All rights reserved. * 
 */
package com.mycompany.managers;

/**
 *
 * @author Travis 
 */
public final class Constants {

    /* =========== Our Design Decision ===========
        We decided to use directories external to our application 
        for the storage and retrieval of user's files.
    
        We do not want to use a database for the following reasons: 
            (a) Database storage and retrieval of large files as 
                BLOB (binary large object) degrades performance.
            (b) BLOBs increase the database complexity.
    
        Therefore, we use the following external directory
        for the storage and retrieval of user's photos.
     
     

    */
    public static final String PHOTOS_ABSOLUTE_PATH = "/home/cloudsd/Weissenberger/UserPhotoStorage/";
    /*
 public static final String PHOTOS_ABSOLUTE_PATH = "C:\\Users\\Travis\\UserPhotoStorage\\";
    
    *//*
    Relative path is defined with respect to the Alternate Document Root starting with 'UserPhotoStorage'.
     */
    public static final String PHOTOS_RELATIVE_PATH = "UserPhotoStorage/";
    public static final String DEFAULT_PHOTO_RELATIVE_PATH = "UserPhotoStorage/defaultUserPhoto.png";

    /* Temporary filename */
    public static final String TEMP_FILE = "tmp_file";

    /* =========== Our Design Decision ===========
        We decided to scale down the user's uploaded photo to 200x200 px,
        which we call the Thumbnail photo, and use it.
    
        We do not want to use the uploaded photo as is, which may be
        very large in size degrading performance.
     */
    public static final Integer THUMBNAIL_SIZE = 200;

    /* Security questions to reset password  */
    public static final String[] QUESTIONS = {
        "In what city were you born?",
        "What is your mother's maiden name?",
        "What elementary school did you attend?",
        "What was the make of your first car?",
        "What is your father's middle name?",
        "What is the name of your most favorite pet?",
        "What street did you grow up on?"
    };

}
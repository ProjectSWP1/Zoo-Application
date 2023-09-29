package com.thezookaycompany.zookayproject.utils;

import jakarta.servlet.http.HttpServletRequest;

public class Utility {

    // hàm get current link
    public static String getSiteUrl(HttpServletRequest request){
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(),"");
    }
}

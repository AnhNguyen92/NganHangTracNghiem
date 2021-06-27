package vn.com.multiplechoice.web.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    private RequestUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}

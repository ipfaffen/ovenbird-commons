package com.ipfaffen.ovenbird.commons;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Isaias Pfaffenseller
 */
public class HttpUtil {

	/**
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
	    String requestedWithHeader = request.getHeader("X-Requested-With");
	    return "XMLHttpRequest".equals(requestedWithHeader);
	}
}
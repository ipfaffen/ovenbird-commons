package com.ipfaffen.ovenbird.commons;

import java.util.regex.Pattern;

/**
 * @author Isaias Pfaffenseller
 */
public class RegexUtil {

	/**
	 * Html related.
	 */
	public final static String TAG_START = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>";
	public final static Pattern TAG_START_PATTERN = Pattern.compile(TAG_START, Pattern.DOTALL);
	public final static String TAG_END = "\\</\\w+\\>";
	public final static Pattern TAG_END_PATTERN = Pattern.compile(TAG_END, Pattern.DOTALL);
	public final static String TAG_SELF_CLOSING = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>";
	public final static String HTML_ENTITY = "&[a-zA-Z][a-zA-Z0-9]+;";
	public final static Pattern HTML_PATTERN = Pattern.compile("(" + TAG_START + ".*" + TAG_END + ")|(" + TAG_SELF_CLOSING + ")|(" + HTML_ENTITY + ")", Pattern.DOTALL);
	
	/**
	 * Will return true if s contains HTML markup tags or entities.
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isHtml(String s) {
		return ((s != null) && HTML_PATTERN.matcher(s).find());
	}
	
	/**
	 * @param s
	 * @param fully
	 * @return
	 */
	public static String removeHtml(String s, boolean fully) {
		if(s == null) {
			return s;
		}
		s = HTML_PATTERN.matcher(s).replaceAll("");
		if(fully) {
			s = TAG_START_PATTERN.matcher(s).replaceAll("");
			s = TAG_END_PATTERN.matcher(s).replaceAll("");
		}
		return s;
	}
	
    /**
     * Good characters for Internationalized Resource Identifiers (IRI).
     * This comprises most common used Unicode characters allowed in IRI as detailed in RFC 3987.
     * Specifically, those two byte Unicode characters are not included.
     */
    public static final String GOOD_IRI_CHAR = "a-zA-Z0-9\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF";
    public static final Pattern IP_ADDRESS = Pattern.compile("((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9]))");
    /**
     * RFC 1035 Section 2.3.4 limits the labels to a maximum 63 octets.
     */
    private static final String IRI = "[" + GOOD_IRI_CHAR + "]([" + GOOD_IRI_CHAR + "\\-]{0,61}[" + GOOD_IRI_CHAR + "]){0,1}";
    private static final String GOOD_GTLD_CHAR = "a-zA-Z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF";
    private static final String GTLD = "[" + GOOD_GTLD_CHAR + "]{2,63}";
    private static final String HOST_NAME = "(" + IRI + "\\.)+" + GTLD;
    public static final Pattern DOMAIN_NAME = Pattern.compile("(" + HOST_NAME + "|" + IP_ADDRESS + ")");

    /**
     * Regular expression pattern to match most part of RFC 3987 Internationalized URLs, aka IRIs.
     * Commonly used Unicode characters are added.
     */
    public static final Pattern WEB_URL = Pattern.compile(
        "((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)"
        + "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_"
        + "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?"
        + "(?:" + DOMAIN_NAME + ")"
        + "(?:\\:\\d{1,5})?)" // Plus option port number.
        + "(\\/(?:(?:[" + GOOD_IRI_CHAR + "\\;\\/\\?\\:\\@\\&\\=\\#\\~" // Plus option query params.
        + "\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?"
        + "(?:\\b|$)"); // And finally, a word boundary or end of input. This is to stop foo.sure from matching as foo.su
	
	/**
	 * @param s
	 * @return
	 */
	public static boolean isWebUrl(String s) {
		return ((s != null) && WEB_URL.matcher(s).find());
	}
	
	public static final String LINK_URL = "\\b(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	
	/**
	 * @param s
	 * @return
	 */
	public static boolean isLinkUrl(String s) {
		return ((s != null) && s.toLowerCase().matches(LINK_URL));
	}
	
	public static final String IMAGE_URL = LINK_URL.concat("\\.(?:jpg|jpeg|gif|png)$");
	
	/**
	 * @param s
	 * @return
	 */
	public static boolean isImageUrl(String s) {
		return ((s != null) && s.toLowerCase().matches(IMAGE_URL));
	}
	
	public static final String STATIC_IMAGE_URL = LINK_URL.concat("\\.(?:jpg|jpeg|png)$");
	
	/**
	 * @param s
	 * @return
	 */
	public static boolean isStaticImageUrl(String s) {
		return ((s != null) && s.toLowerCase().matches(STATIC_IMAGE_URL));
	}

	public static final String GIF_URL = LINK_URL.concat("\\.(?:gif)$");
	
	/**
	 * @param s
	 * @return
	 */
	public static boolean isGifUrl(String s) {
		return ((s != null) && s.toLowerCase().matches(GIF_URL));
	}

	public static final String VIDEO_URL = LINK_URL.concat("\\.(?:mp4|webm|ogg)$");
	
	/**
	 * @param s
	 * @return
	 */
	public static boolean isVideoUrl(String s) {
		return ((s != null) && s.toLowerCase().matches(VIDEO_URL));
	}

	public static final String EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	/**
	 * @param s
	 * @return
	 */
	public static boolean isEmail(String s) {
		return ((s != null) && s.matches(EMAIL));
	}
	
	public static final String USERNAME = "[a-z0-9_]*";
	
	/**
	 * @param s
	 * @return
	 */
	public static boolean isUsername(String s) {
		return ((s != null) && s.matches(USERNAME));
	}
	
	public static final String FULL_NAME = "[(A-Za-zÀ-ú)(\\s)(\\.)]*";
	
	/**
	 * @param s
	 * @return
	 */
	public static boolean isFullName(String s) {
		return ((s != null) && s.matches(FULL_NAME));
	}
	
	public static final String NAME = "[(A-Za-zÀ-ú0-9)(\\s)(\\.)]*";
	
	/**
	 * @param s
	 * @return
	 */
	public static boolean isName(String s) {
		return ((s != null) && s.matches(NAME));
	}
	
	public static final String IDENTIFIER = "[(A-Za-zÀ-ú0-9)(\\s)(\\.)(\\-)(\\_)]*";
	
	/**
	 * @param s
	 * @return
	 */
	public static boolean isIdentifier(String s) {
		return ((s != null) && s.matches(IDENTIFIER));
	}
	
	public static final String RGB_COLOR = "rgb\\(\\s*(0|[1-9]\\d?|1\\d\\d?|2[0-4]\\d|25[0-5])\\s*,\\s*(0|[1-9]\\d?|1\\d\\d?|2[0-4]\\d|25[0-5])\\s*,\\s*(0|[1-9]\\d?|1\\d\\d?|2[0-4]\\d|25[0-5])\\s*\\)";
	
	/**
	 * @param s
	 * @return
	 */
	public static boolean isRgbColor(String s) {
		return ((s != null) && s.matches(RGB_COLOR));
	}
	
	public static final String LATITUDE = "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$";
	
	/**
	 * @param s
	 * @return
	 */
	public static boolean isLatitude(String s) {
		return ((s != null) && s.matches(LATITUDE));
	}
	
	public static final String LONGITUDE = "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$";
	
	/**
	 * @param s
	 * @return
	 */
	public static boolean isLongitude(String s) {
		return ((s != null) && s.matches(LONGITUDE));
	}
}
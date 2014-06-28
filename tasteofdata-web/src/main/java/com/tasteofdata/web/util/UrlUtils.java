package com.tasteofdata.web.util;


import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.tasteofdata.web.util.PathPatternMatcher;

/**
 * @author wwj
 */
public class UrlUtils {
    private static final Logger logger = Logger.getLogger(UrlUtils.class);

    /**
     * 匹配路径是否在控制域的范围内
     * 
     * @param urls
     * @param path
     * @param urlPattern
     * @return
     */
    public static boolean urlMatch(SortedSet<String> urls, String path) {

        if (null == urls || urls.isEmpty()) return false;

        SortedSet<String> hurl = urls.headSet(path + "\0");
        SortedSet<String> turl = urls.tailSet(path + "\0");

        if (hurl.size() > 0) {
            String before = hurl.last();
            if (pathMatch(path, before)) return true;
        }

        if (turl.size() > 0) {
            String after = turl.first();
            if (pathMatch(path, after)) return true;
        }

        return false;
    }

    /**
     * 匹配路径是否在控制域的范围内
     * 
     * @param path
     * @param domain
     * @return
     */
    private static boolean pathMatch(String path, String domain) {
        if (PathPatternMatcher.isPattern(domain)) {
            return PathPatternMatcher.match(domain, path);
        } else {
            return domain.equals(path);
        }
    }

    /**
     * join url
     * 
     * @param origURL
     * @param appendUrl
     * @return
     */
    public static String joinUrl(String origURL, String appendUrl) {
        if (origURL != null && origURL.length() > 0) {
            if (origURL.indexOf("?") == -1) {
                return origURL + "?" + appendUrl;
            } else {
                return origURL + "&" + appendUrl;
            }
        } else {
            return "";
        }
    }

    public static String joinUrlForHyperLink(String origURL, String appendUrl) {
        if (origURL != null && origURL.length() > 0) {
            if (origURL.indexOf("?") == -1) {
                return origURL + "?" + appendUrl;
            } else {
                return origURL + "&amp;" + appendUrl;
            }
        } else {
            return "";
        }
    }

    /**
     * 判断refer中是否含有问号
     * */
    public static boolean isContainQuestionMark(String url) {
        if (!StringUtils.isEmpty(url)) {
            return url.contains("?");
        }
        return false;
    }

    /**
     * 将请求参数转换为字符串
     * */
    public static String getParamters(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        Enumeration<String> emu = (Enumeration<String>) request.getParameterNames();
        StringBuffer sb = new StringBuffer();
        while (emu.hasMoreElements()) {
            String str = emu.nextElement();
            sb.append(str);
            sb.append("=");
            String temp = request.getParameter(str);
            try {
                temp = URLEncoder.encode(temp, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("", e); //$NON-NLS-1$
            }
            sb.append(temp);
            if (emu.hasMoreElements()) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    /**
     * 给URL添加参数
     * 
     * @param url
     * @param key
     * @param value
     * @return
     */
    public static String addParameter(String url, String key, String value) {
        String param = getUrlParamString(key, value);
        return joinUrl(url, param);
    }

    public static String getUrlParamString(String key, String value) {
        StringBuffer sb = new StringBuffer();
        sb.append(key);
        sb.append("=");

        try {
            value = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("", e); //$NON-NLS-1$
        }
        sb.append(value);
        return sb.toString();
    }

    /**
     * 将url中的某些参数去掉
     * */
    public static String removeParameter(String url, String param) {
        if (url != null) {
            String regx = "(&" + param + "=[\\%0-9A-Za-z-_]*)|(" + param + "=[\\%0-9A-Za-z-_]*&)";
            Pattern p = Pattern.compile(regx);
            Matcher m = p.matcher(url);
            return m.replaceAll("");
        }
        return url;
    }

    public static String getRefererUrl(HttpServletRequest request) {
        String referer = request.getHeader("Referer");

        return referer;
    }

    public static String getRequestUrl(HttpServletRequest request) {
        String srcUrl = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        srcUrl = joinUrl(srcUrl, queryString);

        return srcUrl;
    }
    
    public static String extractHost(String str) {
        String host;
        try {
            URL url = new URL(str);
            host = url.getHost();
            if (host == null) {
                return null;
            }
        } catch (MalformedURLException e) {
            System.out.println("error:" + str);
            return null;
        }
        return host.toLowerCase();
    }
    
    public static boolean isSameDomain(String url1, String url2) {
        return StringUtils.equalsIgnoreCase(extractHost(url1), extractHost(url2));
    }

}

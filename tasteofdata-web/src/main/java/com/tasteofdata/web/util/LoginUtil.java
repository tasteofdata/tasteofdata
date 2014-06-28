package com.tasteofdata.web.util;

import com.tasteofdata.land.module.support.cache.CacheService;
import com.tasteofdata.land.module.user.entry.User;
import com.tasteofdata.land.util.security.SecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 登陆信息相关
 * @ClassName: LoginUtil 
 * @author wwj <wangweijie@techwolf.cn>
 * @date 2014年2月14日 下午12:19:20 
 *
 */
public class LoginUtil {
    private static final Logger logger = Logger.getLogger(LoginUtil.class);
    private static final int USER_CACHE_TIMEOUT = 24 * 60 * 60;    //用户信息缓存失效时间 24小时
    private static final String COOKIE_USER_LOGIN_UUID_KEY = "_u_l_uid";
    private static final int LONGUSE_UUID_COOKIE_TIMEOUT = -1;    //用户免登陆COOKIE失效时间 -关闭浏览器失效
    private static final int LOGIN_SECURITYKEY_CACHE_TIMEOUT = 24 * 60 * 60;    //用户免登陆安全字符串缓存失效时间  24小时

    private static final String USER_KEY = "LOGIN_USER";
    /**
     * 获得登陆用户信息
     * @param request
     * @return
     */
    public static User getLoginUser(HttpServletRequest request){
        User user = (User)request.getAttribute(USER_KEY);
        return user;
    }
    
    /**
     * 设置登陆用户信息，给LoginFilet用
     * @param request
     * @param user
     */
    public static void setLoginUser(HttpServletRequest request,User user){
        request.setAttribute(USER_KEY, user);
    }


    private static String getUserCacheKey(String userId){
        return "C_"+USER_KEY+"_"+userId;
    }
    
    /**
     * 登陆成功设置缓存
     * @param request
     * @param response
     * @param cacheService
     * @param user
     */
    public static void setCacheLoginUser(HttpServletRequest request,HttpServletResponse response,CacheService cacheService,User user){
        String ip = WebUtils.getIpAddr(request);

      //设置用户信息缓存
        cacheService.set(getUserCacheKey(String.valueOf(user.getId())),user,USER_CACHE_TIMEOUT);
        
        //设置登陆cookie
        String uuid = UUID.randomUUID().toString();
        
        /*
         * 生成面等安全串
         * 安全串组成：userId|(userId+salt)的md5值
         */
        String securityKey = user.getId()+"|" + getSecurityKey(String.valueOf(user.getId()),user.getSalt());
        
        //把UUID写入cookie
        Cookie cookie = WebUtils.cookie(COOKIE_USER_LOGIN_UUID_KEY, uuid,LONGUSE_UUID_COOKIE_TIMEOUT);
        response.addCookie(cookie);
        
        //把免登安全信息写入缓存
        cacheService.set(uuid, securityKey, LOGIN_SECURITYKEY_CACHE_TIMEOUT);
        logger.info("username="+user.getUserName() +"登陆成功. 登陆IP="+ip);
        if(logger.isDebugEnabled())
            logger.debug("userId="+user.getId()+" login cookie uuid="+uuid);
    }
    
    /**
     * 缓存中取出登陆用户
     * @param request
     * @param response
     * @param cacheService
     * @return
     */
    public static User getCacheLoginUserForFilter(HttpServletRequest request,HttpServletResponse response,CacheService cacheService){
        //取得cookie的uuid
        String uuid = WebUtils.getCookieValue(COOKIE_USER_LOGIN_UUID_KEY, request);
        
        if(StringUtils.isEmpty(uuid)){
            return null;
        }
        
        //从缓存中拿去免登安全串
        String securityKeyStr = cacheService.get(uuid);
        if(StringUtils.isBlank(securityKeyStr)){
            logger.info("用户登陆缓存已经失效，请重新登陆....uuid="+uuid);
            return null;
        }
        
        /*
         * 验证安全串  
         * 安全串组成：userId|ip|安全串组成：userId|(userId+userkey)的md5值
         */
        String[] securityKey = securityKeyStr.split("\\|");
        if(null == securityKey || securityKey.length != 2){
            logger.info("用户登陆缓存不合法，请重新登陆....uuid="+uuid+",securityKey="+securityKeyStr);
            return null;
        }
        
        //从缓存取出user
        User user = cacheService.get(getUserCacheKey(securityKey[0]));
        if( null == user){
            logger.info("用户信息缓存已经失效，请重新登陆....uuid="+uuid+",securityKey="+securityKeyStr);
            return null;
        }
        //验证安全串
        if(!getSecurityKey(String.valueOf(user.getId()),user.getSalt()).equals(securityKey[2])){
            logger.info("用户登陆缓存非法，请重新登陆....uuid="+uuid+",securityKey="+securityKeyStr+",userId="+user.getId());
            return null;
        }
        return user;
    }
    
    public static void clearLoginUserCache(HttpServletRequest request,CacheService cacheService){
        //删除用户缓存
        User user = getLoginUser(request);
        if(null==user)
            return ;

        boolean rs = cacheService.delete(getUserCacheKey(String.valueOf(user.getId())));
        if(!rs){
            cacheService.set(getUserCacheKey(String.valueOf(user.getId())),null,0);
        }
        //取得cookie的uuid
        String uuid = WebUtils.getCookieValue(COOKIE_USER_LOGIN_UUID_KEY, request);
        if(StringUtils.isNotEmpty(uuid)){
            rs = cacheService.delete(uuid);
            if(!rs){
                //设置0s过期的值，来代替删除缓存错误
                cacheService.set(uuid,"",0);
            }
        }
    }
    
    /**
     * 安全密钥(userId+ip+userkey)的md5值
     * @param userId
     * @param userkey
     * @return
     */
    private static String getSecurityKey(String userId,String userkey){
        return SecurityUtils.MD5Encode(userId + userkey);
    }
    
}

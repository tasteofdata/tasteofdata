package com.tasteofdata.land.module.user.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tasteofdata.land.module.exception.UserExcpetion;
import com.tasteofdata.land.module.user.dao.UserMapper;
import com.tasteofdata.land.module.user.entry.User;
import com.tasteofdata.land.module.user.service.UserService;
import com.tasteofdata.land.util.security.SecurityUtils;


@Service("userService")
public class UserServiceImpl implements UserService {
	private static final int SLAT_RANDOM_LEN = 10;
	private static final String SECURITY_KEY = "a@#909SS~@!3%";
	
	@Autowired
	private UserMapper userMapper;
	@Override
	public User queryByUserName(String userName) {
		return userMapper.selectByUserName(userName);
	}

	@Override
	public User queryById(long id) {
		return null;
	}

	@Override
	public User queryByPassword(String userName,String password) throws UserExcpetion{
		User user = userMapper.selectByUserName(userName);
		if(null==user){
			throw new UserExcpetion(UserExcpetion.USERNAME_NOTEXIST);
		}
		String md5Password = this.getPassword(password, user.getSalt());
		if(!user.getPassword().equals(md5Password)){
			throw new UserExcpetion(UserExcpetion.PASSWORD_ERROR);
		}
		return user;
	}

	/**
	 * 盐
	 * @return
	 */
	private String getSalt(){
		return SecurityUtils.MD5Encode(SecurityUtils.getRandomString(SLAT_RANDOM_LEN));
	}
	
	/**
	 * 密码加密算法
	 * @param sourcePasswd
	 * @param salt
	 * @return
	 */
	private String getPassword(String sourcePasswd,String salt){
		salt = SecurityUtils.RC4Encrypt(salt,SECURITY_KEY);
		return StringUtils.trimToEmpty(SecurityUtils.MD5Encode(sourcePasswd + salt));
	}

    @Override
    public int addUser(User user) {
        String salt = getSalt();
        if(StringUtils.isBlank(user.getPassword())){
        	throw new IllegalArgumentException("密码不能为空");
        }
        String password = getPassword(user.getPassword(),salt);
        user.setPassword(password);
        user.setSalt(salt);
        return userMapper.insertUser(user);
    }

    @Override
    public int updateUser(User user) {
       return userMapper.updateUser(user);
    }

    @Override
    public int updateUserPassword(User user, String password, String newPassword) {
        String _password = getPassword(user.getPassword(), user.getSalt());
        if(!_password.equals(password)){
            return 1;       //密码错误
        }
        newPassword = getPassword(newPassword,user.getSalt());
        int rs = userMapper.updateUserPassword(user.getId(), newPassword);
        return rs>0?0:2;
    }
}

package com.tasteofdata.land.module.user.service;

import com.tasteofdata.land.module.exception.UserExcpetion;
import com.tasteofdata.land.module.user.entry.User;

public interface UserService {

	/**
	 * 根据userName查询
	 * @param userName
	 * @return
	 */
	public User queryByUserName(String userName);
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public User queryById(long id);
	
	/**
	 * 根据密码查询
	 * @param userName
	 * @param password
	 * @return
	 */
	public User queryByPassword(String userName,String password) throws UserExcpetion;

    /**
     * 添加用户
     * @param user
     */
    public int addUser(User user);

    /**
     * 更新用户信息,只允许更改用户名、邮箱、手机号
     * @param user
     */
    public int updateUser(User user);

    /**
     * 更新密码
     * @param user
     * @param password 原密码
     * @param newPassword 新密码
     * @return 0：成功，1：原密码错误，2：修改失败
     */
    public int updateUserPassword(User user,String password,String newPassword);

}

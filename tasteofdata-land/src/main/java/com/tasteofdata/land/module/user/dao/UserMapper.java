package com.tasteofdata.land.module.user.dao;

import org.springframework.stereotype.Repository;

import com.tasteofdata.land.module.user.entry.User;

@Repository("userMapper")
public interface UserMapper {

	/**
	 * 根据ID查询用户信息
	 * @param id
	 * @return
	 */
	public User selectById(long id);
	
	/**
	 * 根据username查询用户信息
	 * @param userName
	 * @return
	 */
	public User selectByUserName(String userName);
	
	/**
	 * 插入user
	 * @param user
	 * @return
	 */
	public int insertUser(User user);
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public int updateUser(User user);

    /**
     * 更新用户密码
     * @param user
     * @return
     */
    public int updateUserPassword(long id,String newPassword);
}

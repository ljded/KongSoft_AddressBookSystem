package cn.edu.sdcet.api.Mapper;

import cn.edu.sdcet.api.Entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMI {
	//新用户
	@Insert("insert into `user`(`username`, `password`) value (#{userName}, #{password})")
	int addUser(@Param("userName") String userName, @Param("password") String userPassword);

	//登录
	@Select("select * from `user` where `username` = #{userName} and `password` = #{password}")
	User loginUser(@Param("userName") String userName, @Param("password") String password);

	//检测用户名是否重复
	@Select("select * from `user` where `username` = #{userName}")
	User UserDuplicate(String userName);

	//更新密码
	@Update("update `user` set `password` = #{newPass} where `userid` = #{userId}")
	int updateUserPass(@Param("userId") int userId, @Param("newPass") String newPass);
}

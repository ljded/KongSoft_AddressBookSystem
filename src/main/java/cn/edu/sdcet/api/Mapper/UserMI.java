package cn.edu.sdcet.api.Mapper;

import cn.edu.sdcet.api.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMI {
	//新用户
	//INSERT INTO `group` (`Created_at`, `Updated_at`, `Name`, `User_id`) VALUES ('2025-03-04 17:05:10', '2025-03-04 17:05:14', '默认', 1)
	@Insert("insert into `user`(`username`, `password`) value (#{userName}, #{password})")
	int addUser(@Param("userName") String userName, @Param("password") String userPassword);

	//登录
	@Select("select * from `user` where `username` = #{userName} and `password` = #{password}")
	User loginUser(@Param("userName") String userName, @Param("password") String password);

	//更新密码
	@Update("update `user` set `password` = #{newpass} where `userid` = #{userId}")
	int updateUserpass(@Param("userId") int userId, @Param("newpass") String newpass);
}

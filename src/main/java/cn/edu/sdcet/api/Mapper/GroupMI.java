package cn.edu.sdcet.api.Mapper;

import cn.edu.sdcet.api.Entity.Group;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface GroupMI {
	//创建
	@Insert("insert into `group`(`Name`, `User_id`, `Created_at`, `Updated_at`) value (#{Name}, #{UserID}, #{Date}, #{Date})")
	int addGroup(@Param("Name") String Name, @Param("UserID") int UserID, @Param("Date") Date date);

	//删除
	@Delete("delete from `group` where `group_id`=#{gid} AND `User_id`=#{userId}	")
	int deleteGroup(@Param("gid") int gid, @Param("userId") int uid);

	//修改分组名称
	@Update("update `group` set `Name`=#{newName} where `group_id`=#{gid} AND `User_id`=#{userId}")
	int updateGroup(@Param("newName") String newName, @Param("gid") int gid, @Param("userId") int uid);

	//查询分组
	@SelectProvider(value = GroupProvider.class, method = "selectGroup")
	List<Group> selectGroup(@Param("userId") int userId,@Param("gid") int gid);

}

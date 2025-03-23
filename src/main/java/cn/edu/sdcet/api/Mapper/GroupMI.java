package cn.edu.sdcet.api.Mapper;

import cn.edu.sdcet.api.entity.Group;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface GroupMI {
	//创建
	@Insert("insert into `group`(`Name`, `User_id`, `Created_at`, `Updated_at`) value (#{Name}, #{UserID}, #{Date}, #{Date})")
	int addGroup(@Param("Name") String Name, @Param("UserID") int UserID, @Param("Date") Date date);

	//删除
	@Delete("delete from `group` where `group_id`=#{gid}")
	int deleteGroup(@Param("gid") int gid);

	//修改分组名称
	@Update("update `group` set `Name`=#{newName} where `group_id`=#{gid}")
	int updateGroup(@Param("newName") String newName, @Param("gid") int gid);

	//查询全部分组
	@Select("select * from `group` where `User_id`=#{userId}")
	List<Group> selectAllGroup(int userId);

	//查询分组
	@Select("select * from `group` where `group_id`=#{gid}")
	Group selectGroup(int gid);

}

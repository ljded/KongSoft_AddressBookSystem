package cn.edu.sdcet.api.Mapper;

import cn.edu.sdcet.api.Entity.Contact;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface ContactMI {

	//创建
	@InsertProvider(value = ContactProvider.class, method = "addContact")
	int addContact(@Param("Name") String Name, @Param("Phone") String Phone, @Param("Email") String Email, @Param("Comment") String Comment, @Param("gid") Integer gid, @Param("Date") Date date, @Param("uid") int uid);

	//删除联系人
	@Delete("delete from `contact` where `user_id` = #{uid} AND`ID`=#{ID}")
	int deleteContact(@Param("uid") int uid, @Param("ID") int ID);

	//修改联系人
	@Update("update `contact` set `name`=#{Name}, `phone`=#{Phone}, `email`=#{Email}, `comment`=#{comment}, `group_id`=#{gid} , `Updated_at`=#{Data} where `ID` = #{ID} AND `user_id` = #{uid}")
	int UpdateContact(@Param("ID") int ID, @Param("Name") String Name, @Param("Phone") String phone, @Param("Email") String Email, @Param("comment") String comment, @Param("Data") Date date, @Param("gid") Integer gid, @Param("uid") int uid);

	//动态联系人查询
	@SelectProvider(type = ContactProvider.class, method = "selectContact")
	List<Contact> selectContact(@Param("uid") int uid, @Param("ID") int ID, @Param("name") String name, @Param("phone") String phone);

}

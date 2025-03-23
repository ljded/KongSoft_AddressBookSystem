package cn.edu.sdcet.api.Mapper;

import cn.edu.sdcet.api.entity.Contact;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface ContactMI {

	//创建
	@Insert("insert into `contact`(`name`, `phone`, `email`, `comment`, `group_id`,`Created_at`,`Updated_at`, `user_id`) value (#{Name},#{Phone},#{Email},#{Comment},#{gid},#{Date},#{Date},#{uid})")
	int addContact(@Param("Name") String Name, @Param("Phone") String Phone, @Param("Email") String Email, @Param("Comment") String Comment, @Param("gid") Integer gid, @Param("Date") Date date, @Param("uid") int uid);

	//删除联系人
	@Delete("delete from `contact` where `ID`=#{ID}")
	int deleteContact(@Param("ID") int ID);

	//修改联系人
	//UPDATE `contacts_book_text`.`contact` SET `name` = '测试', `phone` = '45679', `email` = '1234567@qq.com', `comment` = '测试', `Updated_at` = '2025-03-04 17:50:22' WHERE `ID` = 2
	@Update("update `contact` set `name`=#{Name}, `phone`=#{Phone}, `email`=#{Email}, `comment`=#{comment}, `group_id`=#{gid} , `Updated_at`=#{Data} where `ID` = #{ID}")
	int UpdateContact(@Param("ID") int ID, @Param("Name") String Name, @Param("Phone") String phone, @Param("Email") String Email, @Param("comment") String comment, @Param("Data") Date date, @Param("gid") Integer gid);

	//查询全部联系人
	@Select("select * from `contact` where `user_id`=#{uid}")
	List<Contact> getContactsAtID(@Param("uid") int uid);


	//查询指定联系人
	@Select("select * from `contact` where `user_id`=#{uid} and `name`=#{name} and `phone`=#{phone}")
	Contact selectContact(@Param("uid") int uid, @Param("name") String name, @Param("phone") String phone);

	//查询指定联系人
	@Select("select * from `contact` where `ID`=#{ID}")
	Contact selectContactAtID(@Param("ID") int ID);
}

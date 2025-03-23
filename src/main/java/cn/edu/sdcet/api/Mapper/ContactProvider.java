package cn.edu.sdcet.api.Mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

public class ContactProvider {

	public static String selectContact(@Param("uid") int uid, @Param("ID") int ID, @Param("name") String name, @Param("phone") String phone) {
		return new SQL(){{
			SELECT("*");
			FROM("`contact`");
			WHERE("`user_id`=#{uid}");
			if(ID!=0){
				WHERE("`ID`=#{ID}");
			}
			if(name!=null){
				WHERE("`name`=#{name}");
			}
			if(phone!=null){
				WHERE("`phone`=#{phone}");
			}
		}}.toString();
	}

	public static String addContact(@Param("Name") String Name, @Param("Phone") String Phone, @Param("Email") String Email, @Param("Comment") String Comment, @Param("gid") Integer gid, @Param("Date") Date date, @Param("uid") int uid) {
		return new SQL(){{
			INSERT_INTO("contact");
			VALUES("name","#{Name}");
			VALUES("phone","#{Phone}");
			VALUES("email","#{Email}");
			VALUES("Created_at", "#{Date}");
			VALUES("Updated_at", "#{Date}");
			VALUES("user_id", "#{uid}");
			if (Comment != null) {
				VALUES("comment","#{Comment}");
			}
			if (gid != null) {
				if (gid != 0) {
					VALUES("group_id", "#{gid}");
				}
			}
		}}.toString();
	}
}

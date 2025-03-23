package cn.edu.sdcet.api.Mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class GroupProvider {

	public static String selectGroup(@Param("userId") int userId,@Param("gid") int gid){
		return new SQL(){{
			SELECT("*");
			FROM("`group`");
			WHERE("`user_id`=#{userId}");
			if(gid!=0){
				WHERE("`group_id`=#{gid}");
			}
		}}.toString();
	}

}

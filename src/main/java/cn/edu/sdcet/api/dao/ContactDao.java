package cn.edu.sdcet.api.dao;

import cn.edu.sdcet.api.Mapper.ContactMI;
import cn.edu.sdcet.api.entity.Contact;
import cn.edu.sdcet.api.entity.User;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Component
public class ContactDao {

	@Resource
	private ContactMI ContactMI;

	@Resource
	private GroupDao GroupDao;

	/**
	 * 创建联系人
	 */
	public JSONObject addContact(String Name, String Phone, String Email, String Comment, Integer gid, User user) {
		if(ContactMI.addContact(Name, Phone, Email, Comment, gid, new Date(), user.getUserid())!=0){
			Contact contact = ContactMI.selectContact(user.getUserid(), Name, Phone);
			if(contact!=null){
				return contact.toJsonShort();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 创建联系人事务模式
	 */
	@Transactional
	public int addContactAffairs(List<Contact> Comments, User user) {
		int count = 0;
		for (Contact contact : Comments) {
			count += ContactMI.addContact(contact.getName(), contact.getPhone(), contact.getEmail(), contact.getComment(), contact.getGroup_id(), new Date(), user.getUserid());
		}
		return Math.max(count, 0);
	}

	/**
	 * 删除联系人
	 */
	public JSONObject deleteContact(int ID) {
		Contact contact = ContactMI.selectContactAtID(ID);
		if(ContactMI.deleteContact(ID)!=0){
			contact.setDeletedAt(new Date());
			return contact.toJsonLong();
		} else {
			return null;
		}
	}

	/**
	 * 查询指定联系人
	 */
	public JSONObject selectContactAtID(int ID) {
		Contact contact = ContactMI.selectContactAtID(ID);
		if(contact != null){
			return contact.toJsonLong();
		} else {
			return null;
		}
	}

	/**
	 * 更新联系人
	 */
	public JSONObject updateContact(String Name, String Phone, String Email, String Comment, Integer gid,int ID) {
		if(ContactMI.UpdateContact(ID, Name, Phone, Email, Comment, new Date(), gid)!=0){
			Contact contact = ContactMI.selectContactAtID(ID);
			return contact.toJsonShort();
		} else {
			return null;
		}
	}

	/**
	 * 查询用户全部的联系人
	 */
	public JSONArray getContacts(User user) {
		JSONArray objects = new JSONArray();
		List<Contact> contacts = ContactMI.getContactsAtID(user.getUserid());
		for (Contact contact : contacts) {
			objects.add(contact.toJsonLong());
		}
		return objects;
	}

	/**
	 * 将数据库内容转换为 Excel
	 */
	public List<Contact> getExcel(User user)  {
		List<Contact> contacts = new ArrayList<>();
		Map<Integer,String> GroupName = new HashMap<>();
		//获取分组名称 Map
		JSONArray GroupS = GroupDao.AllGroup(user);
		log.info(GroupS.toString());
		for (Object Group : GroupS) {
			JSONObject JSONGroup = (JSONObject) Group;
			Integer id = JSONGroup.getInteger("ID");
			String name = JSONGroup.getString("Name");
			GroupName.put(id,name);
		}
		//获取联系人列表
		JSONArray ContactS = this.getContacts(user);
		log.info(ContactS.toString());
		for (Object Contact : ContactS) {
			JSONObject JSONContact = (JSONObject) Contact;
			Contact contact = new Contact();
			contact.setName(JSONContact.getString("name"));
			contact.setPhone(JSONContact.getString("phone"));
			contact.setEmail(JSONContact.getString("email"));
			contact.setComment(JSONContact.getString("comment"));
			contact.setGroupName(GroupName.get(JSONContact.getInteger("group_id")));

			contacts.add(contact);
		}
		return contacts;
	}

	public Map<String,Integer> getGroupName(User user) {
		Map<String,Integer> GroupName = new HashMap<>();
		//获取分组名称 Map
		JSONArray GroupS = GroupDao.AllGroup(user);
		log.info(GroupS.toString());
		for (Object Group : GroupS) {
			JSONObject JSONGroup = (JSONObject) Group;
			Integer id = JSONGroup.getInteger("ID");
			String name = JSONGroup.getString("Name");
			GroupName.put(name,id);
		}
		return GroupName;
	}

}

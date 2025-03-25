package cn.edu.sdcet.api.Service;

import cn.edu.sdcet.api.Entity.Package;
import cn.edu.sdcet.api.Mapper.ContactMI;
import cn.edu.sdcet.api.Entity.Contact;
import cn.edu.sdcet.api.Entity.User;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ContactService {

	@Resource
	private ContactMI ContactMI;

	@Resource
	private GroupService GroupService;

	/**
	 * 创建联系人
	 */
	public JSONObject addContact(String Name, String Phone, String Email, String Comment, Integer gid, User user) {
		if(ContactMI.addContact(Name, Phone, Email, Comment, gid, new Date(), user.getUserid())!=0){
			List<Contact> contacts = ContactMI.selectContact(user.getUserid(), 0, Name, Phone);
			Contact contact = contacts.getFirst();
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
	public JSONObject deleteContact(User user, int ID) {
		List<Contact> contacts = ContactMI.selectContact(user.getUserid(), ID,null,null);
		Contact contact = contacts.getFirst();
		if(ContactMI.deleteContact(user.getUserid(), ID)!=0){
			contact.setDeletedAt(new Date());
			return contact.toJsonLong();
		} else {
			return null;
		}
	}

	/**
	 * 查询指定联系人
	 */
	public JSONObject selectContactAtID(User user, int ID) {
		List<Contact> contacts = ContactMI.selectContact(user.getUserid(), ID,null,null);
		Contact contact = contacts.getFirst();
		if(contact != null){
			return contact.toJsonLong();
		} else {
			return null;
		}
	}

	/**
	 * 更新联系人
	 */
	public Package updateContact(String Name, String Phone, String Email, String Comment, Integer gid,int ID, User user, Package bean) {
		List<Contact> contacts = ContactMI.selectContact(user.getUserid(), ID, null, null);
		if (contacts.size() == 1) {
			if(ContactMI.UpdateContact(ID, Name, Phone, Email, Comment, new Date(), gid, user.getUserid())!=0){
				contacts = ContactMI.selectContact(user.getUserid(),ID,null,null);
				Contact contact = contacts.getFirst();
				bean.setCode(0);
				bean.setMsg("修改成功");
				bean.newData(contact.toJsonShort());
			} else {
				bean.setCode(400);
				bean.setMsg("修改失败");
			}
		} else {
			bean.setCode(404);
			bean.setMsg("未找到此联系人");
		}
		return bean;
	}

	/**
	 * 查询用户全部的联系人
	 */
	public JSONArray getContacts(User user) {
		JSONArray objects = new JSONArray();
		List<Contact> contacts = ContactMI.selectContact(user.getUserid(),0,null,null);
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
		JSONArray GroupS = GroupService.AllGroup(user);
		for (Object Group : GroupS) {
			JSONObject JSONGroup = (JSONObject) Group;
			Integer id = JSONGroup.getInteger("ID");
			String name = JSONGroup.getString("Name");
			GroupName.put(id,name);
		}
		//获取联系人列表
		JSONArray ContactS = this.getContacts(user);
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
		JSONArray GroupS = GroupService.AllGroup(user);
		for (Object Group : GroupS) {
			JSONObject JSONGroup = (JSONObject) Group;
			Integer id = JSONGroup.getInteger("ID");
			String name = JSONGroup.getString("Name");
			GroupName.put(name,id);
		}
		return GroupName;
	}

}

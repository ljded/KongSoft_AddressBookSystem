package cn.edu.sdcet.api.Service;

import cn.edu.sdcet.api.Entity.Contact;
import cn.edu.sdcet.api.Entity.User;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelRead implements ReadListener<Contact> {

	/**
	 * 存储间隔
	 */
	private static final int BATCH_COUNT = 20;
	/**
	 * 缓存
	 */
	private List<Contact> contactList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
	private final ContactService contactService;
	private final GroupService groupService;
	private final User user;
	Map<String, Integer> groupNames;

	public ExcelRead(ContactService contactService, GroupService groupService, User user) {
		this.contactService = contactService;
		this.user = user;
		groupNames = contactService.getGroupName(user);
		this.groupService = groupService;
	}

	/**
	 * 每条解析都会调用
	 */
	@Override
	public void invoke(Contact olcontact, AnalysisContext analysisContext) {
		log.info("Read Excel :解析 :{}", JSON.toJSONString(olcontact));
		Contact contact = new Contact();
		String groupName = olcontact.getGroupName();
		if (groupName != null) {
			Integer i = groupNames.get(groupName);
			if (i == null) {
				if (groupService.addGroup(user, groupName)) {
					groupNames = contactService.getGroupName(user);
					i = groupNames.get(groupName);
				} else {
					log.info("Read Excel : 错误 : {} 分组创建失败", groupName);
				}
			}
			contact.setGroup_id(i);
		} else {
			contact.setGroup_id(null);
		}
		contact.setName(olcontact.getName());
		contact.setPhone(olcontact.getPhone());
		contact.setEmail(olcontact.getEmail());
		contact.setComment(olcontact.getComment());
		if (!olcontact.Null()) {
			contactList.add(contact);
		}
		if (contactList.size() >= BATCH_COUNT) {
			save();
			contactList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
		}
	}

	/**
	 * 解析完成后调用
	 */
	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
		if (!contactList.isEmpty()) {
			save();
		}
		log.info("Read Excel : 结束 :解析完成");
	}

	public void save() {
		log.info("Read Excel : 保存 :{}条数据，开始存储数据库！", contactList.size());
		if (contactService.addContactAffairs(contactList, user) != 0) {
			log.info("Read Excel : 保存 :保存成功");
		} else {
			log.info("Read Excel : 错误 :保存失败");
		}
	}
}

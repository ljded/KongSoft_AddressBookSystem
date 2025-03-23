package cn.edu.sdcet.api.dao;

import cn.edu.sdcet.api.entity.Contact;
import cn.edu.sdcet.api.entity.User;
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
	private final ContactDao contactDao;
	private final GroupDao groupDao;
	private final User user;
	Map<String, Integer> groupNames;

	public ExcelRead(ContactDao contactDao, GroupDao groupDao, User user) {
		this.contactDao = contactDao;
		this.user = user;
		groupNames = contactDao.getGroupName(user);
		this.groupDao = groupDao;
	}

	/**
	 * 每条解析都会调用
	 */
	@Override
	public void invoke(Contact olcontact, AnalysisContext analysisContext) {
		log.info("解析到一条数据:{}", JSON.toJSONString(olcontact));
		Contact contact = new Contact();
		String groupName = olcontact.getGroupName();
		if (groupName != null) {
			Integer i = groupNames.get(groupName);
			if (i == null) {
				if (groupDao.addGroup(user, groupName)) {
					groupNames = contactDao.getGroupName(user);
					i = groupNames.get(groupName);
				} else {
					log.info("{} 分组创建失败", groupName);
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
		contactList.add(contact);
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
		log.info("解析完成");
	}

	public void save() {
		log.info("{}条数据，开始存储数据库！", contactList.size());
		if (contactDao.addContactAffairs(contactList, user) != 0) {
			log.info("保存成功");
		} else {
			log.info("保存失败");
		}
	}
}

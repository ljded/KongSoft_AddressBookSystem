package cn.edu.sdcet.api.controller;

import cn.edu.sdcet.api.dao.ContactDao;
import cn.edu.sdcet.api.dao.ExcelRead;
import cn.edu.sdcet.api.dao.GroupDao;
import cn.edu.sdcet.api.entity.Contact;
import cn.edu.sdcet.api.entity.Package;
import cn.edu.sdcet.api.entity.Tool;
import cn.edu.sdcet.api.entity.User;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/contact")
public class ContactController {

	@Resource
	ContactDao contactDao;
	@Resource
	GroupDao groupDao;

	/**
	 * 创建联系人
	 */
	@PostMapping(value = "", produces = "application/json")
	@ResponseBody
	public JSONObject addContact(@RequestBody Contact nContact, HttpSession session) {
		Package bean = Tool.getPackage();
		User user = Tool.getUser(session);
		log.info("创建联系人收到请求:contact:{} user:{}", nContact.toString(), user.toString());
		JSONObject object = contactDao.addContact(nContact.getName(),
				nContact.getPhone(), nContact.getEmail(),
				nContact.getComment(), nContact.getGroup_id(), user);
		if(object!=null){
			bean.setCode(0);
			bean.setMsg("创建成功");
			bean.newData(object);
			log.info("创建成功:contact:{}", object);
		} else {
			bean.setMsg("创建失败");
			log.info("创建失败");
		}
		return bean.toJSON();
	}

	/**
	 * 删除联系人
	 */
	@DeleteMapping(value = "/{id}", produces = "application/json")
	@ResponseBody
	public JSONObject deleteContact(@PathVariable int id) {
		Package bean = Tool.getPackage();
		JSONObject object = contactDao.deleteContact(id);
		log.info("删除联系人收到请求:id:{}", id);
		if(object!=null){
			bean.setCode(0);
			bean.setMsg("删除成功");
			bean.newData(object);
			log.info("删除 {} 成功", id);
		} else {
			bean.setMsg("删除失败");
			log.info("删除 {} 失败", id);
		}
		return bean.toJSON();
	}

	/**
	 * 修改联系人
	 */
	@PutMapping(value = "/{id}", produces = "application/json")
	@ResponseBody
	public JSONObject modifyContact(@PathVariable int id, @RequestBody Contact nContact) {
		Package bean = Tool.getPackage();
		log.info("修改联系人收到请求:id:{} contact:{}", id, nContact.toString());
		JSONObject object = contactDao.updateContact(nContact.getName(),
				nContact.getPhone(), nContact.getEmail(),
				nContact.getComment(), nContact.getGroup_id(), id);
		if(object!=null){
			bean.setCode(0);
			bean.setMsg("修改成功");
			bean.newData(object);
			log.info("修改联系人 {} 成功 new:{}", id, object);
		} else {
			bean.setMsg("修改失败");
			log.info("修改联系人 {} 失败", id);
		}
		return bean.toJSON();
	}

	/**
	 * 查询全部联系人
	 */
	@GetMapping(value = "", produces = "application/json")
	@ResponseBody
	public JSONObject getContact(HttpSession session) {
		Package bean = Tool.getPackage();
		User user = Tool.getUser(session);
		log.info("收到查询 {} 全部联系人请求", user.getUsername());
		JSONArray contacts = contactDao.getContacts(user);
		if (contacts != null) {
			bean.setCode(0);
			bean.newData(contacts);
			log.info("查询 {} 全部联系人成功", user.getUsername());
		}
		return bean.toJSON();
	}

	/**
	 * 通过ID查询联系人
	 */
	@GetMapping(value = "/{id}", produces = "application/json")
	@ResponseBody
	public JSONObject getIDContact(@PathVariable int id) {
		Package bean = Tool.getPackage();
		log.info("收到查询 {} 联系人请求", id);
		JSONObject contact = contactDao.selectContactAtID(id);
		if (contact != null) {
			bean.setCode(0);
			bean.newData(contact);
			log.info("查询 {} 联系人成功", id);
		}
		return bean.toJSON();
	}

	@GetMapping("/export")
	public void getExcel(HttpServletResponse response, HttpSession session) throws IOException {
		User user = Tool.getUser(session);
		response.setHeader("Content-Disposition", "attachment; filename=contacts.xlsx");
		List<Contact> excel = contactDao.getExcel(user);
		if (excel != null) {
			EasyExcel.write(response.getOutputStream(), Contact.class).sheet("联系人").doWrite(excel);
		}
	}

	@PostMapping("/import")
	public JSONObject setExcel(MultipartFile file , HttpSession session) throws IOException {
		Package bean = Tool.getPackage();
		User user = Tool.getUser(session);
		JSONObject contact1 = getContact(session);
		InputStream inputStream = file.getInputStream();
		ExcelReaderBuilder read = EasyExcel.read(inputStream, Contact.class, new ExcelRead(contactDao, groupDao, user));
		read.sheet().doRead();
		JSONObject contact2 = getContact(session);
		if (contact1.equals(contact2)) {
			bean.setMsg("导入失败");
		} else {
			bean.setCode(0);
			bean.setMsg("导入成功");
		}
		return bean.toJSON();
	}

}

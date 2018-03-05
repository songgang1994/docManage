package com.dm.ctrl.doc;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.dm.ctrl.BaseCtrl;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.DocBatchCheckTableDto;
import com.dm.dto.ErrCode;
import com.dm.dto.ResultDto;
import com.dm.dto.SysCfgDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.DocItemsEntity;
import com.dm.entity.UserEntity;
import com.dm.srv.doc.DocBatchCheckSrv;
import com.dm.tool.Constant;

/**
 * 系统名：NSK-NRDC业务系统 作成人：曾雷 模块名：文档维护
 */
@Controller
@RequestMapping("/doc")
public class DocBatchCtrl extends BaseCtrl {

	@Autowired
	private DocBatchCheckSrv docBatchCheckSrv;

	/**
	 * 批量审核页面初始化
	 *
	 * @param docCode
	 *            文档编号
	 * @param docType
	 *            文档类型
	 * @param deptId
	 *            部门id
	 * @param docStatus
	 *            文档状态
	 * @param director
	 *            负责人
	 * @return ModelAndView
	 */
	@RequestMapping("/batchcheck")
	public ModelAndView batchCheckInit(String docCode, String docType, String deptId, String docStatus,
			String director) {

		ModelAndView mv = new ModelAndView("doc/DocBatchCheck");
		try {
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);

			// 获取文档类型
			List<DocItemsEntity> docItems = docBatchCheckSrv.queryDocItem();
			mv.addObject("docItems", docItems);
			// 获取审核意见最大长度
			DocItemsEntity approveHistory = docBatchCheckSrv.getDocItemEntity(Constant.DOCUMENT_ITEM_F000012);
			mv.addObject("approve", approveHistory);
			// 获取所属部门下拉框信息
			List<DepartmentInfoEntity> subDept = docBatchCheckSrv.queryDeptListByUserId(user.getUserId());
			// 设置【所属部门ID】
			mv.addObject("subDept", subDept);
//			// 查询上级部门ID = 查询结果．部门ID的所有部门信息
//			List<DepartmentInfoEntity> auditDept = docBatchCheckSrv.queryDeptListByDeptId(subDept);
//			// 设置【审核部门ID】
//			mv.addObject("auditDept", auditDept);

			// 获取文档状态
//			if (docStatus == null) {
//				docStatus = Constant.DOCUMENT_STATUS;
//			}
//			List<ParmMstEntity> status = docBatchCheckSrv.queryDocType(docStatus);
//			mv.addObject("status", status);

		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("exMsg", Constant.MS_ERROR);
			mv.setViewName("util/error");
			return mv;
		}
		return mv;
	}

	/**
	 * 获取批量审核查询表单数据
	 *
	 * @param docCode
	 *            文档编号
	 * @param docType
	 *            文档类型
	 * @param deptId
	 *            部门id
	 * @param docStatus
	 *            文档状态
	 * @param director
	 *            负责人
	 * @return
	 */
	@RequestMapping("/querybatchtable")
	@ResponseBody
	public ResultDto<DocBatchCheckTableDto> getBatchDoc(String docCode, String docType, String deptId, String docStatus,
			String director) {
		ResultDto<DocBatchCheckTableDto> resultDto = new ResultDto<>();
		DocBatchCheckTableDto baseDto = new DocBatchCheckTableDto();

		try {
			// 登陆用户信息
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			// 获取所属部门下拉框信息
			List<DepartmentInfoEntity> subDept = docBatchCheckSrv.queryDeptListByUserId(user.getUserId());
//			// 查询上级部门ID = 查询结果．部门ID的所有部门信息
//			List<DepartmentInfoEntity> auditDept = docBatchCheckSrv.queryDeptListByDeptId(subDept);

			// 获取初始化查询表格数据
			List<DocBatchCheckTableDto> queryBatchCheck = docBatchCheckSrv.batchCheckInit(subDept, docCode,
					docType, deptId, docStatus, director, user.getUserId());
			resultDto.setListData(queryBatchCheck);
			resultDto.setData(baseDto);
			return resultDto;
		} catch (Exception e) {
			e.printStackTrace();
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}
	}

	@RequestMapping("/passorreturn")
	@ResponseBody
	private ResultDto<BaseDto> passOrReturn(String[] docCode,String[] updateTime, String flag, String comment) {
		ResultDto<BaseDto> resultDto = new ResultDto<>();
		BaseDto baseDto = new BaseDto();

		try {
			// 获取登录人信息
			UserEntity user = (UserEntity) session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
			// 通过/退回
			baseDto = docBatchCheckSrv.passOrRet(docCode, updateTime,flag, comment, user.getUserId());
			resultDto.setData(baseDto);
			// 开启发送邮件的线程
			if(baseDto.getBizCode() != BizCode.BIZ_CODE_DOC_UPDATE_BY_OTHER) {
				List<String> str = baseDto.getBizExeMsgs();
				int f = baseDto.getBizCode();
				Callable<Void> callable = new Callable<Void>() {
					public Void call() throws Exception {
						sendEmails(str,f);
						return null;
					}
				};
				FutureTask<Void> future = new FutureTask<Void>(callable);
				new Thread(future).start();
			}

			return resultDto;
		} catch (Exception e) {
			e.printStackTrace();
			resultDto.setRtnCode(ErrCode.RTN_CODE_UNKNOWN);
			return resultDto;
		}

	}

	private void sendEmails(List<String> str,Integer flag) throws UnsupportedEncodingException, MessagingException {

		// 获取sysconfig数据
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();

		SysCfgDto sysCfgInfo = (SysCfgDto) servletContext.getAttribute("SysCfgInfo");

		// 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
		// 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
		String myEmailSMTPHost = sysCfgInfo.getEmailSMTPHost();

		// 设置发送者信息
		String myEmailAccount = sysCfgInfo.getEmailAccount();
		String myEmailPassword = sysCfgInfo.getEmailPassword();

		// 获取首页地址
		String url = sysCfgInfo.getIndex();
		// 设置接受者信息
		for (String s : str) {
			String[] emailInfo = s.split(",");
			if (flag == Constant.APPROVAL_SUCCESS) {// 给下一级所有的审批人发送审批通知邮件：
				String title = "【NRDC管理系统】 【" + emailInfo[0] + "】 审批处理提醒 " + emailInfo[1];
				String content = emailInfo[2] + " 女士/先生 您好<br/> 您有一件新的【NRDC管理系统-" + emailInfo[0] + "】文档需要处理(流水号："
						+ emailInfo[1] + ")，请点击以下链接处理：<br/>" + url + "?docCode=" + emailInfo[1] + "&mode=" + 3
						+ "<br/><br/>注：本邮件有系统自动发送，请勿向此邮箱发送邮件。";
				sendEmail(emailInfo[3], content, myEmailSMTPHost, myEmailAccount, myEmailPassword,title);
			} else if (flag == Constant.APPROVAL_FAILED) {
				String title = "【NRDC管理系统】 【" + emailInfo[0] + "】 退回提醒 " + emailInfo[1];
				String content = emailInfo[2] + " 女士/先生 您好<br/> 您的【NRDC管理系统-" + emailInfo[0] + "】文档(流水号：" + emailInfo[1]
						+ ")被退回，请点击以下链接处理：<br/>" + url + "?docCode=" + emailInfo[1] + "&mode=" + 1 + "<br/><br/>";
				sendEmail(emailInfo[3], content, myEmailSMTPHost, myEmailAccount, myEmailPassword,title);
			}
		}

	}

	private void sendEmail(String receiveMailAccount, String content, String myEmailSMTPHost, String myEmailAccount,
			String myEmailPassword,String title) throws UnsupportedEncodingException, MessagingException {

		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
		Properties props = new Properties(); // 参数配置
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", myEmailSMTPHost); // 发件人的邮箱的 SMTP 服务器地址
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
		props.put("mail.smtp.auth", "true");

		// 2. 根据配置创建会话对象, 用于和邮件服务器交互
		Session session = Session.getInstance(props);
		session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log

		// 3. 创建一封邮件
		MimeMessage message;

		message = createMimeMessage(session, myEmailAccount, receiveMailAccount, content,title);

		// 4. 根据 Session 获取邮件传输对象
		Transport transport = session.getTransport();

		transport.connect(myEmailAccount, myEmailPassword);

		// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人,
		// 密送人
		transport.sendMessage(message, message.getAllRecipients());

		// 7. 关闭连接
		transport.close();
	}

	public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String content,String title)
			throws UnsupportedEncodingException, MessagingException {

		// 1. 创建一封邮件
		MimeMessage message = new MimeMessage(session);

		// 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
		message.setFrom(new InternetAddress(sendMail, sendMail, "UTF-8"));

		// 3. To: 收件人（可以增加多个收件人、抄送、密送）
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveMail, "UTF-8"));

		// 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
		message.setSubject(title, "UTF-8");

		// 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
		// String secretKey= UUID.randomUUID().toString(); //密钥
		// String path = "http://localhost:14602/#/ResetPwd/"; // 跳转地址
		// String num = id + "/" + type; // 所需参数
		// String truepath = path + num; // 地址，参数的整合
		//
		// String key = id + type;
		// String digitalSignature = EncryptionStr(key, MD5); // 数字签名
		// String pathh = truepath + "/" + digitalSignature;
		message.setContent(content, "text/html;charset=UTF-8");

		// 6. 设置发件时间
		message.setSentDate(new Date());

		// 7. 保存设置
		message.saveChanges();

		return message;
	}

}

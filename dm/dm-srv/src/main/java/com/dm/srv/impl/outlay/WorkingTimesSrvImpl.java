package com.dm.srv.impl.outlay;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dm.dao.WorkingTimesDao;
import com.dm.dto.BaseDto;
import com.dm.dto.BizCode;
import com.dm.dto.MonthlyhourXDto;
import com.dm.dto.MonthlyhourYDto;
import com.dm.dto.PersonMonthHourInfo;
import com.dm.dto.StaffInfoDto;
import com.dm.dto.WorkingTimesDto;
import com.dm.dto.MonthHourTableDto;
import com.dm.entity.DepartmentInfoEntity;
import com.dm.entity.WorkingTimesEntity;
import com.dm.srv.impl.BaseSrvImp;
import com.dm.srv.outlay.WorkingTimesSrv;
import com.dm.tool.Constant;

/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：人工经费管理
 */
@Service
public class WorkingTimesSrvImpl extends BaseSrvImp implements WorkingTimesSrv {

	@Autowired
	private WorkingTimesDao wtDao;

	@Override
	public StaffInfoDto getStaffInfo(String userId, String[] deptId) {

		return wtDao.getStaffInfo(userId, deptId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseDto hourEnter(BigDecimal[] workingtimes, String[] projectNo, String[] fyYear, String dateTm,
			String userId,int flug,String newuserId) {

		BaseDto baseDto = new BaseDto();
		baseDto.setBizCode(BizCode.BIZ_CODE_HANDLE_FAILED);
		if(flug == 1) {
			// 删除
			List<WorkingTimesEntity> dllist = new ArrayList<>();
			for (int i = 0; i < projectNo.length; i++) {
				WorkingTimesEntity dl = new WorkingTimesEntity();
				dl.setFyYear(fyYear[i]);
				dl.setWorkingTimes(workingtimes[i]);
				dl.setProjectNo(projectNo[i]);
				dl.setUserId(userId);
				dllist.add(dl);
			}
			int b= wtDao.delWorkingTimesByDateAndIdAndPjnoAndFy(dllist,dateTm, newuserId);
		}else {
			int a=	wtDao.delWorkingTimesByDateAndId(dateTm, newuserId);
		}

		// 新增
		List<WorkingTimesEntity> wtlist = new ArrayList<>();
		for (int i = 0; i < projectNo.length; i++) {
			WorkingTimesEntity wt = new WorkingTimesEntity();
			wt.setFyYear(fyYear[i]);
			wt.setWorkingTimes(workingtimes[i]);
			wt.setProjectNo(projectNo[i]);
			wt.setUserId(userId);
			wtlist.add(wt);
		}

		int count = wtDao.addWorkingTimes(wtlist, dateTm, newuserId);
		if (count > 0) {
			baseDto.setBizCode(BizCode.BIZ_CODE_UPDATE_SUCCESS);
		}
		return baseDto;

	}

	//通过用户id查询部门
	@Override
	public String getDepartByUserId(String userId) {
		List<DepartmentInfoEntity> list  = wtDao.getDepartByUserId(userId);
		String deptName ="";
		if(list.size() != 0) {
			for(int i=0;i<list.size();i++) {
				deptName+=list.get(i).getDeptName()+",";
			}
			deptName = deptName.substring(0,deptName.length()-1);
			return deptName;
		}
		return deptName;
	}

	@Override
	public List<MonthHourTableDto> queryMonthlyHour(String departId, String fyYear) {

		// 行集合
		List<MonthlyhourYDto> list = null;
		// 行对象
		MonthlyhourXDto mhx = null;
		// 列集合
		List<MonthlyhourXDto> mhxs = null;

		// 表格对象
		List<MonthHourTableDto> table = new ArrayList<>();
		MonthHourTableDto tline = null;

		// 获取查询年 月
		int year = Integer.parseInt(fyYear.substring(0, 4));
		int month = Integer.parseInt(fyYear.substring(5, 7));
		String years = null;
		if(month < 10) {
			years = year + "-" + "0" + month;
		}else {
			years = year + "-" + month;
		}
		// 获取查询月度天数
		int countDaysByMonth = getDaysByYearMonth(year, month);

		// thead
		String[] headArray = new String[countDaysByMonth + 2];
		headArray[0] = "姓名";
		for (int i = 1; i < countDaysByMonth + 1; i++) {
			headArray[i] = String.valueOf(i);
		}
		headArray[countDaysByMonth + 1] = "当月合计";
		// 表格行对象赋值
		tline = new MonthHourTableDto();
		// 行 赋值
		tline.setColumnsVal(headArray);
		table.add(tline);

		// tbody
		// 获取部门人员月度工时
		list = wtDao.queryMonthlyHour(departId, years);
		// 处理月度工时数组
		for (int i = 0; i < list.size(); i++) {// 行
			// 月度工时数组
			String[] monthArray = new String[countDaysByMonth + 2];

			mhxs = list.get(i).getLines();
			// 月度工时数组 第一个参数为姓名
			monthArray[0] = list.get(i).getLineVal();
			// 列循环,获取数组第二位至倒数第二位，为每天工时
			for (int k = 0; k < mhxs.size(); k++) {// 列
				mhx = mhxs.get(k);
				// 获取日期
				Integer date = Integer.parseInt(mhx.getColumnKey().substring(8, 10));

				// 日期为数组下标，数组值为工时
				monthArray[date] = mhx.getColumns().getTableVal().setScale(1, BigDecimal.ROUND_HALF_UP).toString();

			}

			// 数组第二位至倒数第二位 存在null，默认为0
			for (int k = 1; k < monthArray.length - 1; k++) {
				if (monthArray[k] == null) {
					monthArray[k] = "0";
				}
			}

			// 数组第二位至倒数第二位 相加，获取当月合计
			BigDecimal mTotal = new BigDecimal("0");
			for (int k = 1; k < monthArray.length - 1; k++) {
				mTotal = mTotal.add(new BigDecimal(monthArray[k]));
			}

			// 将 当月合计赋值给数组
			monthArray[countDaysByMonth + 1] = String.valueOf(mTotal);

			// 表格行对象赋值
			tline = new MonthHourTableDto();
			// 第一列 赋值
			tline.setLineKey(list.get(i).getLineKey());
			// tline.setLineVal(list.get(i).getLineVal());
			// 第二列至倒数第二列 赋值
			tline.setColumnsVal(monthArray);

			table.add(tline);
		}

		// tfoot
		// 部门小计
		String[] departArray = new String[countDaysByMonth + 2];
		departArray[0] = "部门小计";
		// 累积求和
		for (int i = 1; i < departArray.length; i++) {// 列
			BigDecimal dTotal = new BigDecimal("0");
			for (int j = 1; j < table.size(); j++) {// 行
				dTotal = dTotal.add(new BigDecimal(table.get(j).getColumnsVal()[i]));
			}
			departArray[i] = dTotal.toString();
		}
		// 表格行对象赋值
		tline = new MonthHourTableDto();
		// 行 赋值
		tline.setColumnsVal(departArray);
		table.add(tline);

		return table;
	}

	@Override
	public List<PersonMonthHourInfo> getPersonMonthHour(String userId, String fyYear) {
		// 获取查询年 月
		int year = Integer.parseInt(fyYear.substring(0, 4));
		int month = Integer.parseInt(fyYear.substring(5, 7));
		String queryDate = null;
		if(month < 10) {
			queryDate = year + "-" + "0" + month;
		}else {
			queryDate = year + "-" + month;
		}
		List<PersonMonthHourInfo> list = wtDao.getPersonMonthHour(userId, queryDate);
		for(int i = 0; i<list.size(); i++) {
			String deptName = "";
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM");
			list.get(i).setDateTm(list.get(i).getDateTime());
			List<DepartmentInfoEntity> list1 = list.get(i).getDepartment();
			for(int j=0;j<list1.size();j++) {
				deptName+= list1.get(j).getDeptName()+",";
			}
			deptName = deptName.substring(0,deptName.length()-1);
			list.get(i).setDeptName(deptName);
		}

		return list;
	}

	@Override
	public List<Row> exportMonthlyHour(List<MonthHourTableDto> list,String deptName,String fyYear) {


		List<Row> rows = new ArrayList<>();
		// 获取总列数
	    int CountColumnNum = list.get(0).getColumnsVal().length;
	    // 创建Excel文档
	    HSSFWorkbook hwb = new HSSFWorkbook();
	    MonthHourTableDto matchInfo = null;
	    // sheet 对应一个工作页
	    HSSFSheet sheet = hwb.createSheet("pldrxkxxmb");

	    // 查询条件
	    HSSFRow firstrow = sheet.createRow(0); // 下标为0的行开始
	    HSSFCell[] firstcell = new HSSFCell[CountColumnNum];
	    String[] names = new String[CountColumnNum];
	    names[0] = "部门";
	    names[1] = deptName;
	    names[2] = "FY年月";
	    names[3] = fyYear;
	    for (int j = 0; j < 4; j++) {
	        firstcell[j] = firstrow.createCell(j);
	        firstcell[j].setCellValue(new HSSFRichTextString(names[j]));
	    }
	    rows.add(firstrow);

	    //查询结果
	    for (int i = 0; i < list.size(); i++) {
	        // 创建一行
	        HSSFRow row = sheet.createRow(i+1);
	        // 得到要插入的每一条记录
	        matchInfo = list.get(i);

	        for (int colu = 0; colu < CountColumnNum; colu++) {
	        // 在一行内循环
	        HSSFCell x = row.createCell(colu);
	        x.setCellValue(new HSSFRichTextString(matchInfo.getColumnsVal()[colu]));
	        }
	        //行添加
	        rows.add(row);
	    }

		return rows;
	}


	/**
	 * 根据 年、月 获取对应的月份 的 天数
	 */
	public static int getDaysByYearMonth(int year, int month) {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	//根据人员id查询工时
	@Override
	public List<WorkingTimesDto> getWork(String userId) {
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //当前日期年月日
        String dat = sdf.format(d);
        //模糊查询，拼接字符串
        String date = dat + Constant.STR_PERCENT;
        //根据用户id查询工时信息
        List<WorkingTimesDto> list = wtDao.getWork(userId, date);
		return list;
	}

	 // 根据课题编号查询工时信息
	@Override
	public List<WorkingTimesDto> getProjectNoInfo(String projectNo) {
		List<WorkingTimesDto> list = wtDao.getProjectNoInfo(projectNo);
		return list;
	}

	 // 根据课题编号，人员id，fy年度，当前时间查询工时信息
		@Override
		public List<WorkingTimesDto> getProjectNoInfoa(String projectNo,String userId,String fyYear) {
			Date d = new Date();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        //当前日期年月日
	        String dateTm = sdf.format(d);
			List<WorkingTimesDto> list = wtDao.getProjectNoInfoa(projectNo, userId, dateTm,fyYear);
			return list;
		}

		//根据人员id查询人员姓名
		public String getUserName(String userId) {
			String username = wtDao.getUserName(userId);
			return username;
		}

	//根据人员id和时间查询工时
	@Override
	public List<WorkingTimesDto> getDetail(String userId, String time) {
		//时间格式转换
		time = time.replaceAll("/","-" );
		//模糊查询，拼接字符串
		time = time + Constant.STR_PERCENT;
		List<WorkingTimesDto> list = wtDao.getWork(userId, time);
		return list;
	}
}

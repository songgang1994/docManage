package com.dm.ctrl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dm.ctrl.BaseCtrl;
import com.dm.tool.Constant;
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：文件下载
 */
@Controller
@RequestMapping("/file")
public class FileExportCtrl extends BaseCtrl{
	// 出力LOG用
	private static final Logger log = LoggerFactory.getLogger(FileExportCtrl.class);

	@RequestMapping(value="/export",method = RequestMethod.GET)
	public void fileExport(@RequestParam(value = "fileName", defaultValue = "") String fileName) {

		FileInputStream inputStream = null;
		OutputStream outputStream = null ;
		try {
			response.setContentType("application/x-download;charset=UTF-8");
			fileName = java.net.URLDecoder.decode(fileName, "UTF-8");
			String file_name = new String(fileName.getBytes(), "ISO-8859-1");
			response.addHeader("Content-Disposition", "attachment;fileName=" + file_name.concat(Constant.FILE_EXPORT_TYPE));
			File file = new File(Constant.FILE_EXPORT_PATH+fileName+Constant.FILE_EXPORT_TYPE);
			inputStream = new FileInputStream(file);
			outputStream = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, i);
			}
			outputStream.flush();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}finally {
			try {
				outputStream.close();
				inputStream.close();
				delCsv(fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void delCsv(String fileName) {
		File file = new File(Constant.FILE_EXPORT_PATH+fileName+Constant.FILE_EXPORT_TYPE);
		  if (file.exists()) {
			  file.delete();
		      return;
		  }
	};
}

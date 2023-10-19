package com.source.general.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/api/general/util")
public class UtilityController {

	@Value("${sc.system.doc.path}")
	String serverFolderPath;

	@RequestMapping(value = "/get/file/by/name/**", method = RequestMethod.GET)
	public StreamingResponseBody downloadFile(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String requestURL = String.valueOf(request.getRequestURL());

		try {
			String[] filePaths = requestURL.split("/name/");
			String filePath = (filePaths.length >= 2) ? filePaths[1] : "";

			if (filePath.isEmpty()) {
				return null;
			}

			File fileInfo = new File(serverFolderPath + File.separator + filePath);
			response.setHeader("Content-Disposition", "attachment; filename=" + fileInfo.getName());
//			response.setHeader("Content-Disposition", "inline; filename="+ fileInfo.getName()); // Display
			InputStream inputStream = new FileInputStream(fileInfo);

			return outputStream -> {
				int nRead;
				byte[] data = new byte[1024];
				while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
					outputStream.write(data, 0, nRead);
				}
				inputStream.close();
			};
		} catch (Exception e) {
			throw e;
		}
	}

}

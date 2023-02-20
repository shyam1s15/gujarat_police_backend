package com.shyam.gujarat_police.util;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class FileUtils {

	public static String createTempFile(String suffix) throws IOException {
		suffix = suffix.startsWith(".") ? suffix : ("." + suffix);
		String fileName = RequestIdGenerator.generate().replace("-", "");
		return (File.createTempFile(fileName, suffix).getAbsolutePath());
	}

	public static String generateExportFilePath(String moduleName, String fileName) {
		return S3Filepath(moduleName, getExtension(fileName));
	}

	public static String getExtension(String path) {
		return Objects.nonNull(path) && path.contains(".") ? path.substring(path.lastIndexOf("."), path.length())
				: ".xlsx";
	}

	/**
	 * Prepare path for Amazon S3.
	 * 
	 * @param reportTemplate
	 * @param directory
	 * @return
	 */
	public static String S3Filepath(String outputFileName, String extension) {
		extension = extension.startsWith(".") ? extension : ("." + extension);
		outputFileName = outputFileName + "_" + CustomDateUtils.format(new Date(), "MM/dd/yyyy HH:mm:ss.SSS")
				+ extension;
		outputFileName = outputFileName.replace("/", "_").replace(":", "_");
		return outputFileName;
	}

	public static String getInvoiceFileUploadUrl(String fileName, String invoiceId, Long userId) {
		return "invoice/" + userId + "_" + invoiceId;
	}
	public  static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
		File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
		multipart.transferTo(convFile);
		return convFile;
	}
}

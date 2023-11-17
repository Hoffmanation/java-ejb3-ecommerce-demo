package com.mystuff.rest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.mail.event.FolderAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.mystuff.util.AppConstants;

@Path("/admin")
public class FileUploadController {

	@POST
	@Path("/uploadImage")
	@Consumes("multipart/form-data")
	public Response uploadFile(MultipartFormDataInput input, @Context HttpServletRequest request) {
		String fileName = "";
		String filePath = "";
		String absolutePath = "";
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");

		for (InputPart inputPart : inputParts) {
			try {
				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = getFileName(header);
				InputStream inputStream = inputPart.getBody(InputStream.class, null);
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				int nRead;
				byte[] data = new byte[16384];
				while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}
				buffer.flush();
				byte[] bytes = buffer.toByteArray();
				// constructs upload file path
				fileName = AppConstants.UPLOAD_FILE_PATH + fileName;
				File file = new File(fileName);
				absolutePath = file.getAbsolutePath();
				System.out.println("File path : " + absolutePath);
				filePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
				System.out.println("File path : " + filePath);

				if (!file.exists()) {
					file.createNewFile();
				}

				FileOutputStream fop = new FileOutputStream(file);
				fop.write(bytes);
				fop.flush();
				fop.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return Response.status(200).entity(absolutePath).build();

	}

	/**
	 * header sample { Content-Type=[image/png], Content-Disposition=[form-data;
	 * name="file"; filename="filename.extension"] }
	 **/
	// get uploaded filename, is there a easy way in RESTEasy?
	private String getFileName(MultivaluedMap<String, String> header) {
		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
				String[] name = filename.split("=");
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	// save to somewhere
	private void writeFile(byte[] content, String filename) throws IOException {
		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);
		fop.write(content);
		fop.flush();
		fop.close();
	}
}
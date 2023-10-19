package com.source.utill;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileHandlerUtils {

	@Value("${sc.system.doc.path}")
	String serverFolderPath;

	private static final char SANITIZED_CHAR = '_';

	public static final char INVALID_CHARS[] = { '\\', '/', ':', '*', '?', '"', '<', '>', '|', '[', ']', '\'', ';', '=',
			',' };
	

	public String uploadMultipartFileToFile(String folderName, String fileName,
			MultipartFile files) {
		String path = Paths.get(serverFolderPath,folderName).toString();
		if (createDirectoryIfNotExist(path)) {
			try {
				fileName = this.sanitizeFileName(fileName);
				Path folderPath = Paths.get(path, fileName);
				Files.write(folderPath, files.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("Failed to create directory!");
		}
		return fileName;
	}

	public String uploadBase64ToFile(String folderName, String fileName,
			String files) {
		String path = Paths.get(serverFolderPath,folderName).toString();
		if (createDirectoryIfNotExist(path)) {
			try {
				files = files.substring(files.indexOf(",") + 1);

				fileName = this.sanitizeFileName(fileName);
				Path folderPath = Paths.get(path, fileName);
				byte[] byteArray = Base64.getDecoder()
						.decode(files.getBytes(StandardCharsets.UTF_8));
				Files.write(folderPath, byteArray);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("Failed to create directory!");
		}
		return Paths.get(folderName, fileName).toString();
	}

	private boolean createDirectoryIfNotExist(String folderPath) {
		if (!Files.exists(Paths.get(folderPath))) {
			new File(folderPath).mkdirs();
		}
		return true;
	}

	public String sanitizeFileName(String filename) {
		return sanitizeFileName(filename, SANITIZED_CHAR);
	}

	public String sanitizeFileName(String filename, char substitute) {
		 for (char element : INVALID_CHARS) {
		        if (filename.indexOf(element) != -1) {
		            filename = filename.replace(element, substitute);
		        }
		    }
		    if (filename.indexOf(' ') != -1) {
		        filename = filename.replace(' ', substitute);
		    }
		    return filename.toLowerCase();
	}

	public String getServerFolderPath(String folderName) {
		String path = Paths.get(serverFolderPath,folderName).toString();
		if (!createDirectoryIfNotExist(path)) {
			throw new RuntimeException("Failed to create directory!");
		}
		return path;
	}
	
    public  String getFilename(String pathString) {
    	Path path = Paths.get(pathString);
        String filename = path.getFileName().toString();
        
        // Check if the path contains a directory structure
        if (filename.isEmpty() && path.getNameCount() > 0) {
            // If the filename is empty and there are path components,
            // retrieve the last path component as the filename
            filename = path.getName(path.getNameCount() - 1).toString();
        }
        return sanitizeFileName(filename, SANITIZED_CHAR);
    }
    
    public static String getFileNameFromPath(String path) {
        if (path == null || path.isEmpty()) {
            return "";
        }

        int lastSeparatorIndex = path.lastIndexOf("\\");
        if (lastSeparatorIndex != -1) {
            return path.substring(lastSeparatorIndex + 1);
        } else {
            return path;
        }
    }

}

package fr.uha.hassenforder.cage.turtle.compiler.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class IO {

	// copied from BufferedInputStream
	private int DEFAULT_BUFFER_SIZE = 8192;

	private void deleteFile(File toDelete) {
		try {
			if (toDelete.isFile()) {
//				toDelete.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteFolder(File toDelete) {
		try {
			if (toDelete.isDirectory() && toDelete.list().length == 0) {
//				toDelete.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void deleteFolderRecursively(File folder) {
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();
			for (File file : files) {
				deleteFolderRecursively(file);
			}
			deleteFolder(folder);
		} else {
			deleteFile(folder);
		}
	}

	public void delete(File toDelete) {
	}

	public boolean mkdirs(File folder) {
		try {
			folder.mkdirs();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private byte[] read(InputStream is) {
		byte[] content = null;
		try {
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int len;
			while ((len = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			bis.close();
			content = bos.toByteArray();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	public byte[] loadAsBytes(InputStream input) {
		byte[] content = read(input);
		if (content == null)
			return "Stream not readable".getBytes();
		return content;
	}

	public String loadAsString(InputStream input) {
		byte[] content = read(input);
		if (content == null)
			return "Stream not readable";
		return new String(content);
	}

	public String load(File localFile) {
		try {
			if (!localFile.exists())
				return "File not found";
			long length = localFile.length();
			if (length == 0)
				return "";
			FileInputStream fis = new FileInputStream(localFile);
			byte[] content = read(fis);
			return new String(content);
		} catch (IOException e) {
			e.printStackTrace();
			return "File not readable";
		}
	}

	public boolean save(File localFile, String content) {
		try {
			FileOutputStream fos = new FileOutputStream(localFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(content.getBytes());
			bos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void addFileToZip(ZipOutputStream zipOut, File baseFolder, File file) throws IOException {
		try (FileInputStream fis = new FileInputStream(file)) {
			String zipEntryName = baseFolder.toPath().relativize(file.toPath()).toString();
			ZipEntry zipEntry = new ZipEntry(zipEntryName);
			zipOut.putNextEntry(zipEntry);
			byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = fis.read(bytes)) != -1) {
				zipOut.write(bytes, 0, length);
			}
			zipOut.closeEntry();
		} catch (FileNotFoundException fnfe) {
			System.out.println("Exception when attempting to open file: " + fnfe.getMessage());
		}
	}

	private void addFolderToZip(ZipOutputStream zipOut, File baseFolder, File folder) throws IOException {
		File[] files = folder.listFiles();
		if (files == null)
			return;
		for (File file : files) {
			if (file.isDirectory()) {
				addFolderToZip(zipOut, baseFolder, file);
			} else {
				addFileToZip(zipOut, baseFolder, file);
			}
		}
	}

	public byte[] zipFolder(File outputFolder) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ZipOutputStream zipOut = new ZipOutputStream(baos)) {
			addFolderToZip(zipOut, outputFolder, outputFolder);
			zipOut.finish();
			byte[] zipBytes = baos.toByteArray();
			return zipBytes;
		} catch (Exception e) {
			return null;
		}
	}

	private static File newFile(File destDir, ZipEntry zipEntry) {
		try {
			File destFile = new File(destDir, zipEntry.getName());
			String destDirPath = destDir.getCanonicalPath();
			String destFilePath = destFile.getCanonicalPath();
			if (!destFilePath.startsWith(destDirPath + File.separator)) return null;
			return destFile;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void unzip(byte[] input, File destDir) {
		try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(input))) {
			ZipEntry zipEntry;
			while ((zipEntry = zis.getNextEntry()) != null) {
				File newFile = newFile(destDir, zipEntry);
				if (newFile == null) continue;
				if (zipEntry.isDirectory()) {
					if (!newFile.isDirectory() && !newFile.mkdirs()) {
						throw new IOException("Failed to create directory " + newFile);
					}
				} else {
					// fix for Windows-created archives
					File parent = newFile.getParentFile();
					if (!parent.isDirectory() && !parent.mkdirs()) {
						throw new IOException("Failed to create directory " + parent);
					}
					// write file content
					FileOutputStream fos = new FileOutputStream(newFile);
					byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fos.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

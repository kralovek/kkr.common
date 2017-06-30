package kkr.common.errors;

import java.io.File;

public class FileException extends BaseException {
	private File file;

	public FileException(File file, String message) {
		super(message);
		this.file = file;
	}

	public FileException(File file, String message, Throwable th) {
		super(message, th);
		this.file = file;
	}

	public File getFile() {
		return file;
	}
}

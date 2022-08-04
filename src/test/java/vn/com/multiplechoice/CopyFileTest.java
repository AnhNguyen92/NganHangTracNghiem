package vn.com.multiplechoice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.util.FileSystemUtils;

public class CopyFileTest {
	public static void copyFile() throws FileNotFoundException, IOException {
		File sourceFile = new File("D:\\applications\\multiplechoice\\template\\4\\header_dethi.docx");
		File targetFile = new File("D:\\applications\\test.docx");
		FileSystemUtils.copyRecursively(sourceFile, targetFile);
	}
}

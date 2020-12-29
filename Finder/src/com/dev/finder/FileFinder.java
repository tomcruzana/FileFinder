package com.dev.finder;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileFinder extends Finder {

	private String fileType;
	private String rootDir;
	private String fileName;

	public FileFinder() {
		//empty constructor
	}

	public String getRootDir() {
		return rootDir;
	}

	private void setFileType(String fileType) {
		this.fileType = ".".concat(fileType);
	}

	public String getFileName() {

		return formatfileName(fileName);
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private String formatfileName(String fileName) {
		return fileName.concat(fileType);
	}

	private boolean isMatch(String n) {

		// parameter n is the name of the file
		Pattern pattern = Pattern.compile("^.*" + this.fileName + ".*\\" + this.fileType + "$",
				Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(n);

		return (matcher.find()) ? true : false;
	}

	@Override
	public void search(File currentDir, String fileName) {

		try {
			// get all file and dir names
			String[] names = currentDir.list();

			// traverse all dirs (root & subs) and find matching files
			for (String n : names) {

				// if file is present in the current dir then open it
				if (isMatch(n)) {
					open(new File(currentDir + "\\" + n));
				}

				// if file is a sub dir
				if (new File(currentDir + "\\" + n).isDirectory()) {
					// search sub dir and open matching file by using recursion
					System.out.println("Searching " + n + " directory.");
					search(new File(currentDir + "\\" + n), fileName);
				}
			}

		} catch (NullPointerException e) {
			System.out.println("Null Pointer Exception Error");
		} catch (Exception e) {
			System.out.println("Unknown error");
		}

	}

	@Override
	public void open(File file) {
		try {
			// get supported desktop
			Desktop desktop = Desktop.getDesktop();

			// checks file exists or not
			if (file.exists()) {

				// opens the specified file and show which path it was found
				System.out.println("Match found in: " + file.getAbsoluteFile());
				desktop.open(file);
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (Exception e) {
			System.out.println("Unknown error");
		}

	}

	@Override
	public void getInput() {

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Enter a valid file type:");
			setFileType(scanner.nextLine());

			System.out.println("Enter a valid path:");
			this.rootDir = scanner.nextLine();

			System.out.println("Enter file name to begin search:");
			setFileName(scanner.nextLine());

			search(new File(getRootDir()), getFileName());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

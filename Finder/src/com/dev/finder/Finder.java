package com.dev.finder;

import java.io.File;

public abstract class Finder {
	public abstract void search(File rootDir, String fileName);

	public void open(File file) {};

	public abstract void getInput();
}

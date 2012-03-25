package com.Logo.widget;

public interface OnFileBrowserListener {
	//do something when click single file item
	public void onFileItemClick(String filename);
	
	public void onDirItemClick(String path);
}

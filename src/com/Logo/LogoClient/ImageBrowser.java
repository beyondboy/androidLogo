package com.Logo.LogoClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.Logo.widget.FileBrowser;
import com.Logo.widget.OnFileBrowserListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageBrowser extends Activity implements OnFileBrowserListener {

	public boolean onLogoQuery(String filename) {
		
		View view = getLayoutInflater().inflate(R.layout.imagebrowser, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		FileInputStream fis1 = null;
		try {
			fis1 = new FileInputStream(filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		imageView.setImageDrawable(Drawable.createFromStream(fis1, filename));
		new AlertDialog.Builder(this).setTitle("ä¯ÀÀÍ¼Ïñ").setView(view).setPositiveButton("¹Ø±Õ", null).show();
		try {
			fis1.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String UploadUrl = "http://www.luogaxiaoxue.com/picload/simple.fcgi";
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "--***";
		try{
			URL url = new URL(UploadUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			
			conn.setConnectTimeout(120000);
			conn.setReadTimeout(120000);
			/*if (conn.getResponseCode() != 200){
				System.out.println("ÇëÇóURLÊ§°Ü!");
			}*/
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Chareset", "UTF-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			
			dos.writeBytes(twoHyphens+boundary+end);
						
			dos.writeBytes("Content-Disposition:form-data;name=\"file\";filename=\""+filename.substring(filename.lastIndexOf("/")+1)+"\""+end);
			dos.writeBytes("Content-Type:application/octet-stream"+end);
			
			dos.writeBytes(end);
			
			FileInputStream fis = new FileInputStream(filename);
			byte[] buffer = new byte[8192];
			int count = 0;
			while ((count = fis.read(buffer)) != -1){
				dos.write(buffer, 0, count);
			}
			fis.close();
			
			dos.writeBytes(end);
			dos.writeBytes(twoHyphens+boundary+twoHyphens+end);
			dos.flush();
			
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is,"utf-8");
			BufferedReader br = new BufferedReader(isr);
			String result = br.readLine();
			String l1 = br.readLine();
			String l2 = br.readLine();
			String l3 = br.readLine();
			String l4 = br.readLine();
			String l5 = br.readLine();
			String l6 = br.readLine();
			String sct = l4+l5+l6;
			
			Toast.makeText(this, result+l1+l2+l3+"Hello", Toast.LENGTH_LONG);
			dos.close();
			is.close();
			br.close();
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public void onFileItemClick(String filename) {
		// TODO Auto-generated method stub
		if (!filename.toLowerCase().endsWith(".jpg")){
			return;
		}
		System.out.println("hello world!");
		/*View view = getLayoutInflater().inflate(R.layout.imagebrowser, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);*/
		try{
			/*FileInputStream fis = new FileInputStream(filename);
			imageView.setImageDrawable(Drawable.createFromStream(fis, filename));
			new AlertDialog.Builder(this).setTitle("ä¯ÀÀÍ¼Ïñ").setView(view).setPositiveButton("¹Ø±Õ", null).show();
			fis.close();*/			
			onLogoQuery(filename);
		
		}catch (Exception e){
			
		}
	}

	@Override
	public void onDirItemClick(String path) {
		// TODO Auto-generated method stub
		setTitle("µ±Ç°Ä¿Â¼£º"+path);
	}
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filebrowser);
        FileBrowser fileBrowser = (FileBrowser)findViewById(R.id.filebrowser);
        fileBrowser.setOnFileBrowserListener(this);
    }
}

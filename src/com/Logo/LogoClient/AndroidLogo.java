package com.Logo.LogoClient;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

public class AndroidLogo extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	private List<Integer> imageResIdList = new ArrayList<Integer>();
	private Gallery gallery;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        try{
        	java.lang.reflect.Field[] fields = R.drawable.class.getDeclaredFields();
        	for (java.lang.reflect.Field field : fields){
        		if (field.getName().startsWith("item")){
        			imageResIdList.add(field.getInt(R.drawable.class));
        		}
        	}
        	gallery = (Gallery) findViewById(R.id.gallery);
        	ImageAdapter imageAdapter = new ImageAdapter(this);
        	gallery.setAdapter(imageAdapter);
        	Button btnSaveImage = (Button) findViewById(R.id.btnSaveImage);
        	Button btnBrowserImage = (Button) findViewById(R.id.btnBrowserImage);
        	
        	btnSaveImage.setOnClickListener(this);
        	btnBrowserImage.setOnClickListener(this);
        }catch (Exception e){
        	
        }
    }

	public class ImageAdapter extends BaseAdapter{
		int mGalleryItemBackground;
		private Context mContext;
		
		public ImageAdapter(Context context){
			mContext = context;
			TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
			mGalleryItemBackground = typedArray.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0);
			
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView imageView = new ImageView(mContext);
			imageView.setImageResource(imageResIdList.get(position));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(136,88));
			imageView.setBackgroundResource(mGalleryItemBackground);
			return imageView;
		}
	}
/*
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		try{
			switch (view.getId()){
			case R.id.btnSaveImage:
				System.out.println("hello world!");
				String sdcard = android.os.Environment.getExternalStorageDirectory().toString();
				FileOutputStream fos = new FileOutputStream(sdcard+"/item"+gallery.getSelectedItemPosition()+".jpg");
				((BitmapDrawable) getResources().getDrawable(imageResIdList.
						get(gallery.getSelectedItemPosition()))).getBitmap().compress(CompressFormat.JPEG, 50, fos);
				fos.close();
				break;
			case R.id.btnBrowserImage:
				Intent intent = new Intent(this,ImageBrowser.class);
				startActivity(intent);
				break;
			}
			
		}catch(Exception e) {
			
		}
	}
*/

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		try{
			switch (view.getId()){
			case R.id.btnSaveImage:
				
				System.out.println("hello world!");
				String sdcard = android.os.Environment.getExternalStorageDirectory().toString();
				FileOutputStream fos = new FileOutputStream(sdcard+"/item"+gallery.getSelectedItemPosition()+".jpg");
				((BitmapDrawable) getResources().getDrawable(imageResIdList.
						get(gallery.getSelectedItemPosition()))).getBitmap().compress(CompressFormat.JPEG, 50, fos);
				fos.close();
				break;
			case R.id.btnBrowserImage:
				Intent intent = new Intent(this,ImageBrowser.class);
				startActivity(intent);
				break;				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
}
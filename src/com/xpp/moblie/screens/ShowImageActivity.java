package com.xpp.moblie.screens;


import com.polites.android.GestureImageView;
import com.xpp.moblie.util.PictureShowUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;

/**
 * <B>�ļ���:</B>ShowImageActivity.java<br>
 * <B>����:</B>��Ƭ�鿴����<br>
 * <B>����:</B>���<br>
 */

public class ShowImageActivity extends Activity {
	
	private GestureImageView image ; 
	private String dir ;
//	private PictureShowUtils ps;
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.layout_show_image);
    	Bundle bun =getIntent().getExtras();
    	if(bun!=null){
    		dir=(String) bun.get("dir");
    	}
    	init();
//    	ps=new PictureShowUtils();
    	PictureShowUtils.getImage(dir);
    	image.setImageBitmap(PictureShowUtils.getImage(dir));
    }
	
    public void  init(){
    	image=(GestureImageView) findViewById(R.id.image);
    }
    
    
}
package com.xpp.moblie.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.widget.ImageButton;

	@SuppressLint("DrawAllocation")
	public class MyImageButton extends ImageButton {  
	    private String text = null;  //要显示的文字  
	    private int color;               //文字的颜色  
	    private int height ;
	    public MyImageButton(Context context) {
			super(context);
		}
		public void setText(String text){  
	        this.text = text;       //设置文字  
	    }  
	    public void setColor(int color){  
	        this.color = color;    //设置文字颜色  
	    }  
	    public void setHeight(int height) {
			this.height = height;//设置高度
		}
		@Override 
	    protected void onDraw(Canvas canvas) {  
	        super.onDraw(canvas);  
	        Paint paint=new Paint();  
	        paint.setTextAlign(Paint.Align.LEFT);  
	        paint.setColor(color);  
	        canvas.drawText(text, 15, 140, paint);  //绘制文字  (float) (height*0.18)
	    }  
}

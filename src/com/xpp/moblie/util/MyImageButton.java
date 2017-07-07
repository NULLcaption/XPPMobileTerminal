package com.xpp.moblie.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.widget.ImageButton;

	@SuppressLint("DrawAllocation")
	public class MyImageButton extends ImageButton {  
	    private String text = null;  //Ҫ��ʾ������  
	    private int color;               //���ֵ���ɫ  
	    private int height ;
	    public MyImageButton(Context context) {
			super(context);
		}
		public void setText(String text){  
	        this.text = text;       //��������  
	    }  
	    public void setColor(int color){  
	        this.color = color;    //����������ɫ  
	    }  
	    public void setHeight(int height) {
			this.height = height;//���ø߶�
		}
		@Override 
	    protected void onDraw(Canvas canvas) {  
	        super.onDraw(canvas);  
	        Paint paint=new Paint();  
	        paint.setTextAlign(Paint.Align.LEFT);  
	        paint.setColor(color);  
	        canvas.drawText(text, 15, 140, paint);  //��������  (float) (height*0.18)
	    }  
}

package com.xpp.moblie.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class ProgressBarUtil extends ProgressBar {

	
	String text;
    Paint mPaint;
     
    public ProgressBarUtil(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
       
        initText(); 
    }
     
    public ProgressBarUtil(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        //System.out.println("2");
        initText();
    }
 
 
    public ProgressBarUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
       // System.out.println("3");
        initText();
    }
     
    @Override
    public synchronized void setProgress(int progress) {
        // TODO Auto-generated method stub
        setText((double)progress);
        super.setProgress(progress);
         
    }
 
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //this.setText();
        Rect rect = new Rect();  
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);  
        //int x = (getWidth() / 2) - rect.centerX(); 
        int x=0;
        int y = (getHeight() / 2) - rect.centerY();  
        canvas.drawText(this.text, x, y, this.mPaint); 
    }
     
    //初始化，画笔
    private void initText(){
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.WHITE);
        this.mPaint.setTextSize(18);
    }
     
    public void setText(String text){
    	double i=Double.parseDouble(text);
        setText(i);
    }
     
    //设置文字内容
    private void setText(double progress){
        this.text = String.valueOf(progress) + "%";
    }
	
}

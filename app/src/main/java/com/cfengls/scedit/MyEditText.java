package com.cfengls.scedit;
import android.content.*;
import android.widget.*;
import android.graphics.*;
import android.util.*;
import android.text.*;
import android.view.*;

public class MyEditText extends EditText
{
    private Paint line;
    public MyEditText(Context context,AttributeSet As){
        super(context,As);
        setFocusable(true);
        line=new Paint();
        line.setColor(Color.GRAY);
        line.setStrokeWidth(3);
        line.setAntiAlias(true);
        //p.setBitmapFilter();
        setPadding(65,0,65,0);
        setGravity(Gravity.TOP);
    }

    @Override
    protected void onDraw(final Canvas canvas)
    {
// TODO: Implement this metho

        if(getText().toString().length()!=0){
            float y=0;
            Paint p=new Paint();

            p.setColor(Color.GRAY);
            p.setFakeBoldText(true);
            p.setTextSize(25);
            p.setAntiAlias(true);
            //p.setBitmapFilter();
            for(int l=0;l<getLineCount();l++){
                y=((l+1)*getLineHeight())-(getLineHeight()/4);
                if (l + 1 < 10) {

                    canvas.drawText(String.valueOf(l + 1), 30, y+5, p);

                }
                else if (l + 1 < 100) {
                    canvas.drawText(String.valueOf(l + 1), 20, y+5, p);

                }
                else if (l + 1 < 1000) {
                    p.setTextSize(20);
                    canvas.drawText(String.valueOf(l + 1),10 , y+6, p);

                }
                else if (l + 1 < 10000) {
                    p.setTextSize(20);
                    canvas.drawText(String.valueOf(l + 1),0 , y+5, p);

                }

                    y=((l+1)*getLineHeight())-(getLineHeight()/4);

                    canvas.save();

            }
        }
        int k=getLineHeight();
        int i=getLineCount();

        canvas.drawLine(63,0,63,getHeight()+(i*k),line);
        int y=(getLayout().getLineForOffset(getSelectionStart())+1)*k;

        canvas.save();
        canvas.restore();
        super.onDraw(canvas);
    }
    public MyEditText(Context context) {
        super(context);
    }




    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }
    @Override
    public void setTypeface(Typeface tf, int style) {
        super.setTypeface(createTypeface(getContext(),"fonts/ziti.ttf"), style);
    }
}

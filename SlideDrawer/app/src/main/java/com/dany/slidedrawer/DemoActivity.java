package com.dany.slidedrawer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dany.scrolllayout.ScrollLayout;
import com.dany.scrolllayout.content.ContentScrollView;

public class DemoActivity extends AppCompatActivity {

    private ScrollLayout mScrollLayout;
    private ImageView text_foot;
    private ImageView text_cont1;
    private ImageView text_cont2;
    private ContentScrollView mCScrollView;
    private float mCSHeight = 0;
    private float mOpenHeight = 0;
    private float mExitHeiht = 0;

    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
            }
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
//            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
//                text_foot.setVisibility(View.VISIBLE);
//            }
            if(mScrollLayout.getCurrentStatus() != ScrollLayout.Status.EXIT){
                text_foot.setImageResource(R.mipmap.bg_11);
            }else{
                text_foot.setImageResource(R.mipmap.bg_1);
            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_demo);
        initView();
    }

    private void initView() {
        mScrollLayout = (ScrollLayout) findViewById(R.id.scroll_down_layout);
        text_foot = (ImageView) findViewById(R.id.text_foot);
        text_cont1 = (ImageView) findViewById(R.id.text_cont1);
        text_cont2 = (ImageView) findViewById(R.id.text_cont2);
        mCScrollView = (ContentScrollView) findViewById(R.id.csrollview);
        initIvsHeight();
        Log.d("dan.yy","mCSHeight:"+mCSHeight+",mOpenHeight:"+mOpenHeight+"mExitHeiht:"+mExitHeiht);

        /**设置 setting*/
        mScrollLayout.setMinOffset(0);
//        mScrollLayout.setMaxOffset((int) (ScreenUtil.getScreenHeight(this) * 0.5));
       /* mScrollLayout.setSpecificHeight(ScreenUtil.dip2px(this, (float) 148.5));
        mScrollLayout.setMaxOffset(ScreenUtil.dip2px(this, 91));
        mScrollLayout.setExitOffset(ScreenUtil.dip2px(this, (float) 15.5));*/
        mScrollLayout.setSpecificHeight((int) mCSHeight);
        mScrollLayout.setMaxOffset((int) mOpenHeight);
        mScrollLayout.setExitOffset((int) mExitHeiht);
        mScrollLayout.setIsSupportExit(true);
        mScrollLayout.setAllowHorizontalScroll(true);
        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        mScrollLayout.setToExit(false);
        ViewTreeObserver observer =  mScrollLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d("dan.y","ScreenHeight():"+ScreenUtil.getScreenHeight(DemoActivity.this));
                Log.d("dan.y","--------");
                Log.d("dan.y","mScrollLayout.getHeight():"+mScrollLayout.getHeight());
                Log.d("dan.y","--------");
                Log.d("dan.y","mScrollLayout.getX():"+mScrollLayout.getX()+",,mScrollLayout.getY():"+mScrollLayout.getY());
            }
        });


        text_foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mScrollLayout.getCurrentStatus() == ScrollLayout.Status.OPENED){
                    mScrollLayout.setToExit(true);
                    text_foot.setImageResource(R.mipmap.bg_11);
                }else{
                    mScrollLayout.setToOpen();
                    text_foot.setImageResource(R.mipmap.bg_1);
                }
            }
        });
    }

    private void initIvsHeight(){
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.mipmap.bg_1);
        int imageWidth = bitmap1.getWidth();
        int imageHeight = bitmap1.getHeight();
        int height = ScreenUtil.getScreenWidth(this) * imageHeight / imageWidth;
        ViewGroup.LayoutParams para = text_foot.getLayoutParams();
        para.height = height;
        para.width = ScreenUtil.getScreenWidth(this);
        Log.d("dan.yyy","getScreenWidth:"+ScreenUtil.getScreenWidth(this)+
                ",getScreenHeight:"+ScreenUtil.getScreenHeight(this));
        mExitHeiht = height;
        text_foot.setImageResource(R.mipmap.bg_1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.mipmap.bg_2);
        int imageWidth2 = bitmap2.getWidth();
        int imageHeight2 = bitmap2.getHeight();
        int height2 = ScreenUtil.getScreenWidth(this) * imageHeight2 / imageWidth2;
        ViewGroup.LayoutParams para2 = text_cont1.getLayoutParams();
        para2.height = height2;
        para2.width = ScreenUtil.getScreenWidth(this);
        mOpenHeight = height2 + mExitHeiht;
        text_cont1.setImageResource(R.mipmap.bg_2);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),R.mipmap.bg_3);
        int imageWidth3 = bitmap3.getWidth();
        int imageHeight3 = bitmap3.getHeight();
        int height3 = ScreenUtil.getScreenWidth(this) * imageHeight3 / imageWidth3;
        ViewGroup.LayoutParams para3 = text_cont2.getLayoutParams();
        para3.height = height3;
        para3.width = ScreenUtil.getScreenWidth(this);
        mCSHeight = height3 + mOpenHeight;
        text_cont2.setImageResource(R.mipmap.bg_3);
        final int screenwidth = ScreenUtil.getScreenWidth(this);
        RelativeLayout.LayoutParams llp = (RelativeLayout.LayoutParams) mScrollLayout.getLayoutParams();
        llp.height = (int) mCSHeight;
        llp.width = screenwidth;
        mScrollLayout.setLayoutParams(llp);
        bitmap1.recycle();
        bitmap2.recycle();
        bitmap3.recycle();
    }
}

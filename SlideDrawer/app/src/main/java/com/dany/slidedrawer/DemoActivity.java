package com.dany.slidedrawer;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dany.scrolllayout.ScrollLayout;
import com.dany.scrolllayout.content.ContentScrollView;

public class DemoActivity extends AppCompatActivity {

    private ScrollLayout mScrollLayout;
    private TextView text_foot;
    private ContentScrollView mCScrollView;

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
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
                text_foot.setVisibility(View.VISIBLE);
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
        text_foot = (TextView) findViewById(R.id.text_foot);
        mCScrollView = (ContentScrollView) findViewById(R.id.csrollview);

        /**设置 setting*/
        mScrollLayout.setMinOffset(0);
//        mScrollLayout.setMaxOffset((int) (ScreenUtil.getScreenHeight(this) * 0.5));
        mScrollLayout.setSpecificHeight(ScreenUtil.dip2px(this, 290));
        mScrollLayout.setMaxOffset(ScreenUtil.dip2px(this, 170));
        mScrollLayout.setExitOffset(ScreenUtil.dip2px(this, 50));
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
                }else{
                    mScrollLayout.setToOpen();
                }
            }
        });
    }
}

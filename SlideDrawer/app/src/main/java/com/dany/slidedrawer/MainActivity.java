package com.dany.slidedrawer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.dany.scrolllayout.ScrollLayout;
import com.dany.scrolllayout.content.ContentScrollView;

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);
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
                Log.d("dan.y","ScreenHeight():"+ScreenUtil.getScreenHeight(MainActivity.this));
                Log.d("dan.y","--------");
                Log.d("dan.y","mScrollLayout.getHeight():"+mScrollLayout.getHeight());
                Log.d("dan.y","--------");
                Log.d("dan.y","mScrollLayout.getX():"+mScrollLayout.getX()+",,mScrollLayout.getY():"+mScrollLayout.getY());
            }
        });


        text_foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mScrollLayout.isMiddle()){
                    mScrollLayout.setToExit(true);
                }else{
                    mScrollLayout.setToOpen();
                }
            }
        });
    }
}

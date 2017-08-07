package com.wofi.Guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wofi.Activity.LoginActivity;
import com.wofi.R;

import java.util.ArrayList;
import java.util.List;

public class Guide extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager vp;
    private ViewPagerAdapter viewPagerAdapter;
    private List<View> views;
    private TextView textview;

    private ImageView[] dots;
    private int[] ids= {R.id.iv1,R.id.iv2,R.id.iv3};
    private Button btnEnter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.guide);
        textview =(TextView)findViewById(R.id.tvSkip);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Guide.this,LoginActivity.class);
                startActivity(intent);
                Guide.this.finish();
            }});
        intiView();
        initDots();
        enter();
    }

    private void intiView(){
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.one,null));
        views.add(inflater.inflate(R.layout.tow,null));
        views.add(inflater.inflate(R.layout.three,null));

        viewPagerAdapter = new ViewPagerAdapter(views,this);

        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(viewPagerAdapter);

        vp.setOnPageChangeListener(this);
    }

    private void enter(){
        btnEnter = (Button) views.get(2).findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Guide.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void initDots(){
        dots = new ImageView[views.size()];
        for (int i = 0;i<views.size();i++){
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }

    //当页面被滑动调用
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //当前新的页面被选中时调用
    @Override
    public void onPageSelected(int position) {
        for (int i = 0;i< views.size();i++){
            if (position == i){
//设置小圆点为选中状态
                dots[i].setImageResource(R.drawable.login_point_selected);
            }
            else{
//设置小圆点为未选中状态
                dots[i].setImageResource(R.drawable.login_point);

            }
        }
    }

    //活动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int state) {

    }


}

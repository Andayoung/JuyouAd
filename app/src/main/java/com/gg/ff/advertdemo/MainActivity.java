package com.gg.ff.advertdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gg.bb.BanView;
import com.gg.bb.BannerMan;
import com.gg.bb.MiniView;
import com.gg.bb.Tools;
import com.gg.ff.PushMan;
import com.gg.ss.ShortcutMan;
import com.gg.tt.TableScreenMan;
import com.yow.PointListener;
import com.yow.YoManage;


public class MainActivity extends Activity {
    private static final String ID = "aea557acc3b80bc36d34cbb6d3a38e42";
    private static final String TAG="yes";
    private TextView pointsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableScreenMan.getInstance(getApplicationContext(), ID, "official", 1);
        BannerMan.getInstance(getApplicationContext(), ID, "official");
        PushMan.get(getApplicationContext(), ID, "official", true);
        ShortcutMan.getInstance(getApplication(), ID, "official").create();
        YoManage.getInstance(this, "cc9f189330cdb7e6cbaed28babbd820c", "official").init();
        initView();
    }

    private void initView() {
        RelativeLayout mLayout = (RelativeLayout) findViewById(R.id.ad_container_layout);
        BanView banView = new BanView(this);
        mLayout.addView(banView);
//        BanView banView = new BanView(this);
//        FrameLayout.LayoutParams layoutParams = Tools.getBanLayoutParams(this);
//        layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
//        this.addContentView(banView, layoutParams);

        MiniView miniView = new MiniView(this);
        FrameLayout.LayoutParams miniLayoutParams = Tools.getMiniLayoutParams(this);
        miniLayoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        this.addContentView(miniView, miniLayoutParams);

        YoManage.setGivePointListener(pointListener);

        pointsView=(TextView)findViewById(R.id.txt);
        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOffer();
            }
        });
        Button bt1=(Button)findViewById(R.id.btn1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewardOffer();
            }
        });
        Button bt2=(Button)findViewById(R.id.btn2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumeOffer();
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //退出弹出
            TableScreenMan.getInstance(this).showExitDialog(this, new View.OnClickListener() {
                public void onClick(View arg0) {
                    //释放资源
                }
            });
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onDestroy() {
        //关闭弹出
        TableScreenMan.getInstance(this).colseExitDialog();
        super.onDestroy();
    }

    PointListener pointListener = new PointListener() {

        //奖励用户 虚拟币
        @Override
        public void givePointResult(int points, int totalPoints) {

            Log.d(TAG, "givePointResult points=" + points + "#totalPoints=" + totalPoints);
            // points 奖励积分数
            // totalPoints 当前总积分数
            Toast.makeText(MainActivity.this, "完成任务获得积分：" + points + "#当前总积分=" + totalPoints, Toast.LENGTH_LONG).show();
        }


        //获取用户“虚拟币”
        @Override
        public void getPointResult(int points) {
            Log.d(TAG, "getPointResult points=" + points);
            // points 当前用户虚拟币数
            Toast.makeText(MainActivity.this, "当前积分数：" + points, Toast.LENGTH_LONG).show();
            pointsView.setText("当前积分数：" + points);

        }

        //扣除玩家“虚拟币”
        @Override
        public void consumePointResult(int points, int status) {
            Log.d(TAG, "consumePointResult points=" + points + "#status=" + status);
            // points 要扣除的 虚拟币
            // status 0：成功 1:失败 2:用户虚拟币余额不足
            Toast.makeText(MainActivity.this, "消费：" + points + "积分#status=" + status, Toast.LENGTH_LONG).show();

        }

        //手动奖励用户虚拟币
        @Override
        public void rewardPointResult(String unitName, int totalPoints, int status) {
            Log.d(TAG, "rewardPointResult unitName=" + unitName + "#totalPoints=" + totalPoints + "#status=" + status);
            // unitName 当前积分名称
            // totalPoints 当前用户总积分数
            // status 0:奖励成功 ,1:奖励失败
            Toast.makeText(MainActivity.this, "奖励：" + unitName + "#totalPoints=" + totalPoints + "#status=" + status, Toast.LENGTH_LONG).show();

        }
    };

    public void openOffer() {
        // 显示积分墙
        String userId = "123"; // 传入自己的标用户标识
        YoManage.showOffer(this, userId);
    }

    public void getPoint(View view) {
        // 获取积分
        YoManage.getPoints(pointListener);
    }

    public void consumeOffer() {
        int points = 10;
        // 消费积分
        YoManage.consumePoints(points, pointListener);

    }

    public void rewardOffer() {
        int points = 10;
        // 奖励积分
        YoManage.rewardPoints(points, pointListener);

    }

}

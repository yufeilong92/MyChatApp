package com.lawyee.mychatapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lawyee.mychatapp.R;
import com.lawyee.mychatapp.adapter.RlvAdapter;
import com.lawyee.mychatapp.adapter.RlvChatMsgAdapter;
import com.lawyee.mychatapp.bean.ChatMsg;
import com.lawyee.mychatapp.bean.RlvItemContent;
import com.lawyee.mychatapp.util.EmotionKeyboard;
import com.lawyee.mychatapp.viewpager.EmotionAdapter;
import com.lawyee.mychatapp.viewpager.GlobalOnItemClickManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: MainActivity.java
 * @Package com.lawyee.mychatapp.ui
 * @Description: 主显示界面
 * @author: YFL
 * @date: 2017/4/20 9:19
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/4/20 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUESTCODE = 1;
    public static final int RESULTCODE = 2;

    private FrameLayout extendView, emotionView;
    private ImageView extendButton, emotionButton;
    private EditText edittext;
    private Button btnSend;
    private EmotionKeyboard emotionKeyboard;
    private static final int emsNumOfEveryFragment = 20;//每页的表情数量

    private RadioGroup rgTipPoints;
    private RadioButton rbPoint;
    private RecyclerView mRlvLayout;
    private RecyclerView mRlvMainChat;

    private List<ChatMsg> lists = new ArrayList<ChatMsg>();
    private RlvChatMsgAdapter mRlvChatMsgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initContent();
        bindToEmotionKeyboard();

    }


    private void initView() {
        extendButton = (ImageView) findViewById(R.id.img_reply_layout_add);
        emotionButton = (ImageView) findViewById(R.id.img_reply_layout_emotion);
        edittext = (EditText) findViewById(R.id.edit_text);
        edittext.addTextChangedListener(new ButtonBtnWatcher());//动态监听EditText
        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);
        extendView = (FrameLayout) findViewById(R.id.extend_layout);
        emotionView = (FrameLayout) findViewById(R.id.emotion_layout);
        mRlvLayout = (RecyclerView) findViewById(R.id.rlv_layout);
        mRlvMainChat = (RecyclerView) findViewById(R.id.lv_main_Chat);
    }

    private void initContent() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRlvMainChat.setLayoutManager(manager);
        mRlvChatMsgAdapter = new RlvChatMsgAdapter(lists, this);
        mRlvMainChat.setAdapter(mRlvChatMsgAdapter);

    }

    private void bindToEmotionKeyboard() {
        emotionKeyboard = EmotionKeyboard.with(this)
                .setExtendView(extendView)
                .setEmotionView(emotionView)
                .bindToContent(mRlvMainChat)
                .bindToEditText(edittext)
                .bindToExtendButton(extendButton)
                .bindToEmotionButton(emotionButton)
                .build();
        setUpEmotionViewPager();
        setUpExtendView();
    }

    /* 设置表情布局下的视图 */
    private void setUpEmotionViewPager() {
        int fragmentNum;
        /*获取ems文件夹有多少个表情  减1 是因为有个删除键
                         每页20个表情  总共有length个表情
                         先判断能不能整除  判断是否有不满一页的表情
		 */
        int emsTotalNum = getSizeOfAssetsCertainFolder("ems") - 1;//表情的数量(除去删除按钮)
        if (emsTotalNum % emsNumOfEveryFragment == 0) {
            fragmentNum = emsTotalNum / emsNumOfEveryFragment;
        } else {
            fragmentNum = (emsTotalNum / emsNumOfEveryFragment) + 1;
        }
        EmotionAdapter mViewPagerAdapter = new EmotionAdapter(getSupportFragmentManager(), fragmentNum);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);

        GlobalOnItemClickManager globalOnItemClickListener = GlobalOnItemClickManager.getInstance();
        globalOnItemClickListener.attachToEditText((EditText) findViewById(R.id.edit_text));

		/* 设置表情下的提示点 */
        setUpTipPoints(fragmentNum, mViewPager);
    }

    /* 设置扩展布局下的视图 */
    private void setUpExtendView() {
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        RlvAdapter rlvAdapter = new RlvAdapter(RlvItemContent.getItemList(), this);
        mRlvLayout.setAdapter(rlvAdapter);
        mRlvLayout.setLayoutManager(manager);
        rlvAdapter.setItemlistener(new RlvAdapter.OnRecyclerViewItemlistener() {
            @Override
            public void OnItemListener(View view, String data) {
                if (data.equals(getString(R.string.photo))) {//图片
                    Intent intent = new Intent(MainActivity.this, SelectPhotoActivity.class);
                    startActivityForResult(intent, REQUESTCODE);
                } else if (data.equals(getString(R.string.here))) {//位置
                    Toast.makeText(MainActivity.this, "位置", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /**
     * @param num 提示点的数量
     */
    private void setUpTipPoints(int num, ViewPager mViewPager) {
        rgTipPoints = (RadioGroup) findViewById(R.id.rg_reply_layout);
        for (int i = 0; i < num; i++) {
            rbPoint = new RadioButton(this);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(30, 30);
            lp.setMargins(10, 0, 10, 0);
            rbPoint.setLayoutParams(lp);
            rbPoint.setId(i);//为每个RadioButton设置标记
            rbPoint.setButtonDrawable(getResources().getDrawable(R.color.transparent));//设置button为@null
            rbPoint.setBackgroundResource(R.drawable.emotion_tip_points_selector);
            rbPoint.setClickable(false);
            if (i == 0) { // 第一个点默认为选中，与其他点显示颜色不同
                rbPoint.setChecked(true);
            } else {
                rbPoint.setChecked(false);
            }
            rgTipPoints.addView(rbPoint);
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                rgTipPoints.check(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!emotionKeyboard.interceptBackPress()) {
            super.onBackPressed();
        }
    }

    /* 获取assets下某个指定文件夹下的文件数量 */
    private int getSizeOfAssetsCertainFolder(String folderName) {
        int size = 0;
        try {
            size = getAssets().list(folderName).length;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String trim = edittext.getText().toString().trim();
                        ChatMsg chatMsg = new ChatMsg(trim, 0);
                        lists.add(chatMsg);
                        mRlvChatMsgAdapter.notifyItemInserted(lists.size() - 1);//有消息是显示
                        //刷新数据
                        mRlvMainChat.smoothScrollToPosition(lists.size() - 1);//将消息放在rcyclerview
                        edittext.setText("");
                    }
                });


                break;
            default:
                break;
        }
    }


    /* EditText输入框动态监听 */
    class ButtonBtnWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(edittext.getText().toString())) { //有文本内容，按钮为可点击状态
                btnSend.setBackgroundResource(R.drawable.shape_button_reply_button_clickable);
                btnSend.setTextColor(getResources().getColor(R.color.light_white));
            } else { // 无文本内容，按钮为不可点击状态
                btnSend.setBackgroundResource(R.drawable.shape_button_reply_button_unclickable);
                btnSend.setTextColor(getResources().getColor(R.color.reply_button_text_disable));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULTCODE) {
            ArrayList<String> path = data.getStringArrayListExtra("path");
            handlPictures(path);

            Log.d("回调结果", "===" + path.size());
        }
    }

    private void handlPictures(ArrayList<String> path) {
        if (path.size() == 1) {
            ChatMsg chatMsg = new ChatMsg(path.get(0), 1);
            lists.add(chatMsg);
            mRlvChatMsgAdapter.notifyItemInserted(lists.size() - 1);//有消息是显示
            //刷新数据
            mRlvMainChat.smoothScrollToPosition(lists.size() - 1);//将消息放在rcyclerview
        }

    }
}

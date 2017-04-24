package com.lawyee.mychatapp.viewpager;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.lawyee.mychatapp.util.SpanStringUtils;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  GlobalOnItemClickManager.java
 * @Package com.lawyee.mychatapp.viewpager
 * @Description:    表情匹配界面
 * @author: YFL
 * @date:   2017/4/20 9:19
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/4/20 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class GlobalOnItemClickManager {

	private static GlobalOnItemClickManager instance;
	private EditText mEditText;

	public static GlobalOnItemClickManager getInstance() {
		if (instance == null) {
			instance = new GlobalOnItemClickManager();
		}
		return instance;
	}

	public void attachToEditText(EditText editText) {
		mEditText = editText;
	}


	public AdapterView.OnItemClickListener getOnItemClickListener(final int emsPos, final Context context) {
		return new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				/* 判断是不是“删除”按钮
				 *      是   -> 删除上一个表情
				 *      不是 -> 加载相应的表情*/
				int emotionNum = position + emsPos*20;//表情编号
				if(position != 20){
					int index = mEditText.getSelectionStart();//当前光标位置
					String emotionName = "[s:" + String.valueOf(emotionNum) + "]";
					String currentContent = mEditText.getText().toString();
					StringBuilder sb = new StringBuilder(currentContent);
					sb.insert(index, emotionName);
					mEditText.setText(SpanStringUtils.getEmotionContent(context, mEditText, sb.toString()));
					mEditText.setSelection(index + emotionName.length());
				} else {
					mEditText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
				}
			}
		};
	}

}

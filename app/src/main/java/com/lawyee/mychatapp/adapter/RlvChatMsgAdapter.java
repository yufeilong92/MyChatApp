package com.lawyee.mychatapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lawyee.mychatapp.R;
import com.lawyee.mychatapp.bean.ChatMsg;
import com.lawyee.mychatapp.ui.ImageUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: MyChatApp
 * @Package com.lawyee.mychatapp.adapter
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/4/20 11:47
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class RlvChatMsgAdapter extends RecyclerView.Adapter<RlvChatMsgAdapter.ViewHolder> implements View.OnClickListener {

    private List<ChatMsg> mData;
    private Context mContext;

    private final LayoutInflater mInflater;

    public RlvChatMsgAdapter(List<ChatMsg> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    private interface setOnRecyclerViewItemListener {
        void setOnItemListener(View view, ChatMsg msg, int position);
    }

    public static final setOnRecyclerViewItemListener onItemListener = null;

    public static setOnRecyclerViewItemListener getOnItemListener() {
        return onItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_rlv_chat, null);
        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(mData.get(i));
        view.setId(i);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ChatMsg chatMsg = mData.get(i);
        int type = chatMsg.getType();
        switch (type) {
            case 0://只显示文字
                viewHolder.mTvChatShowCon.setVisibility(View.VISIBLE);
                viewHolder.mIvChatShowPic.setVisibility(View.GONE);
                viewHolder.mTvChatShowCon.setText(chatMsg.getContent());

                break;
            case 1://单张图片

                // TODO: 2017/4/20 加载本地图片进行处理
                viewHolder.mTvChatShowCon.setVisibility(View.GONE);
                viewHolder.mIvChatShowPic.setVisibility(View.VISIBLE);
                String newPath = ImageUtil.bitampToString(mContext, chatMsg.getContent(), ""+ i);
                Bitmap bitmap = ImageUtil.getloadPicBitmap(newPath);
                viewHolder.mIvChatShowPic.setImageBitmap(bitmap);
                break;
            default:
                break;
        }


    }

    private static Bitmap getLoacalBitmap(String url){
        try {
            FileInputStream inputStream = new FileInputStream(url);
               return BitmapFactory.decodeStream(inputStream);///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }


    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemListener != null) {
            onItemListener.setOnItemListener(v, (ChatMsg) v.getTag(), v.getId());
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvChatShowCon;
        private ImageView mIvChatShowPic;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvChatShowCon = (TextView) itemView.findViewById(R.id.tv_chat_showCon);
            mIvChatShowPic = (ImageView) itemView.findViewById(R.id.iv_chat_showPic);
        }
    }
}

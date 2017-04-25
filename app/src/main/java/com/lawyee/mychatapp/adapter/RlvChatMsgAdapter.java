package com.lawyee.mychatapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lawyee.mychatapp.R;
import com.lawyee.mychatapp.bean.ChatMsg;
import com.lawyee.mychatapp.ui.ImageUtil;
import com.lawyee.mychatapp.util.SpanStringUtils;
import com.squareup.picasso.Picasso;

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

    public void setOnItemListener(OnRecyclerViewItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    private OnRecyclerViewItemListener onItemListener = null;


    public RlvChatMsgAdapter(List<ChatMsg> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public static interface OnRecyclerViewItemListener {
        void setOnItemListener(View view, ChatMsg msg, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_rlv_chat, null);
        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ChatMsg chatMsg = mData.get(i);
        viewHolder.itemView.setTag(chatMsg);
        viewHolder.itemView.setId(i);
        int type = chatMsg.getType();

        switch (type) {
            case 0://只显示文字
                viewHolder.mIvShowHear.setVisibility(View.VISIBLE);
                viewHolder.mShowMapLl.setVisibility(View.GONE);
                viewHolder.mTvChatShowCon.setVisibility(View.VISIBLE);
                viewHolder.mIvChatShowPic.setVisibility(View.GONE);
                //匹配相应表情
                SpannableString emotionContent = SpanStringUtils.getEmotionContent(mContext, viewHolder.mTvChatShowCon, chatMsg.getContent());
                viewHolder.mTvChatShowCon.setText(emotionContent);

                break;
            case 1://单张图片
                // TODO: 2017/4/20 加载本地图片进行处理
                viewHolder.mIvShowHear.setVisibility(View.VISIBLE);
                viewHolder.mShowMapLl.setVisibility(View.GONE);
                viewHolder.mTvChatShowCon.setVisibility(View.GONE);
                viewHolder.mIvChatShowPic.setVisibility(View.VISIBLE);
                String newPath = ImageUtil.bitampToString(mContext, chatMsg.getContent(), "" + i);
                Bitmap bitmap = ImageUtil.getloadPicBitmap(newPath);
                viewHolder.mIvChatShowPic.setImageBitmap(bitmap);
                break;
            case 2://地图
                viewHolder.mIvShowHear.setVisibility(View.VISIBLE);
                viewHolder.mIvChatShowPic.setVisibility(View.GONE);
                viewHolder.mTvChatShowCon.setVisibility(View.GONE);
                viewHolder.mShowMapLl.setVisibility(View.VISIBLE);
                viewHolder.mTvShowMapTitle.setText(chatMsg.getTitle());

                Log.d("路径", "" + "http://api.map.baidu.com/staticimage?center=" + chatMsg.getLongitude() + "%2C" + chatMsg.getLatitude()
                        + "&zoom=19&width=240&height=160&markers=" + chatMsg.getLongitude() + "%2C" + chatMsg.getLatitude() + "&markerStyles=l%2CA&qq-pf-to=pcqq.c2c");
                Picasso.with(mContext).load("http://api.map.baidu.com/staticimage?center=" + chatMsg.getLongitude() + "%2C" + chatMsg.getLatitude()
                        + "&zoom=19&width=240&height=160&markers=" + chatMsg.getLongitude() + "%2C" + chatMsg.getLatitude() + "&markerStyles=l%2CA&qq-pf-to=pcqq.c2c").
                        into(viewHolder.mIvShowMapPic);
                break;
            default:
                break;
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
        private TextView mTvShowMapTitle;
        private ImageView mIvShowMapPic;
        private LinearLayout mShowMapLl;
        private ImageView mIvShowHear;


        public ViewHolder(View itemView) {
            super(itemView);
            mIvShowHear=(ImageView)itemView.findViewById(R.id.iv_showHear);
            mIvShowMapPic = (ImageView) itemView.findViewById(R.id.iv_showMapPic);
            mTvShowMapTitle = (TextView) itemView.findViewById(R.id.tv_showMapTitle);
            mShowMapLl = (LinearLayout) itemView.findViewById(R.id.showMapLl);
            mTvChatShowCon = (TextView) itemView.findViewById(R.id.tv_chat_showCon);
            mIvChatShowPic = (ImageView) itemView.findViewById(R.id.iv_chat_showPic);
        }
    }
}

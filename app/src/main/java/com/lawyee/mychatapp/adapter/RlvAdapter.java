package com.lawyee.mychatapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lawyee.mychatapp.R;
import com.lawyee.mychatapp.bean.RlvItemContent;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: MyChatApp
 * @Package com.lawyee.mychatapp.adapter
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/4/20 10:18
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class RlvAdapter extends RecyclerView.Adapter<RlvAdapter.ViewHodler> implements View.OnClickListener {

    private List<RlvItemContent.itemData> mData;
    private Context mContext;
    private final LayoutInflater mInflater;
    private  OnRecyclerViewItemlistener itemlistener=null;

    public RlvAdapter(List<RlvItemContent.itemData> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public void onClick(View v) {
        if (itemlistener!=null){
            itemlistener.OnItemListener(v, (String) v.getTag());
        }
    }

    public interface OnRecyclerViewItemlistener {
        void OnItemListener(View view, String data);
    }


    public void setItemlistener(OnRecyclerViewItemlistener itemlistener) {
        this.itemlistener = itemlistener;
    }


    @Override
    public ViewHodler onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_rlv_picture, null);
        view.setOnClickListener(this);
        ViewHodler hodler = new ViewHodler(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(ViewHodler viewHodler, int i) {
        viewHodler.mTvShowName.setText(mData.get(i).getName());
        viewHodler.mIvShowPicture.setImageResource(mData.get(i).getId());
        viewHodler.itemView.setTag(mData.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        private ImageView mIvShowPicture;
        private TextView mTvShowName;

        public ViewHodler(View itemView) {
            super(itemView);
            mIvShowPicture = (ImageView) itemView.findViewById(R.id.iv_showPicture);
            mTvShowName = (TextView) itemView.findViewById(R.id.tv_showName);
        }
    }
}

package com.lawyee.mychatapp.viewpager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * All rights Reserved, Designed By www.lawyee.com
 * @Title:  EmotionAdapter.java 
 * @Package com.lawyee.mychatapp.viewpager   
 * @Description:    表情展示界面适配器
 * @author: YFL
 * @date:   2017/4/20 9:25
 * @version V 1.0 xxxxxxxx
 * @verdescript  版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017/4/20 www.lawyee.com Inc. All rights reserved. 
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */  


public class EmotionAdapter extends FragmentStatePagerAdapter {
    private SparseArray<Fragment> mPages;

    private int fragmentNum;

    public EmotionAdapter(FragmentManager fm, int num) {
        super(fm);
        mPages = new SparseArray<>();
        fragmentNum = num;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = EmotionFragment.newInstance(position*20);
        mPages.put(position, f);
        return f;
    }

    @Override
    public int getCount() {
        return fragmentNum;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (0 <= mPages.indexOfKey(position)) {
            mPages.remove(position);
        }
        super.destroyItem(container, position, object);
    }
}

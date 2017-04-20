package com.lawyee.mychatapp.bean;

import com.lawyee.mychatapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: MyChatApp
 * @Package com.lawyee.mychatapp.bean
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/4/20 10:21
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class RlvItemContent {
    public static final List<itemData> getItemList() {
        ArrayList<itemData> lists = new ArrayList<>();
        itemData itemData = new itemData();
        itemData.setName("照片");
        itemData.setId(R.drawable.ic_insert_photo);
        lists.add(itemData);
        itemData itemData1 = new itemData();
         itemData1.setName("位置");
        itemData1.setId(R.drawable.ic_place);
        lists.add(itemData1);
        return lists;
    }


    public static class itemData {
        String name;
        int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}

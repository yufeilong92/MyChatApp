package com.lawyee.mychatapp.bean;

import java.util.List;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: MyChatApp
 * @Package com.lawyee.mychatapp.bean
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/4/20 11:43
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class ChatMsg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private String content;
    private int type;
    private String picturePath;
    private int picture;
    private List<String> paths;

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    /**
     * @param content 发送内容
     * @param type    发送标示（区别是否有图片0 为文字 ，1为图片）
     * @param picture 图片的路径
     */
    public ChatMsg(String content, int type, int picture) {
        this.content = content;
        this.type = type;
        this.picture = picture;
    }

    /**
     * @param content 内容
     * @param type    判断类型 0位内容 1 为 图片
     */
    public ChatMsg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    /**
     * @param picturePahts 图片集合
     * @param type         判断类型 0位内容 1 为 图片
     */
    public ChatMsg(List<String> picturePahts, int type) {
        this.paths = picturePahts;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}

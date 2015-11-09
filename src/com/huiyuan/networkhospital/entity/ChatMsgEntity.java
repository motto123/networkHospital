package com.huiyuan.networkhospital.entity;

public class ChatMsgEntity {

    public String guid;
    public String taskId;
    public String taskName;
    public int groupId;
    public int sourceId;
    public String sourceName;
    public int type;
    public String content;
    public int length;
    public long date;
    public String longitude;
    public String latitude;
    public String icon;
    public int unread;
    public int listen;
    public int sendState;
    public String taskWarningId; //当消息是任务警告时，使用此taskID
    public String newTaskId;
    public int taskState;

}

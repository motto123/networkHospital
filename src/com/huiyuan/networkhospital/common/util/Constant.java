package com.huiyuan.networkhospital.common.util;


public class Constant {

    /**
     * 发送类型 1-个人 2-群 3-系统
     */
    public final static int SEND_TYPE_CONTACT = 0;
    public final static int SEND_TYPE_GROUP = 1;
    public final static int SEND_TYPE_SYSTEM = 2;

    /**
     * 消息类型 1-文本 2-声音 3-图片
     */
    public final static int MSG_TYPE_UNKNOW = 0;
    public final static int MSG_TYPE_TEXT = 1;
    public final static int MSG_TYPE_VOICE = 3;
    public final static int MSG_TYPE_PICTURE = 2;
    public final static int MSG_TYPE_VIDEO = 4;
    public final static int MSG_TYPE_MAP = 5;
    public final static int MSG_TYPE_JPG = 6;
    public final static int MSG_TYPE_PNG = 7;
    public final static int MSG_TYPE_MP3 = 8;

    /**
     * 发送状态 0-发送失败 1-发送成功 2-已送达 3-正在发送
     */
    public final static int MSG_STATE_FAIL = 0;
    public final static int MSG_STATE_SUCCESS = 1;
    public final static int MSG_STATE_REACH = 2;
    public final static int MSG_STATE_PROGRESS = 3;

    /**
     * 聊天状态 0-文字 1-语音
     */
    public final static int CHAT_MODE_TEXT = 0;
    public final static int CHAT_MODE_VOICE = 1;

    /**
     * 消息状态 0-未读 1-已读
     */
    public final static int MESSAGE_UNREAD = 0;
    public final static int MESSAGE_READ = 1;

    /**
     * 任务处理结果  0-未处理 1-已处理
     */
    public final static int TASK_HANDLE_N = 0;
    public final static int TASK_HANDLE_Y = 1;

    /** 使用照相机拍照获取图片 */
    public static final int SELECT_BY_TACK_PHOTO = 1;
    /** 使用相册中的图片 */
    public static final int SELECT_BY_PICK_PICTURE = 2;
    /** 拍摄视频 */
    public static final int SELECT_BY_TAKE_VIDEO = 3;

    /**
     * 语音消息状态 0-未听 1-已听
     */
    public final static int MESSAGE_UNLISTEN = 0;
    public final static int MESSAGE_LISTEN = 1;
    /**
     * Group的TaskId -1 服务中心TaskId 0
     */
    public final static String TASKID_GROUP = "-1";
    public final static String TASKID_SERVER_CENTER = "0";

    /**
     * 启动登陆界面的返回码
     */
    public final static String RESULT_EXIT = "0";
    public final static String RESULT_LOGIN_SUCCESS = "1";
    public final static String RESULT_LOGIN_FAIL = "1";

    /**
     * 签到类型
     */
    public final static int SIGN_WORK = 1;
    public final static int SIGN_METTING = 2;
    public final static int SIGN_MEAL = 3;
    /**
     * 诉求类型，历史 Or　新增
     */
    public final static int New_REQUIREMENT = 0;//新增
    public final static int HISTORY_REQUIREMENT = 1;//历史记录

    /**
     * FunctionType
     */
    public final static int FUNCTION_GOVERNMENT = 1;
    public final static int FUNCTION_HUMANITY = 2;
    public final static int FUNCTION_LOVE = 3;

    /**
     * 更新消息
     */
    public final static int MSG_HAS_NEW_UPDATA = 1000;
    public final static int MSG_HAS_NO_UPDATA = 1001;
    public final static int MSG_CHECK_FAIL = 1002;
	    /**
     * 实时视频类型
     */
    public final static int VIDEO_REALATIME = 0;
    public final static int VIDEO_PREVIEW = 1;
    /**
     * 视频预览注册
     */
    public final static int REGISTER = 1;
    public final static int REGISTER_CANCLE = 2;
    
    /**
     * 
     * 登录状态
     */
    public final static int LOGINSTATE_CHANGED = 9000;
    /**
     * 任务状态 
     * 
     */
    public final static int TASKSTATE_DELIVER = 1; //下发
    public final static int TASKSTATE_DELET = 2; //删除
    public final static int TASKSTATE_HANDLE = 3; //处理

    /**
     * 签到
     */
    public final static int FUNCTION_SIGN = 1;
    /**
     * 实时沟通
     */
    public final static int FUNCTION_COMMUNICATE = 2;
    /**
     * 政务公开
     */
    public final static int FUNCTION_GOVERNMENT_OPEN = 3;
    /**
     * 民情记录
     */
    public final static int FUNCTION_PEOPLE = 4;
    /**
     * 人文汪家
     */
    public final static int FUNCTION_HUMANITIES = 5;
    /**
     * 资料下载
     */
    public final static int FUNCTION_DOWNLOAD = 6;
    /**
     * 巡更签到
     */
    public final static int FUNCTION_PATROL = 7;
    /**
     * 便民服务
     */
    public final static int FUNCTION_CONVENIENCE = 8;
    /**
     * 汪家简介
     */
    public final static int FUNCTION_INTRODUCTION = 9;
    /**
     * 爱心援助
     */
    public final static int FUNCTION_LOVE_RESCUE = 10;
    /**
     * 更多
     */
    public final static int FUNCTION_MORE = 11;
    /**
     * 党建领航
     */
    public final static int FUNCTION_PARTY = 12;
    /**
     * 活动展示
     */
    public final static int FUNCTION_ACTIVITY = 13;
    /**
     * 个人中心
     */
    public final static int FUNCTION_PERSONAL = 14;

    /**
     * 默认的显示模块
     */
    public final static int defaultModulars[] = {
            FUNCTION_SIGN,
            FUNCTION_COMMUNICATE,
            FUNCTION_INTRODUCTION,
            FUNCTION_GOVERNMENT_OPEN,
            FUNCTION_LOVE_RESCUE,
            FUNCTION_HUMANITIES,
            FUNCTION_CONVENIENCE,
            FUNCTION_PARTY,
            FUNCTION_ACTIVITY,
            FUNCTION_PERSONAL };

    /**
     * 所有的模块
     * 0：app_key
     * 1：图标
     * 2：文字
     */
    public final static int APP_KEY[][] = {
            { FUNCTION_SIGN,
              FUNCTION_COMMUNICATE,
              FUNCTION_LOVE_RESCUE,
              FUNCTION_PEOPLE,
              FUNCTION_HUMANITIES,
              FUNCTION_DOWNLOAD,
              FUNCTION_PATROL,
              FUNCTION_CONVENIENCE,
              FUNCTION_INTRODUCTION,
              FUNCTION_MORE,
              FUNCTION_PARTY,
              FUNCTION_ACTIVITY,
              FUNCTION_PERSONAL,
              FUNCTION_GOVERNMENT_OPEN },
            { 
//              0,//签到
//              0,//实时沟通
//              R.drawable.grid_item_3,
//              R.drawable.grid_item_4,
//              R.drawable.grid_item_5,
//              R.drawable.grid_item_6,
//              R.drawable.grid_item_7,
//              R.drawable.grid_item_8,
//              R.drawable.grid_item_1,
//              R.drawable.grid_item_9,
//              R.drawable.grid_item_party,
//              R.drawable.grid_item_activity,
//              R.drawable.grid_item_user,
//              R.drawable.grid_item_2 
              },
            { 
//              0,//签到
//              0,//实时沟通
//              R.string.main_grid_text3,
//              R.string.main_grid_text4,
//              R.string.main_grid_text5,
//              R.string.main_grid_text6,
//              R.string.main_grid_text7,
//              R.string.main_grid_text8,
//              R.string.navi_introduce,
//              R.string.main_grid_text9,
//              R.string.main_grid_party,
//              R.string.main_grid_activity,
//              R.string.main_grid_personal,
//              R.string.main_grid_text2 
              }
           };
}

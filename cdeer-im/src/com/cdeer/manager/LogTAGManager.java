package com.cdeer.manager;

/**
 * 日志标签管理器
 * 
 * @author jacklin
 * 
 */
public class LogTAGManager {

	public static final String LOG_SEPARATE = " - ";// 日志分隔符

	public static final String LOG_SEPARATE_PARAMS = "|";// 日志参数分隔符

	public static final String SERVER_INFO = "server_info" + LOG_SEPARATE;// 服务器信息

	// =============================客户端===========================================
	public static final String CLIENT_SERVER_INFO = "client_server_info"
			+ LOG_SEPARATE;// 客户端服务器信息

	public static final String CLIENT_CONNECTED = "client_connected"
			+ LOG_SEPARATE;// 客户端建立连接

	public static final String CLIENT_DISCONNECTED = "client_disconnected"
			+ LOG_SEPARATE;// 客户端断开连接

	public static final String CLIENT_IDLE_TIMEOUT = "client_idle_timeout"
			+ LOG_SEPARATE;// 客户端空闲超时

	public static final String CLIENT_ILLEGAL = "client_Illegal" + LOG_SEPARATE;// 客户端非法

	public static final String CLIENT_UNKNOWN_EVENT = "client_unknown_event"
			+ LOG_SEPARATE;// 客户端不识别的事件

	public static final String CLIENT_ERROR = "client_error" + LOG_SEPARATE;// 客户端错误

	public static final String CLIENT_NO_HANDLER = "client_no_handler"
			+ LOG_SEPARATE;// 没有对应的处理器

	public static final String CLIENT_LOGIN_INFO = "client_login_info"
			+ LOG_SEPARATE;// 客户端登录信息

	public static final String CLIENT_LOGIN_SUCCESS = "client_login_success"
			+ LOG_SEPARATE;// 客户端登录成功

	public static final String CLIENT_LOGOUT = "client_logout" + LOG_SEPARATE;// 客户端登出

	public static final String CLIENT_MSG_TYPING = "client_msg_typing"
			+ LOG_SEPARATE;// 正在输入

	public static final String CLIENT_MSG_READ = "client_msg_read"
			+ LOG_SEPARATE;// 已读消息

	public static final String CLIENT_MSG_SEND = "client_msg_send"
			+ LOG_SEPARATE;// 向客户端发送消息

	public static final String CLIENT_MSG_STORAGE = "client_msg_storage"
			+ LOG_SEPARATE;// 消息存储

	public static final String CLIENT_MSG_SEND_LOOP = "client_msg_send_loop"
			+ LOG_SEPARATE;// 循环发送管理器向客户端发送消息

	public static final String CLIENT_MSG_STORAGE_LOOP = "client_msg_storage_loop"
			+ LOG_SEPARATE;// 循环发送管理器消息存储

	public static final String CLIENT_MSG_RECV = "client_msg_recv"
			+ LOG_SEPARATE;// 收到客户端消息

	public static final String CLIENT_MSG_PROCESSED = "client_msg_processed"
			+ LOG_SEPARATE;// 消息已经处理

	public static final String CLIENT_MSG_ACK_SEND = "client_msg_ack_send"
			+ LOG_SEPARATE;// 向客户端发送回执

	public static final String CLIENT_MSG_ACK_RECV = "client_msg_ack_recv"
			+ LOG_SEPARATE;// 收到客户端的回执

	public static final String CLIENT_MSG_DROP = "client_msg_drop"
			+ LOG_SEPARATE;// 舍弃消息

	public static final String CLIENT_MSG_OFFLINE_COUNT = "client_msg_offline_count"
			+ LOG_SEPARATE;// 客户端离线消息数量

	public static final String CLIENT_APPLE_PUSH = "client_apple_push"
			+ LOG_SEPARATE;// 苹果推送

	public static final String CLIENT_APPLE_PUSH_ERROR = "client_apple_push_error"
			+ LOG_SEPARATE;// 苹果推送错误

	public static final String CLIENT_LOOP_MANAGER = "client_loop_manager"
			+ LOG_SEPARATE;// 循环发送管理器

	public static final String CLIENT_LOCATION_IM = "client_location_im"
			+ LOG_SEPARATE;// 收到客户端上传的经纬度

	public static final String CLIENT_LOCATION_NOTIFY = "client_location_notify"
			+ LOG_SEPARATE;// 发送上传经纬度通知

	public static final String IM_MSG_STATS = "im_msg_stats" + LOG_SEPARATE;// IM消息统计

	// ==========================外部调用接口============================================

	public static final String EXTERNAL_SERVER_INFO = "external_server_info"
			+ LOG_SEPARATE;// 外部调用接口服务器信息

	public static final String EXTERNAL_CONNECTED = "external_connected"
			+ LOG_SEPARATE;// 外部调用接口建立连接

	public static final String EXTERNAL_DISCONNECTED = "external_disconnected"
			+ LOG_SEPARATE;// 外部调用接口断开连接

	public static final String EXTERNAL_IDLE_TIMEOUT = "external_idle_timeout"
			+ LOG_SEPARATE;// 外部调用接口空闲超时

	public static final String EXTERNAL_UNKNOWN_EVENT = "external_unknown_event"
			+ LOG_SEPARATE;// 外部调用接口不识别的事件

	public static final String EXTERNAL_MSG_RECV = "external_msg_recv"
			+ LOG_SEPARATE;// 收到外部接口消息

	public static final String EXTERNAL_MSG_SEND = "external_msg_send"
			+ LOG_SEPARATE;// 外部调用接口发送消息

	public static final String EXTERNAL_MSG_STORAGE = "external_msg_storage"
			+ LOG_SEPARATE;// 外部调用接口存储消息

	public static final String EXTERNAL_MSG_CANCEL = "external_msg_cancel"
			+ LOG_SEPARATE;// 外部调用接口撤销消息

	public static final String EXTERNAL_ERROR = "external_error" + LOG_SEPARATE;// 收到外部接口错误

	public static final String EXTERNAL_RESULT = "external_result"
			+ LOG_SEPARATE;// 收到外部接口返回

	// ==========================控制台============================================

	public static final String CONSOLE_INFO = "console_info" + LOG_SEPARATE;// 控制台信息

	public static final String CONSOLE_CONNECTED = "console_connected"
			+ LOG_SEPARATE;// 控制台建立连接

	public static final String CONSOLE_DISCONNECTED = "console_disconnected"
			+ LOG_SEPARATE;// 控制台断开连接

	public static final String CONSOLE_IDLE_TIMEOUT = "console_idle_timeout"
			+ LOG_SEPARATE;// 控制台空闲超时

	public static final String CONSOLE_UNKNOWN_EVENT = "console_unknown_event"
			+ LOG_SEPARATE;// 控制台不识别的事件

	public static final String CONSOLE_MSG_RECV = "console_msg_recv"
			+ LOG_SEPARATE;// 收到控制台消息

	public static final String CONSOLE_MSG_SEND = "console_msg_send"
			+ LOG_SEPARATE;// 控制台发送消息

}

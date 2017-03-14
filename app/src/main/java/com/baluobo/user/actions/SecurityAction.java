package com.baluobo.user.actions;

import com.baluobo.app.flux.Action;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/7/14.
 */
public class SecurityAction extends Action {
    //验证手势密码的类型
    public static final String SECURITY_GESTURE_VERIFY_ACTION = "security_gesture_verify_action";
    //打开应用时验证
    public static final String GESTURE_VERIFY_ACTION_OPEN = "gesture_verify_action_open";
    //修改手势密码时验证
    public static final String GESTURE_VERIFY_ACTION_MODIFY = "gesture_verify_action_modify";
    //关闭手势密码时验证
    public static final String GESTURE_VERIFY_ACTION_CLOSE = "gesture_verify_action_close";
    //按HOME键密码验证
    public static final String GESTURE_VERIFY_ACTION_HOME = "gesture_verify_action_home";

    //修改手势密码类型
    public static final String SECURITY_GESTURE_EDIT_ACTION = "security_gesture_edit_action";
    //开启手势密码时设置
    public static final String GESTURE_MODIFY_NEW = "gesture_modify_new";
    //修改手势密码时设置
    public static final String GESTURE_MODIFY_CHANGE = "gesture_modify_change";
    //打开应用时忘记密码时重新设置
    public static final String GESTURE_MODIFY_RESET = "gesture_modify_reset";
    //按HOME键后恢复时设置
    public static final String GESTURE_MODIFY_HOME = "gesture_modify_home";

    public static final String RECEIVER_ACTION_DISMISS_GESTURE_DIALOG = "receiver_action_dismiss_gesture_dialog";
    public static final String ACTION_RESULT_START = "modify_gesture_action_start";
    public static final String ACTION_RESULT_FINISH = "modify_gesture_action_finish";
    public static final String ACTION_RESULT_SUCCESS = "modify_gesture_action_success";
    public static final String ACTION_RESULT_FAIL = "modify_gesture_action_fail";
    public static final String ACTION_RESULT_VALID_TOKEN = "modify_gesture_action_valid_token";
    public static final String ACTION_RESULT_ERROR_MESSAGE = "modify_gesture_action_error_message";

    public SecurityAction(String type, Object data) {
        super(type, data);
    }

    public SecurityAction(String type) {
        super(type);
    }
}

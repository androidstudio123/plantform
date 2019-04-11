package cn.crm.result;


public enum ResultCode {

    SUCCESS(20000,"执行成功!"), //执行成功
    ERROR(20001,"执行错误!"),   //执行错误
    RETRY(20002,"网络异常"),   //网络异常
    FORBIDDEN(20003,"权限不足"), //权限不足
    ALREADYEXISTS(20004,"已经存在此信息!"),//权限不足
    EMPTYPARAMS(20005,"参数为空,执行错误!"), //权限不足
    LOGINERROR_FREEZE(20006,"用户已经被冻结不能登录，请与管理员联系！"),
    LOGINERROR_LOCK(20007,"登录失败次数过多,锁定10分钟！"),
    LOGINERROR_NONENTITY(20008,"用户名或密码错误,请与管理员联系！"),
    FOREIGNNODEERROR(20010,"此节点存在下级,不能完成删除!"),
    ADMINADD_ERROR(20018,"操作失败,不能选择自身成为所属父级!"),
    TYPE_MISMATCH(20011,"上传的文件类型不是图片类型，不能完成上传!"),
    SEEKSDD_FAILED(20012,"网络异常,定位信息失败,请重新定位或手动选择学校信息!"),
    ERROR_RESOURCE(20013,"资源正在使用中,请稍后重试!"),

    ROOM_DELETE_ERROR(20019,"删除失败，房间授权的有用户！"),

    IMEIORSN_EMPTY(20020,"IMEI号或者SN号不能为空！"),
    IMEI_EXIST(20021,"IMEI已经存在！"),


    OUTDATE(20009,"身份登录过期，请重新登录");   //网络异常


    private int code;
    private String message;

    ResultCode(int code,String message) {
        this.code = code;
        this.message = message;
    }

    public void setVale(int code,String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

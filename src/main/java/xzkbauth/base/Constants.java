package xzkbauth.base;

public interface Constants {
  // 用户会话key
  public static final String AUTHENTICATION_KEY = "user";
  // 用户会话pro
  public static final String AUTHENTICATION_prjcod = "prjcodSession";
  // 用户会话key
  public static final String TOKEN_KEY = "TOKEN";
  // 公共企业号
  public static final String PUBLIC_PROJECT_CODE = "00000000";

  // 用户资源权限key
  //	public static final String USERACTION_KEY = "SESSION_USERACTION_KEY";
  // 在线统计
  public static final String USERS_ONLINE = "onlineUserBindingListener";
  public static final String _onlineUserList = "onlineUserList";
  // 用户数据权限集合
  // String USERDATAAUTH_KEY = "SESSION_USERDATAAUTH_KEY";
  // 管理员
  public static final String SYSTEM_ADMIN = "admin";
  public static final String MESSAGE_KEY = "MESSAGKE";
  public static final String NEWSTYPE = "NEWS"; // 内部新闻
  public static final String NEWSTYPE2 = "OTHER"; // 外部新闻

  public static final String CONTXT = "MYCONTEXT";
  public static final String UPLOADFILEIDS = "uploadFileIDS";
  public static final String DATATUTH_INCLUDE = "0";
  public static final String DATAAUTH_UNCLUDE = "1";

  public static final String SEQUENCE_RES = "RES_SEQ";

  public static final String SEQUENCE_MSG = "MSG_SEQ";
  public static final String SEQUENCE_PL = "PL_SEQ";
  public static final String SEQUENCE_ZAN = "ZAN_SEQ";
  public static final String SEQUENCE_PRJCOD = "MSG_PRJ";
  public static final String SEQUENCE_NOTICE = "NOTICE_SEQ";
  public static final String MONGODB_COLLECTION_MSG = "MSG";
  public static final String MONGODB_COLLECTION_NOTICE = "NOTICE";
  public static final String MONGODB_COLLECTION_PL = "PL";
  public static final String MONGODB_COLLECTION_ZAN = "ZAN";
  public static final String CACHE_RES = "RESOURCE_KEY_1";

  public static final String ADVICE_STATE_ON = "1"; // 1问题开启 0问题关闭
  public static final String ADVICE_STATE_OFF = "0"; // 1问题开启 0问题关闭

  public static final String FILEPICETRUE = "FILEPICETRUE"; // 圖片按照480 800比例等比縮放

  public static final String ThradPoolBeanId = "threadPool"; // 放spring上下文的bean id
  //	public static final String AUTHENTICATION_TOKEN = "SESSION_AUTHENTICATION_TOKEN";
  //
  //	public static final String CONFIG_FILE_KEY = "app.config.file";
  //
  //	public static final String DEFAULT_CONFIG_FILE = "app-config.xml";
  //
  //	public static final String SECURITY_CONTEXT_HOLDER_MODE_KEY =
  // "app.security.context.holder.mode";
  //
  //	public static final String STANDARD_PASSPORT_CLASS =
  // "dwz.framework.core.passport.StandardPassport";

  //	public static final String X509_PASSPORT_CLASS = "dwz.framework.core.passport.X509Passport";

  //	String AUTHENTICATOR_KEY = "passport.authentication-service.authenticator-class";
  //
  //	String DEFAULT_AUTHENTICATOR_CLASS = "dwz.framework.core.authentication.AuthenticatorImpl";
  //
  //	String CREDENTIAL_STORE_CLASS_KEY = "passport.authentication-service.credential-store";
  //
  //	String DB_CREDENTIAL_STORE_CLASS =
  // "dwz.framework.core.authentication.store.db.DbCredentialStore";
  //
  //	String PRINCIPAL_STORE_KEY = "passport.authentication-service.principal-store";
  //
  //	String DB_PRINCIPAL_STORE_CLASS = "dwz.framework.core.authorization.store.db.DbPrincipalStore";
  //
  //	String AUTH_SCHEMAS_KEY = "app.authentication.schemas";
  //
  //	String PRINCIPAL_OPERATION_CACHE_KEY = "Acl Of Principal And Operation";
  //
  //	String AUTHENTICATION_OPERATION_CACHE_KEY = "Acls Of Authentication And Operation";
  //
  //	String OPERATION_NEEDED_CACHE_KEY = "Permissions Of Operation Needed";
  //
  //	int ACL_OF_PRINCIPAL_IN_CACHE = 10;
  //
  //	int ACLS_OF_AUTHENTICATION_IN_CACHE = 10;
  //
  //	int PERMISSIONS_OF_OPERATION_NEEDED_IN_CACHE = 10;
  //
  //	String OPTIONS_DELIM = "\n";
  //
  //	String VALUEOFOPTIONS_DELIM = ",";
  //
  //	String SESSION_ID_KEY = "APP_SESSION_ID_KEY";

  public static final long SECOND = 1000;

  public static final long MINUTE = 60 * SECOND;

  public static final long HOUR = 60 * MINUTE;

  public static final long DAY = 24 * HOUR;

  public static final long WEEK = 7 * DAY;

  public static final String DOMAIN_KEY = "app.server.domain";
  public static final String VALIDATION_CODE = "validation_code";

  public static final String TREEROOT = "ROOT"; // 顶部父节点

  public static final String TREELEAF = "1"; // 叶子节点

  // 参数标识
  public static final String PARAM_ROOT = "0"; // 参数根
  public static final String PARAM_LEAF = "#"; // 参数叶子

  // 参数类型
  public static final String PAMTYP_QRY = "0"; // 查询类
  public static final String PAMTYP_CTL = "1"; // 控制类

  // yes、no
  public static final String YES = "Y"; // 是
  public static final String NO = "N"; // 否

  // 通用状态
  public static final String COMSTS_ACTIVE = "0"; // 有效
  public static final String COMSTS_INACTIVE = "1"; // 无效

  // 通用状态
  public static final String COMSTS_OPEN = "0"; // 正常
  public static final String COMSTS_COLSE = "1"; // 关闭

  // 用户状态
  public static final String USRSTS_ACTIVE = "0"; // 有效
  public static final String USRSTS_IACTIV = "1"; // 无效
  public static final String USRSTS_PEDING = "2"; // 锁定
  public static final String USRSTS_DELETE = "3"; // 删除
  public static final String USRSTS_SYNINIT = "4"; // 同步初始化

  // 参数适用范围
  public static final String PAMRNG_EXTENDED_ENTERPRISE = "U"; // 可扩展企业参数
  public static final String PAMRNG_MODIFIED_ENTERPRISE = "I"; // 可修改企业参数
  public static final String PAMRNG_INDIVIDUAL_SYSTEM = "D"; // 可个性化系统参数
  public static final String PAMRNG_MODIFIED_SYSTEM = "M"; // 可修改系统参数
  public static final String PAMRNG_READ_SYSTEM = "S"; // 不可修改系统参数

  // 参数管理的类别
  public static final String PAM_SYSTEM = "S"; // 系统参数管理
  public static final String PAM_PROJECT = "P"; // 项目参数管理
  public static final String PAM_COMPANY = "C"; // 企业参数管理
  public static final String PAM_PERSON = "T"; // 个人参数管理

  // 用户类型
  public static final String USRTYP_PADMIN = "S"; // 超级管理员
  public static final String USRTYP_SADMIN = "A"; // 系统管理员
  public static final String USRTYP_COMMON = "U"; // 普通用户
  public static final String USRTYP_VISITOR = "V"; // 游客

  // 资源类别
  public static final String RESTYP_WEBCTL = "0"; // web控制
  public static final String RESTYP_TELCTL = "1"; // phone控制
  public static final String RESTYP_FILTER = "2"; // 过滤

  public static final String WEB_SOCKET = "websocket";

  // 卡包缓存key
  public static final String CARDS_CUSTOMER = "cards_customer_"; // 某用户的会员卡列表
  public static final String VOUCHERS_CUSTOMER = "vouchers_customer_"; // 某用户的优惠券列表

  public static final String ERRORTIMES = "errorTimes_";
  public static final String LOGINVERIFYCODE = "loginVerifyCode_";
  public static final String LOGININFO = "userInfo_";
  public static final String USERID = "userId_";
  public static final String ROLELIST = "sys_role_list_";
  String QRCODE = "qrcode_"; // 二维码信息
  public static final String SPECAILROLE = "special";

  /** 商户id* */
  String MERCHANT_ID = "merchant_id_";
  /** 营销活动总标题 */
  String MARKET_ACTIVITY_TITLE = "market_activity_title";
  /** 测试与正式营销活动列表 */
  String TEST_NORMAL_ACTIVITY = "test_normal_activity";
  /** 正式营销活动列表 */
  String NORMAL_ACTIVITY = "normal_activity";

  /** 分行 */
  String BRANCH_COD = "branch_cod_";

  /** 某用户的代金券列表 */
  String COUPONS_CUSTOMER = "coupons_customer_";

  /** 卡号随机数部分 */
  String CARD_NO = "card_no_";
}

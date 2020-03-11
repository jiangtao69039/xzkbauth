package xzkbauth.common.jpa.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class SyUser implements Serializable {

    private static final long serialVersionUID = 8868524511875148958L;
    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //分行码
    private String branchCod;
    //登录名
    private String userCod;
    //密码
    private String password;
    //验证码(手机)
    private String verifyCode;
    //用户名
    private String userName;
    //用户类型
    private String type;
    //手机
    private String phone;
    //用户状态
    private String status;
    //头像
    private Integer imgHead;
    //插入日期
    private Date insertDate;
    //更新日期
    private Date updateDate;
    /**
     * 备注
     */
    private String remark;
    /**
     * 归属；1-c端，2-b端，3-cmb端
     */
    private Integer attribution;
    /**
     * 商户id
     */
    private Integer merchantId;
    /**
     * 门店id
     */
    private Integer storeId;
    /**
     * 最后更新人id
     */
    private Integer lastUpdateUser;
    /**
     * 初始登录密码，针对b端商家
     */
    private String originPassword;
    /**
     * 接收短信通知 0-否 1-是
     */
    private Integer receiveMsg;
    /**
     * 是否允许撤销非本人的收款记录 1-只允许撤销本人的收款记录 2-允许撤销无法确定收银员的收款记录（如用户扫二维码消费）
     */
    private Integer cancelAuth;
    /**
     * 是否能通过微信分享收款 0-不允许(默认) 1-允许
     */
    private Integer canShareWeiXin;
}

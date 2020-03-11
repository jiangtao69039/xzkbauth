package xzkbauth.enums;

import lombok.Getter;

@Getter
public enum LoginEnum {
  // 登录的验证码
  LOGINVERIFYCODE("loginVerifyCode_", "登录的验证码"),
  // 登录的错误次数
  ERRORTIMES("errorTimes_", "登录的错误次数");

  private String code;
  // 对枚举的描述
  private String depict;

  private LoginEnum(String code, String depict) {
    this.code = code;
    this.depict = depict;
  }
}

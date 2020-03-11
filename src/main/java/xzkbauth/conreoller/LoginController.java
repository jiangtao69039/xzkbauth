package xzkbauth.conreoller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xzkbauth.base.BaseController;
import xzkbauth.base.Result;
import xzkbauth.service.LoginServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** @author jiangtao */
@RestController
@RequestMapping("/auth")
public class LoginController extends BaseController {

  Logger logger = LoggerFactory.getLogger(LoginController.class);
  @Autowired private Environment env;
  @Autowired LoginServiceImpl loginService;

  /**
   * 原cmbserver的login接口 sysUser登录
   *
   * @return
   */
  @PostMapping("/system/login")
  public Result login(
      String userCod,
      String password,
      @RequestParam(required = false) String verifyCode,
      @RequestParam(required = false) String phoneCode,
      HttpServletRequest request,
      HttpServletResponse response) {
    // TODO redis序列化方式和原先不同*
    if (StringUtils.isEmpty(userCod) || StringUtils.isEmpty(password)) {
      throw new RuntimeException(env.getProperty("msg.login.empty"));
    }

    return null;
  }
}

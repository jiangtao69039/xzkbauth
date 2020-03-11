package xzkbauth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import xzkbauth.base.CallbackResult;
import xzkbauth.common.jpa.entity.SyBranch;
import xzkbauth.common.jpa.entity.SyUser;
import xzkbauth.common.jpa.entity.SyWarningPlatform;
import xzkbauth.enums.LoginEnum;
import xzkbauth.util.ExcpUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("loginService")
public class LoginServiceImpl {

  @Autowired private Environment env;
  Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
  @Autowired RedisService redisService;
  /**
   * redis中记录的错误次数
   *
   * @param key
   * @return
   */
  public Integer loginErrorTimesInCache(String key) {
    int times = 1;
    if (redisService.exists(key)) {
      times = (int) redisService.get(key);
    }
    return times;
  }

  @Autowired SyUserServiceImpl syUserService;
  @Autowired SyWarningPlatformServiceImpl syWarningPlatformService;

  @Autowired SyBranchServiceImpl syBranchService;

  /**
   * SyUser登录操作
   *
   * @return String -> token
   */
  public CallbackResult<String> systemUserLogin(
      String userCod, String password, String verifyCode, String phoneCode) {
    // 判断登录错误次数是否大于等于3次，是则检验验证码
    String errorTimesKey = LoginEnum.ERRORTIMES.getCode() + userCod;
    int loginTimes = (int) loginErrorTimesInCache(errorTimesKey);
    logger.info(
        "[syUser登录操作],userCode:{},password:{},verifyCode:{},loginTimes:{}",
        userCod,
        password,
        verifyCode,
        loginTimes);
    if (loginTimes > 6) {
      return CallbackResult.failure("syUser登录", "账号锁定，三十分钟后再尝试登录", "300");
    }
    SyUser syUser = syUserService.findByUserCod(userCod);
    String verfiyCodeInCacheKey = LoginEnum.LOGINVERIFYCODE.getCode();
    if (loginTimes > 3) {
      // TODO ?验证码key没有与用户绑定

      String verifyCodeInCache = (String) resultInCache(verfiyCodeInCacheKey);
      if (verifyCodeInCache == null || !verifyCodeInCache.equals(verifyCode)) {
        logger.error("[syUser登录操作],用户上传的验证码:{},redis中的验证码:{}", verifyCode, verifyCodeInCache);
        return check(loginTimes, verfiyCodeInCacheKey, errorTimesKey, syUser);
      }
    }

    // 根据用户名查找用户信息，若为空，则用户登录错误，否则判断密码是否正确
    if (syUser == null) {
      // TODO user = null ?
      SyUser user = new SyUser();
      user.setUserCod(userCod);
      user.setPassword(password);
      user.setVerifyCode(verifyCode);
      return check(loginTimes, verfiyCodeInCacheKey, errorTimesKey, user);
    }
    return null;
  }

  public Object resultInCache(String key) {
    Object result = null;
    if (redisService.exists(key)) {
      result = redisService.get(key);
    }
    return result;
  }

  public CallbackResult<String> check(
      int errorLoginTimes, String verfiyCodeInCacheKey, String errorTimesKey, SyUser user) {
    logger.info("错误次数：" + errorLoginTimes);
    if (errorLoginTimes == 3 || errorLoginTimes == 4) {
      incrLoginErrorTimes(errorTimesKey);
      return CallbackResult.failure(
          "验证登录次数",
          env.getProperty("msg.login.fulcode"),
          "301",
          getVerifyCod(verfiyCodeInCacheKey),
          null);
      // return result(301,env.getProperty("msg.login.fulcode"),getVerifyCod(verfiyCodeInCacheKey));
    } else if (errorLoginTimes == 5) {
      incrLoginErrorTimes(errorTimesKey);
      //			return error(300,"再输错一次账号会被锁定。");
      return CallbackResult.failure(
          "验证登录次数",
          env.getProperty("msg.login.lockagin"),
          "301",
          getVerifyCod(verfiyCodeInCacheKey),
          null);
      // return
      // result(301,env.getProperty("msg.login.lockagin"),getVerifyCod(verfiyCodeInCacheKey));
    } else if (errorLoginTimes >= 6) {
      redisService.set(errorTimesKey, 7, 1800);
      // 添加告警台信息
      addWarningPlatform(user);
      return CallbackResult.failure(
          "验证登录次数",
          env.getProperty("msg.login.retry"),
          "301",
          getVerifyCod(verfiyCodeInCacheKey),
          null);
      // return result(301,env.getProperty("msg.login.retry"),getVerifyCod(verfiyCodeInCacheKey));
    } else {
      incrLoginErrorTimes(errorTimesKey);
      return CallbackResult.failure(
          "验证登录次数",
          env.getProperty("msg.login.errornamepwd"),
          "300",
          getVerifyCod(verfiyCodeInCacheKey),
          null);
      // return
      // result(300,env.getProperty("msg.login.errornamepwd"),getVerifyCod(verfiyCodeInCacheKey));
    }
  }

  /**
   * 增加错误登录次数
   *
   * @param key
   */
  public void incrLoginErrorTimes(String key) {
    if (redisService.exists(key)) {
      int i = (int) redisService.get(key);
      redisService.set(key, i + 1, 1800);
    } else {
      redisService.set(key, 2, 1800);
    }
  }

  /**
   * 生成验证码
   *
   * @param key
   * @return
   */
  public String getVerifyCod(String key) {
    int nres = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;
    String verifyCode = String.valueOf(nres);
    logger.info("verifyCode--" + verifyCode);
    redisService.set(key, verifyCode, 60);
    return verifyCode;
  }

  /**
   * 添加告警台信息
   *
   * @param
   */
  public void addWarningPlatform(SyUser user) {
    try {
      SyUser syUser = syUserService.findByUserCod(user.getUserCod());
      if (syUser == null) {
        String uuid = UUID.randomUUID().toString();
        logger.error("[添加告警台信息出错]--uuid:{},info:{},userCod:{}", uuid, "user未找到", user.getUserCod());
        return;
      }
      SyBranch branch = syBranchService.findByBranchCod(syUser.getBranchCod());
      SyWarningPlatform warningPlatform = new SyWarningPlatform();
      warningPlatform.setStatus(0);
      warningPlatform.setWarningDate(new Date());
      warningPlatform.setUpdateDate(new Date());
      warningPlatform.setItem("后台管理员账号锁定");
      warningPlatform.setDetail(
          syUser.getUserCod()
              + "("
              + Optional.ofNullable(branch).map(SyBranch::getBranchName).orElse("")
              + ")管理员账号锁定");
      syWarningPlatformService.save(warningPlatform);

    } catch (Exception e) {
      String uuid = UUID.randomUUID().toString();
      logger.error(
          "[添加告警台信息出错]--uuid:{},error:{},track:{}",
          uuid,
          e.getMessage(),
          ExcpUtil.getStackTraceString(e));
    }
  }
}

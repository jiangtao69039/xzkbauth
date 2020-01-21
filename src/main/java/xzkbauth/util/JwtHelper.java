package xzkbauth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtHelper {
  public static final String SECRET = "auth_session_secret";
  public static final String ISSUER = "xzkbauth";
  static Logger logger = LoggerFactory.getLogger(JwtHelper.class);

  public static String genToken(Map<String, String> map) {
    String token = "";
    try {
      // 过期时间,设置token有效时长为5hours
      Date expireDate = DateUtil.addHour(new Date(), 5);
      // 签发时间(服务器间存在时差问题，此操作为解决在时间快的服务器生成的token到慢的服务器未生效而导致token验证失败问题)
      Date effectDate = DateUtil.addMinute(new Date(), -5);
      System.out.println("jwt token-----" + effectDate + "--" + expireDate);
      JWTCreator.Builder builder = JWT.create();
      map.forEach(builder::withClaim);
      Algorithm algorithm = Algorithm.HMAC256(SECRET);
      // 生成token
      token =
          builder
              .withIssuer(ISSUER)
              .withIssuedAt(effectDate)
              .withExpiresAt(expireDate)
              .sign(algorithm)
              .toString();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      logger.error(
          "[jwt getToken 出错]--error:{},track:{}", e.getMessage(), ExcpUtil.getStackTraceString(e));
    }
    return token;
  }

  public static Map<String, String> verifyToken(String token) {
    Algorithm algorithm = null;
    try {
      algorithm = Algorithm.HMAC256(SECRET);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      return null;
    }
    JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
    DecodedJWT jwt = null;
    try {
      jwt = verifier.verify(token);
    } catch (JWTVerificationException e) {
      e.printStackTrace();
      logger.error(
          "[jwt verifyToken 出错]--error:{},track:{}",
          e.getMessage(),
          ExcpUtil.getStackTraceString(e));
      if (e.getMessage().startsWith("The Token can't be used before")) {
        System.out.println("登录凭证暂未生效 token--" + token);
      } else if (e.getMessage().startsWith("The Token has expired on")) {
        System.out.println("登录凭证已过期，请重新登录 token--" + token);
      } else {
        System.out.println("非法凭证 token--" + token);
      }
      return null;
    }
    Map<String, Claim> map = jwt.getClaims();
    Map<String, String> resultMap = Maps.newHashMap();
    map.forEach((k, v) -> resultMap.put(k, v.asString()));
    return resultMap;
  }

  public static void main(String[] args) {
    Map<String, String> map = new HashMap<>();
    map.put("id", "1");
    map.put("name", "aaa");
    String token = genToken(map);
    System.out.println(token);
    Map<String, String> r = verifyToken(token);
    r.forEach((k, v) -> System.out.println(k + "  " + v));

    Map<String, String> q = verifyToken("111");
  }
}

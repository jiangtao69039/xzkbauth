package xzkbauth.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {
  @Autowired private HttpServletRequest request;
  @Autowired private Environment env;
  protected Logger logger = null;

  public BaseController() {
    logger = LoggerFactory.getLogger(this.getClass().getName());
  }

  protected Result success(Object object) {
    Result result = new Result();
    result.setState(200);
    result.setMsg("success");
    result.setData(object);
    return result;
  }

  protected Result success() {
    return success(null);
  }

  protected Result error(Integer state, String msg) {
    Result result = new Result();
    result.setState(state);
    result.setMsg(msg);
    result.setData(null);
    return result;
  }

  protected Result result(Integer state, String msg, Object data) {
    Result result = new Result();
    result.setState(state);
    result.setMsg(msg);
    result.setData(data);
    return result;
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public String getProperty(String key) {
    return env.getProperty(key);
  }
}

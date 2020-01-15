package xzkbauth.common.exception;

public class NoTokenException extends RuntimeException {
  public static final long serialVersionUID = 2L;

  private String msg;

  private int code = 402;

  public NoTokenException(String msg) {
    super(msg);
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }
}

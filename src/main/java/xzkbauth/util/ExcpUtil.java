package xzkbauth.util;

/**
 * 错误信息输出到日志的工具类 e.printTrack会把错误信息输出到标准错误中,日志中e.getMessage没有堆栈信息
 *
 * @author jiangtao
 */
public class ExcpUtil {
  // 打印异常堆栈信息
  public static String getStackTraceString(Throwable ex) { // (Exception ex) {
    try {
      StackTraceElement[] traceElements = ex.getStackTrace();

      StringBuilder traceBuilder = new StringBuilder();

      if (traceElements != null && traceElements.length > 0) {
        traceBuilder.append("\n");
        for (StackTraceElement traceElement : traceElements) {
          traceBuilder.append(traceElement.toString());
          traceBuilder.append("\n");
        }
      }

      return traceBuilder.toString();
    } catch (Exception e) {
      return ex.getMessage();
    }
  }

  // 构造异常堆栈信息
  public static String buildErrorMessage(Exception ex) {

    String result;
    String stackTrace = getStackTraceString(ex);
    String exceptionType = ex.toString();
    String exceptionMessage = ex.getMessage();

    result = String.format("%s : %s \r\n %s", exceptionType, exceptionMessage, stackTrace);

    return result;
  }

  // 运用场景一:输出错误信息到日志
  /*try {
      //do something
  } catch (Exception ex) {
      logger.info(ex.toString());
      logger.info("work exception" + ExcpUtil.getStackTraceString(ex));
  }*/

  // 运用场景二:抛出异常
  /*try {
      //do something
  } catch (Exception e) {
      throw new Exception ("ctrip_SendEmail exception", ExcpUtil.buildErrorMessage(e));
  }*/
}

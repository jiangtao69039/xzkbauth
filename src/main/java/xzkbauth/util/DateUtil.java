package xzkbauth.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

  /**
   * 获取SimpleDateFormat
   *
   * @param parttern 日期格式
   * @return SimpleDateFormat对象
   * @throws RuntimeException 异常：非法日期格式
   */
  private static SimpleDateFormat getDateFormat(String parttern) throws RuntimeException {
    return new SimpleDateFormat(parttern);
  }

  /**
   * 获取日期中的某数值。如获取月份
   *
   * @param date 日期
   * @param dateType 日期格式
   * @return 数值
   */
  private static int getInteger(Date date, int dateType) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(dateType);
  }

  /**
   * 增加日期中某类型的某数值。如增加日期
   *
   * @param date 日期字符串
   * @param dateType 类型
   * @param amount 数值
   * @return 计算后日期字符串
   */
  private static String addInteger(String date, int dateType, int amount) {
    String dateString = null;
    DateStyle dateStyle = getDateStyle(date);
    if (dateStyle != null) {
      Date myDate = StringToDate(date, dateStyle);
      myDate = addInteger(myDate, dateType, amount);
      dateString = DateToString(myDate, dateStyle);
    }
    return dateString;
  }

  /**
   * 增加日期中某类型的某数值。如增加日期
   *
   * @param date 日期
   * @param dateType 类型
   * @param amount 数值
   * @return 计算后日期
   */
  private static Date addInteger(Date date, int dateType, int amount) {
    Date myDate = null;
    if (date != null) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(dateType, amount);
      myDate = calendar.getTime();
    }
    return myDate;
  }

  /**
   * 获取精确的日期
   *
   * @param timestamps 时间long集合
   * @return 日期
   */
  private static Date getAccurateDate(List<Long> timestamps) {
    Date date = null;
    long timestamp = 0;
    Map<Long, long[]> map = new HashMap<Long, long[]>();
    List<Long> absoluteValues = new ArrayList<Long>();

    if (timestamps != null && timestamps.size() > 0) {
      if (timestamps.size() > 1) {
        for (int i = 0; i < timestamps.size(); i++) {
          for (int j = i + 1; j < timestamps.size(); j++) {
            long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));
            absoluteValues.add(absoluteValue);
            long[] timestampTmp = {timestamps.get(i), timestamps.get(j)};
            map.put(absoluteValue, timestampTmp);
          }
        }

        // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的
        long minAbsoluteValue = -1;
        if (!absoluteValues.isEmpty()) {
          // 如果timestamps的size为2，这是差值只有一个，因此要给默认值
          minAbsoluteValue = absoluteValues.get(0);
        }
        for (int i = 0; i < absoluteValues.size(); i++) {
          for (int j = i + 1; j < absoluteValues.size(); j++) {
            if (absoluteValues.get(i) > absoluteValues.get(j)) {
              minAbsoluteValue = absoluteValues.get(j);
            } else {
              minAbsoluteValue = absoluteValues.get(i);
            }
          }
        }

        if (minAbsoluteValue != -1) {
          long[] timestampsLastTmp = map.get(minAbsoluteValue);
          if (absoluteValues.size() > 1) {
            timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
          } else if (absoluteValues.size() == 1) {
            // 当timestamps的size为2，需要与当前时间作为参照
            long dateOne = timestampsLastTmp[0];
            long dateTwo = timestampsLastTmp[1];
            if ((Math.abs(dateOne - dateTwo)) < 100000000000L) {
              timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
            } else {
              long now = new Date().getTime();
              if (Math.abs(dateOne - now) <= Math.abs(dateTwo - now)) {
                timestamp = dateOne;
              } else {
                timestamp = dateTwo;
              }
            }
          }
        }
      } else {
        timestamp = timestamps.get(0);
      }
    }

    if (timestamp != 0) {
      date = new Date(timestamp);
    }
    return date;
  }

  /**
   * 判断字符串是否为日期字符串
   *
   * @param date 日期字符串
   * @return true or false
   */
  public static boolean isDate(String date) {
    boolean isDate = false;
    if (date != null) {
      if (StringToDate(date) != null) {
        isDate = true;
      }
    }
    return isDate;
  }

  /**
   * 获取日期字符串的日期风格。失敗返回null。
   *
   * @param date 日期字符串
   * @return 日期风格
   */
  public static DateStyle getDateStyle(String date) {
    DateStyle dateStyle = null;
    Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
    List<Long> timestamps = new ArrayList<Long>();
    for (DateStyle style : DateStyle.values()) {
      Date dateTmp = StringToDate(date, style.getValue());
      if (dateTmp != null) {
        timestamps.add(dateTmp.getTime());
        map.put(dateTmp.getTime(), style);
      }
    }
    dateStyle = map.get(getAccurateDate(timestamps).getTime());
    return dateStyle;
  }

  /**
   * 将日期字符串转化为日期。失败返回null。
   *
   * @param date 日期字符串
   * @return 日期
   */
  public static Date StringToDate(String date) {
    DateStyle dateStyle = null;
    return StringToDate(date, dateStyle);
  }

  /**
   * 将日期字符串转化为日期。失败返回null。
   *
   * @param date 日期字符串
   * @param parttern 日期格式
   * @return 日期
   */
  public static Date StringToDate(String date, String parttern) {
    Date myDate = null;
    if (date != null) {
      try {
        myDate = getDateFormat(parttern).parse(date);
      } catch (Exception e) {
      }
    }
    return myDate;
  }

  /**
   * 将日期字符串转化为日期。失败返回null。
   *
   * @param date 日期字符串
   * @param dateStyle 日期风格
   * @return 日期
   */
  public static Date StringToDate(String date, DateStyle dateStyle) {
    Date myDate = null;
    if (dateStyle == null) {
      List<Long> timestamps = new ArrayList<Long>();
      for (DateStyle style : DateStyle.values()) {
        Date dateTmp = StringToDate(date, style.getValue());
        if (dateTmp != null) {
          timestamps.add(dateTmp.getTime());
        }
      }
      myDate = getAccurateDate(timestamps);
    } else {
      myDate = StringToDate(date, dateStyle.getValue());
    }
    return myDate;
  }

  /**
   * 将日期转化为日期字符串。失败返回null。
   *
   * @param date 日期
   * @param parttern 日期格式
   * @return 日期字符串
   */
  public static String DateToString(Date date, String parttern) {
    String dateString = null;
    if (date != null) {
      try {
        dateString = getDateFormat(parttern).format(date);
      } catch (Exception e) {
      }
    }
    return dateString;
  }

  /**
   * 将日期转化为日期字符串。失败返回null。
   *
   * @param date 日期
   * @param dateStyle 日期风格
   * @return 日期字符串
   */
  public static String DateToString(Date date, DateStyle dateStyle) {
    String dateString = null;
    if (dateStyle != null) {
      dateString = DateToString(date, dateStyle.getValue());
    }
    return dateString;
  }

  /**
   * 将日期字符串转化为另一日期字符串。失败返回null。
   *
   * @param date 旧日期字符串
   * @param parttern 新日期格式
   * @return 新日期字符串
   */
  public static String StringToString(String date, String parttern) {
    return StringToString(date, null, parttern);
  }

  /**
   * 将日期字符串转化为另一日期字符串。失败返回null。
   *
   * @param date 旧日期字符串
   * @param dateStyle 新日期风格
   * @return 新日期字符串
   */
  public static String StringToString(String date, DateStyle dateStyle) {
    return StringToString(date, null, dateStyle);
  }

  /**
   * 将日期字符串转化为另一日期字符串。失败返回null。
   *
   * @param date 旧日期字符串
   * @param olddParttern 旧日期格式
   * @param newParttern 新日期格式
   * @return 新日期字符串
   */
  public static String StringToString(String date, String olddParttern, String newParttern) {
    String dateString = null;
    if (olddParttern == null) {
      DateStyle style = getDateStyle(date);
      if (style != null) {
        Date myDate = StringToDate(date, style.getValue());
        dateString = DateToString(myDate, newParttern);
      }
    } else {
      Date myDate = StringToDate(date, olddParttern);
      dateString = DateToString(myDate, newParttern);
    }
    return dateString;
  }

  /**
   * 将日期字符串转化为另一日期字符串。失败返回null。
   *
   * @param date 旧日期字符串
   * @param olddDteStyle 旧日期风格
   * @param newDateStyle 新日期风格
   * @return 新日期字符串
   */
  public static String StringToString(String date, DateStyle olddDteStyle, DateStyle newDateStyle) {
    String dateString = null;
    if (olddDteStyle == null) {
      DateStyle style = getDateStyle(date);
      dateString = StringToString(date, style.getValue(), newDateStyle.getValue());
    } else {
      dateString = StringToString(date, olddDteStyle.getValue(), newDateStyle.getValue());
    }
    return dateString;
  }

  /**
   * 增加日期的年份。失败返回null。
   *
   * @param date 日期
   * @param yearAmount 增加数量。可为负数
   * @return 增加年份后的日期字符串
   */
  public static String addYear(String date, int yearAmount) {
    return addInteger(date, Calendar.YEAR, yearAmount);
  }

  /**
   * 增加日期的年份。失败返回null。
   *
   * @param date 日期
   * @param yearAmount 增加数量。可为负数
   * @return 增加年份后的日期
   */
  public static Date addYear(Date date, int yearAmount) {
    return addInteger(date, Calendar.YEAR, yearAmount);
  }

  /**
   * 增加日期的月份。失败返回null。
   *
   * @param date 日期
   * @param yearAmount 增加数量。可为负数
   * @return 增加月份后的日期字符串
   */
  public static String addMonth(String date, int yearAmount) {
    return addInteger(date, Calendar.MONTH, yearAmount);
  }

  /**
   * 增加日期的月份。失败返回null。
   *
   * @param date 日期
   * @param yearAmount 增加数量。可为负数
   * @return 增加月份后的日期
   */
  public static Date addMonth(Date date, int yearAmount) {
    return addInteger(date, Calendar.MONTH, yearAmount);
  }

  /**
   * 增加日期的天数。失败返回null。
   *
   * @param date 日期字符串
   * @param dayAmount 增加数量。可为负数
   * @return 增加天数后的日期字符串
   */
  public static String addDay(String date, int dayAmount) {
    return addInteger(date, Calendar.DATE, dayAmount);
  }

  /**
   * 增加日期的天数。失败返回null。
   *
   * @param date 日期
   * @param dayAmount 增加数量。可为负数
   * @return 增加天数后的日期
   */
  public static Date addDay(Date date, int dayAmount) {
    return addInteger(date, Calendar.DATE, dayAmount);
  }

  /**
   * 增加日期的小时。失败返回null。
   *
   * @param date 日期字符串
   * @param dayAmount 增加数量。可为负数
   * @return 增加小时后的日期字符串
   */
  public static String addHour(String date, int hourAmount) {
    return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
  }

  /**
   * 增加日期的小时。失败返回null。
   *
   * @param date 日期
   * @param dayAmount 增加数量。可为负数
   * @return 增加小时后的日期
   */
  public static Date addHour(Date date, int hourAmount) {
    return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
  }

  /**
   * 增加日期的分钟。失败返回null。
   *
   * @param date 日期字符串
   * @param dayAmount 增加数量。可为负数
   * @return 增加分钟后的日期字符串
   */
  public static String addMinute(String date, int hourAmount) {
    return addInteger(date, Calendar.MINUTE, hourAmount);
  }

  /**
   * 增加日期的分钟。失败返回null。
   *
   * @param date 日期
   * @param dayAmount 增加数量。可为负数
   * @return 增加分钟后的日期
   */
  public static Date addMinute(Date date, int hourAmount) {
    return addInteger(date, Calendar.MINUTE, hourAmount);
  }

  /**
   * 增加日期的秒钟。失败返回null。
   *
   * @param date 日期字符串
   * @param dayAmount 增加数量。可为负数
   * @return 增加秒钟后的日期字符串
   */
  public static String addSecond(String date, int hourAmount) {
    return addInteger(date, Calendar.SECOND, hourAmount);
  }

  /**
   * 增加日期的秒钟。失败返回null。
   *
   * @param date 日期
   * @param dayAmount 增加数量。可为负数
   * @return 增加秒钟后的日期
   */
  public static Date addSecond(Date date, int hourAmount) {
    return addInteger(date, Calendar.SECOND, hourAmount);
  }

  /**
   * 获取日期的年份。失败返回0。
   *
   * @param date 日期字符串
   * @return 年份
   */
  public static int getYear(String date) {
    return getYear(StringToDate(date));
  }

  /**
   * 获取日期的年份。失败返回0。
   *
   * @param date 日期
   * @return 年份
   */
  public static int getYear(Date date) {
    return getInteger(date, Calendar.YEAR);
  }

  /**
   * 获取日期的月份。失败返回0。
   *
   * @param date 日期字符串
   * @return 月份
   */
  public static int getMonth(String date) {
    return getMonth(StringToDate(date));
  }

  /**
   * 获取日期的月份。失败返回0。
   *
   * @param date 日期
   * @return 月份
   */
  public static int getMonth(Date date) {
    return getInteger(date, Calendar.MONTH);
  }

  /**
   * 获取日期的天数。失败返回0。
   *
   * @param date 日期字符串
   * @return 天
   */
  public static int getDay(String date) {
    return getDay(StringToDate(date));
  }

  /**
   * 获取日期的天数。失败返回0。
   *
   * @param date 日期
   * @return 天
   */
  public static int getDay(Date date) {
    return getInteger(date, Calendar.DATE);
  }

  /**
   * 获取日期的小时。失败返回0。
   *
   * @param date 日期字符串
   * @return 小时
   */
  public static int getHour(String date) {
    return getHour(StringToDate(date));
  }

  /**
   * 获取日期的小时。失败返回0。
   *
   * @param date 日期
   * @return 小时
   */
  public static int getHour(Date date) {
    return getInteger(date, Calendar.HOUR_OF_DAY);
  }

  /**
   * 获取日期的分钟。失败返回0。
   *
   * @param date 日期字符串
   * @return 分钟
   */
  public static int getMinute(String date) {
    return getMinute(StringToDate(date));
  }

  /**
   * 获取日期的分钟。失败返回0。
   *
   * @param date 日期
   * @return 分钟
   */
  public static int getMinute(Date date) {
    return getInteger(date, Calendar.MINUTE);
  }

  /**
   * 获取日期的秒钟。失败返回0。
   *
   * @param date 日期字符串
   * @return 秒钟
   */
  public static int getSecond(String date) {
    return getSecond(StringToDate(date));
  }

  /**
   * 获取日期的秒钟。失败返回0。
   *
   * @param date 日期
   * @return 秒钟
   */
  public static int getSecond(Date date) {
    return getInteger(date, Calendar.SECOND);
  }

  /**
   * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
   *
   * @param date 日期字符串
   * @return 日期
   */
  public static String getDate(String date) {
    return StringToString(date, DateStyle.YYYY_MM_DD);
  }

  /**
   * 获取日期。默认yyyy-MM-dd格式。失败返回null。
   *
   * @param date 日期
   * @return 日期
   */
  public static String getDate(Date date) {
    return DateToString(date, DateStyle.YYYY_MM_DD);
  }

  /**
   * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
   *
   * @param date 日期字符串
   * @return 时间
   */
  public static String getTime(String date) {
    return StringToString(date, DateStyle.HH_MM_SS);
  }

  /**
   * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
   *
   * @param date 日期
   * @return 时间
   */
  public static String getTime(Date date) {
    return DateToString(date, DateStyle.HH_MM_SS);
  }

  /**
   * 获取日期的星期。失败返回null。
   *
   * @param date 日期字符串
   * @return 星期
   */
  public static Week getWeek(String date) {
    Week week = null;
    DateStyle dateStyle = getDateStyle(date);
    if (dateStyle != null) {
      Date myDate = StringToDate(date, dateStyle);
      week = getWeek(myDate);
    }
    return week;
  }

  /**
   * 获取日期的星期。失败返回null。
   *
   * @param date 日期
   * @return 星期
   */
  public static Week getWeek(Date date) {
    Week week = null;
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    switch (weekNumber) {
      case 0:
        week = Week.SUNDAY;
        break;
      case 1:
        week = Week.MONDAY;
        break;
      case 2:
        week = Week.TUESDAY;
        break;
      case 3:
        week = Week.WEDNESDAY;
        break;
      case 4:
        week = Week.THURSDAY;
        break;
      case 5:
        week = Week.FRIDAY;
        break;
      case 6:
        week = Week.SATURDAY;
        break;
    }
    return week;
  }

  /**
   * 获取两个日期相差的天数
   *
   * @param date 日期字符串
   * @param otherDate 另一个日期字符串
   * @return 相差天数
   */
  public static int getIntervalDays(String date, String otherDate) {
    return getIntervalDays(StringToDate(date), StringToDate(otherDate));
  }

  /**
   * @param date 日期
   * @param otherDate 另一个日期
   * @return 相差天数
   */
  public static int getIntervalDays(Date date, Date otherDate) {
    date = DateUtil.StringToDate(DateUtil.getDate(date));
    long time = Math.abs(date.getTime() - otherDate.getTime());
    return (int) time / (24 * 60 * 60 * 1000);
  }

  /**
   * 给定的一个时间段和特定的星期，我需要获得这个时间段中所有的给定星期的日期
   *
   * @param dateFrom
   * @param dateEnd
   * @param weekDays 1-星期一, 2-星期二, ...
   * @param weekStatus 类型，1-单周，2-双周
   * @return
   */
  public static List<String> getDates(
      String dateFrom, String dateEnd, List<Integer> weekDays, int weekStatus) {
    long time;
    long perDayMilSec = 24L * 60 * 60 * 1000;
    List<String> dateList = new ArrayList<String>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // 需要查询的星期系数
    String strWeekNumber = weekForNum(weekDays);
    try {
      dateFrom = sdf.format(sdf.parse(dateFrom).getTime() - perDayMilSec);
      int i = 0;
      while (true) {
        time = sdf.parse(dateFrom).getTime();
        time = time + perDayMilSec;
        Date date = new Date(time);
        dateFrom = sdf.format(date);
        if (dateFrom.compareTo(dateEnd) <= 0) {
          // 查询的某一时间的星期系数
          Integer weekDay = dayForWeek(date);
          // 判断当期日期的星期系数是否是需要查询的
          if (strWeekNumber.contains(weekDay.toString())) {
            if (weekStatus == 1) { // 单周
              dateList.add(dateFrom);
            } else {
              if (i % 2 == 0) {
                dateList.add(dateFrom);
              }
            }
          }
          i++;
        } else {
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return dateList;
  }

  private static Map<Integer, Integer> weekNumberMap = new HashMap<Integer, Integer>();

  static {
    weekNumberMap.put(0, 1);
    weekNumberMap.put(1, 2);
    weekNumberMap.put(2, 3);
    weekNumberMap.put(3, 4);
    weekNumberMap.put(4, 5);
    weekNumberMap.put(5, 6);
    weekNumberMap.put(6, 7);
  }
  // 将星期转换为对应的系数 0,星期日; 1,星期一; 2....
  public static Integer getWeekNum(int strWeek) {
    return weekNumberMap.get(strWeek);
  }
  // 得到对应星期的系数 0：1，星期
  public static String weekForNum(List<Integer> weekDays) {
    // 返回结果为组合的星期系数
    String weekNumber = "";
    for (Integer weekDay : weekDays) {
      weekNumber = weekNumber + "" + getWeekNum(weekDay).toString();
    }
    return weekNumber;
  }
  // 等到当期时间的周系数。星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7
  public static Integer dayForWeek(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.DAY_OF_WEEK);
  }

  /**
   * <Description>比较两个日期，如果date1大于date2,返回1，相等返回0，小于返回-1
   *
   * @since 2018年3月29日
   * @param date1 20170101
   * @param date2 20180101
   * @return <Description>
   */
  public static int compareDate(String date1, String date2) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    try {
      Date st = sdf.parse(date1);
      Date en = sdf.parse(date2);
      return st.compareTo(en);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * <Description>比较字符串数字，如果num1大于num2,返回1，相等返回0，小于返回-1
   *
   * @since 2018年3月29日
   * @param num1 01
   * @param num2 05
   * @return <Description>
   */
  public static int compareNum(String num1, String num2) {
    Integer s = new Integer(num1);
    Integer e = new Integer(num2);
    return s.compareTo(e);
  }

  /**
   * 增加日期的天数，返回 date+day日0:00
   *
   * @param date
   * @param day
   * @return
   */
  public static Date dealDay2Date(Date date, int day) {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
    Date addDay = addDay(date, day);
    String dateStr = fmt.format(addDay);
    Date result = addDay;
    try {
      result = fmt.parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 增加日期的天数，返回 date+day日0:00
   *
   * @param date
   * @param day
   * @return
   */
  public static String dealDay2Str(Date date, int day) {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
    Date addDay = addDay(date, day);
    String result = fmt.format(addDay);
    return result;
  }
}

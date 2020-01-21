package xzkbauth.util;

public enum Week {

	MONDAY("星期一", "Monday", "Mon.", 1, "周一"),
	TUESDAY("星期二", "Tuesday", "Tues.", 2, "周二"),
	WEDNESDAY("星期三", "Wednesday", "Wed.", 3, "周三"),
	THURSDAY("星期四", "Thursday", "Thur.", 4, "周四"),
	FRIDAY("星期五", "Friday", "Fri.", 5, "周五"),
	SATURDAY("星期六", "Saturday", "Sat.", 6, "周六"),
	SUNDAY("星期日", "Sunday", "Sun.", 7, "周日");
	
	String name_cn;
	String name_en;
	String name_enShort;
	int number;
	String name_cn1;
	
	Week(String name_cn, String name_en, String name_enShort, int number, String name_cn1) {
		this.name_cn = name_cn;
		this.name_en = name_en;
		this.name_enShort = name_enShort;
		this.number = number;
		this.name_cn1 = name_cn1;
	}
	
	public String getChineseName() {
		return name_cn;
	}

	public String getName() {
		return name_en;
	}

	public String getShortName() {
		return name_enShort;
	}

	public int getNumber() {
		return number;
	}
	
	public String getChineseName1() {
		return name_cn1;
	}

}
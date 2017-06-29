package domain;

public class ScheduledItem {

    private Integer id;             // 序號
    private Integer year;           // 年
    private Integer month;          // 月
    private Integer day;            // 日
    private Integer hour;           // 時
    private Integer minute;         // 分
    private Integer expectedTime;   // 預計花費時間
    private Character type;         // 種類: N-不限時, O-準時, D-期限
    private String name;            // 項目名稱
    private String description;     // 項目說明

    public void setId( Integer id ) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setYear( Integer year ) {
        this.year = year;
    }

    public Integer getYear() {
        return year;
    }

    public void setMonth( Integer month ) {
        this.month = month;
    }

    public Integer getMonth() {
        return month;
    }

    public void setDay( Integer day ) {
        this.day = day;
    }

    public Integer getDay() {
        return day;
    }

    public void setHour( Integer hour ) {
        this.hour = hour;
    }

    public Integer getHour() {
        return hour;
    }

    public void setMinute( Integer minute ) {
        this.minute = minute;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setExpectedTime( Integer expectedTime ) {
        this.expectedTime = expectedTime;
    }

    public Integer getExpectedTime() {
        return expectedTime;
    }

    public void setType( Character type ) {
        this.type = type;
    }

    public Character getType() {
        return type;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

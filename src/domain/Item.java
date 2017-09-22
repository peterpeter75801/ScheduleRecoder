package domain;

public class Item {

    private Integer year;           // 年
    private Integer month;          // 月
    private Integer day;            // 日
    private Integer startHour;      // 時-起
    private Integer startMinute;    // 分-起
    private Integer seq;            // 序號
    private Integer endHour;        // 時-迄
    private Integer endMinute;      // 分-迄
    private String name;            // 項目名稱
    private String description;     // 項目說明

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

    public void setStartHour( Integer startHour ) {
        this.startHour = startHour;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartMinute( Integer startMinute ) {
        this.startMinute = startMinute;
    }

    public Integer getStartMinute() {
        return startMinute;
    }
    
    public void setSeq( Integer seq ) {
        this.seq = seq;
    }
    
    public Integer getSeq() {
        return seq;
    }

    public void setEndHour( Integer endHour ) {
        this.endHour = endHour;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndMinute( Integer endMinute ) {
        this.endMinute = endMinute;
    }

    public Integer getEndMinute() {
        return endMinute;
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

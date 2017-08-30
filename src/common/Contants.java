package common;

public class Contants {
    
    public static final int SUCCESS = 0;
    public static final int ERROR = -1;
    public static final int ERROR_NOT_COMPLETE = -2;
    public static final int ERROR_NOT_SUPPORT = -4;
    public static final int ERROR_INVALID_PARAMETER = -5;
    public static final int ERROR_EXCEED_UPPER_LIMIT = -6;
    public static final int DUPLICATE_DATA = -3;
    
    /**
     * Verion alpha-0.25
     * <br /> Bug fix: ScheduledItemUtil.compareToByTime() method incorrect result for comparing yyyy.08.31 and yyyy.09.01 date value.
     * <br /> View: Adjust the width of the ItemPanel -> itemTable's column
     * <br /> Bug fix: The cancel button in ScheduledItemCreateDialog and ScheduledItemUpdateDialog is no response after clicking.
     */
    public static final String VERSION = "Ver. alpha-0.25";
}

package common;

public class Contants {
    
    public static final int SUCCESS = 0;
    public static final int ERROR = -1;
    public static final int ERROR_NOT_COMPLETE = -2;
    public static final int ERROR_NOT_SUPPORT = -4;
    public static final int ERROR_INVALID_PARAMETER = -5;
    public static final int ERROR_EXCEED_UPPER_LIMIT = -6;
    public static final int ERROR_VERSION_OUT_OF_DATE = -7;
    public static final int ERROR_NOT_EXIST = -8;
    public static final int ERROR_EMPTY_FILE = -9;
    public static final int DUPLICATE_DATA = -3;
    
    /**
     * Version alpha-0.28
     * <br /> Bug fix: Fix description column line separator modifying error.
     * <br /> New Feature: Add Undo feature in text editable columns.
     * <br /> New Feature: Allow user modifying start date or time of an item.
     */
    public static final String VERSION = "Ver. alpha-0.28";
}

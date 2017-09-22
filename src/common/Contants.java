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
     * Version alpha-0.27
     * <br /> Rename: Rename ItemDAO.findByTime() method to ItemDAO.findOne()
     * <br /> Domain update: Add column "seq" into Item domain. And consider column "seq" as one of its primary key.
     * <br /> New Feature: Allow creating Items with duplicated date and time.
     */
    public static final String VERSION = "Ver. alpha-0.27";
}

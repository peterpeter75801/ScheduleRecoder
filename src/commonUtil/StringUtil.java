package commonUtil;

public class StringUtil {
    
    public static boolean isNumber( String input ) {
        if( input == null || input.length() <= 0 ) {
            return false;
        }
        if( input.charAt( 0 ) != '-' && (input.charAt( 0 ) < '0' || input.charAt( 0 ) > '9') ) {
            return false;
        }
        for( int i = 1; i < input.length(); i++ ) {
            if( input.charAt( i ) < '0' || input.charAt( i ) > '9' ) {
                return false;
            }
        }
        return true;
    }
    
    public static String decreaseDateTimeTextFieldValue( String value, int minValue, int length ) {
        try {
            if( Integer.parseInt( value ) - 1 < minValue ) {
                return String.format( "%0" + ((length > 0) ? length : "") + "d", minValue );
            } else {
                return String.format( "%0" + ((length > 0) ? length : "") + "d", Integer.parseInt( value ) - 1 );
            }
        } catch( NumberFormatException e ) {
            return value;
        }
    }
    
    public static String increaseDateTimeTextFieldValue( String value, int maxValue, int length ) {
        try {
            if( Integer.parseInt( value ) + 1 > maxValue ) {
                return String.format( "%0" + ((length > 0) ? length : "") + "d", maxValue );
            } else {
                return String.format( "%0" + ((length > 0) ? length : "") + "d", Integer.parseInt( value ) + 1 );
            }
        } catch( NumberFormatException e ) {
            return value;
        }
    }
}

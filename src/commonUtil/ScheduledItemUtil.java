package commonUtil;

import java.util.Calendar;
import java.util.List;

import domain.ScheduledItem;

public class ScheduledItemUtil {
    
    private final static int ATTRIBUTE_NUMBER = 10;
    
    public static ScheduledItem getScheduledItemFromCsvTupleString( String tuple ) throws Exception {
        String[] csvDataArray = CsvFormatParser.parseFromTuple( tuple );
        ScheduledItem scheduledItem = new ScheduledItem();
        scheduledItem.setId( Integer.parseInt( csvDataArray[ 0 ] ) );
        scheduledItem.setYear( Integer.parseInt( csvDataArray[ 1 ] ) );
        scheduledItem.setMonth( Integer.parseInt( csvDataArray[ 2 ] ) );
        scheduledItem.setDay( Integer.parseInt( csvDataArray[ 3 ] ) );
        scheduledItem.setHour( Integer.parseInt( csvDataArray[ 4 ] ) );
        scheduledItem.setMinute( Integer.parseInt( csvDataArray[ 5 ] ) );
        scheduledItem.setExpectedTime( Integer.parseInt( csvDataArray[ 6 ] ) );
        scheduledItem.setType( csvDataArray[ 7 ].charAt( 0 ) );
        scheduledItem.setName( csvDataArray[ 8 ] );
        scheduledItem.setDescription( csvDataArray[ 9 ] );
        return scheduledItem;
    }
    
    public static String getCsvTupleStringFromScheduledItem( ScheduledItem scheduledItem ) {
        String[] csvDataArray = new String[ ATTRIBUTE_NUMBER ];
        csvDataArray[ 0 ] = CsvFormatParser.toCsvData( scheduledItem.getId() );
        csvDataArray[ 1 ] = CsvFormatParser.toCsvData( scheduledItem.getYear() );
        csvDataArray[ 2 ] = CsvFormatParser.toCsvData( scheduledItem.getMonth() );
        csvDataArray[ 3 ] = CsvFormatParser.toCsvData( scheduledItem.getDay() );
        csvDataArray[ 4 ] = CsvFormatParser.toCsvData( scheduledItem.getHour() );
        csvDataArray[ 5 ] = CsvFormatParser.toCsvData( scheduledItem.getMinute() );
        csvDataArray[ 6 ] = CsvFormatParser.toCsvData( scheduledItem.getExpectedTime() );
        csvDataArray[ 7 ] = CsvFormatParser.toCsvData( scheduledItem.getType() );
        csvDataArray[ 8 ] = CsvFormatParser.toCsvData( scheduledItem.getName() );
        csvDataArray[ 9 ] = CsvFormatParser.toCsvData( scheduledItem.getDescription() );
        return CsvFormatParser.mergeCsvDataToATuple( csvDataArray );
    }
    
    public static int compareToByTime( ScheduledItem sitem1, ScheduledItem sitem2 ) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        
        if( sitem1 == null && sitem2 == null ) {
            return 0;
        } else if( sitem1 != null && sitem2 == null ) {
            return 1;
        } else if( sitem1 == null && sitem2 != null ) {
            return -1;
        }
        
        calendar1.clear();
        calendar1.set( sitem1.getYear(), sitem1.getMonth(), sitem1.getDay(), sitem1.getHour(), sitem1.getMinute() );
        calendar2.clear();
        calendar2.set( sitem2.getYear(), sitem2.getMonth(), sitem2.getDay(), sitem2.getHour(), sitem2.getMinute() );
        
        @SuppressWarnings("unused")
        int test = calendar1.compareTo( calendar2 );
        
        return calendar1.compareTo( calendar2 );
    }
    
    public static List<ScheduledItem> sortByTime( List<ScheduledItem> scheduledItemList ) {
        if( scheduledItemList == null ) {
            return scheduledItemList;
        }
        for( int i = 1; i <= scheduledItemList.size(); i++ ) {
            for( int j = 0; j < scheduledItemList.size() - i; j++ ) {
                if( compareToByTime( scheduledItemList.get( j ), scheduledItemList.get( j + 1 ) ) > 0 ) {
                    ScheduledItem swap = scheduledItemList.get( j );
                    scheduledItemList.set( j, scheduledItemList.get( j + 1 ) );
                    scheduledItemList.set( j + 1, swap );
                }
            }
        }
        
        return scheduledItemList;
    }
    
    public static ScheduledItem copy( ScheduledItem sitem ) {
        if( sitem == null ) {
            return null;
        } else {
            ScheduledItem clone = new ScheduledItem();
            clone.setId( sitem.getId() );
            clone.setYear( sitem.getYear() );
            clone.setMonth( sitem.getMonth() );
            clone.setDay( sitem.getDay() );
            clone.setHour( sitem.getHour() );
            clone.setMinute( sitem.getMinute() );
            clone.setExpectedTime( sitem.getExpectedTime() );
            clone.setType( sitem.getType() );
            clone.setName( sitem.getName() );
            clone.setDescription( sitem.getDescription() );
            return clone;
        }
    }
    
    public static boolean equals( ScheduledItem scheduledItem1, ScheduledItem scheduledItem2 ) {
        if( scheduledItem1 == null && scheduledItem2 == null ) {
            return true;
        } else if( scheduledItem1 == null || scheduledItem2 == null ) {
            return false;
        } else if( ComparingUtil.compare( scheduledItem1.getId(), scheduledItem2.getId() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( scheduledItem1.getYear(), scheduledItem2.getYear() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( scheduledItem1.getMonth(), scheduledItem2.getMonth() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( scheduledItem1.getDay(), scheduledItem2.getDay() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( scheduledItem1.getHour(), scheduledItem2.getHour() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( scheduledItem1.getMinute(), scheduledItem2.getMinute() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( scheduledItem1.getExpectedTime(), scheduledItem2.getExpectedTime() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( scheduledItem1.getType(), scheduledItem2.getType() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( scheduledItem1.getName(), scheduledItem2.getName() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( scheduledItem1.getDescription(), scheduledItem2.getDescription() ) != 0 ) {
            return false;
        } else {
            return true;
        }
    }
}

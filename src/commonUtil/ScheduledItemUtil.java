package commonUtil;

import domain.ScheduledItem;

public class ScheduledItemUtil {
    
    private final static int ATTRIBUTE_NUMBER = 9;
    
    public static ScheduledItem getScheduledItemFromCsvTupleString( String tuple ) throws Exception {
        String[] csvDataArray = CsvFormatParser.parseFromTuple( tuple );
        ScheduledItem scheduledItem = new ScheduledItem();
        scheduledItem.setId( Integer.parseInt( csvDataArray[ 0 ] ) );
        scheduledItem.setYear( Integer.parseInt( csvDataArray[ 1 ] ) );
        scheduledItem.setMonth( Integer.parseInt( csvDataArray[ 2 ] ) );
        scheduledItem.setDay( Integer.parseInt( csvDataArray[ 3 ] ) );
        scheduledItem.setHour( Integer.parseInt( csvDataArray[ 4 ] ) );
        scheduledItem.setMinute( Integer.parseInt( csvDataArray[ 5 ] ) );
        scheduledItem.setType( csvDataArray[ 6 ].charAt( 0 ) );
        scheduledItem.setName( csvDataArray[ 7 ] );
        scheduledItem.setDescription( csvDataArray[ 8 ] );
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
        csvDataArray[ 6 ] = CsvFormatParser.toCsvData( scheduledItem.getType() );
        csvDataArray[ 7 ] = CsvFormatParser.toCsvData( scheduledItem.getName() );
        csvDataArray[ 8 ] = CsvFormatParser.toCsvData( scheduledItem.getDescription() );
        return CsvFormatParser.mergeCsvDataToATuple( csvDataArray );
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

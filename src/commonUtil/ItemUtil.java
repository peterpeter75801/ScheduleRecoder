package commonUtil;

import domain.Item;

public class ItemUtil {
    
    private final static int ATTRIBUTE_NUMBER = 9;
    
    public static Item getItemFromCsvTupleString( String tuple ) throws Exception {
        String[] csvDataArray = CsvFormatParser.parseFromTuple( tuple );
        Item item = new Item();
        item.setYear( Integer.parseInt( csvDataArray[ 0 ] ) );
        item.setMonth( Integer.parseInt( csvDataArray[ 1 ] ) );
        item.setDay( Integer.parseInt( csvDataArray[ 2 ] ) );
        item.setStartHour( Integer.parseInt( csvDataArray[ 3 ] ) );
        item.setStartMinute( Integer.parseInt( csvDataArray[ 4 ] ) );
        item.setEndHour( Integer.parseInt( csvDataArray[ 5 ] ) );
        item.setEndMinute( Integer.parseInt( csvDataArray[ 6 ] ) );
        item.setName( csvDataArray[ 7 ] );
        item.setDescription( csvDataArray[ 8 ] );
        return item;
    }
    
    public static String getCsvTupleStringFromItem( Item item ) {
        String[] csvDataArray = new String[ ATTRIBUTE_NUMBER ];
        csvDataArray[ 0 ] = CsvFormatParser.toCsvData( item.getYear() );
        csvDataArray[ 1 ] = CsvFormatParser.toCsvData( item.getMonth() );
        csvDataArray[ 2 ] = CsvFormatParser.toCsvData( item.getDay() );
        csvDataArray[ 3 ] = CsvFormatParser.toCsvData( item.getStartHour() );
        csvDataArray[ 4 ] = CsvFormatParser.toCsvData( item.getStartMinute() );
        csvDataArray[ 5 ] = CsvFormatParser.toCsvData( item.getEndHour() );
        csvDataArray[ 6 ] = CsvFormatParser.toCsvData( item.getEndMinute() );
        csvDataArray[ 7 ] = CsvFormatParser.toCsvData( item.getName() );
        csvDataArray[ 8 ] = CsvFormatParser.toCsvData( item.getDescription() );
        return CsvFormatParser.mergeCsvDataToATuple( csvDataArray );
    }
    
    public static boolean equals( Item item1, Item item2 ) {
        if( item1 == null && item2 == null ) {
            return true;
        } else if( item1 == null || item2 == null ) {
            return false;
        } else if( ComparingUtil.compare( item1.getYear(), item2.getYear() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( item1.getMonth(), item2.getMonth() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( item1.getDay(), item2.getDay() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( item1.getStartHour(), item2.getStartHour() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( item1.getStartMinute(), item2.getStartMinute() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( item1.getEndHour(), item2.getEndHour() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( item1.getEndMinute(), item2.getEndMinute() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( item1.getName(), item2.getName() ) != 0 ) {
            return false;
        } else if( ComparingUtil.compare( item1.getDescription(), item2.getDescription() ) != 0 ) {
            return false;
        } else {
            return true;
        }
    }
}

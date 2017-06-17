package commonUtil;

import java.util.ArrayList;
import java.util.List;

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
    
    public static String getItemCsvFileNameFromItem( Item item ) {
        return String.format( "%04d.%02d.%02d.csv", 
                item.getYear(), item.getMonth(), item.getDay());
    }
    
    public static String getItemCsvFileNameFromItem( int year, int month, int day ) {
        return String.format( "%04d.%02d.%02d.csv", year, month, day );
    }
    
    /**
     * 將輸入的指定格式TXT字串轉換為Item物件，TXT字串格式如下：
     * <table border="1">
     *   <tr><th>屬性</th><th>起始位置</th><th>長度</th><th>預設值</th></tr>
     *   <tr><td>startHour</td><td>0</td><td>2</td><td></td></tr>
     *   <tr><td>COLON</td><td>2</td><td>1</td><td>':'</td></tr>
     *   <tr><td>startMinute</td><td>3</td><td>2</td><td></td></tr>
     *   <tr><td>SPACE</td><td>5</td><td>1</td><td>' '</td></tr>
     *   <tr><td>TILDE</td><td>6</td><td>1</td><td>'~'</td></tr>
     *   <tr><td>SPACE</td><td>7</td><td>1</td><td>' '</td></tr>
     *   <tr><td>endHour</td><td>8</td><td>2</td><td></td></tr>
     *   <tr><td>COLON</td><td>10</td><td>1</td><td>':'</td></tr>
     *   <tr><td>endMinute</td><td>11</td><td>2</td><td></td></tr>
     *   <tr><td>SPACE</td><td>13</td><td>1</td><td>' '</td></tr>
     *   <tr><td>SPACE</td><td>14</td><td>1</td><td>' '</td></tr>
     *   <tr><td>name</td><td>15</td><td>不定</td><td></td></tr>
     * </table>
     * 
     * <br> Example:
     * <br> &nbsp;&nbsp; If input TXT string is:
     * <br> &nbsp;&nbsp;&nbsp;&nbsp; 12:00 ~ 12:10  test 
     * <br> &nbsp;&nbsp; Then the output item is:
     * <br> &nbsp;&nbsp;&nbsp;&nbsp; startHour: 12, startMinute: 00, endHour: 12, endMinute: 10, name: test
     */
    public static Item getItemFromTxtString( String txtString ) throws RuntimeException {
        Item item = new Item();
        item.setStartHour( Integer.parseInt( txtString.substring( 0, 2 ) ) );
        item.setStartMinute( Integer.parseInt( txtString.substring( 3, 5 ) ) );
        item.setEndHour( Integer.parseInt( txtString.substring( 8, 10 ) ) );
        item.setEndMinute( Integer.parseInt( txtString.substring( 11, 13 ) ) );
        item.setName( txtString.substring( 15 ) );
        return item;
    }
    
    /**
     * 將輸入的Item物件轉換為指定格式的TXT字串，TXT字串格式同getItemFromTxtString()方法的定義
     * <br> Example:
     * <br> &nbsp;&nbsp; If input item is:
     * <br> &nbsp;&nbsp;&nbsp;&nbsp; startHour: 12, startMinute: 00, endHour: 12, endMinute: 10, name: test
     * <br> &nbsp;&nbsp; Then the output TXT string will be:
     * <br> &nbsp;&nbsp;&nbsp;&nbsp; 12:00 ~ 12:10  test 
     */
    public static String getTxtStringFromItem( Item item ) {
        StringBuffer buffer = new StringBuffer();
        buffer.append( String.format( "%02d", item.getStartHour() ) );
        buffer.append( ':' );
        buffer.append( String.format( "%02d", item.getStartMinute() ) );
        buffer.append( " ~ " );
        buffer.append( String.format( "%02d", item.getEndHour() ) );
        buffer.append( ':' );
        buffer.append( String.format( "%02d", item.getEndMinute() ) );
        buffer.append( "  " );
        buffer.append( item.getName() );
        return buffer.toString();
    }
    
    public static int timeSubtract( int endHour, int endMinute, int startHour, int startMinute ) {
        if( (startHour*60 + startMinute) <= (endHour*60 + endMinute) ) {
            return (endHour*60 + endMinute) - (startHour*60 + startMinute);
        } else {
            return ((endHour+24)*60 + endMinute) - (startHour*60 + startMinute);
        }
    }
    
    public static int getSpecifiedNameIndexInItemList( List<Item> itemList, String name ) {
        if( name == null ) {
            return -1;
        }
        for( int i = 0; i < itemList.size(); i++ ) {
            if( itemList.get( i ) != null && itemList.get( i ).getName().equals( name ) ) {
                return i;
            }
        }
        return -1;
    }
    
    private static int getSpecifiedNameIndexInItemSpendingTimeList( List<ItemSpendingTime> itemSpendingTimeList, String name ) {
        if( name == null ) {
            return -1;
        }
        for( int i = 0; i < itemSpendingTimeList.size(); i++ ) {
            if( itemSpendingTimeList.get( i ) != null && itemSpendingTimeList.get( i ).getName().equals( name ) ) {
                return i;
            }
        }
        return -1;
    }
    
    private static List<ItemSpendingTime> sortItemSpendingTimeList( List<ItemSpendingTime> itemSpendingTimeList ) {
        for( int i = 1; i <= itemSpendingTimeList.size(); i++ ) {
            for( int j = 0; j < itemSpendingTimeList.size() - i; j++ ) {
                if( itemSpendingTimeList.get( j ).getSpendingTime() < itemSpendingTimeList.get( j + 1 ).getSpendingTime() ) {
                    ItemSpendingTime swap = itemSpendingTimeList.get( j );
                    itemSpendingTimeList.set( j, itemSpendingTimeList.get( j + 1 ) );
                    itemSpendingTimeList.set( j + 1, swap );
                }
            }
        }
        
        return itemSpendingTimeList;
    }
    
    public static String exportStatistics( List<Item> itemList ) {
        List<ItemSpendingTime> itemSpendingTimeList = new ArrayList<ItemSpendingTime>();
        StringBuffer statisticsStringBuf = new StringBuffer();
        
        for( Item item : itemList ) {
            int currentItemSpendingTime = timeSubtract( 
                item.getEndHour(), item.getEndMinute(), item.getStartHour(), item.getStartMinute() );
            int nameIndexInItemSpendingTimeList = 
                getSpecifiedNameIndexInItemSpendingTimeList( itemSpendingTimeList, item.getName() );
            if( nameIndexInItemSpendingTimeList == -1 ) {
                ItemSpendingTime itemSpendingTime = new ItemSpendingTime();
                itemSpendingTime.setName( item.getName() );
                itemSpendingTime.setSpendingTime( currentItemSpendingTime );
                itemSpendingTimeList.add( itemSpendingTime );
            } else {
                int existedItemSpendingTime = itemSpendingTimeList.get( nameIndexInItemSpendingTimeList ).getSpendingTime();
                itemSpendingTimeList.get( nameIndexInItemSpendingTimeList ).setSpendingTime(
                    currentItemSpendingTime + existedItemSpendingTime );
            }
        }
        
        itemSpendingTimeList = sortItemSpendingTimeList( itemSpendingTimeList );
        
        for( ItemSpendingTime itemSpendingTime : itemSpendingTimeList ) {
            statisticsStringBuf.append( itemSpendingTime.getName() + ", " );
            statisticsStringBuf.append( itemSpendingTime.getSpendingTime() );
            statisticsStringBuf.append( "(min)\n" );
        }
        
        return statisticsStringBuf.toString();
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
    
    private static class ItemSpendingTime {
        
        private String name;
        private int spendingTime;

        public void setName( String name ) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setSpendingTime( int spendingTime ) {
            this.spendingTime = spendingTime;
        }

        public int getSpendingTime() {
            return spendingTime;
        }
    }
}

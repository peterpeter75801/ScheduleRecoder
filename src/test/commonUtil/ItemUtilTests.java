package test.commonUtil;

import domain.Item;
import commonUtil.ItemUtil;

import junit.framework.TestCase;

public class ItemUtilTests extends TestCase {
    
    public void testGetItemFromCsvTupleString() {
        String input = "2017,4,21,13,0,16,0,\"撰寫時間記錄程式\",\"\"";
        Item expectedData = getTestData1();
        Item actualData = null;
        try {
            actualData = ItemUtil.getItemFromCsvTupleString( input );
            assertTrue( ItemUtil.equals( expectedData, actualData ) );
        } catch( Exception e ) {
            assertTrue( e.getMessage(), false );
        }
    }
    
    public void testGetCsvTupleStringFromItem() {
        Item input = getTestData1();
        String expectedTupleString = "2017,4,21,13,0,16,0,\"撰寫時間記錄程式\",\"\"";
        String actualTupleString = "";
        try {
            actualTupleString = ItemUtil.getCsvTupleStringFromItem( input );
            assertEquals( expectedTupleString, actualTupleString );
        } catch( Exception e ) {
            assertTrue( e.getMessage(), false );
        }
    }
    
    public void testEquals() {
        Item item1 = getTestData1();
        Item item2 = getTestData2();
        Item item3 = getTestData3();
        
        assertTrue( ItemUtil.equals( item1, item3 ) );
        assertFalse( ItemUtil.equals( item1, item2 ) );
    }
    
    private Item getTestData1() {
        Item testData = new Item();
        testData.setYear( 2017 );
        testData.setMonth( 4 );
        testData.setDay( 21 );
        testData.setStartHour( 13 );
        testData.setStartMinute( 0 );
        testData.setEndHour( 16 );
        testData.setEndMinute( 0 );
        testData.setName( "撰寫時間記錄程式" );
        testData.setDescription( "" );
        return testData;
    }
    
    private Item getTestData2() {
        Item testData = new Item();
        testData.setYear( 2017 );
        testData.setMonth( 4 );
        testData.setDay( 21 );
        testData.setStartHour( 16 );
        testData.setStartMinute( 10 );
        testData.setEndHour( 17 );
        testData.setEndMinute( 0 );
        testData.setName( "撰寫時間記錄程式" );
        testData.setDescription( "" );
        return testData;
    }
    
    private Item getTestData3() {
        Item testData = new Item();
        testData.setYear( 2017 );
        testData.setMonth( 4 );
        testData.setDay( 21 );
        testData.setStartHour( 13 );
        testData.setStartMinute( 0 );
        testData.setEndHour( 16 );
        testData.setEndMinute( 0 );
        testData.setName( "撰寫時間記錄程式" );
        testData.setDescription( "" );
        return testData;
    }
}

package test.commonUtil;

import domain.Item;

import java.util.ArrayList;
import java.util.List;

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
    
    public void testGetItemCsvFileNameFromItem() {
        Item item = getTestData1();
        String expected = "2017.04.21.csv";
        String actual = ItemUtil.getItemCsvFileNameFromItem( item );
        assertEquals( expected, actual );
    }
    
    public void testGetItemCsvFileNameFromItem2() {
        String expected = "2017.04.21.csv";
        String actual = ItemUtil.getItemCsvFileNameFromItem( 2017, 4, 21 );
        assertEquals( expected, actual );
    }
    
    public void testGetItemFromTxtString() {
        String input = "13:00 ~ 16:00  撰寫時間記錄程式";
        Item expected = new Item();
        expected.setStartHour( 13 );
        expected.setStartMinute( 0 );
        expected.setEndHour( 16 );
        expected.setEndMinute( 0 );
        expected.setName( "撰寫時間記錄程式" );
        Item actual = ItemUtil.getItemFromTxtString( input );
        
        assertTrue( ItemUtil.equals( expected, actual ) );
    }
    
    public void testGetTxtStringFromItem() {
        Item input = getTestData1();
        String expected = "13:00 ~ 16:00  撰寫時間記錄程式";
        String actual = ItemUtil.getTxtStringFromItem( input );
        
        assertEquals( expected, actual );
    }
    
    public void testTimeSubtract() {
        int inputEndHour = 10;
        int inputEndMinute = 20;
        int inputStartHour = 9;
        int inputStartMinute = 0;
        int expect = 80;
        int actual = ItemUtil.timeSubtract( inputEndHour, inputEndMinute, inputStartHour, inputStartMinute );
        assertEquals( expect, actual );

        int inputEndHour2 = 00;
        int inputEndMinute2 = 20;
        int inputStartHour2 = 23;
        int inputStartMinute2 = 0;
        int expect2 = 80;
        int actual2 = ItemUtil.timeSubtract( inputEndHour2, inputEndMinute2, inputStartHour2, inputStartMinute2 );
        assertEquals( expect2, actual2 );
    }
    
    public void testGetSpecifiedNameIndexInItemList() {
        List<Item> inputItemList = new ArrayList<Item>();
        inputItemList.add( getTestData3() );
        inputItemList.add( getTestData4() );
        inputItemList.add( getTestData5() );
        String inputName = "辦理國泰金融卡的問題";
        int expect = 2;
        int actual = ItemUtil.getSpecifiedNameIndexInItemList( inputItemList, inputName );
        assertEquals( expect, actual );
    }
    
    public void testExportStatistics() {
        List<Item> inputItemList = new ArrayList<Item>();
        inputItemList.add( getTestData2() );
        inputItemList.add( getTestData3() );
        inputItemList.add( getTestData4() );
        inputItemList.add( getTestData5() );
        String expect = "撰寫時間記錄程式, 230(min)\n洗碗, 35(min)\n辦理國泰金融卡的問題, 85(min)\n";
        String actual = ItemUtil.exportStatistics( inputItemList );
        assertEquals( expect, actual );
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
    
    private Item getTestData4() {
        Item testData = new Item();
        testData.setYear( 2017 );
        testData.setMonth( 4 );
        testData.setDay( 21 );
        testData.setStartHour( 10 );
        testData.setStartMinute( 5 );
        testData.setEndHour( 10 );
        testData.setEndMinute( 40 );
        testData.setName( "洗碗" );
        testData.setDescription( "" );
        return testData;
    }
    
    private Item getTestData5() {
        Item testData = new Item();
        testData.setYear( 2017 );
        testData.setMonth( 4 );
        testData.setDay( 21 );
        testData.setStartHour( 11 );
        testData.setStartMinute( 25 );
        testData.setEndHour( 12 );
        testData.setEndMinute( 50 );
        testData.setName( "辦理國泰金融卡的問題" );
        testData.setDescription( "" );
        return testData;
    }
}

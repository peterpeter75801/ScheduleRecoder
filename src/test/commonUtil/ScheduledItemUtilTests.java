package test.commonUtil;

import commonUtil.ScheduledItemUtil;
import domain.ScheduledItem;

import junit.framework.TestCase;

public class ScheduledItemUtilTests extends TestCase {
    
    public void testGetScheduledItemFromCsvTupleString() {
        String input = "1,2017,4,21,16,0,D,\"回覆聯合信用卡中心筆試信件\",\"\"";
        ScheduledItem expectedData = getTestData1();
        ScheduledItem actualData = null;
        try {
            actualData = ScheduledItemUtil.getScheduledItemFromCsvTupleString( input );
            assertTrue( ScheduledItemUtil.equals( expectedData, actualData ) );
        } catch( Exception e ) {
            assertTrue( e.getMessage(), false );
        }
    }
    
    public void testGetCsvTupleStringFromScheduledItem() {
        ScheduledItem input = getTestData1();
        String expectedTupleString = "1,2017,4,21,16,0,D,\"回覆聯合信用卡中心筆試信件\",\"\"";
        String actualTupleString = "";
        try {
            actualTupleString = ScheduledItemUtil.getCsvTupleStringFromScheduledItem( input );
            assertEquals( expectedTupleString, actualTupleString );
        } catch( Exception e ) {
            assertTrue( e.getMessage(), false );
        }
    }
    
    public void testEquals() {
        ScheduledItem scheduledItem1 = getTestData1();
        ScheduledItem scheduledItem2 = getTestData2();
        ScheduledItem scheduledItem3 = getTestData3();
        
        assertTrue( ScheduledItemUtil.equals( scheduledItem1, scheduledItem3 ) );
        assertFalse( ScheduledItemUtil.equals( scheduledItem1, scheduledItem2 ) );
    }
    
    private ScheduledItem getTestData1() {
        ScheduledItem testData = new ScheduledItem();
        testData.setId( 1 );
        testData.setYear( 2017 );
        testData.setMonth( 4 );
        testData.setDay( 21 );
        testData.setHour( 16 );
        testData.setMinute( 0 );
        testData.setType( 'D' );
        testData.setName( "回覆聯合信用卡中心筆試信件" );
        testData.setDescription( "" );
        return testData;
    }
    
    private ScheduledItem getTestData2() {
        ScheduledItem testData = new ScheduledItem();
        testData.setId( 1 );
        testData.setYear( 2017 );
        testData.setMonth( 4 );
        testData.setDay( 21 );
        testData.setHour( 17 );
        testData.setMinute( 0 );
        testData.setType( 'D' );
        testData.setName( "回覆聯合信用卡中心筆試信件" );
        testData.setDescription( "" );
        return testData;
    }
    
    private ScheduledItem getTestData3() {
        ScheduledItem testData = new ScheduledItem();
        testData.setId( 1 );
        testData.setYear( 2017 );
        testData.setMonth( 4 );
        testData.setDay( 21 );
        testData.setHour( 16 );
        testData.setMinute( 0 );
        testData.setType( 'D' );
        testData.setName( "回覆聯合信用卡中心筆試信件" );
        testData.setDescription( "" );
        return testData;
    }
}

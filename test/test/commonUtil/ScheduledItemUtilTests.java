package test.commonUtil;

import java.util.ArrayList;
import java.util.List;

import commonUtil.ScheduledItemUtil;
import domain.ScheduledItem;

import junit.framework.TestCase;

public class ScheduledItemUtilTests extends TestCase {
    
    public void testGetScheduledItemFromCsvTupleString() {
        String input = "1,2017,4,21,16,0,30,D,\"回覆聯合信用卡中心筆試信件\",\"\"";
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
        String expectedTupleString = "1,2017,4,21,16,0,30,D,\"回覆聯合信用卡中心筆試信件\",\"\"";
        String actualTupleString = "";
        try {
            actualTupleString = ScheduledItemUtil.getCsvTupleStringFromScheduledItem( input );
            assertEquals( expectedTupleString, actualTupleString );
        } catch( Exception e ) {
            assertTrue( e.getMessage(), false );
        }
    }
    
    public void testCompareToByTime() {
        assertTrue( ScheduledItemUtil.compareToByTime( getTestData1(), getTestData3() ) == 0 );
        assertTrue( ScheduledItemUtil.compareToByTime( getTestData3(), getTestData4() ) < 0 );
    }
    
    public void testSortByTime() {
        List<ScheduledItem> input = new ArrayList<ScheduledItem>();
        input.add( getTestData3() );
        input.add( getTestData4() );
        input.add( getTestData6() );
        input.add( getTestData5() );
        List<ScheduledItem> expect = new ArrayList<ScheduledItem>();
        expect.add( getTestData3() );
        expect.add( getTestData4() );
        expect.add( getTestData5() );
        expect.add( getTestData6() );
        
        input = ScheduledItemUtil.sortByTime( input );
        
        assertEquals( expect.size(), input.size() );
        for( int i = 0; i < expect.size(); i++ ) {
            assertTrue( ScheduledItemUtil.equals( expect.get( i ), input.get( i ) ) );
        }
    }
    
    public void testMoveTypeNDataToBottom() {
        List<ScheduledItem> input = new ArrayList<ScheduledItem>();
        input.add( getTestData3() );
        input.add( getTestData4() );
        input.add( getTestData5() );
        input.add( getTestData6() );
        List<ScheduledItem> expect = new ArrayList<ScheduledItem>();
        expect.add( getTestData3() );
        expect.add( getTestData4() );
        expect.add( getTestData6() );
        expect.add( getTestData5() );
        
        input = ScheduledItemUtil.moveTypeNDataToBottom( input );
        
        assertEquals( expect.size(), input.size() );
        for( int i = 0; i < expect.size(); i++ ) {
            assertTrue( ScheduledItemUtil.equals( expect.get( i ), input.get( i ) ) );
        }
    }
    
    public void testCopy() {
        ScheduledItem expect = getTestData1();
        ScheduledItem copy = ScheduledItemUtil.copy( getTestData3() );
        
        assertTrue( ScheduledItemUtil.equals( expect, copy ) );
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
        testData.setExpectedTime( 30 );
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
        testData.setExpectedTime( 30 );
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
        testData.setExpectedTime( 30 );
        testData.setType( 'D' );
        testData.setName( "回覆聯合信用卡中心筆試信件" );
        testData.setDescription( "" );
        return testData;
    }
    
    private ScheduledItem getTestData4() {
        ScheduledItem testData = new ScheduledItem();
        testData.setId( 2 );
        testData.setYear( 2017 );
        testData.setMonth( 5 );
        testData.setDay( 1 );
        testData.setHour( 11 );
        testData.setMinute( 25 );
        testData.setExpectedTime( 85 );
        testData.setType( 'O' );
        testData.setName( "辦理國泰金融卡的問題" );
        testData.setDescription( "" );
        return testData;
    }
    
    private ScheduledItem getTestData5() {
        ScheduledItem testData = new ScheduledItem();
        testData.setId( 3 );
        testData.setYear( 2017 );
        testData.setMonth( 5 );
        testData.setDay( 1 );
        testData.setHour( 13 );
        testData.setMinute( 0 );
        testData.setExpectedTime( 180 );
        testData.setType( 'N' );
        testData.setName( "撰寫時間記錄程式" );
        testData.setDescription( "" );
        return testData;
    }
    
    private ScheduledItem getTestData6() {
        ScheduledItem testData = new ScheduledItem();
        testData.setId( 4 );
        testData.setYear( 2017 );
        testData.setMonth( 5 );
        testData.setDay( 1 );
        testData.setHour( 17 );
        testData.setMinute( 0 );
        testData.setExpectedTime( 30 );
        testData.setType( 'D' );
        testData.setName( "洗碗" );
        testData.setDescription( "" );
        return testData;
    }
}

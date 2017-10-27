package test.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import commonUtil.ItemUtil;
import commonUtil.ScheduledItemUtil;
import domain.Item;
import domain.ScheduledItem;
import junit.framework.TestCase;
import service.Impl.ItemServiceImpl;
import service.Impl.ScheduledItemServiceImpl;

public class ScheduledItemServiceImplTests extends TestCase {
    
    private final String S_ITEM_CSV_FILE_PATH = "data\\ScheduledItem.csv";
    private final String S_ITEM_CSV_FILE_BACKUP_PATH = "data\\ScheduledItem_backup.csv";
    private final String S_ITEM_SEQ_FILE_PATH = "data\\ScheduledItemSeq.txt";
    private final String S_ITEM_SEQ_FILE_BACKUP_PATH = "data\\ScheduledItemSeq_backup.txt";
    
    public void testInsert() throws IOException {
        ScheduledItemServiceImpl scheduledItemServiceImpl = new ScheduledItemServiceImpl();
        ScheduledItem expectData1 = getTestData1();
        expectData1.setId( 1 );
        ScheduledItem expectData2 = getTestData2();
        expectData2.setId( 2 );
        ScheduledItem expectData3 = getTestData3();
        expectData3.setId( 3 );
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            
            scheduledItemServiceImpl.insert( getTestData1() );
            scheduledItemServiceImpl.insert( getTestData2() );
            scheduledItemServiceImpl.insert( getTestData3() );
            
            ScheduledItem actualData1 = scheduledItemServiceImpl.findById( 1 );
            assertTrue( ScheduledItemUtil.equals( expectData1, actualData1 ) );
            ScheduledItem actualData2 = scheduledItemServiceImpl.findById( 2 );
            assertTrue( ScheduledItemUtil.equals( expectData2, actualData2 ) );
            ScheduledItem actualData3 = scheduledItemServiceImpl.findById( 3 );
            assertTrue( ScheduledItemUtil.equals( expectData3, actualData3 ) );
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( S_ITEM_SEQ_FILE_BACKUP_PATH, S_ITEM_SEQ_FILE_PATH );
            restoreFile( S_ITEM_CSV_FILE_BACKUP_PATH, S_ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testUpdate() throws IOException {
        ScheduledItemServiceImpl scheduledItemServiceImpl = new ScheduledItemServiceImpl();
        ScheduledItem expectData1 = getTestData1();
        expectData1.setId( 1 );
        ScheduledItem expectData2 = getTestData2();
        expectData2.setId( 2 );
        ScheduledItem expectData3 = getTestData3();
        expectData3.setId( 3 );
        expectData3.setExpectedTime( 25 );
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            
            scheduledItemServiceImpl.insert( getTestData1() );
            scheduledItemServiceImpl.insert( getTestData2() );
            scheduledItemServiceImpl.insert( getTestData3() );
            
            ScheduledItem modifiedData = getTestData3();
            modifiedData.setId( 3 );
            modifiedData.setExpectedTime( 25 );
            scheduledItemServiceImpl.update( modifiedData );
            
            ScheduledItem actualData1 = scheduledItemServiceImpl.findById( 1 );
            assertTrue( ScheduledItemUtil.equals( expectData1, actualData1 ) );
            ScheduledItem actualData2 = scheduledItemServiceImpl.findById( 2 );
            assertTrue( ScheduledItemUtil.equals( expectData2, actualData2 ) );
            ScheduledItem actualData3 = scheduledItemServiceImpl.findById( 3 );
            assertTrue( ScheduledItemUtil.equals( expectData3, actualData3 ) );
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( S_ITEM_SEQ_FILE_BACKUP_PATH, S_ITEM_SEQ_FILE_PATH );
            restoreFile( S_ITEM_CSV_FILE_BACKUP_PATH, S_ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testDelete() throws IOException {
        ScheduledItemServiceImpl scheduledItemServiceImpl = new ScheduledItemServiceImpl();
        ScheduledItem expectData1 = null;
        ScheduledItem expectData2 = getTestData2();
        expectData2.setId( 2 );
        ScheduledItem expectData3 = getTestData3();
        expectData3.setId( 3 );
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            
            scheduledItemServiceImpl.insert( getTestData1() );
            scheduledItemServiceImpl.insert( getTestData2() );
            scheduledItemServiceImpl.insert( getTestData3() );
            
            ScheduledItem deletedData = getTestData1();
            deletedData.setId( 1 );
            scheduledItemServiceImpl.delete( deletedData );
            
            ScheduledItem actualData1 = scheduledItemServiceImpl.findById( 1 );
            assertTrue( ScheduledItemUtil.equals( expectData1, actualData1 ) );
            ScheduledItem actualData2 = scheduledItemServiceImpl.findById( 2 );
            assertTrue( ScheduledItemUtil.equals( expectData2, actualData2 ) );
            ScheduledItem actualData3 = scheduledItemServiceImpl.findById( 3 );
            assertTrue( ScheduledItemUtil.equals( expectData3, actualData3 ) );
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( S_ITEM_SEQ_FILE_BACKUP_PATH, S_ITEM_SEQ_FILE_PATH );
            restoreFile( S_ITEM_CSV_FILE_BACKUP_PATH, S_ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testFindAllSortByTime() throws IOException {
        ScheduledItemServiceImpl scheduledItemServiceImpl = new ScheduledItemServiceImpl();
        List<ScheduledItem> expectDataList = new ArrayList<ScheduledItem>();
        expectDataList.add( getTestData5() );
        expectDataList.get( expectDataList.size() - 1 ).setId( 5 );
        expectDataList.add( getTestData1() );
        expectDataList.get( expectDataList.size() - 1 ).setId( 1 );
        expectDataList.add( getTestData3() );
        expectDataList.get( expectDataList.size() - 1 ).setId( 3 );
        expectDataList.add( getTestData4() );
        expectDataList.get( expectDataList.size() - 1 ).setId( 4 );
        expectDataList.add( getTestData2() );
        expectDataList.get( expectDataList.size() - 1 ).setId( 2 );
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            
            scheduledItemServiceImpl.insert( getTestData1() );
            scheduledItemServiceImpl.insert( getTestData2() );
            scheduledItemServiceImpl.insert( getTestData3() );
            scheduledItemServiceImpl.insert( getTestData4() );
            scheduledItemServiceImpl.insert( getTestData5() );
            
            List<ScheduledItem> actualDataList = scheduledItemServiceImpl.findAllSortByTime();
            
            assertEquals( expectDataList.size(), actualDataList.size() );
            for( int i = 0; i < expectDataList.size(); i++ ) {
                assertTrue( ScheduledItemUtil.equals( expectDataList.get( i ), actualDataList.get( i ) ) );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( S_ITEM_SEQ_FILE_BACKUP_PATH, S_ITEM_SEQ_FILE_PATH );
            restoreFile( S_ITEM_CSV_FILE_BACKUP_PATH, S_ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testExecute() throws IOException {
        final String ITEM_CSV_FILE_PATH = "data\\Item\\2017.05.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH = "data\\Item\\2017.05.01_backup.csv";
        
        ItemServiceImpl itemServiceImpl = new ItemServiceImpl();
        ScheduledItemServiceImpl scheduledItemServiceImpl = new ScheduledItemServiceImpl();
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            scheduledItemServiceImpl.insert( getTestData1() );
            scheduledItemServiceImpl.insert( getTestData2() );
            scheduledItemServiceImpl.insert( getTestData3() );
            scheduledItemServiceImpl.insert( getTestData4() );
            scheduledItemServiceImpl.insert( getTestData5() );
            scheduledItemServiceImpl.insert( getTestData6() );
            
            ScheduledItem originalData1 = getTestData1();
            originalData1.setId( 1 );
            ScheduledItem originalData2 = getTestData2();
            originalData2.setId( 2 );
            ScheduledItem originalData3 = getTestData6();
            originalData3.setId( 6 );
            ScheduledItem completedData1 = getTestData1();
            completedData1.setName( "辦理國泰金融卡" );
            completedData1.setHour( 11 );
            completedData1.setMinute( 50 );
            ScheduledItem completedData2 = getTestData2();
            completedData2.setName( "撰寫時間記錄程式" );
            completedData2.setHour( 16 );
            completedData2.setMinute( 30 );
            ScheduledItem completedData3 = getTestData6();
            completedData3.setName( "下載更新檔案" );
            completedData3.setHour( 16 );
            completedData3.setMinute( 30 );
            
            scheduledItemServiceImpl.execute( originalData1, completedData1, true );
            scheduledItemServiceImpl.execute( originalData2, completedData2, false );
            scheduledItemServiceImpl.execute( originalData3, completedData3, true );
            
            List<Item> expectItemData = new ArrayList<Item>();
            for( int i = 1; i <= 3; i++ ) {
                Item item = new Item();
                item.setYear( 2017 );
                item.setMonth( 5 );
                item.setDay( 1 );
                switch( i ) {
                case 1:
                    item.setStartHour( 11 );
                    item.setStartMinute( 50 );
                    item.setSeq( 0 );
                    item.setEndHour( 11 );
                    item.setEndMinute( 50 );
                    item.setName( "辦理國泰金融卡" );
                    break;
                case 2:
                    item.setStartHour( 16 );
                    item.setStartMinute( 30 );
                    item.setSeq( 0 );
                    item.setEndHour( 16 );
                    item.setEndMinute( 30 );
                    item.setName( "撰寫時間記錄程式" );
                    break;
                case 3:
                    item.setStartHour( 16 );
                    item.setStartMinute( 30 );
                    item.setSeq( 1 );
                    item.setEndHour( 16 );
                    item.setEndMinute( 30 );
                    item.setName( "下載更新檔案" );
                    break;
                }
                item.setDescription( "" );
                expectItemData.add( item );
            }
            List<ScheduledItem> expectScheduledItemData = new ArrayList<ScheduledItem>();
            expectScheduledItemData.add( getTestData5() );
            expectScheduledItemData.get( expectScheduledItemData.size() - 1 ).setId( 5 );
            expectScheduledItemData.add( getTestData3() );
            expectScheduledItemData.get( expectScheduledItemData.size() - 1 ).setId( 3 );
            expectScheduledItemData.add( getTestData4() );
            expectScheduledItemData.get( expectScheduledItemData.size() - 1 ).setId( 4 );
            expectScheduledItemData.add( getTestData2() );
            expectScheduledItemData.get( expectScheduledItemData.size() - 1 ).setId( 2 );
            
            List<Item> actualItemData = itemServiceImpl.findByDate( 2017, 5, 1 );
            assertEquals( expectItemData.size(), actualItemData.size() );
            for( int i = 0; i < expectItemData.size(); i++ ) {
                assertTrue( "failed at i = " + i, ItemUtil.equals( expectItemData.get( i ), actualItemData.get( i ) ) );
            }
            List<ScheduledItem> actualScheduledItemData = scheduledItemServiceImpl.findAllSortByTime();
            assertEquals( expectScheduledItemData.size(), actualScheduledItemData.size() );
            for( int i = 0; i < expectScheduledItemData.size(); i++ ) {
                assertTrue( "failed at i = " + i, ScheduledItemUtil.equals( expectScheduledItemData.get( i ), actualScheduledItemData.get( i ) ) );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
            restoreFile( S_ITEM_SEQ_FILE_BACKUP_PATH, S_ITEM_SEQ_FILE_PATH );
            restoreFile( S_ITEM_CSV_FILE_BACKUP_PATH, S_ITEM_CSV_FILE_PATH );
        }
    }
    
    private ScheduledItem getTestData1() {
        ScheduledItem testData = new ScheduledItem();
        testData.setId( 0 );
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
    
    private ScheduledItem getTestData2() {
        ScheduledItem testData = new ScheduledItem();
        testData.setId( 0 );
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
    
    private ScheduledItem getTestData3() {
        ScheduledItem testData = new ScheduledItem();
        testData.setId( 0 );
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
    
    private ScheduledItem getTestData4() {
        ScheduledItem testData = new ScheduledItem();
        testData.setId( 0 );
        testData.setYear( -1 );
        testData.setMonth( -1 );
        testData.setDay( -1 );
        testData.setHour( -1 );
        testData.setMinute( -1 );
        testData.setExpectedTime( 20 );
        testData.setType( 'N' );
        testData.setName( "上op.gg研究艾希出裝" );
        testData.setDescription( "" );
        return testData;
    }
    
    private ScheduledItem getTestData5() {
        ScheduledItem testData = new ScheduledItem();
        testData.setId( 0 );
        testData.setYear( 2017 );
        testData.setMonth( 4 );
        testData.setDay( 23 );
        testData.setHour( 22 );
        testData.setMinute( 0 );
        testData.setExpectedTime( 10 );
        testData.setType( 'D' );
        testData.setName( "統一發票對獎" );
        testData.setDescription( "" );
        return testData;
    }
    
    private ScheduledItem getTestData6() {
        ScheduledItem testData = new ScheduledItem();
        testData.setId( 0 );
        testData.setYear( 2017 );
        testData.setMonth( 5 );
        testData.setDay( 1 );
        testData.setHour( 16 );
        testData.setMinute( 0 );
        testData.setExpectedTime( 30 );
        testData.setType( 'O' );
        testData.setName( "下載更新檔案" );
        testData.setDescription( "" );
        return testData;
    }
    
    private void backupFile( String filePath, String backupFilePath )
            throws IOException {
        File f = new File( filePath );
        if( f.exists() && !f.isDirectory() ) {
            f.renameTo( new File( backupFilePath ) );
        }
    }
   
    private void restoreFile( String backupFilePath, String filePath )
            throws IOException {
        File f = new File( filePath );
        if( f.exists() && !f.isDirectory() ) {
            f.delete();
        }
        f = new File( backupFilePath );
        if( f.exists() && !f.isDirectory() ) {
            f.renameTo( new File( filePath ) );
        }
    }
}

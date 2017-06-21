package test.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import commonUtil.ItemUtil;
import junit.framework.TestCase;

import domain.Item;
import service.Impl.ItemServiceImpl;

public class ItemServiceImplTests extends TestCase {
    
    private final String ITEM_CSV_FILE_PATH = "data\\Item\\2017.05.01.csv";
    private final String ITEM_CSV_FILE_BACKUP_PATH = "data\\Item\\2017.05.01_backup.csv";
    
    public void testInsert() throws IOException {
        ItemServiceImpl itemService = new ItemServiceImpl();
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            itemService.insert( getTestData2() );
            itemService.insert( getTestData3() );
            itemService.insert( getTestData1() );
            
            List<Item> expect = new ArrayList<Item>();
            expect.add( getTestData1() );
            expect.add( getTestData2() );
            expect.add( getTestData3() );
            List<Item> actual = itemService.findByDate( 2017, 5, 1 );
            
            assertEquals( expect.size(), actual.size() );
            for( int i = 0; i < expect.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect.get( i ), actual.get( i ) ) );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testInsertItemsInDateGroup() throws IOException {
        ItemServiceImpl itemService = new ItemServiceImpl();
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            List<Item> inputList = new ArrayList<Item>();
            inputList.add( getTestData2() );
            inputList.add( getTestData3() );
            inputList.add( getTestData1() );
            
            itemService.insertItemsInDateGroup( 2017, 5, 1, inputList );
            
            List<Item> expect = new ArrayList<Item>();
            expect.add( getTestData1() );
            expect.add( getTestData2() );
            expect.add( getTestData3() );
            List<Item> actual = itemService.findByDate( 2017, 5, 1 );
            
            assertEquals( expect.size(), actual.size() );
            for( int i = 0; i < expect.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect.get( i ), actual.get( i ) ) );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    private Item getTestData1() {
        Item testData = new Item();
        testData.setYear( 2017 );
        testData.setMonth( 5 );
        testData.setDay( 1 );
        testData.setStartHour( 11 );
        testData.setStartMinute( 25 );
        testData.setEndHour( 12 );
        testData.setEndMinute( 50 );
        testData.setName( "辦理國泰金融卡的問題" );
        testData.setDescription( "" );
        return testData;
    }
    
    private Item getTestData2() {
        Item testData = new Item();
        testData.setYear( 2017 );
        testData.setMonth( 5 );
        testData.setDay( 1 );
        testData.setStartHour( 13 );
        testData.setStartMinute( 0 );
        testData.setEndHour( 16 );
        testData.setEndMinute( 0 );
        testData.setName( "撰寫時間記錄程式" );
        testData.setDescription( "" );
        return testData;
    }
    
    private Item getTestData3() {
        Item testData = new Item();
        testData.setYear( 2017 );
        testData.setMonth( 5 );
        testData.setDay( 1 );
        testData.setStartHour( 16 );
        testData.setStartMinute( 5 );
        testData.setEndHour( 16 );
        testData.setEndMinute( 40 );
        testData.setName( "洗碗" );
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

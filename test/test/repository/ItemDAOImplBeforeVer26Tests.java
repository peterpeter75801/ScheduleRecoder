package test.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import commonUtil.ItemUtil;
import commonUtil.oldVersion.ItemUtilBeforeVer26;
import domain.Item;
import junit.framework.TestCase;
import repository.ItemDAO;
import repository.Impl.ItemDAOImplBeforeVer26;

public class ItemDAOImplBeforeVer26Tests extends TestCase {
    
    private final String ITEM_CSV_FILE_PATH = "./data/Item/2017.04.21.csv";
    private final String ITEM_CSV_FILE_BACKUP_PATH = "./data/Item/2017.04.21_backup.csv";
    private final String FILE_CHARSET = "big5";
    
    public void testInsert() throws IOException {
        ItemDAO itemDAO = new ItemDAOImplBeforeVer26();
        String[] expectedData = {
                "2017,4,21,11,25,12,50,\"辦理國泰金融卡的問題\",\"\"",
                "2017,4,21,13,0,16,0,\"撰寫時間記錄程式\",\"\"",
                "2017,4,21,16,5,16,40,\"洗碗\",\"\"" };
        String[] actualData = new String[ 3 ];
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            itemDAO.insert( getTestData1() );
            itemDAO.insert( getTestData2() );
            itemDAO.insert( getTestData3() );
            
            BufferedReader bufReader = new BufferedReader( new InputStreamReader(
                    new FileInputStream( new File( ITEM_CSV_FILE_PATH ) ),
                    FILE_CHARSET
                )
            );
            bufReader.readLine();    // skip attribute titles
            
            String currentTuple = "";
            int i = 0;
            for( ; (currentTuple = bufReader.readLine()) != null; i++ ) {
                actualData[ i ] = currentTuple;
            }
            bufReader.close();
            
            assertEquals( 3, i );
            for( i = 0; i < 3; i++ ) {
                assertEquals( "failed at i = " + i, expectedData[ i ], actualData[ i ] );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testFindByDate() throws IOException {
        ItemDAO itemDAO = new ItemDAOImplBeforeVer26();
        List<Item> expectedDataList = new ArrayList<Item>();
        expectedDataList.add( getTestData1() );
        expectedDataList.add( getTestData2() );
        expectedDataList.add( getTestData3() );
        List<Item> actualDataList = null;
        
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            itemDAO.insert( getTestData1() );
            itemDAO.insert( getTestData2() );
            itemDAO.insert( getTestData3() );
            
            actualDataList = itemDAO.findByDate( 2017, 4, 21 );
            assertEquals( 3, actualDataList.size() );
            for( int i = 0; i < 3; i++ ) {
                assertTrue( ItemUtilBeforeVer26.equals( expectedDataList.get( i ), actualDataList.get( i ) ) );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testDeleteByDate() throws IOException {
        final String ITEM_CSV_FILE_PATH_1 = "./data/Item/2017.06.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_1 = "./data/Item/2017.06.01_backup.csv";
        final String ITEM_CSV_FILE_PATH_2 = "./data/Item/2017.04.21.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_2 = "./data/Item/2017.04.21_backup.csv";
        
        ItemDAO itemDAO = new ItemDAOImplBeforeVer26();
        try {
            backupFile( ITEM_CSV_FILE_PATH_1, ITEM_CSV_FILE_BACKUP_PATH_1 );
            backupFile( ITEM_CSV_FILE_PATH_2, ITEM_CSV_FILE_BACKUP_PATH_2 );
            
            itemDAO.insert( getTestData1() );
            itemDAO.insert( getTestData2() );
            itemDAO.insert( getTestData3() );
            itemDAO.insert( getTestData4() );

            List<Item> expectedData1 = new ArrayList<Item>();
            List<Item> expectedData2 = new ArrayList<Item>();
            expectedData2.add( getTestData4() );
            
            itemDAO.deleteByDate( 2017, 4, 21 );
            
            List<Item> actualData1 = itemDAO.findByDate( 2017, 4, 21 );
            assertEquals( expectedData1.size(), actualData1.size() );
            for( int i = 0; i < expectedData1.size(); i++ ) {
                assertTrue( "failed at i = " + i, ItemUtil.equals( expectedData1.get( i ), actualData1.get( i ) ) );
            }
            
            List<Item> actualData2 = itemDAO.findByDate( 2017, 6, 1 );
            assertEquals( expectedData2.size(), actualData2.size() );
            for( int i = 0; i < expectedData2.size(); i++ ) {
                assertTrue( "failed at i = " + i, ItemUtil.equals( expectedData2.get( i ), actualData2.get( i ) ) );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH_2, ITEM_CSV_FILE_PATH_2 );
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH_1, ITEM_CSV_FILE_PATH_1 );
        }
    }
    
    private Item getTestData1() {
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
    
    private Item getTestData2() {
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
    
    private Item getTestData3() {
        Item testData = new Item();
        testData.setYear( 2017 );
        testData.setMonth( 4 );
        testData.setDay( 21 );
        testData.setStartHour( 16 );
        testData.setStartMinute( 5 );
        testData.setEndHour( 16 );
        testData.setEndMinute( 40 );
        testData.setName( "洗碗" );
        testData.setDescription( "" );
        return testData;
    }
    
    private Item getTestData4() {
        Item testData = new Item();
        testData.setYear( 2017 );
        testData.setMonth( 6 );
        testData.setDay( 1 );
        testData.setStartHour( 10 );
        testData.setStartMinute( 00 );
        testData.setEndHour( 10 );
        testData.setEndMinute( 00 );
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

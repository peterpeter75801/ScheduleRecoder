package test.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import commonUtil.ItemUtil;
import domain.Item;
import junit.framework.TestCase;
import repository.Impl.ItemDAOImpl;

public class ItemDAOImplTests extends TestCase {
    
    private final String ITEM_CSV_FILE_PATH = "data\\Item\\2017.04.21.csv";
    private final String ITEM_CSV_FILE_BACKUP_PATH = "data\\Item\\2017.04.21_backup.csv";
    private final String FILE_CHARSET = "big5";
    
    public void testInsert() {
        ItemDAOImpl itemDAO = new ItemDAOImpl();
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
            
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        } catch( Exception e ) {
            assertTrue( e.getMessage(), false );
        }
    }
    
    public void testFindByTime() {
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        Item expectedData = getTestData2();
        Item actualData = null;
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            itemDAO.insert( getTestData1() );
            itemDAO.insert( getTestData2() );
            itemDAO.insert( getTestData3() );
            
            actualData = itemDAO.findByTime( 2017, 4, 21, 13, 0 );
            assertTrue( ItemUtil.equals( expectedData, actualData ) );
            
            assertNull( itemDAO.findByTime( 2017, 4, 20, 0, 0 ) );
            assertNull( itemDAO.findByTime( 2017, 4, 21, 0, 0 ) );
            
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        } catch( Exception e ) {
            assertTrue( e.getMessage(), false );
        }
    }
    
    public void testUpdate() {
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        String[] expectedData = {
                "2017,4,21,11,25,12,50,\"辦理國泰金融卡的問題\",\"\"",
                "2017,4,21,13,0,16,30,\"撰寫時間記錄程式\",\"\"",
                "2017,4,21,16,5,16,40,\"洗碗\",\"\"" };
        String[] actualData = new String[ 3 ];
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );

            itemDAO.insert( getTestData1() );
            itemDAO.insert( getTestData2() );
            itemDAO.insert( getTestData3() );

            Item modifiedData = getTestData2();
            modifiedData.setEndMinute( 30 );

            itemDAO.update( modifiedData );

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
            
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        } catch( Exception e ) {
            assertTrue( e.getMessage(), false );
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

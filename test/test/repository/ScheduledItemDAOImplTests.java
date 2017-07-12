package test.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import commonUtil.ScheduledItemUtil;
import domain.ScheduledItem;
import junit.framework.TestCase;
import repository.Impl.ScheduledItemDAOImpl;

public class ScheduledItemDAOImplTests extends TestCase {
    
    private final String S_ITEM_CSV_FILE_PATH = "data\\ScheduledItem.csv";
    private final String S_ITEM_CSV_FILE_BACKUP_PATH = "data\\ScheduledItem_backup.csv";
    private final String S_ITEM_SEQ_FILE_PATH = "data\\ScheduledItemSeq.txt";
    private final String S_ITEM_SEQ_FILE_BACKUP_PATH = "data\\ScheduledItemSeq_backup.txt";
    private final String FILE_CHARSET = "big5";
    
    public void testInsert() throws IOException {
        ScheduledItemDAOImpl scheduledItemDAOImpl = new ScheduledItemDAOImpl();
        List<String> expectedData = new ArrayList<String>();
        expectedData.add( "1,2017,5,1,11,25,85,O,\"辦理國泰金融卡的問題\",\"\"" );
        expectedData.add( "2,2017,5,1,13,0,180,N,\"撰寫時間記錄程式\",\"\"" );
        expectedData.add( "3,2017,5,1,17,0,30,D,\"洗碗\",\"\"" );
        List<String> actualData = new ArrayList<String>();
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            
            scheduledItemDAOImpl.insert( getTestData1() );
            scheduledItemDAOImpl.insert( getTestData2() );
            scheduledItemDAOImpl.insert( getTestData3() );
            
            BufferedReader bufReader = new BufferedReader( new InputStreamReader(
                    new FileInputStream( new File( S_ITEM_CSV_FILE_PATH ) ),
                    FILE_CHARSET
                )
            );
            bufReader.readLine();    // skip attribute titles
            
            String currentTuple = "";
            while( (currentTuple = bufReader.readLine()) != null ){
                actualData.add( currentTuple );
            }
            bufReader.close();
            
            assertEquals( expectedData.size(), actualData.size() );
            for( int i = 0; i < expectedData.size(); i++ ) {
                assertEquals( "failed at i = " + i, expectedData.get( i ), actualData.get( i ) );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( S_ITEM_SEQ_FILE_BACKUP_PATH, S_ITEM_SEQ_FILE_PATH );
            restoreFile( S_ITEM_CSV_FILE_BACKUP_PATH, S_ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testFindAll() throws IOException {
        ScheduledItemDAOImpl scheduledItemDAOImpl = new ScheduledItemDAOImpl();
        List<ScheduledItem> expectDataList = new ArrayList<ScheduledItem>();
        expectDataList.add( getTestData1() );
        expectDataList.add( getTestData2() );
        expectDataList.add( getTestData3() );
        expectDataList.get( 0 ).setId( 1 );
        expectDataList.get( 1 ).setId( 2 );
        expectDataList.get( 2 ).setId( 3 );
        List<ScheduledItem> actualDataList = new ArrayList<ScheduledItem>();
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            
            scheduledItemDAOImpl.insert( getTestData1() );
            scheduledItemDAOImpl.insert( getTestData2() );
            scheduledItemDAOImpl.insert( getTestData3() );
            
            actualDataList = scheduledItemDAOImpl.findAll();
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
    
    public void testUpdate() throws IOException {
        ScheduledItemDAOImpl scheduledItemDAOImpl = new ScheduledItemDAOImpl();
        List<String> expectedData = new ArrayList<String>();
        expectedData.add( "1,2017,5,1,11,25,85,O,\"辦理國泰金融卡的問題\",\"\"" );
        expectedData.add( "2,2017,5,1,13,0,170,N,\"撰寫時間記錄程式\",\"\"" );
        expectedData.add( "3,2017,5,1,17,0,30,D,\"洗碗\",\"\"" );
        List<String> actualData = new ArrayList<String>();
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            
            scheduledItemDAOImpl.insert( getTestData1() );
            scheduledItemDAOImpl.insert( getTestData2() );
            scheduledItemDAOImpl.insert( getTestData3() );
            
            ScheduledItem modifiedData = getTestData2();
            modifiedData.setId( 2 );
            modifiedData.setExpectedTime( 170 );
            
            scheduledItemDAOImpl.update( modifiedData );
            
            BufferedReader bufReader = new BufferedReader( new InputStreamReader(
                    new FileInputStream( new File( S_ITEM_CSV_FILE_PATH ) ),
                    FILE_CHARSET
                )
            );
            bufReader.readLine();    // skip attribute titles
            
            String currentTuple = "";
            while( (currentTuple = bufReader.readLine()) != null ){
                actualData.add( currentTuple );
            }
            bufReader.close();
            
            assertEquals( expectedData.size(), actualData.size() );
            for( int i = 0; i < expectedData.size(); i++ ) {
                assertEquals( "failed at i = " + i, expectedData.get( i ), actualData.get( i ) );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( S_ITEM_SEQ_FILE_BACKUP_PATH, S_ITEM_SEQ_FILE_PATH );
            restoreFile( S_ITEM_CSV_FILE_BACKUP_PATH, S_ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testDelete() throws IOException {
        ScheduledItemDAOImpl scheduledItemDAOImpl = new ScheduledItemDAOImpl();
        List<String> expectedData = new ArrayList<String>();
        expectedData.add( "2,2017,5,1,13,0,180,N,\"撰寫時間記錄程式\",\"\"" );
        expectedData.add( "3,2017,5,1,17,0,30,D,\"洗碗\",\"\"" );
        List<String> actualData = new ArrayList<String>();
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            
            scheduledItemDAOImpl.insert( getTestData1() );
            scheduledItemDAOImpl.insert( getTestData2() );
            scheduledItemDAOImpl.insert( getTestData3() );
            
            ScheduledItem deletedData = getTestData1();
            deletedData.setId( 1 );
            
            scheduledItemDAOImpl.delete( deletedData );
            
            BufferedReader bufReader = new BufferedReader( new InputStreamReader(
                    new FileInputStream( new File( S_ITEM_CSV_FILE_PATH ) ),
                    FILE_CHARSET
                )
            );
            bufReader.readLine();    // skip attribute titles
            
            String currentTuple = "";
            while( (currentTuple = bufReader.readLine()) != null ){
                actualData.add( currentTuple );
            }
            bufReader.close();
            
            assertEquals( expectedData.size(), actualData.size() );
            for( int i = 0; i < expectedData.size(); i++ ) {
                assertEquals( "failed at i = " + i, expectedData.get( i ), actualData.get( i ) );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
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

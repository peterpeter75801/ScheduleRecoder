package test.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import commonUtil.ItemUtil;
import domain.Item;
import junit.framework.TestCase;
import repository.Impl.ItemDAOImpl;

public class ItemDAOImplTests extends TestCase {
    
    private final String ITEM_CSV_FILE_PATH = "data\\Item\\2017.04.21.csv";
    private final String ITEM_CSV_FILE_BACKUP_PATH = "data\\Item\\2017.04.21_backup.csv";
    private final String FILE_CHARSET = "big5";
    
    public void testInsert() throws IOException {
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
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testFindByTime() throws IOException {
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
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testFindByDate() throws IOException {
        ItemDAOImpl itemDAO = new ItemDAOImpl();
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
                assertTrue( ItemUtil.equals( expectedDataList.get( i ), actualDataList.get( i ) ) );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testUpdate() throws IOException {
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
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testDelete() throws IOException {
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        List<String> expectedData = new ArrayList<String>();
        expectedData.add( "2017,4,21,11,25,12,50,\"辦理國泰金融卡的問題\",\"\"" );
        expectedData.add( "2017,4,21,16,5,16,40,\"洗碗\",\"\"" );
        List<String> actualData = new ArrayList<String>();

        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );

            itemDAO.insert( getTestData1() );
            itemDAO.insert( getTestData2() );
            itemDAO.insert( getTestData3() );

            Item deletedData = getTestData2();

            itemDAO.delete( deletedData );

            BufferedReader bufReader = new BufferedReader( new InputStreamReader(
                    new FileInputStream( new File( ITEM_CSV_FILE_PATH ) ),
                    FILE_CHARSET
                )
            );
            bufReader.readLine();    // skip attribute titles
            
            String currentTuple = "";
            int i = 0;
            for( ; (currentTuple = bufReader.readLine()) != null; i++ ) {
                actualData.add( currentTuple );
            }
            bufReader.close();
            
            assertEquals( 2, i );
            for( i = 0; i < 2; i++ ) {
                assertEquals( "failed at i = " + i, expectedData.get( i ), actualData.get( i ) );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testSortByStartTimeInDateGroup() throws IOException {
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        String[] expectedData = {
                "2017,4,21,11,25,12,50,\"辦理國泰金融卡的問題\",\"\"",
                "2017,4,21,13,0,16,0,\"撰寫時間記錄程式\",\"\"",
                "2017,4,21,16,5,16,40,\"洗碗\",\"\"" };
        String[] actualData = new String[ 3 ];
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );

            itemDAO.insert( getTestData3() );
            itemDAO.insert( getTestData1() );
            itemDAO.insert( getTestData2() );
            
            itemDAO.sortByStartTimeInDateGroup( 2017, 4, 21 );
            
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

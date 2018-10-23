package test.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import common.Contants;
import commonUtil.ItemUtil;
import domain.Item;
import junit.framework.TestCase;
import repository.ItemDAO;
import repository.Impl.ItemDAOImpl;
import repository.Impl.ItemDAOImplBeforeVer26;

public class ItemDAOImplTests extends TestCase {
    
    private final String ITEM_CSV_FILE_PATH = "./data/Item/2017.04.21.csv";
    private final String ITEM_CSV_FILE_BACKUP_PATH = "./data/Item/2017.04.21_backup.csv";
    private final String FILE_CHARSET = "big5";
    
    public void testInsert() throws IOException {
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        String[] expectedData = {
                "2017,4,21,11,25,0,12,50,\"辦理國泰金融卡的問題\",\"\"",
                "2017,4,21,13,0,0,16,0,\"撰寫時間記錄程式\",\"\"",
                "2017,4,21,16,5,0,16,40,\"洗碗\",\"\"" };
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
    
    public void testFindOne() throws IOException {
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        Item expectedData = getTestData2();
        Item actualData = null;
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            itemDAO.insert( getTestData1() );
            itemDAO.insert( getTestData2() );
            itemDAO.insert( getTestData3() );
            
            actualData = itemDAO.findOne( 2017, 4, 21, 13, 0, 0 );
            assertTrue( ItemUtil.equals( expectedData, actualData ) );
            
            assertNull( itemDAO.findOne( 2017, 4, 20, 0, 0, 0 ) );
            assertNull( itemDAO.findOne( 2017, 4, 21, 0, 0, 0 ) );
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
                "2017,4,21,11,25,0,12,50,\"辦理國泰金融卡的問題\",\"\"",
                "2017,4,21,13,0,0,16,30,\"撰寫時間記錄程式\",\"\"",
                "2017,4,21,16,5,0,16,40,\"洗碗\",\"\"" };
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
        expectedData.add( "2017,4,21,11,25,0,12,50,\"辦理國泰金融卡的問題\",\"\"" );
        expectedData.add( "2017,4,21,16,5,0,16,40,\"洗碗\",\"\"" );
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
    
    public void testDeleteByDate() throws IOException {
        final String ITEM_CSV_FILE_PATH_1 = "./data/Item/2017.06.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_1 = "./data/Item/2017.06.01_backup.csv";
        final String ITEM_CSV_FILE_PATH_2 = "./data/Item/2017.04.21.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_2 = "./data/Item/2017.04.21_backup.csv";
        
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        try {
            backupFile( ITEM_CSV_FILE_PATH_1, ITEM_CSV_FILE_BACKUP_PATH_1 );
            backupFile( ITEM_CSV_FILE_PATH_2, ITEM_CSV_FILE_BACKUP_PATH_2 );
            
            itemDAO.insert( getTestData1() );
            itemDAO.insert( getTestData2() );
            itemDAO.insert( getTestData3() );
            itemDAO.insert( getTestData5() );

            List<Item> expectedData1 = new ArrayList<Item>();
            List<Item> expectedData2 = new ArrayList<Item>();
            expectedData2.add( getTestData5() );
            
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
    
    public void SortByStartTimeInDateGroup() throws IOException {
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        String[] expectedData = {
                "2017,4,21,11,25,0,12,50,\"辦理國泰金融卡的問題\",\"\"",
                "2017,4,21,13,0,0,16,0,\"撰寫時間記錄程式\",\"\"",
                "2017,4,21,13,0,1,13,30,\"下載更新檔案\",\"\"",
                "2017,4,21,16,5,0,16,40,\"洗碗\",\"\"" };
        String[] actualData = new String[ 4 ];
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );

            itemDAO.insert( getTestData4() );
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
            
            assertEquals( 4, i );
            for( i = 0; i < 4; i++ ) {
                assertEquals( "failed at i = " + i, expectedData[ i ], actualData[ i ] );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testListAllDateContainingData() throws IOException {
        final String ITEM_CSV_FILE_PATH_1 = "./data/Item/2017.06.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_1 = "./data/Item/2017.06.01_backup.csv";
        final String ITEM_CSV_FILE_PATH_2 = "./data/Item/2017.06.03.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_2 = "./data/Item/2017.06.03_backup.csv";
        final String ITEM_CSV_FILE_PATH_3 = "./data/Item/2017.05.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_3 = "./data/Item/2017.05.01_backup.csv";
        
        ItemDAOImpl itemDAO = new ItemDAOImpl();
        try {
            backupFile( ITEM_CSV_FILE_PATH_1, ITEM_CSV_FILE_BACKUP_PATH_1 );
            backupFile( ITEM_CSV_FILE_PATH_2, ITEM_CSV_FILE_BACKUP_PATH_2 );
            backupFile( ITEM_CSV_FILE_PATH_3, ITEM_CSV_FILE_BACKUP_PATH_3 );
            
            List<String> expect = new ArrayList<String>();
            expect.add( "2017.05.01" );
            expect.add( "2017.06.01" );
            expect.add( "2017.06.03" );
            
            Item input1 = getTestData5();
            Item input2 = getTestData5();
            input2.setMonth( 5 );
            Item input3 = getTestData5();
            input3.setMonth( 6 );
            input3.setDay( 3 );
            
            itemDAO.insert( input1 );
            itemDAO.insert( input2 );
            itemDAO.insert( input3 );
            
            List<String> actual = itemDAO.listAllDateContainingData();
            
            assertEquals( expect.size(), actual.size() );
            for( int i = 0; i < expect.size(); i++ ) {
                //assertEquals( expect.get( i ), actual.get( i ) );
            	assertTrue( expect.contains( actual.get( i ) ) );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH_3, ITEM_CSV_FILE_PATH_3 );
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH_2, ITEM_CSV_FILE_PATH_2 );
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH_1, ITEM_CSV_FILE_PATH_1 );
        }
    }
    
    public void testCheckItemDataVersion() throws IOException {
        final String ITEM_CSV_FILE_PATH_1 = "./data/Item/2017.06.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_1 = "./data/Item/2017.06.01_backup.csv";
        final String ITEM_CSV_FILE_PATH_2 = "./data/Item/2017.04.21.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_2 = "./data/Item/2017.04.21_backup.csv";
        
        ItemDAO itemDAO = new ItemDAOImpl();
        ItemDAO itemDAOBeforeVer26 = new ItemDAOImplBeforeVer26();
        try {
            backupFile( ITEM_CSV_FILE_PATH_1, ITEM_CSV_FILE_BACKUP_PATH_1 );
            backupFile( ITEM_CSV_FILE_PATH_2, ITEM_CSV_FILE_BACKUP_PATH_2 );
            
            itemDAO.insert( getTestData1() );
            itemDAOBeforeVer26.insert( getTestData5() );
            
            int returnCode1 = itemDAO.checkItemDataVersion( 2017, 4, 21 );
            int returnCode2 = itemDAO.checkItemDataVersion( 2017, 6, 1 );
            assertEquals( Contants.SUCCESS, returnCode1 );
            assertEquals( Contants.ERROR_VERSION_OUT_OF_DATE, returnCode2 );
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
        testData.setSeq( 0 );
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
        testData.setSeq( 0 );
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
        testData.setSeq( 0 );
        testData.setEndHour( 16 );
        testData.setEndMinute( 40 );
        testData.setName( "洗碗" );
        testData.setDescription( "" );
        return testData;
    }
    
    private Item getTestData4() {
        Item testData = new Item();
        testData.setYear( 2017 );
        testData.setMonth( 4 );
        testData.setDay( 21 );
        testData.setStartHour( 13 );
        testData.setStartMinute( 0 );
        testData.setSeq( 1 );
        testData.setEndHour( 13 );
        testData.setEndMinute( 30 );
        testData.setName( "下載更新檔案" );
        testData.setDescription( "" );
        return testData;
    }
    
    private Item getTestData5() {
        Item testData = new Item();
        testData.setYear( 2017 );
        testData.setMonth( 6 );
        testData.setDay( 1 );
        testData.setStartHour( 10 );
        testData.setStartMinute( 00 );
        testData.setSeq( 0 );
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

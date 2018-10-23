package test.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import commonUtil.ItemUtil;
import junit.framework.TestCase;
import repository.ItemDAO;
import repository.Impl.ItemDAOImplBeforeVer26;
import domain.Item;
import service.ItemService;
import service.Impl.ItemServiceImpl;

public class ItemServiceImplTests extends TestCase {
    
    private final String ITEM_CSV_FILE_PATH = "./data/Item/2017.05.01.csv";
    private final String ITEM_CSV_FILE_BACKUP_PATH = "./data/Item/2017.05.01_backup.csv";
    
    public void testInsert() throws IOException {
        ItemService itemService = new ItemServiceImpl();
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            itemService.insert( getTestData2() );
            itemService.insert( getTestData3() );
            itemService.insert( getTestData1() );
            
            List<Item> expect = new ArrayList<Item>();
            expect.add( getTestData1() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            expect.add( getTestData2() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            expect.add( getTestData3() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
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
    
    public void testInsertDataWithSameTime() throws IOException {
        ItemService itemService = new ItemServiceImpl();
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            List<Item> expect = new ArrayList<Item>();
            expect.add( getTestData1() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            expect.add( getTestData2() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            expect.add( getTestData4() );
            expect.get( expect.size() - 1 ).setSeq( 1 );
            expect.add( getTestData3() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            
            itemService.insert( getTestData1() );
            itemService.insert( getTestData2() );
            itemService.insert( getTestData3() );
            itemService.insert( getTestData4() );
            
            List<Item> actual = itemService.findByDate( 2017, 5, 1 );
            
            assertEquals( expect.size(), actual.size() );
            for( int i = 0; i < expect.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect.get( i ), actual.get( i ) ) );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testInsertItemsInDateGroup() throws IOException {
        ItemService itemService = new ItemServiceImpl();
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            List<Item> inputList = new ArrayList<Item>();
            inputList.add( getTestData2() );
            inputList.add( getTestData3() );
            inputList.add( getTestData1() );
            
            itemService.insertItemsInDateGroup( 2017, 5, 1, inputList );
            
            List<Item> expect = new ArrayList<Item>();
            expect.add( getTestData1() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            expect.add( getTestData2() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            expect.add( getTestData3() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
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
    
    public void testUpdate() throws IOException {
        ItemService itemService = new ItemServiceImpl();
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );

            itemService.insert( getTestData1() );
            itemService.insert( getTestData2() );
            itemService.insert( getTestData3() );
            
            List<Item> expect = new ArrayList<Item>();
            expect.add( getTestData1() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            expect.add( getTestData2() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            expect.get( expect.size() - 1 ).setEndHour( 17 );
            expect.add( getTestData3() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            
            Item modifiedData = getTestData2();
            modifiedData.setSeq( 0 );
            modifiedData.setEndHour( 17 );
            itemService.update( getTestData2(), modifiedData );
            
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
    
    public void testUpdateDataWithSameTime() throws IOException {
        ItemService itemService = new ItemServiceImpl();
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            List<Item> expect = new ArrayList<Item>();
            expect.add( getTestData1() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            expect.add( getTestData2() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            expect.get( expect.size() - 1 ).setEndHour( 17 );
            expect.add( getTestData4() );
            expect.get( expect.size() - 1 ).setSeq( 1 );
            
            itemService.insert( getTestData1() );
            itemService.insert( getTestData2() );
            itemService.insert( getTestData4() );
            
            Item modifiedData = getTestData2();
            modifiedData.setSeq( 0 );
            modifiedData.setEndHour( 17 );
            itemService.update( getTestData2(), modifiedData );
            
            List<Item> actual = itemService.findByDate( 2017, 5, 1 );
            
            assertEquals( expect.size(), actual.size() );
            for( int i = 0; i < expect.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect.get( i ), actual.get( i ) ) );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testUpdateStartTime() throws IOException {
        ItemService itemService = new ItemServiceImpl();
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            itemService.insert( getTestData1() );
            itemService.insert( getTestData2() );
            itemService.insert( getTestData3() );
            
            List<Item> expect = new ArrayList<Item>();
            expect.add( getTestData1() );
            expect.get( expect.size() - 1 ).setStartHour( 12 );
            expect.add( getTestData2() );
            expect.add( getTestData3() );
            
            Item modifiedData = getTestData1();
            modifiedData.setSeq( 0 );
            modifiedData.setStartHour( 12 );
            itemService.update( getTestData1(), modifiedData );
            
            List<Item> actual = itemService.findByDate( 2017, 5, 1 );
            for( int i = 0; i < expect.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect.get( i ), actual.get( i ) ) );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testUpdateStartDate() throws IOException {
        final String ITEM_CSV_FILE_PATH_1 = "./data/Item/2017.06.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_1 = "./data/Item/2017.06.01_backup.csv";
        final String ITEM_CSV_FILE_PATH_2 = "./data/Item/2017.05.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_2 = "./data/Item/2017.05.01_backup.csv";
        
        ItemService itemService = new ItemServiceImpl();
        try {
            backupFile( ITEM_CSV_FILE_PATH_1, ITEM_CSV_FILE_BACKUP_PATH_1 );
            backupFile( ITEM_CSV_FILE_PATH_2, ITEM_CSV_FILE_BACKUP_PATH_2 );
            
            itemService.insert( getTestData1() );
            itemService.insert( getTestData2() );
            itemService.insert( getTestData3() );
            
            List<Item> expect1 = new ArrayList<Item>();
            expect1.add( getTestData2() );
            expect1.add( getTestData3() );
            
            List<Item> expect2 = new ArrayList<Item>();
            expect2.add( getTestData1() );
            expect2.get( expect2.size() - 1 ).setMonth( 6 );
            
            Item modifiedData = getTestData1();
            modifiedData.setSeq( 0 );
            modifiedData.setMonth( 6 );
            itemService.update( getTestData1(), modifiedData );
            
            List<Item> actual1 = itemService.findByDate( 2017, 5, 1 );
            for( int i = 0; i < expect1.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect1.get( i ), actual1.get( i ) ) );
            }
            
            List<Item> actual2 = itemService.findByDate( 2017, 6, 1 );
            for( int i = 0; i < expect2.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect2.get( i ), actual2.get( i ) ) );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH_2, ITEM_CSV_FILE_PATH_2 );
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH_1, ITEM_CSV_FILE_PATH_1 );
        }
    }
    
    public void testDelete() throws IOException {
        ItemService itemService = new ItemServiceImpl();
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );

            itemService.insert( getTestData1() );
            itemService.insert( getTestData2() );
            itemService.insert( getTestData3() );
            
            List<Item> expect = new ArrayList<Item>();
            expect.add( getTestData2() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            expect.add( getTestData3() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            
            Item deletedData = getTestData1();
            deletedData.setSeq( 0 );
            itemService.delete( deletedData );
            
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
    
    public void testDeleteDataWithSameTime() throws IOException {
        ItemService itemService = new ItemServiceImpl();
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            List<Item> expect = new ArrayList<Item>();
            expect.add( getTestData1() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            expect.add( getTestData2() );
            expect.get( expect.size() - 1 ).setSeq( 0 );
            
            itemService.insert( getTestData1() );
            itemService.insert( getTestData2() );
            itemService.insert( getTestData4() );
            
            Item deletedData = getTestData4();
            deletedData.setSeq( 1 );
            itemService.delete( deletedData );
            
            List<Item> actual = itemService.findByDate( 2017, 5, 1 );
            
            assertEquals( expect.size(), actual.size() );
            for( int i = 0; i < expect.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect.get( i ), actual.get( i ) ) );
            }
        } catch( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testConvertOldItemDataToCurrentVersion() throws IOException {
        final String ITEM_CSV_FILE_PATH_1 = "./data/Item/2017.06.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_1 = "./data/Item/2017.06.01_backup.csv";
        final String ITEM_CSV_FILE_PATH_2 = "./data/Item/2017.05.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_2 = "./data/Item/2017.05.01_backup.csv";
        final String ITEM_CSV_FILE_PATH_3 = "./data/Item/2017.06.03.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_3 = "./data/Item/2017.06.03_backup.csv";
        
        ItemDAO itemDAOBeforeVer26 = new ItemDAOImplBeforeVer26();
        ItemService itemService = new ItemServiceImpl();
        try {
            backupFile( ITEM_CSV_FILE_PATH_1, ITEM_CSV_FILE_BACKUP_PATH_1 );
            backupFile( ITEM_CSV_FILE_PATH_2, ITEM_CSV_FILE_BACKUP_PATH_2 );
            backupFile( ITEM_CSV_FILE_PATH_3, ITEM_CSV_FILE_BACKUP_PATH_3 );
            
            itemDAOBeforeVer26.insert( getTestData1() );
            itemDAOBeforeVer26.insert( getTestData2() );
            itemDAOBeforeVer26.insert( getTestData3() );
            itemDAOBeforeVer26.insert( getTestData5() );
            itemDAOBeforeVer26.insert( getTestData6() );
            
            String returnMsg = itemService.convertOldItemDataToCurrentVersion();
            assertEquals( "Success", returnMsg );
            
            List<Item> expect1 = new ArrayList<Item>();
            expect1.add( getTestData1() );
            expect1.add( getTestData2() );
            expect1.add( getTestData3() );
            List<Item> expect2 = new ArrayList<Item>();
            expect2.add( getTestData5() );
            List<Item> expect3 = new ArrayList<Item>();
            expect3.add( getTestData6() );
            
            List<Item> actual1 = itemService.findByDate( 2017, 5, 1 );
            assertEquals( expect1.size(), actual1.size() );
            for( int i = 0; i < expect1.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect1.get( i ), actual1.get( i ) ) );
            }
            
            List<Item> actual2 = itemService.findByDate( 2017, 6, 1 );
            assertEquals( expect2.size(), actual2.size() );
            for( int i = 0; i < expect2.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect2.get( i ), actual2.get( i ) ) );
            }
            
            List<Item> actual3 = itemService.findByDate( 2017, 6, 3 );
            assertEquals( expect3.size(), actual3.size() );
            for( int i = 0; i < expect3.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect3.get( i ), actual3.get( i ) ) );
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
    
    private Item getTestData1() {
        Item testData = new Item();
        testData.setYear( 2017 );
        testData.setMonth( 5 );
        testData.setDay( 1 );
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
        testData.setMonth( 5 );
        testData.setDay( 1 );
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
        testData.setMonth( 5 );
        testData.setDay( 1 );
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
        testData.setMonth( 5 );
        testData.setDay( 1 );
        testData.setStartHour( 13 );
        testData.setStartMinute( 0 );
        testData.setSeq( 0 );
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
    
    private Item getTestData6() {
        Item testData = new Item();
        testData.setYear( 2017 );
        testData.setMonth( 6 );
        testData.setDay( 3 );
        testData.setStartHour( 13 );
        testData.setStartMinute( 0 );
        testData.setSeq( 0 );
        testData.setEndHour( 13 );
        testData.setEndMinute( 30 );
        testData.setName( "下載更新檔案" );
        testData.setDescription( "&lt;test123&gt;<br />test1,<br />test2 &amp;<br />test3" );
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

package test.integrating;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import common.Contants;
import commonUtil.ItemUtil;
import domain.Item;
import junit.framework.TestCase;
import repository.ItemDAO;
import repository.Impl.ItemDAOImplBeforeVer26;
import service.ItemService;
import service.Impl.ItemServiceImpl;
import view.MainFrame;

public class IntegratingTests extends TestCase {
    
    private final String ITEM_CSV_FILE_PATH = "data\\Item\\2017.06.01.csv";
    private final String ITEM_CSV_FILE_BACKUP_PATH = "data\\Item\\2017.06.01_backup.csv";
    private final String ITEM_CSV_FILE_PATH_2 = "data\\Item\\2017.05.01.csv";
    private final String ITEM_CSV_FILE_BACKUP_PATH_2 = "data\\Item\\2017.05.01_backup.csv";
    
    public void testDateListSelection() throws IOException {
        ItemService itemService = new ItemServiceImpl();
        int testerSelection = 0;
        
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( ITEM_CSV_FILE_PATH_2, ITEM_CSV_FILE_BACKUP_PATH_2 );
            
            for( int i = 0; i < 23; i++ ) {
                Item item = getTestData1();
                item.setStartMinute( item.getStartMinute() + i );
                item.setEndMinute( item.getEndMinute() + i );
                itemService.insert( item );
            }
            
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            // 選擇年份為2017
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "2017" );
            // 選擇月份為06，列出2017/06的日期清單
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "06" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            // 檢查執行結果
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, "是否有在表格區域的右側出現捲軸", "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            Thread.sleep( 1000 );
            
            // 選擇月份為05，列出2017/05的日期清單
            bot.keyPress( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_SHIFT );
            inputString( bot, "05" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            // 檢查執行結果
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, "是否有在表格區域的右側出現捲軸", "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.NO_OPTION, testerSelection );
            Thread.sleep( 1000 );
        } catch( Exception e ) {
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH_2, ITEM_CSV_FILE_PATH_2 );
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testCreateItem() throws IOException {
        final String ITEM_CSV_FILE_PATH_1 = "data\\Item\\2017.06.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_1 = "data\\Item\\2017.06.01_backup.csv";
        final String ITEM_CSV_FILE_PATH_2 = "data\\Item\\2017.06.30.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_2 = "data\\Item\\2017.06.30_backup.csv";
        
        ItemService itemService = new ItemServiceImpl();
        int testerSelection = 0;
        
        try {
            backupFile( ITEM_CSV_FILE_PATH_1, ITEM_CSV_FILE_BACKUP_PATH_1 );
            backupFile( ITEM_CSV_FILE_PATH_2, ITEM_CSV_FILE_BACKUP_PATH_2 );
            
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            // 選擇年份為2017
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "2017" );
            // 選擇月份為06，列出2017/06的日期清單
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "06" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            
            // 點選"新增"
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 新增資料
            Item item1 = getTestData1();
            bot.keyPress( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_SHIFT );
            inputString( bot, String.format( "%04d", item1.getYear() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", item1.getMonth() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", item1.getDay() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", item1.getStartHour() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", item1.getStartMinute() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", item1.getEndHour() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", item1.getEndMinute() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "test" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, item1.getDescription() );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            
            // 檢查是否新增成功，並顯示在畫面上
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, "新增的資料是否有出現在畫面上", "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            Thread.sleep( 1000 );
            
            List<Item> expect1 = new ArrayList<Item>();
            expect1.add( getTestData1() );
            expect1.get( expect1.size() - 1 ).setName( "test" );
            expect1.get( expect1.size() - 1 ).setSeq( 0 );
            List<Item> actual1 = itemService.findByDate( 2017, 6, 1 );
            assertEquals( expect1.size(), actual1.size() );
            for( int i = 0; i < expect1.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect1.get( i ), actual1.get( i ) ) );
            }
            
            // 新增第2筆資料
            Item item2 = getTestData1();
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            bot.keyPress( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_SHIFT );
            inputString( bot, String.format( "%04d", item2.getYear() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", item2.getMonth() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "28" );
            bot.keyPress( KeyEvent.VK_DOWN ); bot.keyRelease( KeyEvent.VK_DOWN ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_DOWN ); bot.keyRelease( KeyEvent.VK_DOWN ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_DOWN ); bot.keyRelease( KeyEvent.VK_DOWN ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_DOWN ); bot.keyRelease( KeyEvent.VK_DOWN ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_DOWN ); bot.keyRelease( KeyEvent.VK_DOWN ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", item2.getStartHour() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", item2.getStartMinute() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", item2.getEndHour() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", item2.getEndMinute() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "test" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, item2.getDescription() );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            
            // 檢查是否新增成功，並顯示在畫面上
            Thread.sleep( 1000 );
            bot.keyPress( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_END ); bot.keyRelease( KeyEvent.VK_END ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, "新增的資料是否有出現在畫面上", "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            Thread.sleep( 1000 );
            
            List<Item> expect2 = new ArrayList<Item>();
            expect2.add( getTestData1() );
            expect2.get( expect2.size() - 1 ).setDay( 30 );
            expect2.get( expect2.size() - 1 ).setName( "test" );
            expect2.get( expect2.size() - 1 ).setSeq( 0 );
            List<Item> actual2 = itemService.findByDate( 2017, 6, 30 );
            assertEquals( expect2.size(), actual2.size() );
            for( int i = 0; i < expect2.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect2.get( i ), actual2.get( i ) ) );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH_1, ITEM_CSV_FILE_PATH_1 );
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH_2, ITEM_CSV_FILE_PATH_2 );
        }
    }
    
    public void testInsertItem() throws IOException {
        ItemService itemService = new ItemServiceImpl();
        
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            for( int i = 0; i < 3; i++ ) {
                Item item = getTestData1();
                item.setStartMinute( item.getStartMinute() + i*10 );
                item.setEndMinute( item.getEndMinute() + i*10 + 5 );
                itemService.insert( item );
            }
            
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            
            // 選擇年份為2017
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "2017" );
            // 選擇月份為06，列出2017/06的日期清單
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "06" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            
            // 點選"新增"
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 新增資料
            bot.keyPress( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_SHIFT );
            inputString( bot, "2017" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "06" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "01" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "10" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "00" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "10" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "10" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "test" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查資料是否正確新增
            List<Item> expect = new ArrayList<Item>();
            expect.add( getTestData1() );
            expect.get( expect.size() - 1 ).setEndMinute( 5 );
            expect.add( getTestData1() );
            expect.get( expect.size() - 1 ).setSeq( 1 );
            expect.get( expect.size() - 1 ).setEndMinute( 10 );
            expect.get( expect.size() - 1 ).setName( "test" );
            expect.add( getTestData1() );
            expect.get( expect.size() - 1 ).setStartMinute( 10 );
            expect.get( expect.size() - 1 ).setEndMinute( 15 );
            expect.add( getTestData1() );
            expect.get( expect.size() - 1 ).setStartMinute( 20 );
            expect.get( expect.size() - 1 ).setEndMinute( 25 );
            
            List<Item> actual = itemService.findByDate( 2017, 6, 1 );
            
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
    
    public void testUpdateItem() throws IOException {
        ItemService itemService = new ItemServiceImpl();
        int testerSelection = 0;
        
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            for( int i = 0; i < 3; i++ ) {
                Item item = getTestData1();
                item.setStartMinute( item.getStartMinute() + i*10 );
                item.setEndMinute( item.getEndMinute() + i*10 );
                itemService.insert( item );
            }
            
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            
            // 選擇年份為2017
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "2017" );
            // 選擇月份為06，列出2017/06的日期清單
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "06" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            
            // 在表格中選擇第一筆資料
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_DOWN ); bot.keyRelease( KeyEvent.VK_DOWN ); Thread.sleep( 100 );
            
            // 點選"修改"
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 修改資料
            inputString( bot, "test123" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "<test123>" );
            bot.keyPress( KeyEvent.VK_ENTER ); bot.keyRelease( KeyEvent.VK_ENTER ); Thread.sleep( 100 );
            inputString( bot, "test1," );
            bot.keyPress( KeyEvent.VK_ENTER ); bot.keyRelease( KeyEvent.VK_ENTER ); Thread.sleep( 100 );
            inputString( bot, "test2 &" );
            bot.keyPress( KeyEvent.VK_ENTER ); bot.keyRelease( KeyEvent.VK_ENTER ); Thread.sleep( 100 );
            inputString( bot, "test3" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查資料是否有成功修改
            Item item = itemService.findOne(
                getTestData1().getYear(), getTestData1().getMonth(), getTestData1().getDay(), 
                getTestData1().getStartHour(), getTestData1().getStartMinute(), getTestData1().getSeq() );
            assertEquals( "test123", item.getName() );
            assertEquals( "&lt;test123&gt;<br />test1,<br />test2 &amp;<br />test3", item.getDescription() );
            
            // 檢查資料是否有正確顯示在修改對話框
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, 
                "項目名稱是否顯示為: test123\n描述是否顯示為: \n<test123>\ntest1,\ntest2 &\ntest3", 
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            
            // 回到主畫面
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 修改description欄位 (測試Undo功能)
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_CONTROL );
            bot.keyPress( KeyEvent.VK_HOME ); bot.keyRelease( KeyEvent.VK_HOME ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_CONTROL );
            bot.keyPress( KeyEvent.VK_END ); bot.keyRelease( KeyEvent.VK_END ); Thread.sleep( 100 );
            inputString( bot, "123456" );
            Thread.sleep( 500 );
            bot.keyPress( KeyEvent.VK_CONTROL );
            bot.keyPress( KeyEvent.VK_Z ); bot.keyRelease( KeyEvent.VK_Z ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_Z ); bot.keyRelease( KeyEvent.VK_Z ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_Z ); bot.keyRelease( KeyEvent.VK_Z ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_CONTROL );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查資料的description欄位是否有成功修改
            item = itemService.findOne(
                getTestData1().getYear(), getTestData1().getMonth(), getTestData1().getDay(), 
                getTestData1().getStartHour(), getTestData1().getStartMinute(), getTestData1().getSeq() );
            assertEquals( "test123", item.getName() );
            assertEquals( "&lt;test123&gt;123<br />test1,<br />test2 &amp;<br />test3", item.getDescription() );
            
            // 檢查資料的description欄位是否有正確顯示在修改對話框
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, 
                "項目名稱是否顯示為: test123\n描述是否顯示為: \n<test123>123\ntest1,\ntest2 &\ntest3", 
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            
            // 回到主畫面
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testUpdateItemStartDate() throws IOException {
        final String ITEM_CSV_FILE_PATH_1 = "data\\Item\\2017.06.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_1 = "data\\Item\\2017.06.01_backup.csv";
        final String ITEM_CSV_FILE_PATH_2 = "data\\Item\\2017.05.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_2 = "data\\Item\\2017.05.01_backup.csv";
        
        ItemService itemService = new ItemServiceImpl();
        int testerSelection = 0;
        
        try {
            backupFile( ITEM_CSV_FILE_PATH_1, ITEM_CSV_FILE_BACKUP_PATH_1 );
            backupFile( ITEM_CSV_FILE_PATH_2, ITEM_CSV_FILE_BACKUP_PATH_2 );
            
            for( int i = 0; i < 3; i++ ) {
                Item item = getTestData1();
                item.setStartMinute( item.getStartMinute() + i*10 );
                item.setEndMinute( item.getEndMinute() + i*10 );
                itemService.insert( item );
            }
            
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            
            // 選擇年份為2017
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "2017" );
            // 選擇月份為06，列出2017/06的日期清單
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "06" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            
            // 在表格中選擇第一筆資料
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_DOWN ); bot.keyRelease( KeyEvent.VK_DOWN ); Thread.sleep( 100 );
            
            // 點選"修改"
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 修改資料，把月份改為05
            bot.keyPress( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_SHIFT );
            inputString( bot, "05" );
            bot.keyPress( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查資料是否有成功修改
            List<Item> expect1 = new ArrayList<Item>();
            expect1.add( getTestData1() );
            expect1.get( expect1.size() - 1 ).setMonth( 5 );
            List<Item> expect2 = new ArrayList<Item>();
            for( int i = 1; i < 3; i++ ) {
                Item item = getTestData1();
                item.setStartMinute( item.getStartMinute() + i*10 );
                item.setEndMinute( item.getEndMinute() + i*10 );
                expect2.add( item );
            }
            List<Item> actual1 = itemService.findByDate( 2017, 5, 1 );
            for( int i = 0; i < expect1.size(); i++ ) {
                assertTrue( "failed at i = " + i, ItemUtil.equals( expect1.get( i ), actual1.get( i ) ) );
            }
            List<Item> actual2 = itemService.findByDate( 2017, 6, 1 );
            for( int i = 0; i < expect2.size(); i++ ) {
                assertTrue( "failed at i = " + i, ItemUtil.equals( expect2.get( i ), actual2.get( i ) ) );
            }
            
            // 檢查資料是否有正確顯示在畫面(6月份)
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, 
                "顯示的資料是否為:\n10:10 ~ 10:10  測試\n10:20 ~ 10:20  測試",
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            
            // 選擇月份為05，列出2017/05的日期清單
            bot.keyPress( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_SHIFT );
            inputString( bot, "05" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            
            // 檢查資料是否有正確顯示在畫面(5月份)
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, 
                "顯示的資料是否為:\n10:00 ~ 10:00  測試",
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH_2, ITEM_CSV_FILE_PATH_2 );
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH_1, ITEM_CSV_FILE_PATH_1 );
        }
    }
    
    public void testDeleteItem() throws IOException {
        ItemService itemService = new ItemServiceImpl();
        
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            for( int i = 0; i < 3; i++ ) {
                Item item = getTestData1();
                item.setStartMinute( item.getStartMinute() + i*10 );
                item.setEndMinute( item.getEndMinute() + i*10 );
                itemService.insert( item );
            }
            
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            
            // 選擇年份為2017
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "2017" );
            // 選擇月份為06，列出2017/06的日期清單
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "06" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            
            // 在表格中選擇第二筆資料
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_DOWN ); bot.keyRelease( KeyEvent.VK_DOWN ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_DOWN ); bot.keyRelease( KeyEvent.VK_DOWN ); Thread.sleep( 100 );
            
            // 點選"刪除"
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查資料是否有成功刪除
            List<Item> expect = new ArrayList<Item>();
            for( int i = 0; i < 3; i++ ) {
                Item item = getTestData1();
                item.setSeq( 0 );
                item.setStartMinute( item.getStartMinute() + i*10 );
                item.setEndMinute( item.getEndMinute() + i*10 );
                if( i != 1 ) {
                    expect.add( item );
                }
            }
            List<Item> actual = itemService.findByDate( 2017, 6, 1 );
            
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
    
    public void testImportItem() throws IOException {
        ItemService itemService = new ItemServiceImpl();
        
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            
            // 選擇年份為2017
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "2017" );
            // 選擇月份為06，列出2017/06的日期清單
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "06" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            
            // 點選"匯入"
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 匯入項目資料
            List<String> inputList = new ArrayList<String>();
            inputList.add( "10:00 ~ 10:10  test" );
            inputList.add( "10:10 ~ 10:20  test2" );
            inputList.add( "10:20 ~ 10:30  test3" );
            inputList.add( "10:30 ~ 10:40  test4" );
            inputList.add( "10:40 ~ 10:50  test5" );
            inputList.add( "10:40 ~ 10:55  test6" );
            for( String input : inputList ) {
                inputString( bot, input );
                bot.keyPress( KeyEvent.VK_ENTER ); bot.keyRelease( KeyEvent.VK_ENTER ); Thread.sleep( 100 );
            }
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查資料是否正確匯入
            List<Item> expect = new ArrayList<Item>();
            for( int i = 0; i < 6; i++ ) {
                Item item = getTestData1();
                item.setSeq( 0 );
                item.setStartMinute( item.getStartMinute() + i*10 );
                item.setEndMinute( item.getEndMinute() + i*10 + 10 );
                item.setName( "test" + ((i == 0) ? "" : (i + 1)) );
                if( i == 5 ) {
                    item.setSeq( 1 );
                    item.setStartMinute( 40 );
                    item.setEndMinute( 55 );
                }
                expect.add( item );
            }
            List<Item> actual = itemService.findByDate( 2017, 6, 1 );
            
            assertEquals( expect.size(), actual.size() );
            for( int i = 0; i < expect.size(); i++ ) {
                assertTrue( "failed at i = " + i, ItemUtil.equals( expect.get( i ), actual.get( i ) ) );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testExportItem() throws IOException {
        ItemService itemService = new ItemServiceImpl();
        int testerSelection = 0;
        
        try {
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            for( int i = 0; i < 3; i++ ) {
                Item item = getTestData1();
                item.setStartMinute( item.getStartMinute() + i*10 );
                item.setEndMinute( item.getEndMinute() + i*10 + 10 );
                itemService.insert( item );
            }
            
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            
            // 選擇年份為2017
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "2017" );
            // 選擇月份為06，列出2017/06的日期清單
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "06" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            
            // 點選"匯出"
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查資料是否正確匯出
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, 
                "匯出的資料是否為:\n10:00 ~ 10:10  測試\n10:10 ~ 10:20  測試\n10:20 ~ 10:30  測試", 
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            
            // 回到主畫面
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
        }
    }
    
    public void testConvertOldItemDataToCurrentVersion() throws IOException {
        final String ITEM_CSV_FILE_PATH_1 = "data\\Item\\2017.06.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_1 = "data\\Item\\2017.06.01_backup.csv";
        final String ITEM_CSV_FILE_PATH_2 = "data\\Item\\2017.05.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_2 = "data\\Item\\2017.05.01_backup.csv";
        final String ITEM_CSV_FILE_PATH_3 = "data\\Item\\2017.06.03.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_3 = "data\\Item\\2017.06.03_backup.csv";
        
        ItemDAO itemDAOBeforeVer26 = new ItemDAOImplBeforeVer26();
        ItemService itemService = new ItemServiceImpl();
        int testerSelection = 0;
        
        try {
            backupFile( ITEM_CSV_FILE_PATH_1, ITEM_CSV_FILE_BACKUP_PATH_1 );
            backupFile( ITEM_CSV_FILE_PATH_2, ITEM_CSV_FILE_BACKUP_PATH_2 );
            backupFile( ITEM_CSV_FILE_PATH_3, ITEM_CSV_FILE_BACKUP_PATH_3 );
            
            for( int i = 0; i < 5; i++ ) {
                Item item = getTestData1();
                item.setStartMinute( item.getStartMinute() + i*10 );
                item.setEndMinute( item.getEndMinute() + i*10 );
                if( i == 3 ) {
                    item.setMonth( 5 );
                } else if( i == 4 ) {
                    item.setDay( 3 );
                }
                itemDAOBeforeVer26.insert( item );
            }
            
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            
            // 選擇年份為2017
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "2017" );
            // 選擇月份為06，列出2017/06的日期清單
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "06" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查是否有偵測到item檔案為"alpha-0.26"以前的版本
            testerSelection = JOptionPane.showConfirmDialog(
                mainFrame, 
                "是否有偵測到時間記錄的檔案為\"alpha-0.26\"之前的版本",
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            
            // 選擇進行轉檔作業選項
            bot.keyPress( KeyEvent.VK_ENTER ); bot.keyRelease( KeyEvent.VK_ENTER ); Thread.sleep( 100 );
            Thread.sleep( 3000 );
            
            // 確認轉檔訊息
            bot.keyPress( KeyEvent.VK_ENTER ); bot.keyRelease( KeyEvent.VK_ENTER ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查item檔案是否正確轉檔
            List<Item> expect1 = new ArrayList<Item>();
            List<Item> expect2 = new ArrayList<Item>();
            List<Item> expect3 = new ArrayList<Item>();
            for( int i = 0; i < 5; i++ ) {
                Item item = getTestData1();
                item.setStartMinute( item.getStartMinute() + i*10 );
                item.setEndMinute( item.getEndMinute() + i*10 );
                if( i == 3 ) {
                    item.setMonth( 5 );
                    expect2.add( item );
                } else if( i == 4 ) {
                    item.setDay( 3 );
                    expect3.add( item );
                } else {
                    expect1.add( item );
                }
            }
            
            assertEquals( Contants.SUCCESS, itemService.checkItemDataVersion( 2017, 6, 1 ) );
            List<Item> actual1 = itemService.findByDate( 2017, 6, 1 );
            assertEquals( expect1.size(), actual1.size() );
            for( int i = 0; i < expect1.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect1.get( i ), actual1.get( i ) ) );
            }
            
            assertEquals( Contants.SUCCESS, itemService.checkItemDataVersion( 2017, 5, 1 ) );
            List<Item> actual2 = itemService.findByDate( 2017, 5, 1 );
            assertEquals( expect2.size(), actual2.size() );
            for( int i = 0; i < expect2.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect2.get( i ), actual2.get( i ) ) );
            }
            
            assertEquals( Contants.SUCCESS, itemService.checkItemDataVersion( 2017, 6, 3 ) );
            List<Item> actual3 = itemService.findByDate( 2017, 6, 3 );
            assertEquals( expect3.size(), actual3.size() );
            for( int i = 0; i < expect3.size(); i++ ) {
                assertTrue( ItemUtil.equals( expect3.get( i ), actual3.get( i ) ) );
            }
            
            // 檢查資料是否正確顯示
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, 
                "顯示的資料是否為:\n10:00 ~ 10:00  測試\n10:10 ~ 10:10  測試\n10:20 ~ 10:20  測試", 
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH_3, ITEM_CSV_FILE_PATH_3 );
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH_2, ITEM_CSV_FILE_PATH_2 );
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH_1, ITEM_CSV_FILE_PATH_1 );
        }
    }
    
    public void testDateTimeTextFieldArrowKeyOperation() throws IOException {
        final String ITEM_CSV_FILE_PATH_1 = "data\\Item\\2017.06.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_1 = "data\\Item\\2017.06.01_backup.csv";
        final String ITEM_CSV_FILE_PATH_2 = "data\\Item\\2017.05.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_2 = "data\\Item\\2017.05.01_backup.csv";
        final String ITEM_CSV_FILE_PATH_3 = "data\\Item\\2017.01.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH_3 = "data\\Item\\2017.01.01_backup.csv";
        
        ItemService itemService = new ItemServiceImpl();
        int testerSelection = 0;
        
        try {
            backupFile( ITEM_CSV_FILE_PATH_1, ITEM_CSV_FILE_BACKUP_PATH_1 );
            backupFile( ITEM_CSV_FILE_PATH_2, ITEM_CSV_FILE_BACKUP_PATH_2 );
            backupFile( ITEM_CSV_FILE_PATH_3, ITEM_CSV_FILE_BACKUP_PATH_3 );
            
            for( int i = 0; i < 3; i++ ) {
                Item item = getTestData1();
                item.setMonth( 1 );
                item.setStartMinute( item.getStartMinute() + i*10 );
                item.setEndMinute( item.getEndMinute() + i*10 + 5 );
                itemService.insert( item );
            }
            for( int i = 0; i < 4; i++ ) {
                Item item = getTestData1();
                item.setMonth( 5 );
                item.setStartMinute( item.getStartMinute() + i*10 );
                item.setEndMinute( item.getEndMinute() + i*10 + 5 );
                itemService.insert( item );
            }
            for( int i = 0; i < 5; i++ ) {
                Item item = getTestData1();
                item.setStartMinute( item.getStartMinute() + i*10 );
                item.setEndMinute( item.getEndMinute() + i*10 + 5 );
                itemService.insert( item );
            }
            
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            
            // 選擇年份為2017
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "2017" );
            // 選擇月份為06，列出2017/06的日期清單
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "06" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查資料是否有正確的顯示在畫面(6月份)
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame,
                "顯示的資料是否為:\n10:00 ~ 10:05  測試\n10:10 ~ 10:15  測試\n10:20 ~ 10:25  測試\n10:30 ~ 10:35  測試\n10:40 ~ 10:45  測試",
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            
            // 選擇月份為05(用方向鍵選擇)
            bot.keyPress( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_UP ); bot.keyRelease( KeyEvent.VK_UP ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查資料是否有正確的顯示在畫面(5月份)
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame,
                "顯示的資料是否為:\n10:00 ~ 10:05  測試\n10:10 ~ 10:15  測試\n10:20 ~ 10:25  測試\n10:30 ~ 10:35  測試",
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            
            // 選擇月份為01(用方向鍵選擇)
            bot.keyPress( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_UP ); bot.keyRelease( KeyEvent.VK_UP ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_UP ); bot.keyRelease( KeyEvent.VK_UP ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_UP ); bot.keyRelease( KeyEvent.VK_UP ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_UP ); bot.keyRelease( KeyEvent.VK_UP ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_UP ); bot.keyRelease( KeyEvent.VK_UP ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_UP ); bot.keyRelease( KeyEvent.VK_UP ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查資料是否有正確的顯示在畫面(1月份)
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame,
                "顯示的資料是否為:\n10:00 ~ 10:05  測試\n10:10 ~ 10:15  測試\n10:20 ~ 10:25  測試",
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
        } catch ( Exception e ) {
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
        testData.setMonth( 6 );
        testData.setDay( 1 );
        testData.setStartHour( 10 );
        testData.setStartMinute( 00 );
        testData.setSeq( 0 );
        testData.setEndHour( 10 );
        testData.setEndMinute( 00 );
        testData.setName( "測試" );
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
    
    private void inputString( Robot bot, String s ) {
        HashMap<Character, Integer> charToKeyCodeMap = new HashMap<Character, Integer>();
        charToKeyCodeMap.put( 'a', KeyEvent.VK_A ); charToKeyCodeMap.put( 'A', KeyEvent.VK_A );
        charToKeyCodeMap.put( 'b', KeyEvent.VK_B ); charToKeyCodeMap.put( 'B', KeyEvent.VK_B );
        charToKeyCodeMap.put( 'c', KeyEvent.VK_C ); charToKeyCodeMap.put( 'C', KeyEvent.VK_C );
        charToKeyCodeMap.put( 'd', KeyEvent.VK_D ); charToKeyCodeMap.put( 'D', KeyEvent.VK_D );
        charToKeyCodeMap.put( 'e', KeyEvent.VK_E ); charToKeyCodeMap.put( 'E', KeyEvent.VK_E );
        charToKeyCodeMap.put( 'f', KeyEvent.VK_F ); charToKeyCodeMap.put( 'F', KeyEvent.VK_F );
        charToKeyCodeMap.put( 'g', KeyEvent.VK_G ); charToKeyCodeMap.put( 'G', KeyEvent.VK_G );
        charToKeyCodeMap.put( 'h', KeyEvent.VK_H ); charToKeyCodeMap.put( 'H', KeyEvent.VK_H );
        charToKeyCodeMap.put( 'i', KeyEvent.VK_I ); charToKeyCodeMap.put( 'I', KeyEvent.VK_I );
        charToKeyCodeMap.put( 'j', KeyEvent.VK_J ); charToKeyCodeMap.put( 'J', KeyEvent.VK_J );
        charToKeyCodeMap.put( 'k', KeyEvent.VK_K ); charToKeyCodeMap.put( 'K', KeyEvent.VK_K );
        charToKeyCodeMap.put( 'l', KeyEvent.VK_L ); charToKeyCodeMap.put( 'L', KeyEvent.VK_L );
        charToKeyCodeMap.put( 'm', KeyEvent.VK_M ); charToKeyCodeMap.put( 'M', KeyEvent.VK_M );
        charToKeyCodeMap.put( 'n', KeyEvent.VK_N ); charToKeyCodeMap.put( 'N', KeyEvent.VK_N );
        charToKeyCodeMap.put( 'o', KeyEvent.VK_O ); charToKeyCodeMap.put( 'O', KeyEvent.VK_O );
        charToKeyCodeMap.put( 'p', KeyEvent.VK_P ); charToKeyCodeMap.put( 'P', KeyEvent.VK_P );
        charToKeyCodeMap.put( 'q', KeyEvent.VK_Q ); charToKeyCodeMap.put( 'Q', KeyEvent.VK_Q );
        charToKeyCodeMap.put( 'r', KeyEvent.VK_R ); charToKeyCodeMap.put( 'R', KeyEvent.VK_R );
        charToKeyCodeMap.put( 's', KeyEvent.VK_S ); charToKeyCodeMap.put( 'S', KeyEvent.VK_S );
        charToKeyCodeMap.put( 't', KeyEvent.VK_T ); charToKeyCodeMap.put( 'T', KeyEvent.VK_T );
        charToKeyCodeMap.put( 'u', KeyEvent.VK_U ); charToKeyCodeMap.put( 'U', KeyEvent.VK_U );
        charToKeyCodeMap.put( 'v', KeyEvent.VK_V ); charToKeyCodeMap.put( 'V', KeyEvent.VK_V );
        charToKeyCodeMap.put( 'w', KeyEvent.VK_W ); charToKeyCodeMap.put( 'W', KeyEvent.VK_W );
        charToKeyCodeMap.put( 'x', KeyEvent.VK_X ); charToKeyCodeMap.put( 'X', KeyEvent.VK_X );
        charToKeyCodeMap.put( 'y', KeyEvent.VK_Y ); charToKeyCodeMap.put( 'Y', KeyEvent.VK_Y );
        charToKeyCodeMap.put( 'z', KeyEvent.VK_Z ); charToKeyCodeMap.put( 'Z', KeyEvent.VK_Z );
        charToKeyCodeMap.put( '1', KeyEvent.VK_1 ); charToKeyCodeMap.put( '!', KeyEvent.VK_1 );
        charToKeyCodeMap.put( '2', KeyEvent.VK_2 ); charToKeyCodeMap.put( '@', KeyEvent.VK_2 );
        charToKeyCodeMap.put( '3', KeyEvent.VK_3 ); charToKeyCodeMap.put( '#', KeyEvent.VK_3 );
        charToKeyCodeMap.put( '4', KeyEvent.VK_4 ); charToKeyCodeMap.put( '$', KeyEvent.VK_4 );
        charToKeyCodeMap.put( '5', KeyEvent.VK_5 ); charToKeyCodeMap.put( '%', KeyEvent.VK_5 );
        charToKeyCodeMap.put( '6', KeyEvent.VK_6 ); charToKeyCodeMap.put( '^', KeyEvent.VK_6 );
        charToKeyCodeMap.put( '7', KeyEvent.VK_7 ); charToKeyCodeMap.put( '&', KeyEvent.VK_7 );
        charToKeyCodeMap.put( '8', KeyEvent.VK_8 ); charToKeyCodeMap.put( '*', KeyEvent.VK_8 );
        charToKeyCodeMap.put( '9', KeyEvent.VK_9 ); charToKeyCodeMap.put( '(', KeyEvent.VK_9 );
        charToKeyCodeMap.put( '0', KeyEvent.VK_0 ); charToKeyCodeMap.put( ')', KeyEvent.VK_0 );
        charToKeyCodeMap.put( '-', KeyEvent.VK_MINUS ); charToKeyCodeMap.put( '_', KeyEvent.VK_MINUS );
        charToKeyCodeMap.put( '=', KeyEvent.VK_EQUALS ); charToKeyCodeMap.put( '+', KeyEvent.VK_EQUALS );
        charToKeyCodeMap.put( '[', KeyEvent.VK_OPEN_BRACKET ); charToKeyCodeMap.put( '{', KeyEvent.VK_OPEN_BRACKET );
        charToKeyCodeMap.put( ']', KeyEvent.VK_CLOSE_BRACKET ); charToKeyCodeMap.put( '}', KeyEvent.VK_CLOSE_BRACKET );
        charToKeyCodeMap.put( '\\', KeyEvent.VK_BACK_SLASH ); charToKeyCodeMap.put( '|', KeyEvent.VK_BACK_SLASH );
        charToKeyCodeMap.put( ';', KeyEvent.VK_SEMICOLON ); charToKeyCodeMap.put( ':', KeyEvent.VK_SEMICOLON );
        charToKeyCodeMap.put( '\'', KeyEvent.VK_QUOTE ); charToKeyCodeMap.put( '\"', KeyEvent.VK_QUOTE );
        charToKeyCodeMap.put( ',', KeyEvent.VK_COMMA ); charToKeyCodeMap.put( '<', KeyEvent.VK_COMMA );
        charToKeyCodeMap.put( '.', KeyEvent.VK_PERIOD ); charToKeyCodeMap.put( '>', KeyEvent.VK_PERIOD );
        charToKeyCodeMap.put( '/', KeyEvent.VK_SLASH ); charToKeyCodeMap.put( '?', KeyEvent.VK_SLASH );
        charToKeyCodeMap.put( '`', KeyEvent.VK_BACK_QUOTE ); charToKeyCodeMap.put( '~', KeyEvent.VK_BACK_QUOTE );
        charToKeyCodeMap.put( ' ', KeyEvent.VK_SPACE );
        ArrayList<Character> shiftPunctuationList = new ArrayList<Character>( 
                Arrays.asList( '~', '!', '@', '#', '$', '%', '^', '&', '*', 
                '(', ')', '_', '+', '{', '}', '|', ':', '\"', '<', '>', '?' ) );
        for( int i = 0; i < s.length(); i++ ) {
            if( Character.isUpperCase( s.charAt( i ) ) || 
                    shiftPunctuationList.indexOf( s.charAt( i ) ) >= 0 ) {
                bot.keyPress( KeyEvent.VK_SHIFT );
            }
            bot.keyPress( charToKeyCodeMap.get( s.charAt( i ) ) );
            bot.keyRelease( charToKeyCodeMap.get( s.charAt( i ) ) );
            if( Character.isUpperCase( s.charAt( i ) ) || 
                    shiftPunctuationList.indexOf( s.charAt( i ) ) >= 0 ) {
                bot.keyRelease( KeyEvent.VK_SHIFT );
            }
        }
    }
}

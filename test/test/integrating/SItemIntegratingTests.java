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

import commonUtil.ItemUtil;
import commonUtil.ScheduledItemUtil;
import domain.Item;
import domain.ScheduledItem;
import junit.framework.TestCase;
import service.ItemService;
import service.ScheduledItemService;
import service.Impl.ItemServiceImpl;
import service.Impl.ScheduledItemServiceImpl;
import view.MainFrame;

public class SItemIntegratingTests extends TestCase {
    
    private final String S_ITEM_CSV_FILE_PATH = "./data/ScheduledItem.csv";
    private final String S_ITEM_CSV_FILE_BACKUP_PATH = "./data/ScheduledItem_backup.csv";
    private final String S_ITEM_SEQ_FILE_PATH = "./data/ScheduledItemSeq.txt";
    private final String S_ITEM_SEQ_FILE_BACKUP_PATH = "./data/ScheduledItemSeq_backup.txt";
    
    private MainFrame mainFrame = null;
    
    public void testLoadScheduledItems() throws IOException {
        int testerSelection = 0;
        ScheduledItemService scheduledItemService = new ScheduledItemServiceImpl();
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            
            // 新增初始資料
            for( int i = 1; i <= 5; i++ ) {
                ScheduledItem scheduledItem = getTestData1();
                scheduledItem.setMinute( scheduledItem.getMinute() + i * 5 );
                scheduledItem.setName( scheduledItem.getName() + i );
                scheduledItemService.insert( scheduledItem );
            }
            
            // 執行視窗程式
            mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            // 切換至事項排程頁籤
            bot.keyPress( KeyEvent.VK_RIGHT ); bot.keyRelease( KeyEvent.VK_RIGHT ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查執行結果
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, "是否有顯示測試的排程項目資料\"test1\"、\"test2\"、\"test3\"、\"test4\"和\"test5\"", 
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            Thread.sleep( 1000 );
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( S_ITEM_SEQ_FILE_BACKUP_PATH, S_ITEM_SEQ_FILE_PATH );
            restoreFile( S_ITEM_CSV_FILE_BACKUP_PATH, S_ITEM_CSV_FILE_PATH );
            mainFrame.dispose();
        }
    }
    
    public void testCreateScheduledItem() throws IOException {
        int testerSelection = 0;
        ScheduledItemService scheduledItemService = new ScheduledItemServiceImpl();
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            
            mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            // 點選事項排程頁籤的"新增"按鈕
            bot.keyPress( KeyEvent.VK_RIGHT ); bot.keyRelease( KeyEvent.VK_RIGHT ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 新增資料
            ScheduledItem scheduledItem = getTestData1();
            inputString( bot, String.format( "%c", scheduledItem.getType() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%04d", scheduledItem.getYear() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", scheduledItem.getMonth() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", scheduledItem.getDay() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", scheduledItem.getHour() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", scheduledItem.getMinute() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%d", scheduledItem.getExpectedTime() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, scheduledItem.getName() );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, scheduledItem.getDescription() );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            
            // 檢查是否新增成功，並顯示在畫面上
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, "新增的資料是否有出現在畫面上", "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            Thread.sleep( 1000 );
            
            ScheduledItem expect = getTestData1();
            expect.setId( 1 );
            ScheduledItem actual = scheduledItemService.findById( 1 );
            assertTrue( ScheduledItemUtil.equals( expect, actual ) );
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( S_ITEM_SEQ_FILE_BACKUP_PATH, S_ITEM_SEQ_FILE_PATH );
            restoreFile( S_ITEM_CSV_FILE_BACKUP_PATH, S_ITEM_CSV_FILE_PATH );
            mainFrame.dispose();
        }
    }
    
    public void testCreateScheduledItemWithEmptyExpectedTime() throws IOException {
        int testerSelection = 0;
        ScheduledItemService scheduledItemService = new ScheduledItemServiceImpl();
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            
            mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            // 點選事項排程頁籤的"新增"按鈕
            bot.keyPress( KeyEvent.VK_RIGHT ); bot.keyRelease( KeyEvent.VK_RIGHT ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 新增資料
            ScheduledItem scheduledItem = getTestData1();
            inputString( bot, String.format( "%c", scheduledItem.getType() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%04d", scheduledItem.getYear() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", scheduledItem.getMonth() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", scheduledItem.getDay() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", scheduledItem.getHour() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, String.format( "%02d", scheduledItem.getMinute() ) );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            // 預計花費時間(ExpectedTime)欄位設為空白
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, scheduledItem.getName() );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, scheduledItem.getDescription() );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            
            // 檢查是否新增成功，並顯示在畫面上
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, "新增的資料是否有出現在畫面上，且預計花費時間欄位為空白", "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            Thread.sleep( 1000 );
            
            ScheduledItem expect = getTestData1();
            expect.setId( 1 );
            expect.setExpectedTime( -1 );
            ScheduledItem actual = scheduledItemService.findById( 1 );
            assertTrue( ScheduledItemUtil.equals( expect, actual ) );
            
            // 檢查資料是否有正確顯示在修改對話框
            bot.keyPress( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_SHIFT ); Thread.sleep( 1000 );
            bot.keyPress( KeyEvent.VK_DOWN ); bot.keyRelease( KeyEvent.VK_DOWN ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, "預計花費時間欄位是否為空白?", "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            Thread.sleep( 500 );
            
            // 回到主畫面
            bot.keyPress( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( S_ITEM_SEQ_FILE_BACKUP_PATH, S_ITEM_SEQ_FILE_PATH );
            restoreFile( S_ITEM_CSV_FILE_BACKUP_PATH, S_ITEM_CSV_FILE_PATH );
            mainFrame.dispose();
        }
    }
    
    public void testUpdateScheduledItem() throws IOException {
        int testerSelection = 0;
        ScheduledItemService scheduledItemService = new ScheduledItemServiceImpl();
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            
            // 新增初始資料
            for( int i = 1; i <= 5; i++ ) {
                ScheduledItem scheduledItem = getTestData1();
                scheduledItem.setMinute( scheduledItem.getMinute() + i * 5 );
                scheduledItem.setName( scheduledItem.getName() + i );
                scheduledItemService.insert( scheduledItem );
            }
            
            mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            // 選擇事項排程頁籤資料列表的第一筆資料
            bot.keyPress( KeyEvent.VK_RIGHT ); bot.keyRelease( KeyEvent.VK_RIGHT ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_DOWN ); bot.keyRelease( KeyEvent.VK_DOWN ); Thread.sleep( 100 );
            // 點選事項排程頁籤的"修改"按鈕
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 修改資料
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
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
            
            // 檢查是否修改成功，並顯示在畫面上
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, "畫面上的第一筆資料的名稱是否修改為\"test123\"", "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            Thread.sleep( 1000 );
            
            // 檢查是否修改成功
            ScheduledItem expect = getTestData1();
            expect.setName( "test123" );
            expect.setMinute( 30 );
            expect.setId( 1 );
            expect.setDescription( "&lt;test123&gt;<br />test1,<br />test2 &amp;<br />test3" );
            ScheduledItem actual = scheduledItemService.findById( 1 );
            assertTrue( ScheduledItemUtil.equals( expect, actual ) );
            
            // 檢查資料是否有正確顯示在修改對話框
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, 
                "項目名稱是否顯示為: test123\n描述是否顯示為: \n<test123>\ntest1,\ntest2 &\ntest3", 
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            Thread.sleep( 500 );
            
            // 回到主畫面
            bot.keyPress( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( S_ITEM_SEQ_FILE_BACKUP_PATH, S_ITEM_SEQ_FILE_PATH );
            restoreFile( S_ITEM_CSV_FILE_BACKUP_PATH, S_ITEM_CSV_FILE_PATH );
            mainFrame.dispose();
        }
    }
    
    public void testDeleteScheduledItem() throws IOException {
        int testerSelection = 0;
        ScheduledItemService scheduledItemService = new ScheduledItemServiceImpl();
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            
            // 新增初始資料
            for( int i = 1; i <= 5; i++ ) {
                ScheduledItem scheduledItem = getTestData1();
                scheduledItem.setMinute( scheduledItem.getMinute() + i * 5 );
                scheduledItem.setName( scheduledItem.getName() + i );
                scheduledItemService.insert( scheduledItem );
            }
            
            mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            // 選擇事項排程頁籤資料列表的第一筆資料
            bot.keyPress( KeyEvent.VK_RIGHT ); bot.keyRelease( KeyEvent.VK_RIGHT ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_DOWN ); bot.keyRelease( KeyEvent.VK_DOWN ); Thread.sleep( 100 );
            // 點選事項排程頁籤的"取消"按鈕
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            // 確認取消(刪除)排程事項
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查畫面是否有更新
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, "畫面上是否只剩4筆資料\"test2\"、\"test3\"、\"test4\"和\"test5\"", "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            Thread.sleep( 1000 );
            
            // 檢查是否刪除成功
            ScheduledItem expect = null;
            ScheduledItem actual = scheduledItemService.findById( 1 );
            assertEquals( expect, actual );
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( S_ITEM_SEQ_FILE_BACKUP_PATH, S_ITEM_SEQ_FILE_PATH );
            restoreFile( S_ITEM_CSV_FILE_BACKUP_PATH, S_ITEM_CSV_FILE_PATH );
            mainFrame.dispose();
        }
    }
    
    public void testDisplayScheduledItemDetail() throws IOException {
        int testerSelection = 0;
        ScheduledItemService scheduledItemService = new ScheduledItemServiceImpl();
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            
            // 新增初始資料
            for( int i = 1; i <= 5; i++ ) {
                ScheduledItem scheduledItem = getTestData1();
                scheduledItem.setMinute( scheduledItem.getMinute() + i * 5 );
                scheduledItem.setName( scheduledItem.getName() + i );
                if( i == 1 ) {
                    scheduledItem.setDescription( "&lt;test123&gt;<br />test1,<br />test2 &amp;<br />test3" );
                }
                scheduledItemService.insert( scheduledItem );
            }
            
            mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            // 選擇事項排程頁籤資料列表的第一筆資料
            bot.keyPress( KeyEvent.VK_RIGHT ); bot.keyRelease( KeyEvent.VK_RIGHT ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_DOWN ); bot.keyRelease( KeyEvent.VK_DOWN ); Thread.sleep( 100 );
            // 點選事項排程頁籤的"詳細"按鈕
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查資料是否正確顯示
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, 
                "顯示的資料是否為:\n時間: 2017年05月01日  11時30分    種類: 準時\n" + 
                    "預計花費時間: 85分\n項目: test1\n說明: \n" + 
                    "<test123>\ntest1,\ntest2 &\ntest3", 
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            Thread.sleep( 500 );
            
            // 回到主畫面
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( S_ITEM_SEQ_FILE_BACKUP_PATH, S_ITEM_SEQ_FILE_PATH );
            restoreFile( S_ITEM_CSV_FILE_BACKUP_PATH, S_ITEM_CSV_FILE_PATH );
            mainFrame.dispose();
        }
    }
    
    public void testDisplayScheduledItemDetailHotKey() throws IOException {
        int testerSelection = 0;
        ScheduledItemService scheduledItemService = new ScheduledItemServiceImpl();
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            
            // 新增初始資料
            for( int i = 1; i <= 5; i++ ) {
                ScheduledItem scheduledItem = getTestData1();
                scheduledItem.setMinute( scheduledItem.getMinute() + i * 5 );
                scheduledItem.setName( scheduledItem.getName() + i );
                if( i == 1 ) {
                    scheduledItem.setDescription( "&lt;test123&gt;<br />test1,<br />test2 &amp;<br />test3" );
                }
                scheduledItemService.insert( scheduledItem );
            }
            
            mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            // 選擇事項排程頁籤資料列表的第一筆資料
            bot.keyPress( KeyEvent.VK_RIGHT ); bot.keyRelease( KeyEvent.VK_RIGHT ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_DOWN ); bot.keyRelease( KeyEvent.VK_DOWN ); Thread.sleep( 100 );
            // 測試事項排程清單的Enter HotKey功能
            bot.keyPress( KeyEvent.VK_ENTER ); bot.keyRelease( KeyEvent.VK_ENTER ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查資料是否正確顯示
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, 
                "顯示的資料是否為:\n時間: 2017年05月01日  11時30分    種類: 準時\n" + 
                    "預計花費時間: 85分\n項目: test1\n說明: \n" + 
                    "<test123>\ntest1,\ntest2 &\ntest3", 
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            Thread.sleep( 500 );
            
            // 回到主畫面
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( S_ITEM_SEQ_FILE_BACKUP_PATH, S_ITEM_SEQ_FILE_PATH );
            restoreFile( S_ITEM_CSV_FILE_BACKUP_PATH, S_ITEM_CSV_FILE_PATH );
            mainFrame.dispose();
        }
    }
    
    public void testExecuteScheduledItem() throws IOException {
        final String ITEM_CSV_FILE_PATH = "./data/Item/2017.05.01.csv";
        final String ITEM_CSV_FILE_BACKUP_PATH = "./data/Item/2017.05.01_backup.csv";
        
        int testerSelection = 0;
        ItemService itemService = new ItemServiceImpl();
        ScheduledItemService scheduledItemService = new ScheduledItemServiceImpl();
        
        try {
            backupFile( S_ITEM_CSV_FILE_PATH, S_ITEM_CSV_FILE_BACKUP_PATH );
            backupFile( S_ITEM_SEQ_FILE_PATH, S_ITEM_SEQ_FILE_BACKUP_PATH );
            backupFile( ITEM_CSV_FILE_PATH, ITEM_CSV_FILE_BACKUP_PATH );
            
            // 新增初始資料
            for( int i = 1; i <= 5; i++ ) {
                ScheduledItem scheduledItem = getTestData1();
                scheduledItem.setMinute( scheduledItem.getMinute() + i * 5 );
                scheduledItem.setName( scheduledItem.getName() + i );
                if( i == 1 ) {
                    scheduledItem.setDescription( "&lt;test123&gt;<br />test1,<br />test2 &amp;<br />test3" );
                }
                scheduledItemService.insert( scheduledItem );
            }
            
            mainFrame = new MainFrame();
            mainFrame.setVisible( true );
            
            JOptionPane.showMessageDialog( mainFrame, "請切換為英文輸入法", "Message", JOptionPane.INFORMATION_MESSAGE );
            
            Robot bot =  new Robot();
            Thread.sleep( 3000 );
            // 選擇事項排程頁籤資料列表的第一筆資料
            bot.keyPress( KeyEvent.VK_RIGHT ); bot.keyRelease( KeyEvent.VK_RIGHT ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_DOWN ); bot.keyRelease( KeyEvent.VK_DOWN ); Thread.sleep( 100 );
            // 點選事項排程頁籤的"執行"按鈕
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 輸入執行日期 & 時間
            bot.keyPress( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_SHIFT );
            inputString( bot, "2017" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "05" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "01" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "11" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "50" );
            // 輸入已執行項目名稱
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "test1_completed" );
            // 輸入已執行項目描述
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_CONTROL );
            bot.keyPress( KeyEvent.VK_A ); bot.keyRelease( KeyEvent.VK_A ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_CONTROL );
            inputString( bot, "test123" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            // 勾選"已完成，刪除此排程項目"
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 執行下一個事項排程，輸入執行日期 & 時間
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SHIFT );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyRelease( KeyEvent.VK_SHIFT );
            inputString( bot, "2017" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "05" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "01" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "12" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "00" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            // 輸入已執行項目描述
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "test2" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            // 檢查事項排程資料是否正確
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, 
                "<html><head><style type=\"text/css\">" + 
                    "table, th, td {border: 1px solid black; border-collapse: collapse;}</style></head>" + 
                "<body><p>顯示的資料是否為:</p><table>" + 
                    "<tr><th>類型</th><th>日期</th><th>時間</th><th>項目</th><th>預計花費</th></tr>" + 
                    "<tr><td>準時</td><td>2017.05.01</td><td>11:35</td><td>test2</td><td>85</td></tr>" + 
                    "<tr><td>準時</td><td>2017.05.01</td><td>11:40</td><td>test3</td><td>85</td></tr>" + 
                    "<tr><td>準時</td><td>2017.05.01</td><td>11:45</td><td>test4</td><td>85</td></tr>" + 
                    "<tr><td>準時</td><td>2017.05.01</td><td>11:50</td><td>test5</td><td>85</td></tr>" + 
                "</table></body></html>", 
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            
            List<ScheduledItem> expectScheduledItemData = new ArrayList<ScheduledItem>();
            for( int i = 2; i <= 5; i++ ) {
                ScheduledItem scheduledItem = getTestData1();
                scheduledItem.setId( i );
                scheduledItem.setMinute( scheduledItem.getMinute() + i * 5 );
                scheduledItem.setName( scheduledItem.getName() + i );
                expectScheduledItemData.add( scheduledItem );
            }
            List<ScheduledItem> actualScheduledItemData = scheduledItemService.findAllSortByTime();
            assertEquals( expectScheduledItemData.size(), actualScheduledItemData.size() );
            for( int i = 0; i < expectScheduledItemData.size(); i++ ) {
                assertTrue( "failed at i = " + i, ScheduledItemUtil.equals( expectScheduledItemData.get( i ), actualScheduledItemData.get( i ) ) );
            }
            
            // 檢查時間記錄的資料是否正確
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_LEFT ); bot.keyRelease( KeyEvent.VK_LEFT ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "2017" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            inputString( bot, "05" );
            bot.keyPress( KeyEvent.VK_TAB ); bot.keyRelease( KeyEvent.VK_TAB ); Thread.sleep( 100 );
            bot.keyPress( KeyEvent.VK_SPACE ); bot.keyRelease( KeyEvent.VK_SPACE ); Thread.sleep( 100 );
            Thread.sleep( 1000 );
            
            testerSelection = JOptionPane.showConfirmDialog( 
                mainFrame, 
                "<html><head><style type=\"text/css\">" + 
                        "table, th, td {border: 1px solid black; border-collapse: collapse;}</style></head>" + 
                "<body><p>顯示的資料是否為:</p><table>" + 
                    "<tr><th>時間</th><th>項目</th></tr>" + 
                    "<tr><td>11:50 ~ 11:50</td><td>test1_completed</td></tr>" + 
                    "<tr><td>12:00 ~ 12:00</td><td>test2</td></tr>" + 
                "</table></body></html>", 
                "Check", JOptionPane.YES_NO_OPTION );
            assertEquals( JOptionPane.YES_OPTION, testerSelection );
            
            List<Item> expectItemData = new ArrayList<Item>();
            for( int i = 1; i <= 2; i++ ) {
                Item item = new Item();
                item.setYear( 2017 );
                item.setMonth( 5 );
                item.setDay( 1 );
                item.setSeq( 0 );
                switch( i ) {
                case 1:
                    item.setStartHour( 11 );
                    item.setStartMinute( 50 );
                    item.setEndHour( 11 );
                    item.setEndMinute( 50 );
                    item.setName( "test1_completed" );
                    item.setDescription( "test123" );
                    break;
                case 2:
                    item.setStartHour( 12 );
                    item.setStartMinute( 00 );
                    item.setEndHour( 12 );
                    item.setEndMinute( 00 );
                    item.setName( "test2" );
                    item.setDescription( "test2" );
                    break;
                }
                expectItemData.add( item );
            }
            List<Item> actualItemData = itemService.findByDate( 2017, 5, 1 );
            assertEquals( expectItemData.size(), actualItemData.size() );
            for( int i = 0; i < expectItemData.size(); i++ ) {
                assertTrue( "failed at i = " + i, ItemUtil.equals( expectItemData.get( i ), actualItemData.get( i ) ) );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            assertTrue( e.getMessage(), false );
        } finally {
            restoreFile( ITEM_CSV_FILE_BACKUP_PATH, ITEM_CSV_FILE_PATH );
            restoreFile( S_ITEM_SEQ_FILE_BACKUP_PATH, S_ITEM_SEQ_FILE_PATH );
            restoreFile( S_ITEM_CSV_FILE_BACKUP_PATH, S_ITEM_CSV_FILE_PATH );
            mainFrame.dispose();
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
        testData.setName( "test" );
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
    
    private void inputString( Robot bot, String s ) throws InterruptedException {
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
            Thread.sleep( 100 );
            if( Character.isUpperCase( s.charAt( i ) ) || 
                    shiftPunctuationList.indexOf( s.charAt( i ) ) >= 0 ) {
                bot.keyRelease( KeyEvent.VK_SHIFT );
            }
        }
    }
}

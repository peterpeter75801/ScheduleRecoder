package view;

import java.awt.AWTKeyStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import common.Contants;
import commonUtil.ScheduledItemUtil;
import domain.ScheduledItem;
import service.ScheduledItemService;
import service.Impl.ScheduledItemServiceImpl;

public class ScheduledItemPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    private ScheduledItemService scheduledItemService;

    private MainFrame ownerFrame;
    private ScheduledItemCreateDialog scheduledItemCreateDialog; 
    private ScheduledItemUpdateDialog scheduledItemUpdateDialog;
    private ScheduledItemDetailDialog scheduledItemDetailDialog;
    private ScheduledItemExecuteDialog scheduledItemExecuteDialog;
    
    private MnemonicKeyHandler mnemonicKeyHandler;
    private Font generalFont;
    private Font itemTableFont;
    private JTable itemTable;
    private JScrollPane itemTableScrollPane;
    private JButton createButton;
    private JButton updateButton;
    private JButton executeButton;
    private JButton cancelButton;
    private JButton detailButton;
    
    private JLabel versionLabel;
    
    public ScheduledItemPanel( MainFrame ownerFrame ) {
        setLayout( null );
        
        scheduledItemService = new ScheduledItemServiceImpl();
        
        mnemonicKeyHandler = new MnemonicKeyHandler();
        
        generalFont = new Font( "細明體", Font.PLAIN, 16 );
        itemTableFont = new Font( "細明體", Font.PLAIN, 12 );
        
        this.ownerFrame = ownerFrame;
        scheduledItemCreateDialog = new ScheduledItemCreateDialog( ownerFrame, scheduledItemService );
        scheduledItemUpdateDialog = new ScheduledItemUpdateDialog( ownerFrame, scheduledItemService );
        scheduledItemDetailDialog = new ScheduledItemDetailDialog( ownerFrame, scheduledItemService );
        scheduledItemExecuteDialog = new ScheduledItemExecuteDialog( ownerFrame, scheduledItemService );
        
        initialItemTable();
        
        createButton = new JButton( "新增(N)" );
        createButton.setBounds( 697, 54, 72, 22 );
        createButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        createButton.setFont( generalFont );
        createButton.addKeyListener( mnemonicKeyHandler );
        createButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                scheduledItemCreateDialog.openDialog();
            }
        });
        add( createButton );
        
        updateButton = new JButton( "修改(U)" );
        updateButton.setBounds( 697, 98, 72, 22 );
        updateButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        updateButton.setFont( generalFont );
        updateButton.addKeyListener( mnemonicKeyHandler );
        updateButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                openScheduledItemUpdateDialog();
            }
        });
        add( updateButton );
        
        executeButton = new JButton( "執行(E)" );
        executeButton.setBounds( 697, 142, 72, 22 );
        executeButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        executeButton.setFont( generalFont );
        executeButton.addKeyListener( mnemonicKeyHandler );
        executeButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                openScheduledItemExecuteDialog();
            }
        });
        add( executeButton );
        
        cancelButton = new JButton( "取消(C)" );
        cancelButton.setBounds( 697, 186, 72, 22 );
        cancelButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        cancelButton.setFont( generalFont );
        cancelButton.addKeyListener( mnemonicKeyHandler );
        cancelButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                deleteScheduledItem();
            }
        });
        add( cancelButton );
        
        detailButton = new JButton( "詳細(D)" );
        detailButton.setBounds( 697, 230, 72, 22 );
        detailButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        detailButton.setFont( generalFont );
        detailButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                openScheduledItemDetailDialog();
            }
        });
        detailButton.addKeyListener( mnemonicKeyHandler );
        add( detailButton );
        
        versionLabel = new JLabel( Contants.VERSION, SwingConstants.RIGHT );
        versionLabel.setBounds( 520, 508, 265, 22 );
        versionLabel.setFont( generalFont );
        add( versionLabel );
        
        loadScheduledItems();
    }
    
    public void loadScheduledItems() {
        // 初始化畫面中的排程項目列表
        final int DEFAULT_ROW_COUNT = 20;
        DefaultTableModel itemTableModel = (DefaultTableModel) itemTable.getModel();
        if( itemTable.getRowCount() > DEFAULT_ROW_COUNT ) {
            for( int i = itemTable.getRowCount() - 1; i >= DEFAULT_ROW_COUNT; i-- ) {
                itemTableModel.removeRow( i );
            }
        }
        for( int i = 0; i < itemTable.getRowCount(); i++ ) {
            itemTableModel.setValueAt( "", i, 0 );
            itemTableModel.setValueAt( "", i, 1 );
            itemTableModel.setValueAt( "", i, 2 );
            itemTableModel.setValueAt( "", i, 3 );
            itemTableModel.setValueAt( "", i, 4 );
            itemTableModel.setValueAt( "", i, 5 );
        }
        
        // 取得目前所有的排程項目(ScheduledItem)資料
        try {
            List<ScheduledItem> scheduledItemList = scheduledItemService.findAllSortByTime();
            for( int i = 0; i < scheduledItemList.size(); i++ ) {
                ScheduledItem scheduledItem = scheduledItemList.get( i );
                DefaultTableModel model = (DefaultTableModel) itemTable.getModel();
                String currentExpectedTimeString = "";
                if( scheduledItem.getExpectedTime() != -1 ) {
                    currentExpectedTimeString = String.format( "%d min", scheduledItem.getExpectedTime() );
                }
                if( i >= itemTable.getRowCount() ) {
                    model.addRow( new Object[]{
                        ScheduledItemUtil.getTypeNameFromCode( scheduledItem.getType() ),
                        String.format( "%04d.%02d.%02d", scheduledItem.getYear(), scheduledItem.getMonth(), scheduledItem.getDay() ),
                        String.format( "%02d:%02d", scheduledItem.getHour(), scheduledItem.getMinute() ),
                        scheduledItem.getName(),
                        currentExpectedTimeString,
                        String.format( "%d", scheduledItem.getId() ) } );
                } else {
                    model.setValueAt( ScheduledItemUtil.getTypeNameFromCode( scheduledItem.getType() ), i, 0 );
                    model.setValueAt( String.format( "%04d.%02d.%02d", scheduledItem.getYear(), scheduledItem.getMonth(), scheduledItem.getDay() ), 
                        i, 1 );
                    model.setValueAt( String.format( "%02d:%02d", scheduledItem.getHour(), scheduledItem.getMinute() ), i, 2 );
                    model.setValueAt( scheduledItem.getName(), i, 3 );
                    model.setValueAt( currentExpectedTimeString, i, 4 );
                    model.setValueAt( String.format( "%d", scheduledItem.getId() ), i, 5 );
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog( null, "排程事項讀取資料發生錯誤", "Error", JOptionPane.ERROR_MESSAGE );
        }
    }
    
    private void deleteScheduledItem() {
        int itemTableSelectedIndex = itemTable.getSelectedRow();
        
        if( itemTableSelectedIndex < 0 ) {
            JOptionPane.showMessageDialog( ownerFrame, "未選擇資料", "Warning", JOptionPane.WARNING_MESSAGE );
            return;
        }
        
        int id = 0;
        String itemTableSelectedIdValue = (String) itemTable.getModel().getValueAt( itemTableSelectedIndex, 5 );
        try {
            id = Integer.parseInt( itemTableSelectedIdValue );
        } catch( NumberFormatException e ) {
            JOptionPane.showMessageDialog( ownerFrame, "選擇無效的資料", "Warning", JOptionPane.WARNING_MESSAGE );
            return;
        } catch( StringIndexOutOfBoundsException e ) {
            JOptionPane.showMessageDialog( ownerFrame, "選擇無效的資料", "Warning", JOptionPane.WARNING_MESSAGE );
            return;
        }
        
        int comfirmation = JOptionPane.showConfirmDialog( 
                ownerFrame, "確認刪除?", "Check", JOptionPane.YES_NO_OPTION );
        if( comfirmation != JOptionPane.YES_OPTION ) {
            return;
        }
        
        ScheduledItem scheduledItemForDelete = new ScheduledItem();
        scheduledItemForDelete.setId( id );
        
        int returnCode = 0;
        try {
            returnCode = scheduledItemService.delete( scheduledItemForDelete );
        } catch ( Exception e ) {
            e.printStackTrace();
            returnCode = Contants.ERROR;
        }
        switch( returnCode ) {
        case Contants.SUCCESS:
            loadScheduledItems();
            break;
        case Contants.ERROR:
            JOptionPane.showMessageDialog( null, "刪除失敗", "Error", JOptionPane.ERROR_MESSAGE );
            break;
        default:
            break;
        }
    }
    
    private void initialItemTable() {
        final int DEFAULT_ROW_COUNT = 20;
        final int TABLE_WIDTH = 656;
        final int TABLE_HEIGHT = 440;
        final int TABLE_HEADER_HEIGHT = 22;
        final int TABLE_ROW_HEIGHT = 22;
        final int[] TABLE_COLUMN_WIDTH = { 48, 96, 56, 392, 64, 0 };
        final int BORDER_HEIGHT_FIX = 3;
        final String[] columnNames = { "類型", "日期", "時間", "項目", "預計花費", "ID(hidden)" };
        
        itemTable = new JTable( new DefaultTableModel( columnNames, DEFAULT_ROW_COUNT ) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable( int rowIndex, int columnIndex ) {
                return false;
            }
        });
        itemTable.setFont( itemTableFont );
        
        itemTable.getTableHeader().setReorderingAllowed( false );
        itemTable.getTableHeader().setPreferredSize( new Dimension( TABLE_WIDTH, TABLE_HEADER_HEIGHT ) );
        itemTable.setRowHeight( TABLE_ROW_HEIGHT );
        
        itemTable.getColumnModel().getColumn( 0 ).setPreferredWidth( TABLE_COLUMN_WIDTH[ 0 ] );
        itemTable.getColumnModel().getColumn( 1 ).setPreferredWidth( TABLE_COLUMN_WIDTH[ 1 ] );
        itemTable.getColumnModel().getColumn( 2 ).setPreferredWidth( TABLE_COLUMN_WIDTH[ 2 ] );
        itemTable.getColumnModel().getColumn( 3 ).setPreferredWidth( TABLE_COLUMN_WIDTH[ 3 ] );
        itemTable.getColumnModel().getColumn( 4 ).setPreferredWidth( TABLE_COLUMN_WIDTH[ 4 ] );
        itemTable.removeColumn( itemTable.getColumnModel().getColumn( 5 ) );
        
        itemTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        itemTable.setPreferredScrollableViewportSize( new Dimension( TABLE_WIDTH, TABLE_HEIGHT ) );
        itemTable.addKeyListener( mnemonicKeyHandler );
        
        // 設定 Tab 和 Shift+Tab 為跳轉按鍵(traversal key)
        Set<AWTKeyStroke> forward = new HashSet<AWTKeyStroke>(
                itemTable.getFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS ) );
        forward.add( KeyStroke.getKeyStroke( "TAB" ) );
        itemTable.setFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forward );
        Set<AWTKeyStroke> backward = new HashSet<AWTKeyStroke>(
                itemTable.getFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS ) );
        backward.add( KeyStroke.getKeyStroke( "shift TAB" ) );
        itemTable.setFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backward );
        
        // 移除 JTable 中 Enter 鍵的預設功能
        itemTable.getInputMap( JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT )
            .put( KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 ), "Enter" );
        itemTable.getActionMap().put( "Enter", new AbstractAction() {
        
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( ActionEvent event ) { /* do nothing */ }
        });
        
        itemTableScrollPane = new JScrollPane( itemTable );
        itemTableScrollPane.setBounds( 16, 29, TABLE_WIDTH, TABLE_HEIGHT + TABLE_HEADER_HEIGHT + BORDER_HEIGHT_FIX );
        
        add( itemTableScrollPane );
    }
    
    private void openScheduledItemUpdateDialog() {
        int id = getItemTableSelectedId();
        if( id != -1 ) {
            scheduledItemUpdateDialog.openDialog( id );
        }
    }
    
    private void openScheduledItemDetailDialog() {
        int id = getItemTableSelectedId();
        if( id != -1 ) {
            scheduledItemDetailDialog.openDialog( id );
        }
    }
    
    private void openScheduledItemExecuteDialog() {
        int id = getItemTableSelectedId();
        if( id != -1 ) {
            scheduledItemExecuteDialog.openDialog( id );
        }
    }
    
    private int getItemTableSelectedId() {
        int itemTableSelectedIndex = itemTable.getSelectedRow();
        
        if( itemTableSelectedIndex < 0 ) {
            JOptionPane.showMessageDialog( ownerFrame, "未選擇資料", "Warning", JOptionPane.WARNING_MESSAGE );
            return -1;
        }
        
        int id = -1;
        String itemTableSelectedIdValue = (String) itemTable.getModel().getValueAt( itemTableSelectedIndex, 5 );
        try {
            id = Integer.parseInt( itemTableSelectedIdValue );
        } catch( NumberFormatException e ) {
            JOptionPane.showMessageDialog( ownerFrame, "選擇無效的資料", "Warning", JOptionPane.WARNING_MESSAGE );
            return -1;
        } catch( StringIndexOutOfBoundsException e ) {
            JOptionPane.showMessageDialog( ownerFrame, "選擇無效的資料", "Warning", JOptionPane.WARNING_MESSAGE );
            return -1;
        }
        
        return id;
    }
    
    private class MnemonicKeyHandler implements KeyListener {
        
        @Override
        public void keyPressed( KeyEvent event ) {
            switch( event.getKeyCode() ) {
            case KeyEvent.VK_ENTER:
                if( event.getSource() == createButton ) {
                    scheduledItemCreateDialog.openDialog();
                } else if( event.getSource() == updateButton ) {
                    openScheduledItemUpdateDialog();
                } else if( event.getSource() == executeButton ) {
                    openScheduledItemExecuteDialog();
                } else if( event.getSource() == cancelButton ) {
                    deleteScheduledItem();
                } else if( event.getSource() == detailButton || event.getSource() == itemTable ) {
                    openScheduledItemDetailDialog();
                }
                break;
            case KeyEvent.VK_N:
                scheduledItemCreateDialog.openDialog();
                break;
            case KeyEvent.VK_U:
                openScheduledItemUpdateDialog();
                break;
            case KeyEvent.VK_E:
                openScheduledItemExecuteDialog();
                break;
            case KeyEvent.VK_C:
                deleteScheduledItem();
                break;
            case KeyEvent.VK_D:
                openScheduledItemDetailDialog();
                break;
            default:
                break;
            }
        }

        @Override
        public void keyReleased( KeyEvent event ) {}

        @Override
        public void keyTyped( KeyEvent event ) {}
    }
}

package view;

import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
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
    
    private Font generalFont;
    private JTable itemTable;
    private JScrollPane itemTableScrollPane;
    private JButton createButton;
    private JButton updateButton;
    private JButton completeButton;
    private JButton cancelButton;
    private JButton detailButton;
    
    private JLabel versionLabel;
    
    public ScheduledItemPanel( MainFrame ownerFrame ) {
        setLayout( null );
        
        scheduledItemService = new ScheduledItemServiceImpl();
        
        generalFont = new Font( "細明體", Font.PLAIN, 16 );
        
        this.ownerFrame = ownerFrame;
        scheduledItemCreateDialog = new ScheduledItemCreateDialog( ownerFrame, scheduledItemService );
        scheduledItemUpdateDialog = new ScheduledItemUpdateDialog( ownerFrame, scheduledItemService );
        
        initialItemTable();
        
        createButton = new JButton( "新增(N)" );
        createButton.setBounds( 697, 54, 72, 22 );
        createButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        createButton.setFont( generalFont );
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
        updateButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                openScheduledItemUpdateDialog();
            }
        });
        add( updateButton );
        
        completeButton = new JButton( "完成(O)" );
        completeButton.setBounds( 697, 142, 72, 22 );
        completeButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        completeButton.setFont( generalFont );
        add( completeButton );
        
        cancelButton = new JButton( "取消(C)" );
        cancelButton.setBounds( 697, 186, 72, 22 );
        cancelButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        cancelButton.setFont( generalFont );
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
        add( detailButton );
        
        versionLabel = new JLabel( Contants.VERSION, SwingConstants.RIGHT );
        versionLabel.setBounds( 520, 508, 265, 22 );
        versionLabel.setFont( generalFont );
        add( versionLabel );
        
        loadScheduledItems();
        
        //adjustComponentOrder();
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
                if( i >= itemTable.getRowCount() ) {
                    model.addRow( new Object[]{
                        ScheduledItemUtil.getTypeNameFromCode( scheduledItem.getType() ),
                        String.format( "%04d.%02d.%02d", scheduledItem.getYear(), scheduledItem.getMonth(), scheduledItem.getDay() ),
                        String.format( "%02d:%02d", scheduledItem.getHour(), scheduledItem.getMinute() ),
                        scheduledItem.getName(),
                        String.format( "%d min", scheduledItem.getExpectedTime() ),
                        String.format( "%d", scheduledItem.getId() ) } );
                } else {
                    model.setValueAt( ScheduledItemUtil.getTypeNameFromCode( scheduledItem.getType() ), i, 0 );
                    model.setValueAt( String.format( "%04d.%02d.%02d", scheduledItem.getYear(), scheduledItem.getMonth(), scheduledItem.getDay() ), 
                        i, 1 );
                    model.setValueAt( String.format( "%02d:%02d", scheduledItem.getHour(), scheduledItem.getMinute() ), i, 2 );
                    model.setValueAt( scheduledItem.getName(), i, 3 );
                    model.setValueAt( String.format( "%d min", scheduledItem.getExpectedTime() ), i, 4 );
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
        //itemTable.addKeyListener( mnemonicKeyHandler );
        
        Set<AWTKeyStroke> forward = new HashSet<AWTKeyStroke>(
                itemTable.getFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS ) );
        forward.add( KeyStroke.getKeyStroke( "TAB" ) );
        itemTable.setFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forward );
        Set<AWTKeyStroke> backward = new HashSet<AWTKeyStroke>(
                itemTable.getFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS ) );
        backward.add( KeyStroke.getKeyStroke( "shift TAB" ) );
        itemTable.setFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backward );
        
        itemTableScrollPane = new JScrollPane( itemTable );
        itemTableScrollPane.setBounds( 16, 29, TABLE_WIDTH, TABLE_HEIGHT + TABLE_HEADER_HEIGHT + BORDER_HEIGHT_FIX );
        
        add( itemTableScrollPane );
    }
    
    private void adjustComponentOrder() {
        final int FOCUSABLE_COMPONENT_COUNT = 6;
        Vector<Component> order = new Vector<Component>( FOCUSABLE_COMPONENT_COUNT );
        order.add( itemTable );
        order.add( createButton );
        order.add( updateButton );
        order.add( completeButton );
        order.add( cancelButton );
        order.add( detailButton );
        this.setFocusTraversalPolicyProvider( true );
        this.setFocusTraversalPolicy( new FocusTraversalPolicy() {
            @Override
            public Component getComponentAfter( Container aContainer, Component aComponent ) {
                return order.get( (order.indexOf( aComponent ) + 1) % order.size() );
            }

            @Override
            public Component getComponentBefore( Container aContainer, Component aComponent ) {
                int index = order.indexOf( aComponent ) - 1;
                if( index < 0 ) {
                    index = order.size() - 1;
                }
                return order.get( index );
            }

            @Override
            public Component getDefaultComponent( Container aContainer ) {
                return itemTable;
            }

            @Override
            public Component getFirstComponent( Container aContainer ) {
                return itemTable;
            }

            @Override
            public Component getLastComponent( Container aContainer ) {
                return detailButton;
            }
        });
    }
    
    private void openScheduledItemUpdateDialog() {
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
        
        scheduledItemUpdateDialog.openDialog( id );
    }
}

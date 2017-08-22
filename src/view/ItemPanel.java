package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import common.Contants;
import domain.Item;
import service.ItemService;
import service.Impl.ItemServiceImpl;

public class ItemPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    private ItemService itemService;
    
    private MainFrame ownerFrame;
    private ItemCreateDialog itemCreateDialog;
    private ItemUpdateDialog itemUpdateDialog;
    private ItemImportDialog itemImportDialog;
    private ItemExportDialog itemExportDialog;
    
    private FocusHandler focusHandler;
    private MnemonicKeyHandler mnemonicKeyHandler;
    private SpecialFocusTraversalPolicyHandler specialFocusTraversalPolicyHandler;
    private Font generalFont;
    private JLabel yearLabel;
    private JTextField yearTextField;
    private JLabel monthLabel;
    private JTextField monthTextField;
    private JButton listDateButton;
    private JList<String> dateList;
    private JScrollPane dateListScrollPane;
    private JTable itemTable;
    private JScrollPane itemTableScrollPane;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton importButton;
    private JButton exportButton;
    
    private JLabel versionLabel;
    
    // TODO: 選擇dateList選項以後，自動選擇itemTable的第一筆資料
    // TODO: 將程式初始的焦點設定在itemTable
    // TODO: 將JTabbedPane標籤加入tab切換的循環中
    
    public ItemPanel( MainFrame ownerFrame ) {
        setLayout( null );
        
        itemService = new ItemServiceImpl();
        
        focusHandler = new FocusHandler();
        mnemonicKeyHandler = new MnemonicKeyHandler();
        specialFocusTraversalPolicyHandler = new SpecialFocusTraversalPolicyHandler();
        
        generalFont = new Font( "細明體", Font.PLAIN, 16 );
        
        this.ownerFrame = ownerFrame;
        itemCreateDialog = new ItemCreateDialog( ownerFrame, itemService );
        itemUpdateDialog = new ItemUpdateDialog( ownerFrame, itemService );
        itemImportDialog = new ItemImportDialog( ownerFrame, itemService );
        itemExportDialog = new ItemExportDialog( ownerFrame, itemService );
        
        initialYearAndMonthTextField();
        initialListDateButton();
        initialItemTable();
        initialDateList();
        
        createButton = new JButton( "新增(N)" );
        createButton.setBounds( 697, 54, 72, 22 );
        createButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        createButton.setFont( generalFont );
        createButton.addKeyListener( mnemonicKeyHandler );
        createButton.setFocusTraversalKeysEnabled( false );
        createButton.addKeyListener( specialFocusTraversalPolicyHandler );
        createButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                itemCreateDialog.openDialog();
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
                openItemUpdateDialog();
            }
        });
        add( updateButton );
        
        deleteButton = new JButton( "刪除(D)" );
        deleteButton.setBounds( 697, 142, 72, 22 );
        deleteButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        deleteButton.setFont( generalFont );
        deleteButton.addKeyListener( mnemonicKeyHandler );
        deleteButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                deleteItem();
            }
        });
        add( deleteButton );
        
        importButton = new JButton( "匯入(I)" );
        importButton.setBounds( 697, 186, 72, 22 );
        importButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        importButton.setFont( generalFont );
        importButton.addKeyListener( mnemonicKeyHandler );
        importButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                itemImportDialog.openDialog( dateList.getSelectedValue() );
            }
        });
        add( importButton );
        
        exportButton = new JButton( "匯出(E)" );
        exportButton.setBounds( 697, 230, 72, 22 );
        exportButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        exportButton.setFont( generalFont );
        exportButton.addKeyListener( mnemonicKeyHandler );
        exportButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                itemExportDialog.openDialog( dateList.getSelectedValue() );
            }
        });
        add( exportButton );
        
        versionLabel = new JLabel( Contants.VERSION, SwingConstants.RIGHT );
        versionLabel.setBounds( 520, 508, 265, 22 );
        versionLabel.setFont( generalFont );
        add( versionLabel );
        
        setPreferredSize( new Dimension( 793, 533 ) );
    }
    
    private void checkYearAndMonthTextField() {
        Calendar calendar = Calendar.getInstance();
        
        try {
            int year = Integer.parseInt( yearTextField.getText() );
            int month = Integer.parseInt( monthTextField.getText() );
            calendar.set( year, month - 1, 1 );
        } catch( NumberFormatException e ) {
            calendar.setTime( new Date() );
        }
        
        yearTextField.setText( String.format( "%04d", calendar.get( Calendar.YEAR ) ) );
        monthTextField.setText( String.format( "%02d", calendar.get( Calendar.MONTH ) + 1 ) );
    }
    
    private void deleteItem() {
        String dateListSelectedValue = dateList.getSelectedValue();
        int itemTableSelectedIndex = itemTable.getSelectedRow();
        
        if( dateListSelectedValue == null || itemTableSelectedIndex < 0 ) {
            JOptionPane.showMessageDialog( ownerFrame, "未選擇資料", "Warning", JOptionPane.WARNING_MESSAGE );
            return;
        }
        
        int year, month, day, startHour, startMinute;
        String itemTableSelectedTimeValue = (String) itemTable.getValueAt( itemTableSelectedIndex, 0 );
        try {
            year = Integer.parseInt( dateListSelectedValue.substring( 0, 4 ) );
            month = Integer.parseInt( dateListSelectedValue.substring( 5, 7 ) );
            day = Integer.parseInt( dateListSelectedValue.substring( 8, 10 ) );
            startHour = Integer.parseInt( itemTableSelectedTimeValue.substring( 0, 2 ) );
            startMinute = Integer.parseInt( itemTableSelectedTimeValue.substring( 3, 5 ) );
        } catch( NumberFormatException e ) {
            JOptionPane.showMessageDialog( ownerFrame, "選擇無效的資料", "Warning", JOptionPane.WARNING_MESSAGE );
            return;
        } catch( StringIndexOutOfBoundsException e ) {
            JOptionPane.showMessageDialog( ownerFrame, "選擇無效的資料", "Warning", JOptionPane.WARNING_MESSAGE );
            return;
        }
        
        Item itemForDelete = new Item();
        itemForDelete.setYear( year );
        itemForDelete.setMonth( month );
        itemForDelete.setDay( day );
        itemForDelete.setStartHour( startHour );
        itemForDelete.setStartMinute( startMinute );
        
        int returnCode = 0;
        try {
            returnCode = itemService.delete( itemForDelete );
        } catch ( Exception e ) {
            e.printStackTrace();
            returnCode = Contants.ERROR;
        }
        switch( returnCode ) {
        case Contants.SUCCESS:
            reselectDateList();
            break;
        case Contants.ERROR:
            JOptionPane.showMessageDialog( null, "刪除失敗", "Error", JOptionPane.ERROR_MESSAGE );
            break;
        default:
            break;
        }
    }
    
    private void initialListDateButton() {
        listDateButton = new JButton( "列出" );
        listDateButton.setBounds( 120, 10, 40, 22 );
        listDateButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        listDateButton.setFont( generalFont );
        listDateButton.setFocusTraversalKeysEnabled( false );
        listDateButton.addKeyListener( specialFocusTraversalPolicyHandler );
        listDateButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                checkYearAndMonthTextField();
                reInitialDateList();
            }
        });
        add( listDateButton );
    }
    
    private void initialYearAndMonthTextField() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( new Date() );
        
        yearTextField = new JTextField( 4 );
        yearTextField.setBounds( 16, 10, 40, 22 );
        yearTextField.setFont( generalFont );
        yearTextField.addFocusListener( focusHandler );
        yearTextField.setText( String.format( "%04d", calendar.get( Calendar.YEAR ) ) );
        add( yearTextField );
        
        yearLabel = new JLabel( "年" );
        yearLabel.setBounds( 56, 10, 16, 22 );
        yearLabel.setFont( generalFont );
        add( yearLabel );
        
        monthTextField = new JTextField( 2 );
        monthTextField.setBounds( 72, 10, 24, 22 );
        monthTextField.setFont( generalFont );
        monthTextField.addFocusListener( focusHandler );
        monthTextField.setText( String.format( "%02d", calendar.get( Calendar.MONTH ) + 1 ) );
        add( monthTextField );
        
        monthLabel = new JLabel( "月" );
        monthLabel.setBounds( 96, 10, 16, 22 );
        monthLabel.setFont( generalFont );
        add( monthLabel );
    }
    
    private void initialDateList() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( new Date() );
        int year = calendar.get( Calendar.YEAR );
        int month = calendar.get( Calendar.MONTH ) + 1;
        
        String[] dateListString = new String[ calendar.getActualMaximum( Calendar.DAY_OF_MONTH ) ];
        for( int i = 0; i < dateListString.length; i++ ) {
            dateListString[ i ] = String.format( "%04d.%02d.%02d", year, month, i+1 );
        }
        
        dateList = new JList<String>( dateListString );
        dateList.setVisibleRowCount( 17 );
        dateList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        dateList.getSelectionModel().addListSelectionListener( new ListSelectionListener() {
            @Override
            public void valueChanged( ListSelectionEvent event ) {
                String dateString = dateList.getSelectedValue();
                
                if( dateString == null ) {
                    return;
                }
                
                int year = Integer.parseInt( dateString.substring( 0, 4 ) );
                int month = Integer.parseInt( dateString.substring( 5, 7 ) );
                int day = Integer.parseInt( dateString.substring( 8, 10 ) );

                reInitialItemTable();
                findByDateIntoItemTable( year, month, day );
            }
        });
        dateList.setSelectedIndex( calendar.get( Calendar.DAY_OF_MONTH ) - 1 );
        dateList.setFont( generalFont );
        dateList.addKeyListener( mnemonicKeyHandler );
        dateList.setFocusTraversalKeysEnabled( false );
        dateList.addKeyListener( specialFocusTraversalPolicyHandler );
        
        dateListScrollPane = new JScrollPane( dateList );
        dateListScrollPane.setBounds( 16, 54, 144, 440 );

        dateList.ensureIndexIsVisible( dateList.getSelectedIndex() );
        
        add( dateListScrollPane );
    }
    
    private void reInitialDateList() {
        DefaultListModel<String> newDateListModel = new DefaultListModel<String>();
        
        Calendar calendar = Calendar.getInstance();
        int year, month;
        
        try {
            year = Integer.parseInt( yearTextField.getText() );
            month = Integer.parseInt( monthTextField.getText() );
            calendar.set( year, month - 1, 1 );
        } catch( NumberFormatException e ) {
            calendar.setTime( new Date() );
            year = calendar.get( Calendar.YEAR );
            month = calendar.get( Calendar.MONTH ) + 1;
        }
        
        for( int i = 0; i < calendar.getActualMaximum( Calendar.DAY_OF_MONTH ); i++ ) {
            newDateListModel.add( i, String.format( "%04d.%02d.%02d", year, month, i+1 ) );
        }
        
        dateList.setModel( newDateListModel );
        dateList.setSelectedIndex( calendar.get( Calendar.DAY_OF_MONTH ) - 1 );

        dateList.ensureIndexIsVisible( dateList.getSelectedIndex() );
    }
    
    private void findByDateIntoItemTable( int year, int month, int day ) {
        try {
            List<Item> itemList = itemService.findByDate( year, month, day );
            for( int i = 0; i < itemList.size(); i++ ) {
                Item item = itemList.get( i );
                DefaultTableModel model = (DefaultTableModel) itemTable.getModel();
                if( i >= itemTable.getRowCount() ) {
                    model.addRow( new Object[]{ 
                        String.format( "%02d:%02d ~ %02d:%02d", item.getStartHour(), item.getStartMinute(), item.getEndHour(), item.getEndMinute() ),
                        item.getName() } );
                } else {
                    model.setValueAt( String.format( "%02d:%02d ~ %02d:%02d", item.getStartHour(), item.getStartMinute(), item.getEndHour(), item.getEndMinute() ), i, 0 );
                    model.setValueAt( item.getName(), i, 1 );
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog( null, "讀取資料發生錯誤", "Error", JOptionPane.ERROR_MESSAGE );
        }
    }
    
    private void initialItemTable() {
        final int DEFAULT_ROW_COUNT = 20;
        final int TABLE_WIDTH = 496;
        final int TABLE_HEIGHT = 440;
        final int TABLE_HEADER_HEIGHT = 22;
        final int TABLE_ROW_HEIGHT = 22;
        final int[] TABLE_COLUMN_WIDTH = { 128, 368 };
        final int BORDER_HEIGHT_FIX = 3;
        final String[] columnNames = { "時間", "項目" };
        
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
        
        itemTable.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        itemTable.setPreferredScrollableViewportSize( new Dimension( TABLE_WIDTH, TABLE_HEIGHT ) );
        itemTable.addKeyListener( mnemonicKeyHandler );
        
        itemTable.getInputMap( JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT ).put(
            KeyStroke.getKeyStroke( KeyEvent.VK_TAB, 0 ), "none" );
        itemTable.getInputMap( JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT ).put(
            KeyStroke.getKeyStroke( KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK ), "none" );        
        
        itemTable.setFocusTraversalKeysEnabled( false );
        itemTable.addKeyListener( specialFocusTraversalPolicyHandler );
        
        itemTableScrollPane = new JScrollPane( itemTable );
        itemTableScrollPane.setBounds( 176, 29, TABLE_WIDTH, TABLE_HEIGHT + TABLE_HEADER_HEIGHT + BORDER_HEIGHT_FIX );
        
        add( itemTableScrollPane );
    }
    
    private void reInitialItemTable() {
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
        }
    }
    
    private void openItemUpdateDialog() {
        String dateListSelectedValue = dateList.getSelectedValue();
        int itemTableSelectedIndex = itemTable.getSelectedRow();
        
        if( dateListSelectedValue == null || itemTableSelectedIndex < 0 ) {
            JOptionPane.showMessageDialog( ownerFrame, "未選擇資料", "Warning", JOptionPane.WARNING_MESSAGE );
            return;
        }
        
        int year, month, day, startHour, startMinute;
        String itemTableSelectedTimeValue = (String) itemTable.getValueAt( itemTableSelectedIndex, 0 );
        try {
            year = Integer.parseInt( dateListSelectedValue.substring( 0, 4 ) );
            month = Integer.parseInt( dateListSelectedValue.substring( 5, 7 ) );
            day = Integer.parseInt( dateListSelectedValue.substring( 8, 10 ) );
            startHour = Integer.parseInt( itemTableSelectedTimeValue.substring( 0, 2 ) );
            startMinute = Integer.parseInt( itemTableSelectedTimeValue.substring( 3, 5 ) );
        } catch( NumberFormatException e ) {
            JOptionPane.showMessageDialog( ownerFrame, "選擇無效的資料", "Warning", JOptionPane.WARNING_MESSAGE );
            return;
        } catch( StringIndexOutOfBoundsException e ) {
            JOptionPane.showMessageDialog( ownerFrame, "選擇無效的資料", "Warning", JOptionPane.WARNING_MESSAGE );
            return;
        }
        
        itemUpdateDialog.openDialog( year, month, day, startHour, startMinute );
    }
    
    public void reselectDateList() {
        int currentSelectedIndex = dateList.getSelectedIndex();
        dateList.clearSelection();
        dateList.setSelectedIndex( currentSelectedIndex );
    }
    
    private class FocusHandler extends FocusAdapter {
        @Override
        public void focusGained( FocusEvent event ) {
            JTextField sourceComponent = (JTextField) event.getSource();
            sourceComponent.selectAll();
        }
    }
    
    private class MnemonicKeyHandler implements KeyListener {
        
        @Override
        public void keyPressed( KeyEvent event ) {
            switch( event.getKeyCode() ) {
            case KeyEvent.VK_ENTER:
                if( event.getSource() == createButton ) {
                    itemCreateDialog.openDialog();
                } else if( event.getSource() == updateButton ) {
                    openItemUpdateDialog();
                } else if( event.getSource() == deleteButton ) {
                    deleteItem();
                } else if( event.getSource() == importButton ) {
                    itemImportDialog.openDialog( dateList.getSelectedValue() );
                } else if( event.getSource() == exportButton ) {
                    itemExportDialog.openDialog( dateList.getSelectedValue() );
                } else if( event.getSource() == yearTextField || event.getSource() == monthTextField || 
                        event.getSource() == listDateButton ) {
                    checkYearAndMonthTextField();
                    reInitialDateList();
                }
                break;
            case KeyEvent.VK_N:
                itemCreateDialog.openDialog();
                break;
            case KeyEvent.VK_U:
                openItemUpdateDialog();
                break;
            case KeyEvent.VK_D:
                deleteItem();
                break;
            case KeyEvent.VK_I:
                itemImportDialog.openDialog( dateList.getSelectedValue() );
                break;
            case KeyEvent.VK_E:
                itemExportDialog.openDialog( dateList.getSelectedValue() );
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
    
    private class SpecialFocusTraversalPolicyHandler implements KeyListener {

        @Override
        public void keyPressed( KeyEvent event ) {
            if( event.getKeyCode() != KeyEvent.VK_TAB ) {
                return;
            }
            
            if( event.getSource() == listDateButton && !event.isShiftDown() ) {
                dateList.requestFocus();
            } else if( event.getSource() == listDateButton && event.isShiftDown() ) {
                monthTextField.requestFocus();
            } else if( event.getSource() == dateList && !event.isShiftDown() ) {
                itemTable.requestFocus();
            } else if( event.getSource() == dateList && event.isShiftDown() ) {
                listDateButton.requestFocus();
            } else if( event.getSource() == itemTable && !event.isShiftDown() ) {
                createButton.requestFocus();
            } else if( event.getSource() == itemTable && event.isShiftDown() ) {
                dateList.requestFocus();
            } else if( event.getSource() == createButton && !event.isShiftDown() ) {
                updateButton.requestFocus();
            } else if( event.getSource() == createButton && event.isShiftDown() ) {
                itemTable.requestFocus();
            }
        }

        @Override
        public void keyReleased( KeyEvent event ) {}

        @Override
        public void keyTyped( KeyEvent event ) {}
    }
}

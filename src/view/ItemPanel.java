package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ItemPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
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
    
    public ItemPanel() {
        setLayout( null );
        
        generalFont = new Font( "細明體", Font.PLAIN, 16 );
        
        yearTextField = new JTextField( 4 );
        yearTextField.setBounds( 16, 10, 40, 22 );
        yearTextField.setFont( generalFont );
        add( yearTextField );
        
        yearLabel = new JLabel( "年" );
        yearLabel.setBounds( 56, 10, 16, 22 );
        yearLabel.setFont( generalFont );
        add( yearLabel );
        
        monthTextField = new JTextField( 2 );
        monthTextField.setBounds( 72, 10, 24, 22 );
        monthTextField.setFont( generalFont );
        add( monthTextField );
        
        monthLabel = new JLabel( "月" );
        monthLabel.setBounds( 96, 10, 16, 22 );
        monthLabel.setFont( generalFont );
        add( monthLabel );
        
        listDateButton = new JButton( "列出" );
        listDateButton.setBounds( 120, 10, 40, 22 );
        listDateButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        listDateButton.setFont( generalFont );
        add( listDateButton );
        
        initialDateList();
        initialItemTable();
        
        createButton = new JButton( "新增" );
        createButton.setBounds( 697, 54, 64, 22 );
        createButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        createButton.setFont( generalFont );
        add( createButton );
        
        updateButton = new JButton( "修改" );
        updateButton.setBounds( 697, 98, 64, 22 );
        updateButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        updateButton.setFont( generalFont );
        add( updateButton );
        
        deleteButton = new JButton( "刪除" );
        deleteButton.setBounds( 697, 142, 64, 22 );
        deleteButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        deleteButton.setFont( generalFont );
        add( deleteButton );
        
        importButton = new JButton( "匯入" );
        importButton.setBounds( 697, 186, 64, 22 );
        importButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        importButton.setFont( generalFont );
        add( importButton );
        
        exportButton = new JButton( "匯出" );
        exportButton.setBounds( 697, 230, 64, 22 );
        exportButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        exportButton.setFont( generalFont );
        add( exportButton );
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
        dateList.setFont( generalFont );
        
        dateListScrollPane = new JScrollPane( dateList );
        dateListScrollPane.setBounds( 16, 54, 144, 440 );
        
        add( dateListScrollPane );
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
        
        itemTableScrollPane = new JScrollPane( itemTable );
        itemTableScrollPane.setBounds( 176, 29, TABLE_WIDTH, TABLE_HEIGHT + TABLE_HEADER_HEIGHT + BORDER_HEIGHT_FIX );
        
        add( itemTableScrollPane );
    }
}

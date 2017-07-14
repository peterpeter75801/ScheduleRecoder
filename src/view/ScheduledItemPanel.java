package view;

import java.awt.AWTKeyStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import common.Contants;

public class ScheduledItemPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;

    private MainFrame ownerFrame;
    
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
        
        generalFont = new Font( "細明體", Font.PLAIN, 16 );
        
        this.ownerFrame = ownerFrame;
        
        initialItemTable();
        
        createButton = new JButton( "新增(N)" );
        createButton.setBounds( 697, 54, 72, 22 );
        createButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        createButton.setFont( generalFont );
        add( createButton );
        
        updateButton = new JButton( "修改(U)" );
        updateButton.setBounds( 697, 98, 72, 22 );
        updateButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        updateButton.setFont( generalFont );
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
    }
    
    private void initialItemTable() {
        final int DEFAULT_ROW_COUNT = 20;
        final int TABLE_WIDTH = 656;
        final int TABLE_HEIGHT = 440;
        final int TABLE_HEADER_HEIGHT = 22;
        final int TABLE_ROW_HEIGHT = 22;
        final int[] TABLE_COLUMN_WIDTH = { 48, 96, 56, 392, 64 };
        final int BORDER_HEIGHT_FIX = 3;
        final String[] columnNames = { "類型", "日期", "時間", "項目", "預計花費" };
        
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
}

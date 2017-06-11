package view;

import java.awt.AWTKeyStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import commonUtil.ItemUtil;
import domain.Item;
import service.Contants;
import service.ItemService;

public class ItemImportDialog extends JDialog {
    
    private static final long serialVersionUID = 1L;
    
    private ItemService itemService;
    
    private FocusHandler focusHandler;
    private Font generalFont;
    private JPanel dialogPanel;
    private JTextField yearTextField;
    private JLabel yearLabel;
    private JTextField monthTextField;
    private JLabel monthLabel;
    private JTextField dayTextField;
    private JLabel dayLabel;
    private JLabel importContentLabel;
    private JTextArea importContentTextArea;
    private JScrollPane importContentScrollPane;
    private JButton confirmButton;
    private JButton cancelButton;
    
    public ItemImportDialog( MainFrame ownerFrame, ItemService itemService ) {
        super( ownerFrame, "Import Item", true );
        
        this.itemService = itemService;
        
        focusHandler = new FocusHandler();
        
        generalFont = new Font( "細明體", Font.PLAIN, 16 );
        
        dialogPanel = new JPanel();
        dialogPanel.setLayout( null );
        
        yearTextField = new JTextField( 4 );
        yearTextField.setBounds( 16, 10, 40, 22 );
        yearTextField.setFont( generalFont );
        yearTextField.addFocusListener( focusHandler );
        dialogPanel.add( yearTextField );
        
        yearLabel = new JLabel( "年" );
        yearLabel.setBounds( 56, 10, 16, 22 );
        yearLabel.setFont( generalFont );
        dialogPanel.add( yearLabel );
        
        monthTextField = new JTextField( 2 );
        monthTextField.setBounds( 72, 10, 24, 22 );
        monthTextField.setFont( generalFont );
        monthTextField.addFocusListener( focusHandler );
        dialogPanel.add( monthTextField );
        
        monthLabel = new JLabel( "月" );
        monthLabel.setBounds( 96, 10, 16, 22 );
        monthLabel.setFont( generalFont );
        dialogPanel.add( monthLabel );
        
        dayTextField = new JTextField( 2 );
        dayTextField.setBounds( 112, 10, 24, 22 );
        dayTextField.setFont( generalFont );
        dayTextField.addFocusListener( focusHandler );
        dialogPanel.add( dayTextField );
        
        dayLabel = new JLabel( "日" );
        dayLabel.setBounds( 136, 10, 16, 22 );
        dayLabel.setFont( generalFont );
        dialogPanel.add( dayLabel );
        
        importContentLabel = new JLabel( "匯入內容:" );
        importContentLabel.setBounds( 16, 54, 80, 22 );
        importContentLabel.setFont( generalFont );
        dialogPanel.add( importContentLabel );
        
        importContentTextArea = new JTextArea();
        importContentTextArea.setSize( 425, 198 );
        importContentTextArea.setFont( generalFont );
        importContentScrollPane = new JScrollPane( importContentTextArea );
        importContentScrollPane.setBounds( 16, 76, 449, 203 );
        importContentScrollPane.setPreferredSize( new Dimension( 449, 203 ) );
        dialogPanel.add( importContentScrollPane );
        
        Set<AWTKeyStroke> forward = new HashSet<AWTKeyStroke>(
                importContentTextArea.getFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS ) );
        forward.add( KeyStroke.getKeyStroke( "TAB" ) );
        importContentTextArea.setFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forward );
        Set<AWTKeyStroke> backward = new HashSet<AWTKeyStroke>(
                importContentTextArea.getFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS ) );
        backward.add( KeyStroke.getKeyStroke( "shift TAB" ) );
        importContentTextArea.setFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backward );
        
        confirmButton = new JButton( "匯入" );
        confirmButton.setBounds( 168, 296, 48, 22 );
        confirmButton.setFont( generalFont );
        confirmButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        confirmButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                int returnCode = importItem();
                switch( returnCode ) {
                case Contants.SUCCESS:
                case Contants.ERROR_NOT_COMPLETE:
                    setVisible( false );
                    ownerFrame.getItemPanel().reselectDateList();
                    break;
                case Contants.ERROR:
                    break;
                default:
                    break;
                }
            }
        });
        dialogPanel.add( confirmButton );
        
        cancelButton = new JButton( "取消" );
        cancelButton.setBounds( 264, 296, 48, 22 );
        cancelButton.setFont( generalFont );
        cancelButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        cancelButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                setVisible( false );
            }
        });
        dialogPanel.add( cancelButton );
        
        dialogPanel.setPreferredSize( new Dimension( 482, 340 ) );
        add( dialogPanel );
        
        pack();
        setLocationRelativeTo( ownerFrame );
        setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );
        setVisible( false );
    }
    
    private int importItem() {
        String[] itemTxtString = importContentTextArea.getText().split( "\\n" );
        List<Item> itemList = new ArrayList<Item>();
        for( int i = 0; i < itemTxtString.length; i++ ) {
            try {
                if( itemTxtString[ i ] != null && itemTxtString[ i ].length() > 0 ) {
                    Item item = ItemUtil.getItemFromTxtString( itemTxtString[ i ] );
                    itemList.add( item );
                }
            } catch( RuntimeException e ) {
                e.printStackTrace();
                JOptionPane.showMessageDialog( null, 
                    String.format( "匯入資料\"%s\"時發生錯誤", itemTxtString[ i ] ), 
                    "Error", JOptionPane.ERROR_MESSAGE );
                return Contants.ERROR;
            }
        }
        for( Item item : itemList ) {
            try {
                itemService.insert( item );
            } catch ( IOException e ) {
                e.printStackTrace();
                JOptionPane.showMessageDialog( null, "資料未全部匯入", "Error", JOptionPane.ERROR_MESSAGE );
                return Contants.ERROR_NOT_COMPLETE;
            }
        }
        
        return Contants.SUCCESS;
    }
    
    public void openDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( new Date() );
        
        yearTextField.setText( String.format( "%04d", calendar.get( Calendar.YEAR ) ) );
        monthTextField.setText( String.format( "%02d", calendar.get( Calendar.MONTH ) + 1 ) );
        dayTextField.setText( String.format( "%02d", calendar.get( Calendar.DAY_OF_MONTH ) ) );
        
        importContentTextArea.requestFocus();
        
        setVisible( true );
    }
    
    private class FocusHandler extends FocusAdapter {
        @Override
        public void focusGained( FocusEvent event ) {
            JTextField sourceComponent = (JTextField) event.getSource();
            sourceComponent.selectAll();
        }
    }
}

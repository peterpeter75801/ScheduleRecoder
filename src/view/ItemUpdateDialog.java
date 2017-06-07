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
import java.util.HashSet;
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

import domain.Item;
import service.Contants;
import service.ItemService;

public class ItemUpdateDialog extends JDialog {
    
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
    private JLabel startTimeLabel;
    private JTextField startHourTextField;
    private JLabel startHourLabel;
    private JTextField startMinuteTextField;
    private JLabel startMinuteLabel;
    private JLabel endTimeLabel;
    private JTextField endHourTextField;
    private JLabel endHourLabel;
    private JTextField endMinuteTextField;
    private JLabel endMinuteLabel;
    private JLabel itemLabel;
    private JTextField itemTextField;
    private JLabel descriptionLabel;
    private JTextArea descriptionTextArea;
    private JScrollPane descriptionScrollPane;
    private JButton confirmButton;
    private JButton cancelButton;
    
    public ItemUpdateDialog( MainFrame ownerFrame, ItemService itemService ) {
        super( ownerFrame, "Update Item", true );
        
        this.itemService = itemService;
        
        focusHandler = new FocusHandler();
        
        generalFont = new Font( "細明體", Font.PLAIN, 16 );
        
        dialogPanel = new JPanel();
        dialogPanel.setLayout( null );
        
        yearTextField = new JTextField( 4 );
        yearTextField.setBounds( 16, 10, 40, 22 );
        yearTextField.setFont( generalFont );
        yearTextField.setEnabled( false );
        yearTextField.addFocusListener( focusHandler );
        dialogPanel.add( yearTextField );
        
        yearLabel = new JLabel( "年" );
        yearLabel.setBounds( 56, 10, 16, 22 );
        yearLabel.setFont( generalFont );
        dialogPanel.add( yearLabel );
        
        monthTextField = new JTextField( 2 );
        monthTextField.setBounds( 72, 10, 24, 22 );
        monthTextField.setFont( generalFont );
        monthTextField.setEnabled( false );
        monthTextField.addFocusListener( focusHandler );
        dialogPanel.add( monthTextField );
        
        monthLabel = new JLabel( "月" );
        monthLabel.setBounds( 96, 10, 16, 22 );
        monthLabel.setFont( generalFont );
        dialogPanel.add( monthLabel );
        
        dayTextField = new JTextField( 2 );
        dayTextField.setBounds( 112, 10, 24, 22 );
        dayTextField.setFont( generalFont );
        dayTextField.setEnabled( false );
        dayTextField.addFocusListener( focusHandler );
        dialogPanel.add( dayTextField );
        
        dayLabel = new JLabel( "日" );
        dayLabel.setBounds( 136, 10, 16, 22 );
        dayLabel.setFont( generalFont );
        dialogPanel.add( dayLabel );
        
        startTimeLabel = new JLabel( "起始時間:" );
        startTimeLabel.setBounds( 16, 54, 80, 22 );
        startTimeLabel.setFont( generalFont );
        dialogPanel.add( startTimeLabel );
        
        startHourTextField = new JTextField( 2 );
        startHourTextField.setBounds( 96, 54, 24, 22 );
        startHourTextField.setFont( generalFont );
        startHourTextField.setEnabled( false );
        startHourTextField.addFocusListener( focusHandler );
        dialogPanel.add( startHourTextField );
        
        startHourLabel = new JLabel( "時" );
        startHourLabel.setBounds( 120, 54, 16, 22 );
        startHourLabel.setFont( generalFont );
        dialogPanel.add( startHourLabel );
        
        startMinuteTextField = new JTextField( 2 );
        startMinuteTextField.setBounds( 136, 54, 24, 22 );
        startMinuteTextField.setFont( generalFont );
        startMinuteTextField.setEnabled( false );
        startMinuteTextField.addFocusListener( focusHandler );
        dialogPanel.add( startMinuteTextField );
        
        startMinuteLabel = new JLabel( "分" );
        startMinuteLabel.setBounds( 160, 54, 16, 22 );
        startMinuteLabel.setFont( generalFont );
        dialogPanel.add( startMinuteLabel );
        
        endTimeLabel = new JLabel( "結束時間:" );
        endTimeLabel.setBounds( 208, 54, 80, 22 );
        endTimeLabel.setFont( generalFont );
        dialogPanel.add( endTimeLabel );
        
        endHourTextField = new JTextField( 2 );
        endHourTextField.setBounds( 288, 54, 24, 22 );
        endHourTextField.setFont( generalFont );
        endHourTextField.addFocusListener( focusHandler );
        dialogPanel.add( endHourTextField );
        
        endHourLabel = new JLabel( "時" );
        endHourLabel.setBounds( 312, 54, 16, 22 );
        endHourLabel.setFont( generalFont );
        dialogPanel.add( endHourLabel );
        
        endMinuteTextField = new JTextField( 2 );
        endMinuteTextField.setBounds( 328, 54, 24, 22 );
        endMinuteTextField.setFont( generalFont );
        endMinuteTextField.addFocusListener( focusHandler );
        dialogPanel.add( endMinuteTextField );
        
        endMinuteLabel = new JLabel( "分" );
        endMinuteLabel.setBounds( 352, 54, 16, 22 );
        endMinuteLabel.setFont( generalFont );
        dialogPanel.add( endMinuteLabel );
        
        itemLabel = new JLabel( "項目:" );
        itemLabel.setBounds( 16, 98, 48, 22 );
        itemLabel.setFont( generalFont );
        dialogPanel.add( itemLabel );
        
        itemTextField = new JTextField();
        itemTextField.setBounds( 64, 98, 401, 22 );
        itemTextField.setFont( generalFont );
        itemTextField.addFocusListener( focusHandler );
        dialogPanel.add( itemTextField );
        
        descriptionLabel = new JLabel( "說明:" );
        descriptionLabel.setBounds( 16, 142, 48, 22 );
        descriptionLabel.setFont( generalFont );
        dialogPanel.add( descriptionLabel );
        
        descriptionTextArea = new JTextArea();
        descriptionTextArea.setSize( 425, 110 );
        descriptionTextArea.setFont( generalFont );
        descriptionTextArea.setLineWrap( true );
        descriptionTextArea.setWrapStyleWord( true );
        descriptionScrollPane = new JScrollPane( descriptionTextArea );
        descriptionScrollPane.setBounds( 16, 164, 449, 115 );
        descriptionScrollPane.setPreferredSize( new Dimension( 449, 115 ) );
        dialogPanel.add( descriptionScrollPane );
        
        Set<AWTKeyStroke> forward = new HashSet<AWTKeyStroke>(
                descriptionTextArea.getFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS ) );
        forward.add( KeyStroke.getKeyStroke( "TAB" ) );
        descriptionTextArea.setFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forward );
        Set<AWTKeyStroke> backward = new HashSet<AWTKeyStroke>(
                descriptionTextArea.getFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS ) );
        backward.add( KeyStroke.getKeyStroke( "shift TAB" ) );
        descriptionTextArea.setFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backward );
        
        confirmButton = new JButton( "修改" );
        confirmButton.setBounds( 168, 296, 48, 22 );
        confirmButton.setFont( generalFont );
        confirmButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        confirmButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                int returnCode = 0;
                try {
                    returnCode = updateItem();
                } catch ( Exception e ) {
                    e.printStackTrace();
                    returnCode = Contants.ERROR;
                }
                switch( returnCode ) {
                case Contants.SUCCESS:
                    setVisible( false );
                    ownerFrame.getItemPanel().reselectDateList();
                    break;
                case Contants.ERROR:
                    JOptionPane.showMessageDialog( null, "更新失敗", "Error", JOptionPane.ERROR_MESSAGE );
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
    
    public void openDialog( int year, int month, int day, int startHour, int startMinute ) {
        Item item = null;
        
        try {
            item = itemService.findByTime( year, month, day, startHour, startMinute );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        if( item == null ) {
            JOptionPane.showMessageDialog( this, "載入資料失敗", "Error", JOptionPane.ERROR_MESSAGE );
            return;
        }
        
        yearTextField.setText( String.format( "%04d", item.getYear() ) );
        monthTextField.setText( String.format( "%02d", item.getMonth() ) );
        dayTextField.setText( String.format( "%02d", item.getDay() ) );
        startHourTextField.setText( String.format( "%02d", item.getStartHour() ) );
        startMinuteTextField.setText( String.format( "%02d", item.getStartMinute() ) );
        endHourTextField.setText( String.format( "%02d", item.getEndHour() ) );
        endMinuteTextField.setText( String.format( "%02d", item.getEndMinute() ) );
        itemTextField.setText( item.getName() );
        descriptionTextArea.setText( item.getDescription() );
        
        itemTextField.requestFocus();
        
        setVisible( true );
    }
    
    private int updateItem() throws Exception {
        Item item = new Item();
        item.setYear( Integer.parseInt( yearTextField.getText() ) );
        item.setMonth( Integer.parseInt( monthTextField.getText() ) );
        item.setDay( Integer.parseInt( dayTextField.getText() ) );
        item.setStartHour( Integer.parseInt( startHourTextField.getText() ) );
        item.setStartMinute( Integer.parseInt( startMinuteTextField.getText() ) );
        item.setEndHour( Integer.parseInt( endHourTextField.getText() ) );
        item.setEndMinute( Integer.parseInt( endMinuteTextField.getText() ) );
        item.setName( itemTextField.getText() );
        item.setDescription( descriptionTextArea.getText() );
        
        return itemService.update( item );
    }
    
    private class FocusHandler extends FocusAdapter {
        @Override
        public void focusGained( FocusEvent event ) {
            JTextField sourceComponent = (JTextField) event.getSource();
            sourceComponent.selectAll();
        }
    }
}

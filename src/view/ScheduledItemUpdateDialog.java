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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import common.Contants;
import commonUtil.CsvFormatParser;
import domain.ScheduledItem;
import service.ScheduledItemService;

public class ScheduledItemUpdateDialog extends JDialog {
    
private static final long serialVersionUID = 1L;
    
    private ScheduledItemService scheduledItemService;
    
    private int scheduledItemId = 0;
    
    private MainFrame ownerFrame;
    
    private FocusHandler focusHandler;
    private MnemonicKeyHandler mnemonicKeyHandler;
    private RadioButtonKeyHandler radioButtonKeyHandler;
    private Font generalFont;
    private JPanel dialogPanel;
    private JLabel typeLabel;
    private JRadioButton onTimeRadioButton;
    private JRadioButton dueTimeRadioButton;
    private JRadioButton proposedRadioButton;
    private JRadioButton noRestrictionRadioButton;
    private ButtonGroup exportTypeButtonGroup;
    private JLabel timeLabel;
    private JTextField yearTextField;
    private JLabel yearLabel;
    private JTextField monthTextField;
    private JLabel monthLabel;
    private JTextField dayTextField;
    private JLabel dayLabel;
    private JTextField hourTextField;
    private JLabel hourLabel;
    private JTextField minuteTextField;
    private JLabel minuteLabel;
    private JLabel expectedTimeLabel;
    private JTextField expectedTimeTextField;
    private JLabel expectedTimeUnitLabel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel descriptionLabel;
    private JTextArea descriptionTextArea;
    private JScrollPane descriptionScrollPane;
    private JButton confirmButton;
    private JButton cancelButton;
    
    public ScheduledItemUpdateDialog( MainFrame ownerFrame, ScheduledItemService scheduledItemService ) {
        super( ownerFrame, "Update Schedule", true );
        
        this.scheduledItemService = scheduledItemService;
        
        this.ownerFrame = ownerFrame;
        
        focusHandler = new FocusHandler();
        mnemonicKeyHandler = new MnemonicKeyHandler();
        radioButtonKeyHandler = new RadioButtonKeyHandler();
        
        generalFont = new Font( "細明體", Font.PLAIN, 16 );
        
        dialogPanel = new JPanel();
        dialogPanel.setLayout( null );
        
        typeLabel = new JLabel( "種類: " );
        typeLabel.setBounds( 16, 10, 48, 22 );
        typeLabel.setFont( generalFont );
        dialogPanel.add( typeLabel );
        
        onTimeRadioButton = new JRadioButton( "準時(O)", true );
        onTimeRadioButton.setBounds( 64, 10, 80, 22 );
        onTimeRadioButton.setFont( generalFont );
        onTimeRadioButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        onTimeRadioButton.addKeyListener( mnemonicKeyHandler );
        onTimeRadioButton.addKeyListener( radioButtonKeyHandler );
        dialogPanel.add( onTimeRadioButton );
        
        dueTimeRadioButton = new JRadioButton( "期限(D)", false );
        dueTimeRadioButton.setBounds( 152, 10, 80, 22 );
        dueTimeRadioButton.setFont( generalFont );
        dueTimeRadioButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        dueTimeRadioButton.addKeyListener( mnemonicKeyHandler );
        dueTimeRadioButton.addKeyListener( radioButtonKeyHandler );
        dialogPanel.add( dueTimeRadioButton );
        
        proposedRadioButton = new JRadioButton( "建議(P)", false );
        proposedRadioButton.setBounds( 240, 10, 80, 22 );
        proposedRadioButton.setFont( generalFont );
        proposedRadioButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        proposedRadioButton.addKeyListener( mnemonicKeyHandler );
        proposedRadioButton.addKeyListener( radioButtonKeyHandler );
        dialogPanel.add( proposedRadioButton );
        
        noRestrictionRadioButton = new JRadioButton( "不限時(N)", false );
        noRestrictionRadioButton.setBounds( 328, 10, 96, 22 );
        noRestrictionRadioButton.setFont( generalFont );
        noRestrictionRadioButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        noRestrictionRadioButton.addKeyListener( mnemonicKeyHandler );
        noRestrictionRadioButton.addKeyListener( radioButtonKeyHandler );
        dialogPanel.add( noRestrictionRadioButton );
        
        exportTypeButtonGroup = new ButtonGroup();
        exportTypeButtonGroup.add( onTimeRadioButton );
        exportTypeButtonGroup.add( dueTimeRadioButton );
        exportTypeButtonGroup.add( proposedRadioButton );
        exportTypeButtonGroup.add( noRestrictionRadioButton );
        
        timeLabel = new JLabel( "時間: " );
        timeLabel.setBounds( 16, 42, 48, 22 );
        timeLabel.setFont( generalFont );
        dialogPanel.add( timeLabel );
        
        yearTextField = new JTextField( 4 );
        yearTextField.setBounds( 64, 42, 40, 22 );
        yearTextField.setFont( generalFont );
        yearTextField.addFocusListener( focusHandler );
        yearTextField.addKeyListener( mnemonicKeyHandler );
        dialogPanel.add( yearTextField );
        
        yearLabel = new JLabel( "年" );
        yearLabel.setBounds( 104, 42, 16, 22 );
        yearLabel.setFont( generalFont );
        dialogPanel.add( yearLabel );
        
        monthTextField = new JTextField( 2 );
        monthTextField.setBounds( 120, 42, 24, 22 );
        monthTextField.setFont( generalFont );
        monthTextField.addFocusListener( focusHandler );
        monthTextField.addKeyListener( mnemonicKeyHandler );
        dialogPanel.add( monthTextField );
        
        monthLabel = new JLabel( "月" );
        monthLabel.setBounds( 144, 42, 16, 22 );
        monthLabel.setFont( generalFont );
        dialogPanel.add( monthLabel );
        
        dayTextField = new JTextField( 2 );
        dayTextField.setBounds( 160, 42, 24, 22 );
        dayTextField.setFont( generalFont );
        dayTextField.addFocusListener( focusHandler );
        dayTextField.addKeyListener( mnemonicKeyHandler );
        dialogPanel.add( dayTextField );
        
        dayLabel = new JLabel( "日" );
        dayLabel.setBounds( 184, 42, 16, 22 );
        dayLabel.setFont( generalFont );
        dialogPanel.add( dayLabel );
        
        hourTextField = new JTextField( 2 );
        hourTextField.setBounds( 216, 42, 24, 22 );
        hourTextField.setFont( generalFont );
        hourTextField.addFocusListener( focusHandler );
        hourTextField.addKeyListener( mnemonicKeyHandler );
        dialogPanel.add( hourTextField );
        
        hourLabel = new JLabel( "時" );
        hourLabel.setBounds( 240, 42, 16, 22 );
        hourLabel.setFont( generalFont );
        dialogPanel.add( hourLabel );
        
        minuteTextField = new JTextField( 2 );
        minuteTextField.setBounds( 256, 42, 24, 22 );
        minuteTextField.setFont( generalFont );
        minuteTextField.addFocusListener( focusHandler );
        minuteTextField.addKeyListener( mnemonicKeyHandler );
        dialogPanel.add( minuteTextField );
        
        minuteLabel = new JLabel( "分" );
        minuteLabel.setBounds( 280, 42, 16, 22 );
        minuteLabel.setFont( generalFont );
        dialogPanel.add( minuteLabel );
        
        expectedTimeLabel = new JLabel( "預計花費時間: " );
        expectedTimeLabel.setBounds( 16, 74, 112, 22 );
        expectedTimeLabel.setFont( generalFont );
        dialogPanel.add( expectedTimeLabel );
        
        expectedTimeTextField = new JTextField( 4 );
        expectedTimeTextField.setBounds( 128, 74, 40, 22 );
        expectedTimeTextField.setFont( generalFont );
        expectedTimeTextField.addFocusListener( focusHandler );
        expectedTimeTextField.addKeyListener( mnemonicKeyHandler );
        dialogPanel.add( expectedTimeTextField );
        
        expectedTimeUnitLabel = new JLabel( "分" );
        expectedTimeUnitLabel.setBounds( 168, 74, 16, 22 );
        expectedTimeUnitLabel.setFont( generalFont );
        dialogPanel.add( expectedTimeUnitLabel );
        
        nameLabel = new JLabel( "項目: " );
        nameLabel.setBounds( 16, 106, 48, 22 );
        nameLabel.setFont( generalFont );
        dialogPanel.add( nameLabel );
        
        nameTextField = new JTextField();
        nameTextField.setBounds( 64, 106, 401, 22 );
        nameTextField.setFont( generalFont );
        nameTextField.addFocusListener( focusHandler );
        nameTextField.addKeyListener( mnemonicKeyHandler );
        dialogPanel.add( nameTextField );
        
        descriptionLabel = new JLabel( "說明: " );
        descriptionLabel.setBounds( 16, 138, 48, 22 );
        descriptionLabel.setFont( generalFont );
        dialogPanel.add( descriptionLabel );
        
        descriptionTextArea = new JTextArea();
        descriptionTextArea.setSize( 425, 110 );
        descriptionTextArea.setFont( generalFont );
        descriptionTextArea.setLineWrap( true );
        descriptionTextArea.setWrapStyleWord( true );
        descriptionScrollPane = new JScrollPane( descriptionTextArea );
        descriptionScrollPane.setBounds( 16, 160, 449, 115 );
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
        
        confirmButton = new JButton( "更新" );
        confirmButton.setBounds( 168, 296, 48, 22 );
        confirmButton.setFont( generalFont );
        confirmButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        confirmButton.addKeyListener( mnemonicKeyHandler );
        confirmButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                updateScheduledItem();
            }
        });
        dialogPanel.add( confirmButton );
        
        cancelButton = new JButton( "取消" );
        cancelButton.setBounds( 264, 296, 48, 22 );
        cancelButton.setFont( generalFont );
        cancelButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        cancelButton.addKeyListener( mnemonicKeyHandler );
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
    
    public void openDialog( int id ) {
        ScheduledItem scheduledItem = null;
        
        try {
            scheduledItem = scheduledItemService.findById( id );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        if( scheduledItem == null ) {
            JOptionPane.showMessageDialog( this, "載入資料失敗", "Error", JOptionPane.ERROR_MESSAGE );
            return;
        }
        
        scheduledItemId = id;
        
        onTimeRadioButton.setSelected( false );
        dueTimeRadioButton.setSelected( false );
        proposedRadioButton.setSelected( false );
        noRestrictionRadioButton.setSelected( false );
        switch( scheduledItem.getType() ) {
        case 'O':
            onTimeRadioButton.setSelected( true );
            onTimeRadioButton.requestFocus();
            break;
        case 'D':
            dueTimeRadioButton.setSelected( true );
            dueTimeRadioButton.requestFocus();
            break;
        case 'P':
            proposedRadioButton.setSelected( true );
            proposedRadioButton.requestFocus();
            break;
        case 'N':
            noRestrictionRadioButton.setSelected( true );
            noRestrictionRadioButton.requestFocus();
            break;
        }
        
        yearTextField.setText( String.format( "%04d", scheduledItem.getYear() ) );
        monthTextField.setText( String.format( "%02d", scheduledItem.getMonth() ) );
        dayTextField.setText( String.format( "%02d", scheduledItem.getDay() ) );
        hourTextField.setText( String.format( "%02d", scheduledItem.getHour() ) );
        minuteTextField.setText( String.format( "%02d", scheduledItem.getMinute() ) );
        if( scheduledItem.getExpectedTime() == -1 ) {
            expectedTimeTextField.setText( "" );
        } else {
            expectedTimeTextField.setText( String.format( "%d", scheduledItem.getExpectedTime() ) );
        }
        nameTextField.setText( scheduledItem.getName() );
        descriptionTextArea.setText( CsvFormatParser.restoreCharacterFromHtmlFormat( scheduledItem.getDescription() ) );
        
        setVisible( true );
    }
    
    private void updateScheduledItem() {
        int returnCode = 0;
        try {
            ScheduledItem scheduledItem = new ScheduledItem();
            scheduledItem.setId( scheduledItemId );
            scheduledItem.setYear( Integer.parseInt( yearTextField.getText() ) );
            scheduledItem.setMonth( Integer.parseInt( monthTextField.getText() ) );
            scheduledItem.setDay( Integer.parseInt( dayTextField.getText() ) );
            scheduledItem.setHour( Integer.parseInt( hourTextField.getText() ) );
            scheduledItem.setMinute( Integer.parseInt( minuteTextField.getText() ) );
            if( expectedTimeTextField.getText() == null || expectedTimeTextField.getText().length() <= 0 ) {
                scheduledItem.setExpectedTime( -1 );
            } else {
                scheduledItem.setExpectedTime( Integer.parseInt( expectedTimeTextField.getText() ) );
            }
            if( onTimeRadioButton.isSelected() ) {
                scheduledItem.setType( 'O' );
            } else if( dueTimeRadioButton.isSelected() ) {
                scheduledItem.setType( 'D' );
            } else if( proposedRadioButton.isSelected() ) {
                scheduledItem.setType( 'P' );
            } else if( noRestrictionRadioButton.isSelected() ) {
                scheduledItem.setType( 'N' );
            } else {
                scheduledItem.setType( '\0' );
            }
            scheduledItem.setName( nameTextField.getText() );
            if( CsvFormatParser.checkSpecialCharacter( descriptionTextArea.getText() ) ) {
                scheduledItem.setDescription( CsvFormatParser.specialCharacterToHtmlFormat( descriptionTextArea.getText() ) );
            } else {
                scheduledItem.setDescription( descriptionTextArea.getText() );
            }
            returnCode = scheduledItemService.update( scheduledItem );
        } catch ( Exception e ) {
            e.printStackTrace();
            returnCode = Contants.ERROR;
        }
        switch( returnCode ) {
        case Contants.SUCCESS:
            setVisible( false );
            ownerFrame.getScheduledItemPanel().loadScheduledItems();
            break;
        case Contants.ERROR:
            JOptionPane.showMessageDialog( null, "更新失敗", "Error", JOptionPane.ERROR_MESSAGE );
            break;
        default:
            break;
        }
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
                if( event.getSource() != cancelButton ) {
                    updateScheduledItem();
                } else {
                    setVisible( false );
                }
                break;
            case KeyEvent.VK_ESCAPE:
                setVisible( false );
                break;
            }
        }

        @Override
        public void keyReleased( KeyEvent event ) {}

        @Override
        public void keyTyped( KeyEvent event ) {}
    }
    
    private class RadioButtonKeyHandler implements KeyListener {

        @Override
        public void keyPressed( KeyEvent event ) {
            if( event.getKeyCode() == KeyEvent.VK_O || event.getKeyCode() == KeyEvent.VK_D ||
                    event.getKeyCode() == KeyEvent.VK_P || event.getKeyCode() == KeyEvent.VK_N ) {
                onTimeRadioButton.setSelected( false );
                dueTimeRadioButton.setSelected( false );
                proposedRadioButton.setSelected( false );
                noRestrictionRadioButton.setSelected( false );
            }
            switch( event.getKeyCode() ) {
            case KeyEvent.VK_O:
                onTimeRadioButton.requestFocus();
                onTimeRadioButton.setSelected( true );
                break;
            case KeyEvent.VK_D:
                dueTimeRadioButton.requestFocus();
                dueTimeRadioButton.setSelected( true );
                break;
            case KeyEvent.VK_P:
                proposedRadioButton.requestFocus();
                proposedRadioButton.setSelected( true );
                break;
            case KeyEvent.VK_N:
                noRestrictionRadioButton.requestFocus();
                noRestrictionRadioButton.setSelected( true );
                break;
            }
        }

        @Override
        public void keyReleased( KeyEvent event ) {}

        @Override
        public void keyTyped( KeyEvent event ) {}
    }
}

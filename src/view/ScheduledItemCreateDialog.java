package view;

import java.awt.AWTKeyStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import service.ScheduledItemService;

public class ScheduledItemCreateDialog extends JDialog {
    
    private static final long serialVersionUID = 1L;
    
    private ScheduledItemService scheduledItemService;
    
    private MainFrame ownerFrame;
    
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
    
    private JLabel testLabel;
    
    public ScheduledItemCreateDialog( MainFrame ownerFrame, ScheduledItemService scheduledItemService ) {
        super( ownerFrame, "Create Schedule", true );
        
        this.scheduledItemService = scheduledItemService;
        
        this.ownerFrame = ownerFrame;
        
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
        dialogPanel.add( onTimeRadioButton );
        
        dueTimeRadioButton = new JRadioButton( "期限(D)", false );
        dueTimeRadioButton.setBounds( 152, 10, 80, 22 );
        dueTimeRadioButton.setFont( generalFont );
        dueTimeRadioButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        dialogPanel.add( dueTimeRadioButton );
        
        proposedRadioButton = new JRadioButton( "建議(P)", false );
        proposedRadioButton.setBounds( 240, 10, 80, 22 );
        proposedRadioButton.setFont( generalFont );
        proposedRadioButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        dialogPanel.add( proposedRadioButton );
        
        noRestrictionRadioButton = new JRadioButton( "不定時(N)", false );
        noRestrictionRadioButton.setBounds( 328, 10, 96, 22 );
        noRestrictionRadioButton.setFont( generalFont );
        noRestrictionRadioButton.setMargin( new Insets( 0, 0, 0, 0 ) );
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
        dialogPanel.add( yearTextField );
        
        yearLabel = new JLabel( "年" );
        yearLabel.setBounds( 104, 42, 16, 22 );
        yearLabel.setFont( generalFont );
        dialogPanel.add( yearLabel );
        
        monthTextField = new JTextField( 2 );
        monthTextField.setBounds( 120, 42, 24, 22 );
        monthTextField.setFont( generalFont );
        dialogPanel.add( monthTextField );
        
        monthLabel = new JLabel( "月" );
        monthLabel.setBounds( 144, 42, 16, 22 );
        monthLabel.setFont( generalFont );
        dialogPanel.add( monthLabel );
        
        dayTextField = new JTextField( 2 );
        dayTextField.setBounds( 160, 42, 24, 22 );
        dayTextField.setFont( generalFont );
        dialogPanel.add( dayTextField );
        
        dayLabel = new JLabel( "日" );
        dayLabel.setBounds( 184, 42, 16, 22 );
        dayLabel.setFont( generalFont );
        dialogPanel.add( dayLabel );
        
        hourTextField = new JTextField( 2 );
        hourTextField.setBounds( 216, 42, 24, 22 );
        hourTextField.setFont( generalFont );
        dialogPanel.add( hourTextField );
        
        hourLabel = new JLabel( "時" );
        hourLabel.setBounds( 240, 42, 16, 22 );
        hourLabel.setFont( generalFont );
        dialogPanel.add( hourLabel );
        
        minuteTextField = new JTextField( 2 );
        minuteTextField.setBounds( 256, 42, 24, 22 );
        minuteTextField.setFont( generalFont );
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
        nameTextField.setBounds( 64, 106, 401, 22);
        nameTextField.setFont( generalFont );
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
        
        confirmButton = new JButton( "新增" );
        confirmButton.setBounds( 168, 296, 48, 22 );
        confirmButton.setFont( generalFont );
        confirmButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        dialogPanel.add( confirmButton );
        
        cancelButton = new JButton( "取消" );
        cancelButton.setBounds( 264, 296, 48, 22 );
        cancelButton.setFont( generalFont );
        cancelButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        dialogPanel.add( cancelButton );
        
        dialogPanel.setPreferredSize( new Dimension( 482, 340 ) );
        add( dialogPanel );
        
        pack();
        setLocationRelativeTo( ownerFrame );
        setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );
        setVisible( false );
    }
    
    public void openDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( new Date() );
        
        yearTextField.setText( String.format( "%04d", calendar.get( Calendar.YEAR ) ) );
        monthTextField.setText( String.format( "%02d", calendar.get( Calendar.MONTH ) + 1 ) );
        dayTextField.setText( String.format( "%02d", calendar.get( Calendar.DAY_OF_MONTH ) ) );
        hourTextField.setText( String.format( "%02d", calendar.get( Calendar.HOUR_OF_DAY ) ) );
        minuteTextField.setText( String.format( "%02d", calendar.get( Calendar.MINUTE ) ) );
        
        expectedTimeTextField.setText( "" );
        nameTextField.setText( "" );
        descriptionTextArea.setText( "" );
        
        onTimeRadioButton.setSelected( true );
        dueTimeRadioButton.setSelected( false );
        proposedRadioButton.setSelected( false );
        noRestrictionRadioButton.setSelected( false );
        onTimeRadioButton.requestFocus();
        
        setVisible( true );
    }
}

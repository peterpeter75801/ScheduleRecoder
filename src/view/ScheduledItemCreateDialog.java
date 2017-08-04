package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
    private JRadioButton noRestrictionJRadioButton;
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
        
        noRestrictionJRadioButton = new JRadioButton( "不定時(N)", false );
        noRestrictionJRadioButton.setBounds( 328, 10, 96, 22 );
        noRestrictionJRadioButton.setFont( generalFont );
        noRestrictionJRadioButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        dialogPanel.add( noRestrictionJRadioButton );
        
        exportTypeButtonGroup = new ButtonGroup();
        exportTypeButtonGroup.add( onTimeRadioButton );
        exportTypeButtonGroup.add( dueTimeRadioButton );
        exportTypeButtonGroup.add( proposedRadioButton );
        exportTypeButtonGroup.add( noRestrictionJRadioButton );
        
        testLabel = new JLabel( "123456789012345678901234567890123456789012345678901234567890" );
        testLabel.setBounds( 0, 32, 481, 22 );
        testLabel.setFont( generalFont );
        dialogPanel.add( testLabel );
        
        timeLabel = new JLabel( "時間: " );
        timeLabel.setBounds( 16, 54, 48, 22 );
        timeLabel.setFont( generalFont );
        dialogPanel.add( timeLabel );
        
        yearTextField = new JTextField( 4 );
        yearTextField.setBounds( 64, 54, 40, 22 );
        yearTextField.setFont( generalFont );
        dialogPanel.add( yearTextField );
        
        yearLabel = new JLabel( "年" );
        yearLabel.setBounds( 104, 54, 16, 22 );
        yearLabel.setFont( generalFont );
        dialogPanel.add( yearLabel );
        
        monthTextField = new JTextField( 2 );
        monthTextField.setBounds( 120, 54, 24, 22 );
        monthTextField.setFont( generalFont );
        dialogPanel.add( monthTextField );
        
        monthLabel = new JLabel( "月" );
        monthLabel.setBounds( 144, 54, 16, 22 );
        monthLabel.setFont( generalFont );
        dialogPanel.add( monthLabel );
        
        dayTextField = new JTextField( 2 );
        dayTextField.setBounds( 160, 54, 24, 22 );
        dayTextField.setFont( generalFont );
        dialogPanel.add( dayTextField );
        
        dayLabel = new JLabel( "日" );
        dayLabel.setBounds( 184, 54, 16, 22 );
        dayLabel.setFont( generalFont );
        dialogPanel.add( dayLabel );
        
        hourTextField = new JTextField( 2 );
        hourTextField.setBounds( 216, 54, 24, 22 );
        hourTextField.setFont( generalFont );
        dialogPanel.add( hourTextField );
        
        hourLabel = new JLabel( "時" );
        hourLabel.setBounds( 240, 54, 16, 22 );
        hourLabel.setFont( generalFont );
        dialogPanel.add( hourLabel );
        
        minuteTextField = new JTextField( 2 );
        minuteTextField.setBounds( 256, 54, 24, 22 );
        minuteTextField.setFont( generalFont );
        dialogPanel.add( minuteTextField );
        
        minuteLabel = new JLabel( "分" );
        minuteLabel.setBounds( 280, 54, 16, 22 );
        minuteLabel.setFont( generalFont );
        dialogPanel.add( minuteLabel );
        
        expectedTimeLabel = new JLabel( "預計花費時間: " );
        expectedTimeLabel.setBounds( 16, 98, 112, 22 );
        expectedTimeLabel.setFont( generalFont );
        dialogPanel.add( expectedTimeLabel );
        
        expectedTimeTextField = new JTextField( 4 );
        expectedTimeTextField.setBounds( 128, 98, 40, 22 );
        expectedTimeTextField.setFont( generalFont );
        dialogPanel.add( expectedTimeTextField );
        
      //nameLabel
      //nameTextField
      //descriptionLabel
      //descriptionTextArea
      //descriptionScrollPane
      //confirmButton
      //cancelButton
        
        dialogPanel.setPreferredSize( new Dimension( 482, 340 ) );
        add( dialogPanel );
        
        pack();
        setLocationRelativeTo( ownerFrame );
        setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );
        setVisible( false );
    }
    
    public void openDialog() {
        setVisible( true );
    }
}

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
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

import common.Contants;
import commonUtil.CsvFormatParser;
import commonUtil.DateUtil;
import commonUtil.StringUtil;
import domain.ScheduledItem;
import service.ScheduledItemService;

public class ScheduledItemExecuteDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    
    private ScheduledItemService scheduledItemService;
    
    private ScheduledItem originalScheduledItem;
    
    private MainFrame ownerFrame;
    
    private UndoManager undoManager;
    private FocusHandler focusHandler;
    private MnemonicKeyHandler mnemonicKeyHandler;
    private UndoEditHandler undoEditHandler;
    private UndoHotKeyHandler undoHotKeyHandler;
    private DateTimeTextFieldHotKeyHandler dateTimeTextFieldHotKeyHandler;
    private Font generalFont;
    private JPanel dialogPanel;
    private JLabel dateLabel;
    private JTextField yearTextField;
    private JLabel yearLabel;
    private JTextField monthTextField;
    private JLabel monthLabel;
    private JTextField dayTextField;
    private JLabel dayLabel;
    private JLabel timeLabel;
    private JTextField hourTextField;
    private JLabel hourLabel;
    private JTextField minuteTextField;
    private JLabel minuteLabel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel descriptionLabel;
    private JTextArea descriptionTextArea;
    private JScrollPane descriptionScrollPane;
    private JCheckBox deleteItemCheckBox;
    private JButton confirmButton;
    private JButton cancelButton;
    
    public ScheduledItemExecuteDialog( MainFrame ownerFrame, ScheduledItemService scheduledItemService ) {
        super( ownerFrame, "Execute Schedule", true );
        
        this.scheduledItemService = scheduledItemService;
        
        this.ownerFrame = ownerFrame;
        
        undoManager = new UndoManager();
        focusHandler = new FocusHandler();
        mnemonicKeyHandler = new MnemonicKeyHandler();
        undoEditHandler = new UndoEditHandler();
        undoHotKeyHandler = new UndoHotKeyHandler();
        dateTimeTextFieldHotKeyHandler = new DateTimeTextFieldHotKeyHandler();
        
        generalFont = new Font( "細明體", Font.PLAIN, 16 );
        
        dialogPanel = new JPanel();
        dialogPanel.setLayout( null );
        
        dateLabel = new JLabel( "執行日期: " );
        dateLabel.setBounds( 16, 10, 80, 22 );
        dateLabel.setFont( generalFont );
        dialogPanel.add( dateLabel );
        
        yearTextField = new JTextField( 4 );
        yearTextField.setBounds( 96, 10, 40, 22 );
        yearTextField.setFont( generalFont );
        yearTextField.addFocusListener( focusHandler );
        yearTextField.addKeyListener( mnemonicKeyHandler );
        yearTextField.addKeyListener( dateTimeTextFieldHotKeyHandler );
        yearTextField.addKeyListener( undoHotKeyHandler );
        yearTextField.getDocument().addUndoableEditListener( undoEditHandler );
        dialogPanel.add( yearTextField );
        
        yearLabel = new JLabel( "年" );
        yearLabel.setBounds( 136, 10, 16, 22 );
        yearLabel.setFont( generalFont );
        dialogPanel.add( yearLabel );
        
        monthTextField = new JTextField( 2 );
        monthTextField.setBounds( 152, 10, 24, 22 );
        monthTextField.setFont( generalFont );
        monthTextField.addFocusListener( focusHandler );
        monthTextField.addKeyListener( mnemonicKeyHandler );
        monthTextField.addKeyListener( dateTimeTextFieldHotKeyHandler );
        monthTextField.addKeyListener( undoHotKeyHandler );
        monthTextField.getDocument().addUndoableEditListener( undoEditHandler );
        dialogPanel.add( monthTextField );
        
        monthLabel = new JLabel( "月" );
        monthLabel.setBounds( 176, 10, 16, 22 );
        monthLabel.setFont( generalFont );
        dialogPanel.add( monthLabel );
        
        dayTextField = new JTextField( 2 );
        dayTextField.setBounds( 192, 10, 24, 22 );
        dayTextField.setFont( generalFont );
        dayTextField.addFocusListener( focusHandler );
        dayTextField.addKeyListener( mnemonicKeyHandler );
        dayTextField.addKeyListener( dateTimeTextFieldHotKeyHandler );
        dayTextField.addKeyListener( undoHotKeyHandler );
        dayTextField.getDocument().addUndoableEditListener( undoEditHandler );
        dialogPanel.add( dayTextField );
        
        dayLabel = new JLabel( "日" );
        dayLabel.setBounds( 216, 10, 16, 22 );
        dayLabel.setFont( generalFont );
        dialogPanel.add( dayLabel );
        
        timeLabel = new JLabel( "執行時間: " );
        timeLabel.setBounds( 264, 10, 80, 22 );
        timeLabel.setFont( generalFont );
        dialogPanel.add( timeLabel );
        
        hourTextField = new JTextField( 2 );
        hourTextField.setBounds( 344, 10, 24, 22 );
        hourTextField.setFont( generalFont );
        hourTextField.addFocusListener( focusHandler );
        hourTextField.addKeyListener( mnemonicKeyHandler );
        hourTextField.addKeyListener( dateTimeTextFieldHotKeyHandler );
        hourTextField.addKeyListener( undoHotKeyHandler );
        hourTextField.getDocument().addUndoableEditListener( undoEditHandler );
        dialogPanel.add( hourTextField );
        
        hourLabel = new JLabel( "時" );
        hourLabel.setBounds( 368, 10, 16, 22 );
        hourLabel.setFont( generalFont );
        dialogPanel.add( hourLabel );
        
        minuteTextField = new JTextField( 2 );
        minuteTextField.setBounds( 384, 10, 24, 22 );
        minuteTextField.setFont( generalFont );
        minuteTextField.addFocusListener( focusHandler );
        minuteTextField.addKeyListener( mnemonicKeyHandler );
        minuteTextField.addKeyListener( dateTimeTextFieldHotKeyHandler );
        minuteTextField.addKeyListener( undoHotKeyHandler );
        minuteTextField.getDocument().addUndoableEditListener( undoEditHandler );
        dialogPanel.add( minuteTextField );
        
        minuteLabel = new JLabel( "分" );
        minuteLabel.setBounds( 409, 10, 16, 22 );
        minuteLabel.setFont( generalFont );
        dialogPanel.add( minuteLabel );
        
        nameLabel = new JLabel( "項目: " );
        nameLabel.setBounds( 16, 42, 48, 22 );
        nameLabel.setFont( generalFont );
        dialogPanel.add( nameLabel );
        
        nameTextField = new JTextField();
        nameTextField.setBounds( 64, 42, 401, 22 );
        nameTextField.setFont( generalFont );
        nameTextField.addFocusListener( focusHandler );
        nameTextField.addKeyListener( mnemonicKeyHandler );
        nameTextField.addKeyListener( undoHotKeyHandler );
        nameTextField.getDocument().addUndoableEditListener( undoEditHandler );
        dialogPanel.add( nameTextField );
        
        descriptionLabel = new JLabel( "說明: " );
        descriptionLabel.setBounds( 16, 74, 48, 22 );
        descriptionLabel.setFont( generalFont );
        dialogPanel.add( descriptionLabel );
        
        descriptionTextArea = new JTextArea();
        descriptionTextArea.setSize( 425, 155 );
        descriptionTextArea.setFont( generalFont );
        descriptionTextArea.setLineWrap( true );
        descriptionTextArea.setWrapStyleWord( true );
        descriptionTextArea.addKeyListener( undoHotKeyHandler );
        descriptionTextArea.getDocument().addUndoableEditListener( undoEditHandler );
        descriptionScrollPane = new JScrollPane( descriptionTextArea );
        descriptionScrollPane.setBounds( 16, 96, 449, 160 );
        descriptionScrollPane.setPreferredSize( new Dimension( 449, 160 ) );
        dialogPanel.add( descriptionScrollPane );
        
        Set<AWTKeyStroke> forward = new HashSet<AWTKeyStroke>(
                descriptionTextArea.getFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS ) );
        forward.add( KeyStroke.getKeyStroke( "TAB" ) );
        descriptionTextArea.setFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forward );
        Set<AWTKeyStroke> backward = new HashSet<AWTKeyStroke>(
                descriptionTextArea.getFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS ) );
        backward.add( KeyStroke.getKeyStroke( "shift TAB" ) );
        descriptionTextArea.setFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backward );
        
        deleteItemCheckBox = new JCheckBox( "已完成，刪除此排程項目" );
        deleteItemCheckBox.setBounds( 16, 256, 200, 22 );
        deleteItemCheckBox.setFont( generalFont );
        deleteItemCheckBox.setMargin( new Insets( 0, 0, 0, 0 ) );
        dialogPanel.add( deleteItemCheckBox );
        
        confirmButton = new JButton( "完成" );
        confirmButton.setBounds( 168, 296, 48, 22 );
        confirmButton.setFont( generalFont );
        confirmButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        confirmButton.addKeyListener( mnemonicKeyHandler );
        confirmButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                executeScheduledItem();
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
        
        originalScheduledItem = scheduledItem;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( new Date() );
        
        yearTextField.setText( String.format( "%04d", calendar.get( Calendar.YEAR ) ) );
        monthTextField.setText( String.format( "%02d", calendar.get( Calendar.MONTH ) + 1 ) );
        dayTextField.setText( String.format( "%02d", calendar.get( Calendar.DAY_OF_MONTH ) ) );
        hourTextField.setText( String.format( "%02d", calendar.get( Calendar.HOUR_OF_DAY ) ) );
        minuteTextField.setText( String.format( "%02d", calendar.get( Calendar.MINUTE ) ) );
        
        nameTextField.setText( scheduledItem.getName() );
        descriptionTextArea.setText( CsvFormatParser.restoreCharacterFromHtmlFormat( scheduledItem.getDescription() ) );
        deleteItemCheckBox.setSelected( false );
        
        nameTextField.requestFocus();
        
        setVisible( true );
    }
    
    private void executeScheduledItem() {
        int returnCode = 0;
        try {
            ScheduledItem completedScheduledItem = new ScheduledItem();
            completedScheduledItem.setId( originalScheduledItem.getId() );
            completedScheduledItem.setYear( Integer.parseInt( yearTextField.getText() ) );
            completedScheduledItem.setMonth( Integer.parseInt( monthTextField.getText() ) );
            completedScheduledItem.setDay( Integer.parseInt( dayTextField.getText() ) );
            completedScheduledItem.setHour( Integer.parseInt( hourTextField.getText() ) );
            completedScheduledItem.setMinute( Integer.parseInt( minuteTextField.getText() ) );
            completedScheduledItem.setExpectedTime( originalScheduledItem.getExpectedTime() );
            completedScheduledItem.setType( originalScheduledItem.getType() );
            completedScheduledItem.setName( nameTextField.getText() );
            if( CsvFormatParser.checkSpecialCharacter( descriptionTextArea.getText() ) ) {
                completedScheduledItem.setDescription( CsvFormatParser.specialCharacterToHtmlFormat( descriptionTextArea.getText() ) );
            } else {
                completedScheduledItem.setDescription( descriptionTextArea.getText() );
            }
            returnCode = scheduledItemService.execute( 
                originalScheduledItem, completedScheduledItem, deleteItemCheckBox.isSelected() );
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
            JOptionPane.showMessageDialog( null, "執行失敗", "Error", JOptionPane.ERROR_MESSAGE );
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
                    executeScheduledItem();
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
    
    private class UndoEditHandler implements UndoableEditListener {
        
        @Override
        public void undoableEditHappened( UndoableEditEvent event ) {
            undoManager.addEdit( event.getEdit() );
        }
    }
    
    private class UndoHotKeyHandler implements KeyListener {
        
        @Override
        public void keyPressed( KeyEvent event ) {
            if( event.isControlDown() && event.getKeyCode() == KeyEvent.VK_Z && undoManager.canUndo() ) {
                undoManager.undo();
            } else if( event.isControlDown() && event.getKeyCode() == KeyEvent.VK_Y && undoManager.canRedo() ) {
                undoManager.redo();
            }
        }

        @Override
        public void keyReleased( KeyEvent event ) {}

        @Override
        public void keyTyped( KeyEvent event ) {}
    }
    
    private class DateTimeTextFieldHotKeyHandler implements KeyListener {
        
        private final int YEAR_MAX_VALUE = Integer.MAX_VALUE;
        private final int YEAR_MIN_VALUE = 1900;
        private final int MONTH_MAX_VALUE = 12;
        private final int MONTH_MIN_VALUE = 1;
        private final int DAY_MIN_VALUE = 1;
        private final int HOUR_MAX_VALUE = 23;
        private final int HOUR_MIN_VALUE = 0;
        private final int MINUTE_MAX_VALUE = 59;
        private final int MINUTE_MIN_VALUE = 0;
        
        @Override
        public void keyPressed( KeyEvent event ) {
            Calendar calendar = Calendar.getInstance();
            
            switch( event.getKeyCode() ) {
            case KeyEvent.VK_UP:
                if( event.getSource() == yearTextField ) {
                    yearTextField.setText( StringUtil.isNumber( yearTextField.getText() )
                        ? StringUtil.decreaseDateTimeTextFieldValue( yearTextField.getText(), YEAR_MIN_VALUE, 4 )
                        : String.format( "%04d", calendar.get( Calendar.YEAR ) )
                    );
                    yearTextField.selectAll();
                } else if( event.getSource() == monthTextField ) {
                    monthTextField.setText( StringUtil.isNumber( monthTextField.getText() )
                        ? StringUtil.decreaseDateTimeTextFieldValue( monthTextField.getText(), MONTH_MIN_VALUE, 2 )
                        : String.format( "%02d", calendar.get( Calendar.MONTH ) + 1 )
                    );
                } else if( event.getSource() == dayTextField ) {
                    dayTextField.setText( StringUtil.isNumber( dayTextField.getText() )
                        ? StringUtil.decreaseDateTimeTextFieldValue( dayTextField.getText(), DAY_MIN_VALUE, 2 )
                        : String.format( "%02d", calendar.get( Calendar.DAY_OF_MONTH ) )
                    );
                } else if( event.getSource() == hourTextField ) {
                    hourTextField.setText( StringUtil.isNumber( hourTextField.getText() )
                        ? StringUtil.decreaseDateTimeTextFieldValue( hourTextField.getText(), HOUR_MIN_VALUE, 2 )
                        : String.format( "%02d", calendar.get( Calendar.HOUR_OF_DAY ) )
                    );
                } else if( event.getSource() == minuteTextField ) {
                    minuteTextField.setText( StringUtil.isNumber( minuteTextField.getText() )
                        ? StringUtil.decreaseDateTimeTextFieldValue( minuteTextField.getText(), MINUTE_MIN_VALUE, 2 )
                        : String.format( "%02d", calendar.get( Calendar.MINUTE ) )
                    );
                }
                break;
            case KeyEvent.VK_DOWN:
                if( event.getSource() == yearTextField ) {
                    yearTextField.setText( StringUtil.isNumber( yearTextField.getText() )
                        ? StringUtil.increaseDateTimeTextFieldValue( yearTextField.getText(), YEAR_MAX_VALUE, 4 )
                        : String.format( "%04d", calendar.get( Calendar.YEAR ) )
                    );
                    yearTextField.selectAll();
                } else if( event.getSource() == monthTextField ) {
                    monthTextField.setText( StringUtil.isNumber( monthTextField.getText() )
                        ? StringUtil.increaseDateTimeTextFieldValue( monthTextField.getText(), MONTH_MAX_VALUE, 2 )
                        : String.format( "%02d", calendar.get( Calendar.MONTH ) + 1 )
                    );
                } else if( event.getSource() == dayTextField ) {
                    dayTextField.setText( StringUtil.isNumber( dayTextField.getText() )
                        ? StringUtil.increaseDateTimeTextFieldValue( dayTextField.getText(), 
                            DateUtil.getMaxDayValue( yearTextField.getText(), monthTextField.getText() ), 2 )
                        : String.format( "%02d", calendar.get( Calendar.DAY_OF_MONTH ) )
                    );
                } else if( event.getSource() == hourTextField ) {
                    hourTextField.setText( StringUtil.isNumber( hourTextField.getText() )
                        ? StringUtil.increaseDateTimeTextFieldValue( hourTextField.getText(), HOUR_MAX_VALUE, 2 )
                        : String.format( "%02d", calendar.get( Calendar.HOUR_OF_DAY ) )
                    );
                } else if( event.getSource() == minuteTextField ) {
                    minuteTextField.setText( StringUtil.isNumber( minuteTextField.getText() )
                        ? StringUtil.increaseDateTimeTextFieldValue( minuteTextField.getText(), MINUTE_MAX_VALUE, 2 )
                        : String.format( "%02d", calendar.get( Calendar.MINUTE ) )
                    );
                }
                break;
            }
        }

        @Override
        public void keyReleased( KeyEvent event ) { }

        @Override
        public void keyTyped( KeyEvent event ) { }
        
    }
}

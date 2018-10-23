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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;

import common.Contants;
import commonUtil.CsvFormatParser;
import commonUtil.DateUtil;
import commonUtil.StringUtil;
import domain.ScheduledItem;
import service.ScheduledItemService;

public class ScheduledItemUpdateDialog extends JDialog {
    
private static final long serialVersionUID = 1L;
    
    private ScheduledItemService scheduledItemService;
    
    private int scheduledItemId = 0;
    private boolean popupMenuClosedFlag;
    
    private MainFrame ownerFrame;
    
    private CopyAndPastePopUpMenu copyAndPastePopUpMenu;
    private UndoManager undoManager;
    private FocusHandler focusHandler;
    private MnemonicKeyHandler mnemonicKeyHandler;
    private CopyPasteMenuKeyHandler copyPasteMenuKeyHandler;
    private CopyPasteMouseMenuHandler copyPasteMouseMenuHandler;
    private RadioButtonKeyHandler radioButtonKeyHandler;
    private UndoEditHandler undoEditHandler;
    private UndoHotKeyHandler undoHotKeyHandler;
    private DateTimeTextFieldHotKeyHandler dateTimeTextFieldHotKeyHandler;
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
        
        copyAndPastePopUpMenu = new CopyAndPastePopUpMenu();
        copyAndPastePopUpMenu.addPopupMenuListener( new PopupMenuClosingHandler() );
        
        undoManager = new UndoManager();
        focusHandler = new FocusHandler();
        mnemonicKeyHandler = new MnemonicKeyHandler();
        copyPasteMenuKeyHandler = new CopyPasteMenuKeyHandler( copyAndPastePopUpMenu );
        copyPasteMouseMenuHandler = new CopyPasteMouseMenuHandler( copyAndPastePopUpMenu );
        radioButtonKeyHandler = new RadioButtonKeyHandler();
        undoEditHandler = new UndoEditHandler();
        undoHotKeyHandler = new UndoHotKeyHandler();
        dateTimeTextFieldHotKeyHandler = new DateTimeTextFieldHotKeyHandler();
        
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
        yearTextField.addKeyListener( copyPasteMenuKeyHandler );
        yearTextField.addMouseListener( copyPasteMouseMenuHandler );
        yearTextField.addKeyListener( dateTimeTextFieldHotKeyHandler );
        yearTextField.addKeyListener( undoHotKeyHandler );
        yearTextField.getDocument().addUndoableEditListener( undoEditHandler );
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
        monthTextField.addKeyListener( copyPasteMenuKeyHandler );
        monthTextField.addMouseListener( copyPasteMouseMenuHandler );
        monthTextField.addKeyListener( dateTimeTextFieldHotKeyHandler );
        monthTextField.addKeyListener( undoHotKeyHandler );
        monthTextField.getDocument().addUndoableEditListener( undoEditHandler );
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
        dayTextField.addKeyListener( copyPasteMenuKeyHandler );
        dayTextField.addMouseListener( copyPasteMouseMenuHandler );
        dayTextField.addKeyListener( dateTimeTextFieldHotKeyHandler );
        dayTextField.addKeyListener( undoHotKeyHandler );
        dayTextField.getDocument().addUndoableEditListener( undoEditHandler );
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
        hourTextField.addKeyListener( copyPasteMenuKeyHandler );
        hourTextField.addMouseListener( copyPasteMouseMenuHandler );
        hourTextField.addKeyListener( dateTimeTextFieldHotKeyHandler );
        hourTextField.addKeyListener( undoHotKeyHandler );
        hourTextField.getDocument().addUndoableEditListener( undoEditHandler );
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
        minuteTextField.addKeyListener( copyPasteMenuKeyHandler );
        minuteTextField.addMouseListener( copyPasteMouseMenuHandler );
        minuteTextField.addKeyListener( dateTimeTextFieldHotKeyHandler );
        minuteTextField.addKeyListener( undoHotKeyHandler );
        minuteTextField.getDocument().addUndoableEditListener( undoEditHandler );
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
        expectedTimeTextField.addKeyListener( copyPasteMenuKeyHandler );
        expectedTimeTextField.addMouseListener( copyPasteMouseMenuHandler );
        expectedTimeTextField.addKeyListener( undoHotKeyHandler );
        expectedTimeTextField.getDocument().addUndoableEditListener( undoEditHandler );
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
        nameTextField.addKeyListener( copyPasteMenuKeyHandler );
        nameTextField.addMouseListener( copyPasteMouseMenuHandler );
        nameTextField.addKeyListener( undoHotKeyHandler );
        nameTextField.getDocument().addUndoableEditListener( undoEditHandler );
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
        descriptionTextArea.addKeyListener( copyPasteMenuKeyHandler );
        descriptionTextArea.addMouseListener( copyPasteMouseMenuHandler );
        descriptionTextArea.addKeyListener( undoHotKeyHandler );
        descriptionTextArea.getDocument().addUndoableEditListener( undoEditHandler );
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
        popupMenuClosedFlag = false;
        
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
            if( popupMenuClosedFlag ) {
                popupMenuClosedFlag = false;
            } else {
                JTextField sourceComponent = (JTextField) event.getSource();
                sourceComponent.selectAll();
            }
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
    
    private class CopyPasteMenuKeyHandler implements KeyListener {
        
        private CopyAndPastePopUpMenu copyAndPastePopUpMenu;
        
        public CopyPasteMenuKeyHandler( CopyAndPastePopUpMenu copyAndPastePopUpMenu ) {
            this.copyAndPastePopUpMenu = copyAndPastePopUpMenu;
        }
        
        @Override
        public void keyPressed( KeyEvent event ) {
            if( event.getKeyCode() == KeyEvent.VK_CONTEXT_MENU ) {
                JTextComponent eventComponent = (JTextComponent)event.getComponent();
                int showPosX = (eventComponent.getCaret().getMagicCaretPosition() != null) ? 
                        (int)eventComponent.getCaret().getMagicCaretPosition().getX() : 0;
                int showPosY = (eventComponent.getCaret().getMagicCaretPosition() != null) ? 
                        (int)eventComponent.getCaret().getMagicCaretPosition().getY() : 0;
                copyAndPastePopUpMenu.show( eventComponent, showPosX, showPosY );
            }
        }

        @Override
        public void keyReleased( KeyEvent event ) {}

        @Override
        public void keyTyped( KeyEvent event ) {}
    }
    
    private class CopyPasteMouseMenuHandler extends MouseAdapter {
        
        private CopyAndPastePopUpMenu copyAndPastePopUpMenu;
        
        public CopyPasteMouseMenuHandler( CopyAndPastePopUpMenu copyAndPastePopUpMenu ) {
            this.copyAndPastePopUpMenu = copyAndPastePopUpMenu;
        }
        
        public void mousePressed( MouseEvent event ) {
            if( event.isPopupTrigger() ) {
                copyAndPastePopUpMenu.show( event.getComponent(), event.getX(), event.getY() );
            }
        }
        
        public void mouseReleased( MouseEvent event ) {
            if( event.isPopupTrigger() ) {
                copyAndPastePopUpMenu.show( event.getComponent(), event.getX(), event.getY() );
            }
        }
    }
    
    private class PopupMenuClosingHandler implements PopupMenuListener {
        
        @Override
        public void popupMenuCanceled( PopupMenuEvent event ) {
            popupMenuClosedFlag = true;
            ((JPopupMenu)event.getSource()).getInvoker().requestFocus();
        }

        @Override
        public void popupMenuWillBecomeInvisible( PopupMenuEvent event ) {
            popupMenuClosedFlag = true;
            ((JPopupMenu)event.getSource()).getInvoker().requestFocus();
        }

        @Override
        public void popupMenuWillBecomeVisible( PopupMenuEvent event ) {}
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

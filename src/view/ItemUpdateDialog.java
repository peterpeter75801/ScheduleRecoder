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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
import domain.Item;
import service.ItemService;

public class ItemUpdateDialog extends JDialog {
    
    private static final long serialVersionUID = 1L;
    
    private ItemService itemService;
    
    private Item originalItem;
    private int itemSeq = 0;
    private boolean popupMenuClosedFlag;
    
    private MainFrame ownerFrame;
    
    private CopyAndPastePopUpMenu copyAndPastePopUpMenu;
    private UndoManager undoManager;
    private FocusHandler focusHandler;
    private MnemonicKeyHandler mnemonicKeyHandler;
    private CopyPasteMenuKeyHandler copyPasteMenuKeyHandler;
    private CopyPasteMouseMenuHandler copyPasteMouseMenuHandler;
    private UndoEditHandler undoEditHandler;
    private UndoHotKeyHandler undoHotKeyHandler;
    private DateTimeTextFieldHotKeyHandler dateTimeTextFieldHotKeyHandler;
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
        
        this.ownerFrame = ownerFrame;
        
        copyAndPastePopUpMenu = new CopyAndPastePopUpMenu();
        copyAndPastePopUpMenu.addPopupMenuListener( new PopupMenuClosingHandler() );
        
        undoManager = new UndoManager();
        focusHandler = new FocusHandler();
        mnemonicKeyHandler = new MnemonicKeyHandler();
        copyPasteMenuKeyHandler = new CopyPasteMenuKeyHandler( copyAndPastePopUpMenu );
        copyPasteMouseMenuHandler = new CopyPasteMouseMenuHandler( copyAndPastePopUpMenu );
        undoEditHandler = new UndoEditHandler();
        undoHotKeyHandler = new UndoHotKeyHandler();
        dateTimeTextFieldHotKeyHandler = new DateTimeTextFieldHotKeyHandler();
        
        generalFont = new Font( "細明體", Font.PLAIN, 16 );
        
        dialogPanel = new JPanel();
        dialogPanel.setLayout( null );
        
        yearTextField = new JTextField( 4 );
        yearTextField.setBounds( 16, 10, 40, 22 );
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
        yearLabel.setBounds( 56, 10, 16, 22 );
        yearLabel.setFont( generalFont );
        dialogPanel.add( yearLabel );
        
        monthTextField = new JTextField( 2 );
        monthTextField.setBounds( 72, 10, 24, 22 );
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
        monthLabel.setBounds( 96, 10, 16, 22 );
        monthLabel.setFont( generalFont );
        dialogPanel.add( monthLabel );
        
        dayTextField = new JTextField( 2 );
        dayTextField.setBounds( 112, 10, 24, 22 );
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
        startHourTextField.addFocusListener( focusHandler );
        startHourTextField.addKeyListener( mnemonicKeyHandler );
        startHourTextField.addKeyListener( copyPasteMenuKeyHandler );
        startHourTextField.addMouseListener( copyPasteMouseMenuHandler );
        startHourTextField.addKeyListener( dateTimeTextFieldHotKeyHandler );
        startHourTextField.addKeyListener( undoHotKeyHandler );
        startHourTextField.getDocument().addUndoableEditListener( undoEditHandler );
        dialogPanel.add( startHourTextField );
        
        startHourLabel = new JLabel( "時" );
        startHourLabel.setBounds( 120, 54, 16, 22 );
        startHourLabel.setFont( generalFont );
        dialogPanel.add( startHourLabel );
        
        startMinuteTextField = new JTextField( 2 );
        startMinuteTextField.setBounds( 136, 54, 24, 22 );
        startMinuteTextField.setFont( generalFont );
        startMinuteTextField.addFocusListener( focusHandler );
        startMinuteTextField.addKeyListener( mnemonicKeyHandler );
        startMinuteTextField.addKeyListener( copyPasteMenuKeyHandler );
        startMinuteTextField.addMouseListener( copyPasteMouseMenuHandler );
        startMinuteTextField.addKeyListener( dateTimeTextFieldHotKeyHandler );
        startMinuteTextField.addKeyListener( undoHotKeyHandler );
        startMinuteTextField.getDocument().addUndoableEditListener( undoEditHandler );
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
        endHourTextField.addKeyListener( mnemonicKeyHandler );
        endHourTextField.addKeyListener( copyPasteMenuKeyHandler );
        endHourTextField.addMouseListener( copyPasteMouseMenuHandler );
        endHourTextField.addKeyListener( dateTimeTextFieldHotKeyHandler );
        endHourTextField.addKeyListener( undoHotKeyHandler );
        endHourTextField.getDocument().addUndoableEditListener( undoEditHandler );
        dialogPanel.add( endHourTextField );
        
        endHourLabel = new JLabel( "時" );
        endHourLabel.setBounds( 312, 54, 16, 22 );
        endHourLabel.setFont( generalFont );
        dialogPanel.add( endHourLabel );
        
        endMinuteTextField = new JTextField( 2 );
        endMinuteTextField.setBounds( 328, 54, 24, 22 );
        endMinuteTextField.setFont( generalFont );
        endMinuteTextField.addFocusListener( focusHandler );
        endMinuteTextField.addKeyListener( mnemonicKeyHandler );
        endMinuteTextField.addKeyListener( copyPasteMenuKeyHandler );
        endMinuteTextField.addMouseListener( copyPasteMouseMenuHandler );
        endMinuteTextField.addKeyListener( dateTimeTextFieldHotKeyHandler );
        endMinuteTextField.addKeyListener( undoHotKeyHandler );
        endMinuteTextField.getDocument().addUndoableEditListener( undoEditHandler );
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
        itemTextField.addKeyListener( mnemonicKeyHandler );
        itemTextField.addKeyListener( copyPasteMenuKeyHandler );
        itemTextField.addMouseListener( copyPasteMouseMenuHandler );
        itemTextField.addKeyListener( undoHotKeyHandler );
        itemTextField.getDocument().addUndoableEditListener( undoEditHandler );
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
        descriptionTextArea.addKeyListener( copyPasteMenuKeyHandler );
        descriptionTextArea.addMouseListener( copyPasteMouseMenuHandler );
        descriptionTextArea.addKeyListener( undoHotKeyHandler );
        descriptionTextArea.getDocument().addUndoableEditListener( undoEditHandler );
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
        confirmButton.addKeyListener( mnemonicKeyHandler );
        confirmButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                updateItem();
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
    
    public void openDialog( int year, int month, int day, int startHour, int startMinute, int seq ) {
        Item item = null;
        
        try {
            item = itemService.findOne( year, month, day, startHour, startMinute, seq );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        if( item == null ) {
            JOptionPane.showMessageDialog( this, "載入資料失敗", "Error", JOptionPane.ERROR_MESSAGE );
            return;
        }
        
        itemSeq = seq;
        
        yearTextField.setText( String.format( "%04d", item.getYear() ) );
        monthTextField.setText( String.format( "%02d", item.getMonth() ) );
        dayTextField.setText( String.format( "%02d", item.getDay() ) );
        startHourTextField.setText( String.format( "%02d", item.getStartHour() ) );
        startMinuteTextField.setText( String.format( "%02d", item.getStartMinute() ) );
        endHourTextField.setText( String.format( "%02d", item.getEndHour() ) );
        endMinuteTextField.setText( String.format( "%02d", item.getEndMinute() ) );
        itemTextField.setText( item.getName() );
        descriptionTextArea.setText( CsvFormatParser.restoreCharacterFromHtmlFormat( item.getDescription() ) );
        
        originalItem = item;
        popupMenuClosedFlag = false;
        
        itemTextField.requestFocus();
        
        setVisible( true );
    }
    
    private void updateItem() {
        int returnCode = 0;
        try {
            Item item = new Item();
            item.setYear( Integer.parseInt( yearTextField.getText() ) );
            item.setMonth( Integer.parseInt( monthTextField.getText() ) );
            item.setDay( Integer.parseInt( dayTextField.getText() ) );
            item.setStartHour( Integer.parseInt( startHourTextField.getText() ) );
            item.setStartMinute( Integer.parseInt( startMinuteTextField.getText() ) );
            item.setSeq( itemSeq );
            item.setEndHour( Integer.parseInt( endHourTextField.getText() ) );
            item.setEndMinute( Integer.parseInt( endMinuteTextField.getText() ) );
            item.setName( itemTextField.getText() );
            if( CsvFormatParser.checkSpecialCharacter( descriptionTextArea.getText() ) ) {
                item.setDescription( CsvFormatParser.specialCharacterToHtmlFormat( descriptionTextArea.getText() ) );
            } else {
                item.setDescription( descriptionTextArea.getText() );
            }
            returnCode = itemService.update( originalItem, item );
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
                    updateItem();
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
                } else if( event.getSource() == startHourTextField ) {
                    startHourTextField.setText( StringUtil.isNumber( startHourTextField.getText() )
                        ? StringUtil.decreaseDateTimeTextFieldValue( startHourTextField.getText(), HOUR_MIN_VALUE, 2 )
                        : String.format( "%02d", calendar.get( Calendar.HOUR_OF_DAY ) )
                    );
                } else if( event.getSource() == startMinuteTextField ) {
                    startMinuteTextField.setText( StringUtil.isNumber( startMinuteTextField.getText() )
                        ? StringUtil.decreaseDateTimeTextFieldValue( startMinuteTextField.getText(), MINUTE_MIN_VALUE, 2 )
                        : String.format( "%02d", calendar.get( Calendar.MINUTE ) )
                    );
                } else if( event.getSource() == endHourTextField ) {
                    endHourTextField.setText( StringUtil.isNumber( endHourTextField.getText() )
                        ? StringUtil.decreaseDateTimeTextFieldValue( endHourTextField.getText(), HOUR_MIN_VALUE, 2 )
                        : String.format( "%02d", calendar.get( Calendar.HOUR_OF_DAY ) )
                    );
                } else if( event.getSource() == endMinuteTextField ) {
                    endMinuteTextField.setText( StringUtil.isNumber( endMinuteTextField.getText() )
                        ? StringUtil.decreaseDateTimeTextFieldValue( endMinuteTextField.getText(), MINUTE_MIN_VALUE, 2 )
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
                } else if( event.getSource() == startHourTextField ) {
                    startHourTextField.setText( StringUtil.isNumber( startHourTextField.getText() )
                        ? StringUtil.increaseDateTimeTextFieldValue( startHourTextField.getText(), HOUR_MAX_VALUE, 2 )
                        : String.format( "%02d", calendar.get( Calendar.HOUR_OF_DAY ) )
                    );
                } else if( event.getSource() == startMinuteTextField ) {
                    startMinuteTextField.setText( StringUtil.isNumber( startMinuteTextField.getText() )
                        ? StringUtil.increaseDateTimeTextFieldValue( startMinuteTextField.getText(), MINUTE_MAX_VALUE, 2 )
                        : String.format( "%02d", calendar.get( Calendar.MINUTE ) )
                    );
                } else if( event.getSource() == endHourTextField ) {
                    endHourTextField.setText( StringUtil.isNumber( endHourTextField.getText() )
                        ? StringUtil.increaseDateTimeTextFieldValue( endHourTextField.getText(), HOUR_MAX_VALUE, 2 )
                        : String.format( "%02d", calendar.get( Calendar.HOUR_OF_DAY ) )
                    );
                } else if( event.getSource() == endMinuteTextField ) {
                    endMinuteTextField.setText( StringUtil.isNumber( endMinuteTextField.getText() )
                        ? StringUtil.increaseDateTimeTextFieldValue( endMinuteTextField.getText(), MINUTE_MAX_VALUE, 2 )
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

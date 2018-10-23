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
import commonUtil.DateUtil;
import commonUtil.ItemUtil;
import commonUtil.StringUtil;
import domain.Item;
import service.ItemService;

public class ItemImportDialog extends JDialog {
    
    private static final long serialVersionUID = 1L;
    
    private ItemService itemService;
    
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
    private JLabel importContentLabel;
    private JTextArea importContentTextArea;
    private JScrollPane importContentScrollPane;
    private JButton confirmButton;
    private JButton cancelButton;
    
    public ItemImportDialog( MainFrame ownerFrame, ItemService itemService ) {
        super( ownerFrame, "Import Item", true );
        
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
        
        importContentLabel = new JLabel( "匯入內容:" );
        importContentLabel.setBounds( 16, 54, 80, 22 );
        importContentLabel.setFont( generalFont );
        dialogPanel.add( importContentLabel );
        
        importContentTextArea = new JTextArea();
        importContentTextArea.setSize( 425, 198 );
        importContentTextArea.setFont( generalFont );
        importContentTextArea.addKeyListener( copyPasteMenuKeyHandler );
        importContentTextArea.addMouseListener( copyPasteMouseMenuHandler );
        importContentTextArea.addKeyListener( undoHotKeyHandler );
        importContentTextArea.getDocument().addUndoableEditListener( undoEditHandler );
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
        confirmButton.addKeyListener( mnemonicKeyHandler );
        confirmButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                importItem();
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
    
    private void importItem() {
        int status;
        String[] itemTxtString = importContentTextArea.getText().split( "\\n" );
        List<Item> itemList = new ArrayList<Item>();
        int year = Integer.parseInt( yearTextField.getText() );
        int month = Integer.parseInt( monthTextField.getText() );
        int day = Integer.parseInt( dayTextField.getText() );
        for( int i = 0; i < itemTxtString.length; i++ ) {
            try {
                if( itemTxtString[ i ] != null && itemTxtString[ i ].length() > 0 ) {
                    Item item = ItemUtil.getItemFromTxtString( itemTxtString[ i ] );
                    item.setYear( year );
                    item.setMonth( month );
                    item.setDay( day );
                    itemList.add( item );
                }
            } catch( RuntimeException e ) {
                e.printStackTrace();
                JOptionPane.showMessageDialog( null, 
                    String.format( "匯入資料\"%s\"時發生錯誤", itemTxtString[ i ] ), 
                    "Error", JOptionPane.ERROR_MESSAGE );
                status = Contants.ERROR;
                return;
            }
        }
        
        try {
            status = itemService.insertItemsInDateGroup( year, month, day, itemList );
        } catch ( Exception e ) {
            status = Contants.ERROR;
            e.printStackTrace();
        }
        
        if( status != Contants.SUCCESS ) {
            JOptionPane.showMessageDialog( null, "發生錯誤，資料可能未全部匯入", "Error", JOptionPane.ERROR_MESSAGE );
        }
        
        setVisible( false );
        ownerFrame.getItemPanel().reselectDateList();
    }
    
    public void openDialog( String selectedDateString ) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( new Date() );
        
        yearTextField.setText( selectedDateString.substring( 0, 4 ) );
        monthTextField.setText( selectedDateString.substring( 5, 7 ) );
        dayTextField.setText( selectedDateString.substring( 8, 10 ) );
        
        popupMenuClosedFlag = false;
        
        importContentTextArea.setText( "" );
        importContentTextArea.requestFocus();
        
        setVisible( true );
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
                    importItem();
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

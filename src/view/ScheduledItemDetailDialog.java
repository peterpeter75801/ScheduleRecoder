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
import javax.swing.text.JTextComponent;

import commonUtil.CsvFormatParser;
import domain.ScheduledItem;
import service.ScheduledItemService;

public class ScheduledItemDetailDialog extends JDialog {
    
    private static final long serialVersionUID = 1L;
    
    private ScheduledItemService scheduledItemService;
    
    private boolean popupMenuClosedFlag;
    
    private CopyAndPastePopUpMenu copyAndPastePopUpMenu;
    private FocusHandler focusHandler;
    private MnemonicKeyHandler mnemonicKeyHandler;
    private CopyPasteMenuKeyHandler copyPasteMenuKeyHandler;
    private CopyPasteMouseMenuHandler copyPasteMouseMenuHandler;
    private Font generalFont;
    private JPanel dialogPanel;
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
    private JLabel typeLabel;
    private JTextField typeTextField;
    private JLabel expectedTimeLabel;
    private JTextField expectedTimeTextField;
    private JLabel expectedTimeUnitLabel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel descriptionLabel;
    private JTextArea descriptionTextArea;
    private JScrollPane descriptionScrollPane;
    private JButton okButton;
    
    public ScheduledItemDetailDialog( MainFrame ownerFrame, ScheduledItemService scheduledItemService ) {
        super( ownerFrame, "Detail", true );
        
        this.scheduledItemService = scheduledItemService;
        
        copyAndPastePopUpMenu = new CopyAndPastePopUpMenu();
        copyAndPastePopUpMenu.addPopupMenuListener( new PopupMenuClosingHandler() );
        
        focusHandler = new FocusHandler();
        mnemonicKeyHandler = new MnemonicKeyHandler();
        copyPasteMenuKeyHandler = new CopyPasteMenuKeyHandler( copyAndPastePopUpMenu );
        copyPasteMouseMenuHandler = new CopyPasteMouseMenuHandler( copyAndPastePopUpMenu );
        
        generalFont = new Font( "細明體", Font.PLAIN, 16 );
        
        dialogPanel = new JPanel();
        dialogPanel.setLayout( null );
        
        timeLabel = new JLabel( "時間: " );
        timeLabel.setBounds( 16, 10, 48, 22 );
        timeLabel.setFont( generalFont );
        dialogPanel.add( timeLabel );
        
        yearTextField = new JTextField( 4 );
        yearTextField.setBounds( 64, 10, 40, 22 );
        yearTextField.setFont( generalFont );
        yearTextField.setEditable( false );
        yearTextField.addFocusListener( focusHandler );
        yearTextField.addKeyListener( mnemonicKeyHandler );
        yearTextField.addKeyListener( copyPasteMenuKeyHandler );
        yearTextField.addMouseListener( copyPasteMouseMenuHandler );
        dialogPanel.add( yearTextField );
        
        yearLabel = new JLabel( "年" );
        yearLabel.setBounds( 104, 10, 16, 22 );
        yearLabel.setFont( generalFont );
        dialogPanel.add( yearLabel );
        
        monthTextField = new JTextField( 2 );
        monthTextField.setBounds( 120, 10, 24, 22 );
        monthTextField.setFont( generalFont );
        monthTextField.setEditable( false );
        monthTextField.addFocusListener( focusHandler );
        monthTextField.addKeyListener( mnemonicKeyHandler );
        monthTextField.addKeyListener( copyPasteMenuKeyHandler );
        monthTextField.addMouseListener( copyPasteMouseMenuHandler );
        dialogPanel.add( monthTextField );
        
        monthLabel = new JLabel( "月" );
        monthLabel.setBounds( 144, 10, 16, 22 );
        monthLabel.setFont( generalFont );
        dialogPanel.add( monthLabel );
        
        dayTextField = new JTextField( 2 );
        dayTextField.setBounds( 160, 10, 24, 22 );
        dayTextField.setFont( generalFont );
        dayTextField.setEditable( false );
        dayTextField.addFocusListener( focusHandler );
        dayTextField.addKeyListener( mnemonicKeyHandler );
        dayTextField.addKeyListener( copyPasteMenuKeyHandler );
        dayTextField.addMouseListener( copyPasteMouseMenuHandler );
        dialogPanel.add( dayTextField );
        
        dayLabel = new JLabel( "日" );
        dayLabel.setBounds( 184, 10, 16, 22 );
        dayLabel.setFont( generalFont );
        dialogPanel.add( dayLabel );
        
        hourTextField = new JTextField( 2 );
        hourTextField.setBounds( 216, 10, 24, 22 );
        hourTextField.setFont( generalFont );
        hourTextField.setEditable( false );
        hourTextField.addFocusListener( focusHandler );
        hourTextField.addKeyListener( mnemonicKeyHandler );
        hourTextField.addKeyListener( copyPasteMenuKeyHandler );
        hourTextField.addMouseListener( copyPasteMouseMenuHandler );
        dialogPanel.add( hourTextField );
        
        hourLabel = new JLabel( "時" );
        hourLabel.setBounds( 240, 10, 16, 22 );
        hourLabel.setFont( generalFont );
        dialogPanel.add( hourLabel );
        
        minuteTextField = new JTextField( 2 );
        minuteTextField.setBounds( 256, 10, 24, 22 );
        minuteTextField.setFont( generalFont );
        minuteTextField.setEditable( false );
        minuteTextField.addFocusListener( focusHandler );
        minuteTextField.addKeyListener( mnemonicKeyHandler );
        minuteTextField.addKeyListener( copyPasteMenuKeyHandler );
        minuteTextField.addMouseListener( copyPasteMouseMenuHandler );
        dialogPanel.add( minuteTextField );
        
        minuteLabel = new JLabel( "分" );
        minuteLabel.setBounds( 280, 10, 16, 22 );
        minuteLabel.setFont( generalFont );
        dialogPanel.add( minuteLabel );
        
        typeLabel = new JLabel( "種類: " );
        typeLabel.setBounds( 328, 10, 48, 22 );
        typeLabel.setFont( generalFont );
        dialogPanel.add( typeLabel );
        
        typeTextField = new JTextField( 6 );
        typeTextField.setBounds( 376, 10, 56, 22 );
        typeTextField.setFont( generalFont );
        typeTextField.setEditable( false );
        typeTextField.addFocusListener( focusHandler );
        typeTextField.addKeyListener( mnemonicKeyHandler );
        typeTextField.addKeyListener( copyPasteMenuKeyHandler );
        typeTextField.addMouseListener( copyPasteMouseMenuHandler );
        dialogPanel.add( typeTextField );
        
        expectedTimeLabel = new JLabel( "預計花費時間: " );
        expectedTimeLabel.setBounds( 16, 42, 112, 22 );
        expectedTimeLabel.setFont( generalFont );
        dialogPanel.add( expectedTimeLabel );
        
        expectedTimeTextField = new JTextField( 4 );
        expectedTimeTextField.setBounds( 128, 42, 40, 22 );
        expectedTimeTextField.setFont( generalFont );
        expectedTimeTextField.setEditable( false );
        expectedTimeTextField.addFocusListener( focusHandler );
        expectedTimeTextField.addKeyListener( mnemonicKeyHandler );
        expectedTimeTextField.addKeyListener( copyPasteMenuKeyHandler );
        expectedTimeTextField.addMouseListener( copyPasteMouseMenuHandler );
        dialogPanel.add( expectedTimeTextField );
        
        expectedTimeUnitLabel = new JLabel( "分" );
        expectedTimeUnitLabel.setBounds( 168, 42, 16, 22 );
        expectedTimeUnitLabel.setFont( generalFont );
        dialogPanel.add( expectedTimeUnitLabel );
        
        nameLabel = new JLabel( "項目: " );
        nameLabel.setBounds( 16, 74, 48, 22 );
        nameLabel.setFont( generalFont );
        dialogPanel.add( nameLabel );
        
        nameTextField = new JTextField();
        nameTextField.setBounds( 64, 74, 401, 22 );
        nameTextField.setFont( generalFont );
        nameTextField.setEditable( false );
        nameTextField.addFocusListener( focusHandler );
        nameTextField.addKeyListener( mnemonicKeyHandler );
        nameTextField.addKeyListener( copyPasteMenuKeyHandler );
        nameTextField.addMouseListener( copyPasteMouseMenuHandler );
        dialogPanel.add( nameTextField );
        
        descriptionLabel = new JLabel( "說明: " );
        descriptionLabel.setBounds( 16, 106, 48, 22 );
        descriptionLabel.setFont( generalFont );
        dialogPanel.add( descriptionLabel );
        
        descriptionTextArea = new JTextArea();
        descriptionTextArea.setSize( 425, 154 );
        descriptionTextArea.setFont( generalFont );
        descriptionTextArea.setEditable( false );
        descriptionTextArea.setLineWrap( true );
        descriptionTextArea.setWrapStyleWord( true );
        descriptionTextArea.addKeyListener( mnemonicKeyHandler );
        descriptionTextArea.addKeyListener( copyPasteMenuKeyHandler );
        descriptionTextArea.addMouseListener( copyPasteMouseMenuHandler );
        descriptionScrollPane = new JScrollPane( descriptionTextArea );
        descriptionScrollPane.setBounds( 16, 128, 449, 159 );
        descriptionScrollPane.setPreferredSize( new Dimension( 449, 159 ) );
        dialogPanel.add( descriptionScrollPane );
        
        Set<AWTKeyStroke> forward = new HashSet<AWTKeyStroke>(
                descriptionTextArea.getFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS ) );
        forward.add( KeyStroke.getKeyStroke( "TAB" ) );
        descriptionTextArea.setFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forward );
        Set<AWTKeyStroke> backward = new HashSet<AWTKeyStroke>(
                descriptionTextArea.getFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS ) );
        backward.add( KeyStroke.getKeyStroke( "shift TAB" ) );
        descriptionTextArea.setFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backward );
        
        okButton = new JButton( "確認" );
        okButton.setBounds( 224, 302, 48, 22 );
        okButton.setFont( generalFont );
        okButton.setMargin( new Insets( 0, 0, 0, 0 ) );
        okButton.addKeyListener( mnemonicKeyHandler );
        okButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent event ) {
                setVisible( false );
            }
        });
        dialogPanel.add( okButton );
        
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
        
        switch( scheduledItem.getType() ) {
        case 'O':
            typeTextField.setText( "準時" );
            break;
        case 'D':
            typeTextField.setText( "期限" );
            break;
        case 'P':
            typeTextField.setText( "建議" );
            break;
        case 'N':
            typeTextField.setText( "不限時" );
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
        
        popupMenuClosedFlag = false;
        
        okButton.requestFocus();
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
}

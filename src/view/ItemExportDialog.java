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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
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
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

import commonUtil.ItemUtil;
import domain.Item;
import service.ItemService;

public class ItemExportDialog extends JDialog {
    
    private static final long serialVersionUID = 1L;
    
    private ItemService itemService;
    
    private String itemTxtString;
    private String itemStatistics;
    private String itemIncludingDesc;
    
    private UndoManager undoManager;
    private FocusHandler focusHandler;
    private MnemonicKeyHandler mnemonicKeyHandler;
    private UndoEditHandler undoEditHandler;
    private UndoHotKeyHandler undoHotKeyHandler;
    private Font generalFont;
    private JPanel dialogPanel;
    private JTextField yearTextField;
    private JLabel yearLabel;
    private JTextField monthTextField;
    private JLabel monthLabel;
    private JTextField dayTextField;
    private JLabel dayLabel;
    private JRadioButton txtStringRadioButton;
    private JRadioButton statisticRadioButton;
    private JRadioButton includingDescRadioButton;
    private ButtonGroup exportTypeButtonGroup;
    private JLabel exportContentLabel;
    private JTextArea exportContentTextArea;
    private JScrollPane exportContentScrollPane;
    private JButton okButton;
    
    public ItemExportDialog( MainFrame ownerFrame, ItemService itemService ) {
        super( ownerFrame, "Export Item", true );
        
        this.itemService = itemService;
        
        undoManager = new UndoManager();
        focusHandler = new FocusHandler();
        mnemonicKeyHandler = new MnemonicKeyHandler();
        undoEditHandler = new UndoEditHandler();
        undoHotKeyHandler = new UndoHotKeyHandler();
        
        generalFont = new Font( "細明體", Font.PLAIN, 16 );
        
        dialogPanel = new JPanel();
        dialogPanel.setLayout( null );
        
        yearTextField = new JTextField( 4 );
        yearTextField.setBounds( 16, 10, 40, 22 );
        yearTextField.setFont( generalFont );
        yearTextField.setEditable( false );
        yearTextField.addFocusListener( focusHandler );
        yearTextField.addKeyListener( mnemonicKeyHandler );
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
        monthTextField.setEditable( false );
        monthTextField.addFocusListener( focusHandler );
        monthTextField.addKeyListener( mnemonicKeyHandler );
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
        dayTextField.setEditable( false );
        dayTextField.addFocusListener( focusHandler );
        dayTextField.addKeyListener( mnemonicKeyHandler );
        dayTextField.addKeyListener( undoHotKeyHandler );
        dayTextField.getDocument().addUndoableEditListener( undoEditHandler );
        dialogPanel.add( dayTextField );
        
        dayLabel = new JLabel( "日" );
        dayLabel.setBounds( 136, 10, 16, 22 );
        dayLabel.setFont( generalFont );
        dialogPanel.add( dayLabel );
        
        exportContentLabel = new JLabel( "匯出內容:" );
        exportContentLabel.setBounds( 16, 54, 80, 22 );
        exportContentLabel.setFont( generalFont );
        dialogPanel.add( exportContentLabel );
        
        txtStringRadioButton = new JRadioButton( "字串格式", true );
        txtStringRadioButton.setBounds( 112, 54, 96, 22 );
        txtStringRadioButton.setFont( generalFont );
        txtStringRadioButton.addKeyListener( mnemonicKeyHandler );
        txtStringRadioButton.addItemListener( new ItemListener() {
            @Override
            public void itemStateChanged( ItemEvent event ) {
                JRadioButton sourceRadioButton = (JRadioButton)event.getSource();
                if( sourceRadioButton.isSelected() ) {
                    exportContentTextArea.setText( itemTxtString );
                }
            }
        });
        dialogPanel.add( txtStringRadioButton );
        
        statisticRadioButton = new JRadioButton( "統計", false );
        statisticRadioButton.setBounds( 224, 54, 60, 22 );
        statisticRadioButton.setFont( generalFont );
        statisticRadioButton.addKeyListener( mnemonicKeyHandler );
        statisticRadioButton.addItemListener( new ItemListener() {
            @Override
            public void itemStateChanged( ItemEvent event ) {
                JRadioButton sourceRadioButton = (JRadioButton)event.getSource();
                if( sourceRadioButton.isSelected() ) {
                    exportContentTextArea.setText( itemStatistics );
                }
            }
        });
        dialogPanel.add( statisticRadioButton );
        
        includingDescRadioButton = new JRadioButton( "包含說明", false );
        includingDescRadioButton.setBounds( 304, 54, 96, 22 );
        includingDescRadioButton.setFont( generalFont );
        includingDescRadioButton.addKeyListener( mnemonicKeyHandler );
        includingDescRadioButton.addItemListener( new ItemListener() {
            @Override
            public void itemStateChanged( ItemEvent event ) {
                JRadioButton sourceRadioButton = (JRadioButton)event.getSource();
                if( sourceRadioButton.isSelected() ) {
                    exportContentTextArea.setText( itemIncludingDesc );
                }
            }
        });
        dialogPanel.add( includingDescRadioButton );
        
        exportTypeButtonGroup = new ButtonGroup();
        exportTypeButtonGroup.add( txtStringRadioButton );
        exportTypeButtonGroup.add( statisticRadioButton );
        exportTypeButtonGroup.add( includingDescRadioButton );
        
        exportContentTextArea = new JTextArea();
        exportContentTextArea.setSize( 425, 198 );
        exportContentTextArea.setFont( generalFont );
        exportContentTextArea.addKeyListener( mnemonicKeyHandler );
        exportContentTextArea.addKeyListener( undoHotKeyHandler );
        exportContentTextArea.getDocument().addUndoableEditListener( undoEditHandler );
        exportContentScrollPane = new JScrollPane( exportContentTextArea );
        exportContentScrollPane.setBounds( 16, 76, 449, 203 );
        exportContentScrollPane.setPreferredSize( new Dimension( 449, 203 ) );
        dialogPanel.add( exportContentScrollPane );
        
        Set<AWTKeyStroke> forward = new HashSet<AWTKeyStroke>(
                exportContentTextArea.getFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS ) );
        forward.add( KeyStroke.getKeyStroke( "TAB" ) );
        exportContentTextArea.setFocusTraversalKeys( KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forward );
        Set<AWTKeyStroke> backward = new HashSet<AWTKeyStroke>(
                exportContentTextArea.getFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS ) );
        backward.add( KeyStroke.getKeyStroke( "shift TAB" ) );
        exportContentTextArea.setFocusTraversalKeys( KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backward );
        
        okButton = new JButton( "確認" );
        okButton.setBounds( 224, 296, 48, 22 );
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
    
    public void openDialog( String selectedDateString ) {
        int year = Integer.parseInt( selectedDateString.substring( 0, 4 ) );
        int month = Integer.parseInt( selectedDateString.substring( 5, 7 ) );
        int day = Integer.parseInt( selectedDateString.substring( 8, 10 ) );
        
        yearTextField.setText( selectedDateString.substring( 0, 4 ) );
        monthTextField.setText( selectedDateString.substring( 5, 7 ) );
        dayTextField.setText( selectedDateString.substring( 8, 10 ) );
        
        exportItemListAndStatistics( year, month, day );
        
        txtStringRadioButton.setSelected( true );
        statisticRadioButton.setSelected( false );
        
        exportContentTextArea.setText( itemTxtString );
        exportContentTextArea.requestFocus();
        
        setVisible( true );
    }
    
    private void exportItemListAndStatistics( int year, int month, int day ) {
        itemTxtString = new String();
        itemStatistics = new String();
        itemIncludingDesc = new String();
        
        try {
            List<Item> itemList = itemService.findByDate( year, month, day );
            StringBuffer txtStringBuf = new StringBuffer();
            StringBuffer txtStringIncludingDesc = new StringBuffer();
            
            for( Item item : itemList ) {
                txtStringBuf.append( ItemUtil.getTxtStringFromItem( item ) );
                txtStringBuf.append( "\n" );
                txtStringIncludingDesc.append( ItemUtil.getTxtStringIncludingDescFromItem( item ) );
                txtStringIncludingDesc.append( "\n" );
            }
            
            itemTxtString = txtStringBuf.toString();
            itemStatistics = ItemUtil.exportStatistics( itemList );
            itemIncludingDesc = txtStringIncludingDesc.toString();
        } catch ( Exception e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog( null, "匯出錯誤", "Error", JOptionPane.ERROR_MESSAGE );
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
                if( event.getSource() != exportContentTextArea ) {
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
}

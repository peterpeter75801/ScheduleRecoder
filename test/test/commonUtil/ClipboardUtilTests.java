package test.commonUtil;

import commonUtil.ClipboardUtil;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import javax.swing.JTextField;

import junit.framework.TestCase;

public class ClipboardUtilTests extends TestCase {
    
    private Transferable clipboardContentBackup;
    
    public void setUp() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboardContentBackup = clipboard.getContents( this );
    }
    
    public void tearDown() throws IOException {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents( clipboardContentBackup, null );
    }
    
    public void testCut() throws Exception {
        TestTextComponent input = new TestTextComponent( "Test component text.", 0, 15 );
        String expectComponentText = "text.";
        String expectClipboardContent = "Test component ";
        
        ClipboardUtil.cut( input );
        
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String actualComponentText = input.getText();
        String actualClipboardcontent = (String)clipboard.getContents( this ).getTransferData( DataFlavor.stringFlavor );
        assertEquals( expectComponentText, actualComponentText );
        assertEquals( expectClipboardContent, actualClipboardcontent );
    }
    
    public void testCopy() throws Exception {
        TestTextComponent input = new TestTextComponent( "Test component text.", 0, 15 );
        String expectComponentText = "Test component text.";
        String expectClipboardContent = "Test component ";
        
        ClipboardUtil.copy( input );
        
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String actualComponentText = input.getText();
        String actualClipboardcontent = (String)clipboard.getContents( this ).getTransferData( DataFlavor.stringFlavor );
        assertEquals( expectComponentText, actualComponentText );
        assertEquals( expectClipboardContent, actualClipboardcontent );
    }
    
    public void testPaste() throws Exception {
        TestTextComponent input = new TestTextComponent( "Test component text.", 20, 20 );
        Clipboard inputClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        inputClipboard.setContents( new StringSelection( "123" ), null );
        String expectComponentText = "Test component text.123";
        
        ClipboardUtil.paste( input );
        
        String actualComponentText = input.getText();
        assertEquals( expectComponentText, actualComponentText );
    }
    
    private class TestTextComponent extends JTextField {
        
        private static final long serialVersionUID = 1L;
        
        private String text;
        private int selectionStart;
        private int selectionEnd;
        
        public TestTextComponent( String text, int selectionStart, int selectionEnd ) {
            super();
            
            this.text = text;
            this.selectionStart = selectionStart;
            this.selectionEnd = selectionEnd;
            
            setText( text );
            setSelectionStart( selectionStart );
            setSelectionEnd( selectionEnd );
        }
        
        public String toString() {
            return String.format( "TestTextComponent[text=%s, selectionStart=%d, selectionEnd=%d]", text, selectionStart, selectionEnd );
        }
    }
}

package view;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ItemPanel extends JPanel {
    
    public JLabel testLabel;
    
    public ItemPanel() {
        setLayout( null );
        
        testLabel = new JLabel( "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" );
        testLabel.setBounds( 0, 0, 800, 22 );
        testLabel.setFont( new Font( "細明體", Font.PLAIN, 16 ) );
        add( testLabel );
    }
}

package view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {
    
    private JTabbedPane tabbedPane;
    private ItemPanel itemPanel;
    
    public MainFrame() {
        super( "Schedule Recorder" );
        
        tabbedPane = new JTabbedPane();
        
        itemPanel = new ItemPanel();
        tabbedPane.addTab( "Item", null, itemPanel, "Item Panel" );
        
        add( tabbedPane );
        
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 800, 600 );
    }
}

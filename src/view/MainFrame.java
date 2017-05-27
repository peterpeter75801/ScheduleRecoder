package view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private JTabbedPane tabbedPane;
    private ItemPanel itemPanel;
    
    public MainFrame() {
        super( "Schedule Recorder" );
        
        tabbedPane = new JTabbedPane();
        
        itemPanel = new ItemPanel( this );
        tabbedPane.addTab( "Item", null, itemPanel, "Item Panel" );
        
        add( tabbedPane );
        
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        pack();
    }
}

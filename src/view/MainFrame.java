package view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private JTabbedPane tabbedPane;
    private ItemPanel itemPanel;
    private ScheduledItemPanel scheduledItemPanel; 
    
    public MainFrame() {
        super( "Schedule Recorder" );
        
        tabbedPane = new JTabbedPane();
        
        itemPanel = new ItemPanel( this );
        scheduledItemPanel = new ScheduledItemPanel( this );
        tabbedPane.addTab( "時間記錄", null, itemPanel, "時間項目記錄" );
        tabbedPane.addTab( "事項排程", null, scheduledItemPanel, "預計執行事項排程" );
        
        add( tabbedPane );
        
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        pack();
    }
    
    public ItemPanel getItemPanel() {
        return itemPanel;
    }
}

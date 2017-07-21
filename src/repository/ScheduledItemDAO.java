package repository;

import java.io.IOException;
import java.util.List;

import domain.ScheduledItem;

public interface ScheduledItemDAO {
    
    public boolean insert( ScheduledItem scheduledItem ) throws IOException;
    
    public ScheduledItem findById( Integer id ) throws Exception;
    
    public List<ScheduledItem> findAll() throws Exception;
    
    public boolean update( ScheduledItem scheduledItem ) throws Exception;
    
    public boolean delete( ScheduledItem scheduledItem ) throws Exception;
    
    public int getCurrentSeqNumber() throws Exception;
}

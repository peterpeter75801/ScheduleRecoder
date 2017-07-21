package service;

import java.util.List;

import domain.ScheduledItem;

public interface ScheduledItemService {
    
    public int insert( ScheduledItem scheduledItem ) throws Exception;
    
    public ScheduledItem findById( Integer id ) throws Exception;
    
    public List<ScheduledItem> findAllSortByTime() throws Exception;
    
    public int update( ScheduledItem scheduledItem ) throws Exception;
    
    public int delete( ScheduledItem scheduledItem ) throws Exception;
}

package service.Impl;

import java.util.List;

import common.Contants;
import commonUtil.ScheduledItemUtil;
import domain.Item;
import domain.ScheduledItem;
import repository.ItemDAO;
import repository.ScheduledItemDAO;
import repository.Impl.ItemDAOImpl;
import repository.Impl.ScheduledItemDAOImpl;
import service.ScheduledItemService;

public class ScheduledItemServiceImpl implements ScheduledItemService {
    
    private ItemDAO itemDAO;
    private ScheduledItemDAO scheduledItemDAO;
    
    public ScheduledItemServiceImpl() {
        itemDAO = new ItemDAOImpl();
        scheduledItemDAO = new ScheduledItemDAOImpl();
    }

    @Override
    public int insert( ScheduledItem scheduledItem ) throws Exception {
        boolean returnCode;
        
        if( scheduledItemDAO.getCurrentSeqNumber() >= Integer.MAX_VALUE ) {
            return Contants.ERROR_EXCEED_UPPER_LIMIT;
        }
        
        returnCode = scheduledItemDAO.insert( scheduledItem );
        if( !returnCode ) {
            return Contants.ERROR;
        }
                
        return Contants.SUCCESS;
    }

    @Override
    public ScheduledItem findById( Integer id ) throws Exception {
        return scheduledItemDAO.findById( id );
    }

    @Override
    public List<ScheduledItem> findAllSortByTime() throws Exception {
        List<ScheduledItem> scheduledItemList = scheduledItemDAO.findAll();
        
        scheduledItemList = ScheduledItemUtil.sortByTime( scheduledItemList );
        scheduledItemList = ScheduledItemUtil.moveTypeNDataToBottom( scheduledItemList );
        
        return scheduledItemList;
    }

    @Override
    public int update( ScheduledItem scheduledItem ) throws Exception {
        boolean returnCode = scheduledItemDAO.update( scheduledItem );
        
        if( !returnCode ) {
            return Contants.ERROR;
        } else {
            return Contants.SUCCESS;
        }
    }

    @Override
    public int delete( ScheduledItem scheduledItem ) throws Exception {
        boolean returnCode = scheduledItemDAO.delete( scheduledItem );
        
        if( !returnCode ) {
            return Contants.ERROR;
        } else {
            return Contants.SUCCESS;
        }
    }

    @Override
    public int execute( ScheduledItem original, ScheduledItem completed, boolean deleteFlag ) throws Exception {
        boolean returnCode;
        int itemSeq = 0;
        try {
            while( itemDAO.findOne( completed.getYear(), completed.getMonth(), completed.getDay(), 
                    completed.getHour(), completed.getMinute(), itemSeq ) != null ) {
                if( itemSeq >= Integer.MAX_VALUE ) {
                    return Contants.ERROR_EXCEED_UPPER_LIMIT;
                }
                itemSeq++;
            }
        } catch( Exception e ) {
            return Contants.ERROR;
        }
        
        Item completedItem = new Item();
        completedItem.setYear( completed.getYear() );
        completedItem.setMonth( completed.getMonth() );
        completedItem.setDay( completed.getDay() );
        completedItem.setStartHour( completed.getHour() );
        completedItem.setStartMinute( completed.getMinute() );
        completedItem.setSeq( itemSeq );
        completedItem.setEndHour( completed.getHour() );
        completedItem.setEndMinute( completed.getMinute() );
        completedItem.setName( completed.getName() );
        completedItem.setDescription( completed.getDescription() );
        
        returnCode = itemDAO.insert( completedItem );
        if( !returnCode ) {
            return Contants.ERROR;
        }
        
        returnCode = itemDAO.sortByStartTimeInDateGroup( completed.getYear(), completed.getMonth(), completed.getDay() );
        if( !returnCode ) {
            return Contants.ERROR;
        }
        
        if( deleteFlag ) {
            returnCode = scheduledItemDAO.delete( original );
        }
        if( deleteFlag && !returnCode ) {
            return Contants.ERROR;
        }
        
        return Contants.SUCCESS;
    }

}

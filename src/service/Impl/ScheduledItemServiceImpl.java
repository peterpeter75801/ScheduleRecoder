package service.Impl;

import java.util.List;

import common.Contants;
import commonUtil.ScheduledItemUtil;
import domain.ScheduledItem;
import repository.ScheduledItemDAO;
import repository.Impl.ScheduledItemDAOImpl;
import service.ScheduledItemService;

public class ScheduledItemServiceImpl implements ScheduledItemService {
    
    private ScheduledItemDAO scheduledItemDAO;
    
    public ScheduledItemServiceImpl() {
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

}

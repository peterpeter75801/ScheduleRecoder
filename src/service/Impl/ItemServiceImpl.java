package service.Impl;

import java.io.IOException;
import java.util.List;

import common.Contants;
import domain.Item;
import repository.ItemDAO;
import repository.Impl.ItemDAOImpl;
import service.ItemService;

public class ItemServiceImpl implements ItemService {
    
    private ItemDAO itemDAO;
    
    public ItemServiceImpl() {
        itemDAO = new ItemDAOImpl();
    }

    @Override
    public int insert( Item item ) throws IOException {
        try {
            if( findByTime( item.getYear(), item.getMonth(), item.getDay(), item.getStartHour(), item.getStartMinute() ) != null ) {
                return Contants.DUPLICATE_DATA;
            }
        } catch ( Exception e ) {
            return Contants.ERROR;
        }
        
        boolean returnCode = itemDAO.insert( item );
        if( !returnCode ) {
            return Contants.ERROR;
        }
        
        return Contants.SUCCESS;
    }

    @Override
    public Item findByTime( Integer year, Integer month, Integer day, Integer startHour, Integer startMinute )
            throws Exception {
        return itemDAO.findByTime( year, month, day, startHour, startMinute );
    }

    @Override
    public List<Item> findByDate( Integer year, Integer month, Integer day ) throws Exception {
        return itemDAO.findByDate( year, month, day );
    }

    @Override
    public int update( Item item ) throws Exception {
        boolean returnCode = itemDAO.update( item );
        
        if( !returnCode ) {
            return Contants.ERROR;
        } else {
            return Contants.SUCCESS;
        }
    }

    @Override
    public int delete( Item item ) throws Exception {
        boolean returnCode = itemDAO.delete( item );
        
        if( !returnCode ) {
            return Contants.ERROR;
        } else {
            return Contants.SUCCESS;
        }
    }
}

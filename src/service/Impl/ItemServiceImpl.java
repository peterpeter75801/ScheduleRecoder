package service.Impl;

import java.io.IOException;

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
    public boolean insert( Item item ) throws IOException {
        return itemDAO.insert( item );
    }

    @Override
    public Item findByTime( Integer year, Integer month, Integer day, Integer startHour, Integer startMinute )
            throws Exception {
        return itemDAO.findByTime( year, month, day, startHour, startMinute );
    }

    @Override
    public boolean update( Item item ) throws Exception {
        return itemDAO.update( item );
    }

    @Override
    public boolean delete(Item item) throws Exception {
        return itemDAO.delete( item );
    }
}

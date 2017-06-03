package service;

import java.io.IOException;
import java.util.List;

import domain.Item;

public interface ItemService {
    
    public int insert( Item item ) throws IOException;
    
    public Item findByTime( Integer year, Integer month, Integer day, 
            Integer startHour, Integer startMinute ) throws Exception;
    
    public List<Item> findByDate( Integer year, Integer month, Integer day ) throws Exception;
    
    public boolean update( Item item ) throws Exception;
    
    public boolean delete( Item item ) throws Exception;
}
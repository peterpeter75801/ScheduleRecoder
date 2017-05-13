package service;

import java.io.IOException;

import domain.Item;

public interface ItemService {
    
    public boolean insert( Item item ) throws IOException;
    
    public Item findByTime( Integer year, Integer month, Integer day, 
            Integer startHour, Integer startMinute ) throws Exception;
    
    public boolean update( Item item ) throws Exception;
    
    public boolean delete( Item item ) throws Exception;
}
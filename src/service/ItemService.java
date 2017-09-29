package service;

import java.util.List;

import domain.Item;

public interface ItemService {
    
    public int insert( Item item ) throws Exception;
    
    public int insertItemsInDateGroup( Integer year, Integer month, Integer day, 
            List<Item> itemList ) throws Exception;
    
    public Item findOne( Integer year, Integer month, Integer day, 
            Integer startHour, Integer startMinute, Integer seq ) throws Exception;
    
    public List<Item> findByDate( Integer year, Integer month, Integer day ) throws Exception;
    
    public int update( Item originalItem, Item newItem ) throws Exception;
    
    public int delete( Item item ) throws Exception;
    
    public int checkItemDataVersion( Integer year, Integer month, Integer day ) throws Exception;
    
    public String convertOldItemDataToCurrentVersion() throws Exception;
}
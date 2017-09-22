package repository;

import java.io.IOException;
import java.util.List;

import domain.Item;

public interface ItemDAO {
    
    public boolean insert( Item item ) throws IOException;
    
    public Item findOne( Integer year, Integer month, Integer day, 
            Integer startHour, Integer startMinute, Integer seq ) throws Exception;
    
    public List<Item> findByDate( Integer year, Integer month, Integer day ) throws Exception;
    
    public boolean update( Item item ) throws Exception;
    
    public boolean delete( Item item ) throws Exception;
    
    public boolean deleteByDate( Integer year, Integer month, Integer day ) throws Exception;
    
    public boolean sortByStartTimeInDateGroup( 
            Integer year, Integer month, Integer day ) throws Exception;
    
    public List<String> listAllDateContainingData() throws Exception;
    
    public int checkItemDataVersion( Integer year, Integer month, Integer day ) throws Exception;
    
    public boolean backupByDate( String date ) throws Exception;
    
    public boolean restoreByDate( String date ) throws Exception;
    
    public boolean dropBackupByDate( String date ) throws Exception;
}

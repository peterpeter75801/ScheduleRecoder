package service.Impl;

import java.util.List;

import common.Contants;
import commonUtil.ItemUtil;
import domain.Item;
import repository.ItemDAO;
import repository.Impl.ItemDAOImpl;
import repository.Impl.ItemDAOImplBeforeVer26;
import service.ItemService;

public class ItemServiceImpl implements ItemService {
    
    private ItemDAO itemDAO;
    
    public ItemServiceImpl() {
        itemDAO = new ItemDAOImpl();
    }

    @Override
    public int insert( Item item ) throws Exception {
        boolean returnCode;
        int seq = 0;
        try {
            while( findOne( item.getYear(), item.getMonth(), item.getDay(), item.getStartHour(), item.getStartMinute(), seq ) != null ) {
                if( seq >= Integer.MAX_VALUE ) {
                    return Contants.ERROR_EXCEED_UPPER_LIMIT;
                }
                seq++;
            }
            item.setSeq( seq );
        } catch ( Exception e ) {
            return Contants.ERROR;
        }
        
        returnCode = itemDAO.insert( item );
        if( !returnCode ) {
            return Contants.ERROR;
        }
        
        returnCode = itemDAO.sortByStartTimeInDateGroup( item.getYear(), item.getMonth(), item.getDay() );
        if( !returnCode ) {
            return Contants.ERROR;
        }
        
        return Contants.SUCCESS;
    }

    @Override
    public int insertItemsInDateGroup( Integer year, Integer month, Integer day, 
            List<Item> itemList ) throws Exception {
        int returnCode;
        
        if( year == null || month == null || day == null ) {
            return Contants.ERROR_INVALID_PARAMETER;
        }
        
        for( Item item : itemList ) {
            try {
                if( !year.equals( item.getYear() ) || !month.equals( item.getMonth() ) || !day.equals( item.getDay() ) ) {
                    return Contants.ERROR_NOT_SUPPORT;
                }
                returnCode = insert( item );
                if( returnCode != Contants.SUCCESS ) {
                    return Contants.ERROR;
                }
            } catch ( Exception e ) {
                e.printStackTrace();
                return Contants.ERROR_NOT_COMPLETE;
            }
        }
        
        if( !itemDAO.sortByStartTimeInDateGroup( year, month, day ) ) {
            return Contants.ERROR;
        }
        
        return Contants.SUCCESS;
    }

    @Override
    public Item findOne( Integer year, Integer month, Integer day, Integer startHour, Integer startMinute, Integer seq )
            throws Exception {
        return itemDAO.findOne( year, month, day, startHour, startMinute, seq );
    }

    @Override
    public List<Item> findByDate( Integer year, Integer month, Integer day ) throws Exception {
        return itemDAO.findByDate( year, month, day );
    }

    @Override
    public int update( Item originalItem, Item newItem ) throws Exception {
        boolean returnCode = false;
        
        if( ItemUtil.comparePrimaryKey( originalItem, newItem ) == 0 ) {
            returnCode = itemDAO.update( newItem );
        } else {
            int status = insert( newItem );
            if( status != Contants.SUCCESS ) {
                return status;
            }
            status = delete( originalItem );
            if( status != Contants.SUCCESS ) {
                return status;
            }
            returnCode = true;
        }
        
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

    @Override
    public int checkItemDataVersion( Integer year, Integer month, Integer day ) throws Exception {
        return itemDAO.checkItemDataVersion( year, month, day );
    }

    @Override
    public String convertOldItemDataToCurrentVersion() throws Exception {
        final int STANDARD_YYYY_MM_DD_FILE_NAME_LENGTH = 10;
        
        ItemDAO itemDAOBeforeVer26 = new ItemDAOImplBeforeVer26();
        List<String> itemFileList = itemDAO.listAllDateContainingData();
        StringBuffer errorLogBuf = new StringBuffer();
        
        // 備份要做轉換的Item檔案
        for( String itemFile : itemFileList ) {
            itemDAOBeforeVer26.backupByDate( itemFile );
        }
        
        // 將舊版本的Item檔案轉換為新版本的Item檔案
        for( String itemFile : itemFileList ) {
            int returnCode = Contants.SUCCESS;
            
            if( itemFile == null ) {
                errorLogBuf.append( "Error: Convert null item file\n" );
            } else if( itemFile.length() != STANDARD_YYYY_MM_DD_FILE_NAME_LENGTH ||
                    !Character.isDigit( itemFile.charAt( 0 ) ) || 
                    !Character.isDigit( itemFile.charAt( 1 ) ) || 
                    !Character.isDigit( itemFile.charAt( 2 ) ) || 
                    !Character.isDigit( itemFile.charAt( 3 ) ) || 
                    itemFile.charAt( 4 ) != '.' || 
                    !Character.isDigit( itemFile.charAt( 5 ) ) || 
                    !Character.isDigit( itemFile.charAt( 6 ) ) || 
                    itemFile.charAt( 7 ) != '.' || 
                    !Character.isDigit( itemFile.charAt( 8 ) ) || 
                    !Character.isDigit( itemFile.charAt( 9 ) ) ) {
                errorLogBuf.append( "Error: Convert invalid item file name \"" + itemFile + "\"\n" );
            } else {
                int year = Integer.parseInt( itemFile.substring( 0, 4 ) );
                int month = Integer.parseInt( itemFile.substring( 5, 7 ) );
                int day = Integer.parseInt( itemFile.substring( 8, 10 ) );
                List<Item> itemListOfCurrentDay = itemDAOBeforeVer26.findByDate( year, month, day );
                itemDAOBeforeVer26.deleteByDate( year, month, day );
                returnCode = insertItemsInDateGroup( year, month, day, itemListOfCurrentDay );
            }
            
            if( returnCode != Contants.SUCCESS ) {
                errorLogBuf.append( "Error: Error occur while converting item file name \"" + itemFile + "\"\n" );
            }
        }
        
        if( errorLogBuf.length() > 0 ) {
            // 如果轉換中有錯誤發生(error log中有內容)，將備份的Item檔案還原，並回傳error log
            for( String itemFile : itemFileList ) {
                itemDAOBeforeVer26.restoreByDate( itemFile );
            }
            return errorLogBuf.toString();
        } else {
            // 如果轉換過程正常(無error log)，將備份的Item檔案刪除，並回傳"Success"訊息
            for( String itemFile : itemFileList ) {
                itemDAOBeforeVer26.dropBackupByDate( itemFile );
            }
            return "Success";
        }
    }
}

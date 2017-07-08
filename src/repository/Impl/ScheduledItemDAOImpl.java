package repository.Impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import commonUtil.ItemUtil;
import commonUtil.ScheduledItemUtil;
import domain.ScheduledItem;
import repository.ScheduledItemDAO;

public class ScheduledItemDAOImpl implements ScheduledItemDAO {
    
    private final String S_ITEM_CSV_FILE_ATTR_STRING = "id,year,month,day,hour,minute,expectedTime,type,name,description";
    private final String INITIAL_SEQ_NUMBER = "1";
    
    private final String SCHEDULED_RECORDER_DATA_PATH = "data";
    private final String S_ITEM_CSV_FILE_PATH = "data\\ScheduledItem.csv";
    private final String S_ITEM_SEQ_FILE_PATH = "data\\ScheduledItemSeq.txt";
    private final String FILE_CHARSET = "big5";

    @Override
    public boolean insert( ScheduledItem scheduledItem ) throws IOException {
        ScheduledItem scheduledItemWithNewId = ScheduledItemUtil.copy( scheduledItem );
        
        // 取得Scheduled Item目前的流水號(ID)
        if( !checkIfFileExists( S_ITEM_SEQ_FILE_PATH ) ) {
            createSeqFile( S_ITEM_SEQ_FILE_PATH );
        }
        BufferedReader bufReader = new BufferedReader( new InputStreamReader(
                new FileInputStream( new File( S_ITEM_SEQ_FILE_PATH ) ),
                FILE_CHARSET
            )
        );
        Integer currentSeqNumber = null;
        try {
            currentSeqNumber = Integer.parseInt( bufReader.readLine() );
            scheduledItemWithNewId.setId( currentSeqNumber );
        } catch( NumberFormatException e ) {
            return false;
        } finally {
            bufReader.close();
        }
        
        // 新增Scheduled Item資料
        if( !checkIfFileExists( S_ITEM_CSV_FILE_PATH ) ) {
            createCsvFile( S_ITEM_CSV_FILE_PATH );
        }
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream( new File( S_ITEM_CSV_FILE_PATH ), true ),
                    FILE_CHARSET
                )
            );
        writer.write( ScheduledItemUtil.getCsvTupleStringFromScheduledItem( scheduledItemWithNewId ) );
        writer.newLine();
        writer.close();
        
        // 更新Scheduled Item的流水號(ID)
        writer = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream( new File( S_ITEM_SEQ_FILE_PATH ), false ),
                    FILE_CHARSET
                )
            );
        currentSeqNumber++;
        writer.write( currentSeqNumber.toString() );
        writer.newLine();
        writer.close();
        
        return true;
    }

    @Override
    public List<ScheduledItem> findAll() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean update( ScheduledItem scheduledItem ) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete( ScheduledItem scheduledItem ) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }
    
    private boolean checkIfFileExists( String fileName ) {
        File f = new File( fileName );
        if( !f.exists() ) {
            return false;
        } else if( f.isDirectory() || f.length() <= 0 ) {
            f.delete();
            return false;
        } else {
            return true;
        }
    }
    
    private void createCsvFile( String fileName ) throws IOException {
        File f = new File( SCHEDULED_RECORDER_DATA_PATH );
        f.mkdirs();
        
        BufferedWriter bufWriter = new BufferedWriter( 
                new OutputStreamWriter( 
                    new FileOutputStream( new File( fileName ), false ), 
                    FILE_CHARSET 
                ) 
            );
        bufWriter.write( S_ITEM_CSV_FILE_ATTR_STRING );
        bufWriter.newLine();
        bufWriter.close();
    }
    
    private void createSeqFile( String fileName ) throws IOException {
        File f = new File( SCHEDULED_RECORDER_DATA_PATH );
        f.mkdirs();
        
        BufferedWriter bufWriter = new BufferedWriter( 
                new OutputStreamWriter( 
                    new FileOutputStream( new File( fileName ), false ), 
                    FILE_CHARSET 
                ) 
            );
        bufWriter.write( INITIAL_SEQ_NUMBER );
        bufWriter.newLine();
        bufWriter.close();
    }
}

package repository.Impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import commonUtil.oldVersion.ItemUtilBeforeVer26;
import domain.Item;
import repository.ItemDAO;

public class ItemDAOImplBeforeVer26 implements ItemDAO {
    
    private final String ITEM_CSV_FILE_ATTR_STRING = "year,month,day,startHour,startMinute,endHour,endMinute,name,description";
    private final String ITEM_CSV_FILE_PATH = "./data/Item/";
    private final String FILE_CHARSET = "big5";

    @Override
    public boolean insert( Item item ) throws IOException {
        String csvFilePath = ITEM_CSV_FILE_PATH + ItemUtilBeforeVer26.getItemCsvFileNameFromItem( item );
        if( !checkIfCsvFileExists( csvFilePath ) ) {
            createCsvFile( csvFilePath );
        }
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream( new File( csvFilePath ), true ),
                    FILE_CHARSET
                )
            );
        writer.write( ItemUtilBeforeVer26.getCsvTupleStringFromItem( item ) );
        writer.newLine();
        writer.close();
        
        return true;
    }

    @Override
    public Item findOne( Integer year, Integer month, Integer day, Integer startHour, Integer startMinute, Integer seq )
            throws Exception {
        // not support
        return null;
    }

    @Override
    public List<Item> findByDate( Integer year, Integer month, Integer day ) throws Exception {
        String csvFilePath = ITEM_CSV_FILE_PATH + 
                ItemUtilBeforeVer26.getItemCsvFileNameFromItem( year, month, day );
        ArrayList<Item> itemList = new ArrayList<Item>();
        
        if( !checkIfCsvFileExists( csvFilePath ) ) {
            return itemList;
        }
        
        String currentTuple = "";
        Item currentItem = new Item();
        BufferedReader bufReader = new BufferedReader( new InputStreamReader(
                new FileInputStream( new File( csvFilePath ) ),
                FILE_CHARSET
            )
        );
        // read attribute titles
        bufReader.readLine();
        // fetch data
        while( (currentTuple = bufReader.readLine()) != null ) {
            currentItem = ItemUtilBeforeVer26.getItemFromCsvTupleString( currentTuple );
            itemList.add( currentItem );
        }
        bufReader.close();
        
        return itemList;
    }

    @Override
    public boolean update( Item item ) throws Exception {
        // not support
        return false;
    }

    @Override
    public boolean delete( Item item ) throws Exception {
        // not support
        return false;
    }

    @Override
    public boolean deleteByDate( Integer year, Integer month, Integer day ) throws Exception {
        File f = new File( ITEM_CSV_FILE_PATH + 
            String.format( "%04d.%02d.%02d", year, month, day ) + 
            ".csv" );
        boolean returnCode = f.delete();
        
        return returnCode;
    }

    @Override
    public boolean sortByStartTimeInDateGroup( Integer year, Integer month, Integer day ) throws Exception {
        // not support
        return false;
    }

    @Override
    public int checkItemDataVersion( Integer year, Integer month, Integer day ) throws Exception {
        // not support
        return 0;
    }

    @Override
    public List<String> listAllDateContainingData() throws Exception {
        // not support
        return null;
    }

    @Override
    public boolean backupByDate( String date ) throws Exception {
        final int BUFFER_LENGTH = 1;
        File srcFile = new File( ITEM_CSV_FILE_PATH + date + ".csv" );
        File destFile = new File( ITEM_CSV_FILE_PATH + date + "_dao_backup.csv" );
        if( srcFile.exists() && !srcFile.isDirectory() && destFile.createNewFile() ) {
            FileInputStream input = new FileInputStream( srcFile );
            FileOutputStream output = new FileOutputStream( destFile );
            byte[] buffer = new byte[ BUFFER_LENGTH ];
            int readByte = 0;
            while( (readByte = input.read( buffer )) != -1 ) {
                output.write( buffer, 0, readByte );
            }
            input.close();
            output.close();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean restoreByDate( String date ) throws Exception {
        File f = new File( ITEM_CSV_FILE_PATH + date + ".csv" );
        if( f.exists() && !f.isDirectory() ) {
            f.delete();
        }
        f = new File( ITEM_CSV_FILE_PATH + date + "_dao_backup.csv" );
        if( f.exists() && !f.isDirectory() ) {
            f.renameTo( new File( ITEM_CSV_FILE_PATH + date + ".csv" ) );
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean dropBackupByDate( String date ) throws Exception {
        File f = new File( ITEM_CSV_FILE_PATH + date + "_dao_backup.csv" );
        boolean returnCode = f.delete();
        
        return returnCode;
    }
    
    private boolean checkIfCsvFileExists( String fileName ) {
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
        File f = new File( ITEM_CSV_FILE_PATH );
        f.mkdirs();
        
        BufferedWriter bufWriter = new BufferedWriter( 
                new OutputStreamWriter( 
                    new FileOutputStream( new File( fileName ), false ), 
                    FILE_CHARSET 
                ) 
            );
        bufWriter.write( ITEM_CSV_FILE_ATTR_STRING );
        bufWriter.newLine();
        bufWriter.close();
    }
}

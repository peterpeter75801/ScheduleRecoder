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

import commonUtil.ComparingUtil;
import commonUtil.ItemUtil;
import domain.Item;
import repository.ItemDAO;

public class ItemDAOImpl implements ItemDAO {
    
    private final String ITEM_CSV_FILE_ATTR_STRING = "year,month,day,startHour,startMinute,endHour,endMinute,name,description";
    private final String ITEM_CSV_FILE_PATH = "data\\Item\\";
    private final String FILE_CHARSET = "big5";

    @Override
    public boolean insert( Item item ) throws IOException {
        String csvFilePath = ITEM_CSV_FILE_PATH + ItemUtil.getItemCsvFileNameFromItem( item );
        if( !checkIfCsvFileExists( csvFilePath ) ) {
            createCsvFile( csvFilePath );
        }
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream( new File( csvFilePath ), true ),
                    FILE_CHARSET
                )
            );
        writer.write( ItemUtil.getCsvTupleStringFromItem( item ) );
        writer.newLine();
        writer.close();
        
        return true;
    }

    @Override
    public Item findByTime( Integer year, Integer month, Integer day, 
            Integer startHour, Integer startMinute ) throws Exception {
        String csvFilePath = ITEM_CSV_FILE_PATH + 
                ItemUtil.getItemCsvFileNameFromItem( year, month, day );
        if( !checkIfCsvFileExists( csvFilePath ) ) {
            return null;
        }
        
        String currentTuple = "";
        Item currentItem = new Item();
        Item searchResultItem = null;
        BufferedReader bufReader = new BufferedReader( new InputStreamReader(
                new FileInputStream( new File( csvFilePath ) ),
                FILE_CHARSET
            )
        );
        // read attribute titles
        bufReader.readLine();
        // search data
        while( (currentTuple = bufReader.readLine()) != null ) {
            currentItem = ItemUtil.getItemFromCsvTupleString( currentTuple );
            if( ComparingUtil.compare( currentItem.getYear(), year ) == 0 &&
                    ComparingUtil.compare( currentItem.getMonth(), month ) == 0 &&
                    ComparingUtil.compare( currentItem.getDay(), day ) == 0 &&
                    ComparingUtil.compare( currentItem.getStartHour(), startHour ) == 0 &&
                    ComparingUtil.compare( currentItem.getStartMinute(), startMinute ) == 0 ) {
                searchResultItem = currentItem;
                break;
            }
        }
        bufReader.close();
        
        return searchResultItem;
    }

    @Override
    public List<Item> findByDate( Integer year, Integer month, Integer day ) throws Exception {
        String csvFilePath = ITEM_CSV_FILE_PATH + 
                ItemUtil.getItemCsvFileNameFromItem( year, month, day );
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
            currentItem = ItemUtil.getItemFromCsvTupleString( currentTuple );
            itemList.add( currentItem );
        }
        bufReader.close();
        
        return itemList;
    }

    @Override
    public boolean update( Item item ) throws Exception {
        String csvFilePath = ITEM_CSV_FILE_PATH + 
                ItemUtil.getItemCsvFileNameFromItem( item.getYear(), item.getMonth(), item.getDay() );
        if( !checkIfCsvFileExists( csvFilePath ) ) {
            return false;
        }
        
        ArrayList<String> fileContentBuffer = new ArrayList<String>();
        String currentTuple = "";
        Item currentItem = new Item();

        BufferedReader bufReader = new BufferedReader( new InputStreamReader(
                new FileInputStream( new File( csvFilePath ) ),
                FILE_CHARSET
            )
        );
        // read attribute titles
        fileContentBuffer.add( bufReader.readLine() );
        // search data & modify data
        while( (currentTuple = bufReader.readLine()) != null ) {
            currentItem = ItemUtil.getItemFromCsvTupleString( currentTuple );
            if( ComparingUtil.compare( currentItem.getYear(), item.getYear() ) == 0 &&
                    ComparingUtil.compare( currentItem.getMonth(), item.getMonth() ) == 0 &&
                    ComparingUtil.compare( currentItem.getDay(), item.getDay() ) == 0 &&
                    ComparingUtil.compare( currentItem.getStartHour(), item.getStartHour() ) == 0 &&
                    ComparingUtil.compare( currentItem.getStartMinute(), item.getStartMinute() ) == 0 ) {
                // modify data
                fileContentBuffer.add( ItemUtil.getCsvTupleStringFromItem( item ) );
            } else {
                // not modify data
                fileContentBuffer.add( currentTuple );
            }
        }
        bufReader.close();
        
        // write file content buffer to Item/yyyy.mm.dd.csv
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream( new File( csvFilePath ), false ),
                    FILE_CHARSET
                )
            );
        for( String content : fileContentBuffer ) {
            writer.write( content );
            writer.newLine();
        }
        writer.close();
 
        return true;
    }

    @Override
    public boolean delete( Item item ) throws Exception {
        String csvFilePath = ITEM_CSV_FILE_PATH + 
                ItemUtil.getItemCsvFileNameFromItem( item.getYear(), item.getMonth(), item.getDay() );
        if( !checkIfCsvFileExists( csvFilePath ) ) {
            return false;
        }
        
        ArrayList<String> fileContentBuffer = new ArrayList<String>();
        String currentTuple = "";
        Item currentItem = new Item();

        BufferedReader bufReader = new BufferedReader( new InputStreamReader(
                new FileInputStream( new File( csvFilePath ) ),
                FILE_CHARSET
            )
        );
        // read attribute titles
        fileContentBuffer.add( bufReader.readLine() );
        // search data & delete data
        while( (currentTuple = bufReader.readLine()) != null ) {
            currentItem = ItemUtil.getItemFromCsvTupleString( currentTuple );
            if( ComparingUtil.compare( currentItem.getYear(), item.getYear() ) != 0 ||
                    ComparingUtil.compare( currentItem.getMonth(), item.getMonth() ) != 0 ||
                    ComparingUtil.compare( currentItem.getDay(), item.getDay() ) != 0 ||
                    ComparingUtil.compare( currentItem.getStartHour(), item.getStartHour() ) != 0 ||
                    ComparingUtil.compare( currentItem.getStartMinute(), item.getStartMinute() ) != 0 ) {
                // keep data that not match the deleting data
                fileContentBuffer.add( currentTuple );
            }
        }
        bufReader.close();
        
        // write file content buffer to Item/yyyy.mm.dd.csv
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream( new File( csvFilePath ), false ),
                    FILE_CHARSET
                )
            );
        for( String content : fileContentBuffer ) {
            writer.write( content );
            writer.newLine();
        }
        writer.close();
 
        return true;
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

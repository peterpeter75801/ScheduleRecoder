package repository.Impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import common.Contants;
import commonUtil.ComparingUtil;
import commonUtil.ItemUtil;
import domain.Item;
import repository.ItemDAO;

public class ItemDAOImpl implements ItemDAO {
    
    private final String ITEM_CSV_FILE_ATTR_STRING = "year,month,day,startHour,startMinute,seq,endHour,endMinute,name,description";
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
    public Item findOne( Integer year, Integer month, Integer day, 
            Integer startHour, Integer startMinute, Integer seq ) throws Exception {
        String csvFilePath = ITEM_CSV_FILE_PATH + 
                ItemUtil.getItemCsvFileNameFromItem( year, month, day );
        if( !checkIfCsvFileExists( csvFilePath ) ) {
            return null;
        }
        
        String currentTuple = "";
        Item currentItem = new Item();
        Item searchResultItem = null;
        try {
            BufferedReader bufReader = new BufferedReader( new InputStreamReader(
                    new FileInputStream( new File( csvFilePath ) ),
                    FILE_CHARSET
                ));
            // read attribute titles
            bufReader.readLine();
            // search data
            while( (currentTuple = bufReader.readLine()) != null ) {
                currentItem = ItemUtil.getItemFromCsvTupleString( currentTuple );
                if( ComparingUtil.compare( currentItem.getYear(), year ) == 0 &&
                        ComparingUtil.compare( currentItem.getMonth(), month ) == 0 &&
                        ComparingUtil.compare( currentItem.getDay(), day ) == 0 &&
                        ComparingUtil.compare( currentItem.getStartHour(), startHour ) == 0 &&
                        ComparingUtil.compare( currentItem.getStartMinute(), startMinute ) == 0 &&
                        ComparingUtil.compare( currentItem.getSeq(), seq ) == 0 ) {
                    searchResultItem = currentItem;
                    break;
                }
            }
            bufReader.close();
        } catch( FileNotFoundException e ) {
            return null;
        }
        
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
                    ComparingUtil.compare( currentItem.getStartMinute(), item.getStartMinute() ) == 0 &&
                    ComparingUtil.compare( currentItem.getSeq(), item.getSeq() ) == 0 ) {
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
                    ComparingUtil.compare( currentItem.getStartMinute(), item.getStartMinute() ) != 0 ||
                    ComparingUtil.compare( currentItem.getSeq(), item.getSeq() ) != 0 ) {
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

    @Override
    public boolean deleteByDate( Integer year, Integer month, Integer day ) throws Exception {
        File f = new File( ITEM_CSV_FILE_PATH + 
            String.format( "%04d.%02d.%02d", year, month, day ) + 
            ".csv" );
        boolean returnCode = f.delete();
        
        return returnCode;
    }

    @Override
    public boolean sortByStartTimeInDateGroup( 
            Integer year, Integer month, Integer day ) throws Exception {
        String csvFilePath = ITEM_CSV_FILE_PATH + 
                ItemUtil.getItemCsvFileNameFromItem( year, month, day );
        ArrayList<Item> itemList = new ArrayList<Item>();
        
        if( !checkIfCsvFileExists( csvFilePath ) ) {
            return false;
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
        
        // sort item list by seq
        for( int i = 1; i <= itemList.size(); i++ ) {
            for( int j = 0; j < itemList.size() - i; j++ ) {
                if( itemList.get( j ).getSeq() > itemList.get( j + 1 ).getSeq() ) {
                    Item swap = itemList.get( j );
                    itemList.set( j, itemList.get( j + 1 ) );
                    itemList.set( j + 1, swap );
                }
            }
        }
        
        // sort item list by time
        for( int i = 1; i <= itemList.size(); i++ ) {
            for( int j = 0; j < itemList.size() - i; j++ ) {
                if( ( itemList.get( j ).getStartHour() * 60 + itemList.get( j ).getStartMinute() ) > 
                        itemList.get( j + 1 ).getStartHour() * 60 + itemList.get( j + 1 ).getStartMinute() ) {
                    Item swap = itemList.get( j );
                    itemList.set( j, itemList.get( j + 1 ) );
                    itemList.set( j + 1, swap );
                }
            }
        }
        
        // write item list to Item/yyyy.mm.dd.csv
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream( new File( csvFilePath ), false ),
                    FILE_CHARSET
                )
            );
        writer.write( ITEM_CSV_FILE_ATTR_STRING );
        writer.newLine();
        for( Item item : itemList ) {
            writer.write( ItemUtil.getCsvTupleStringFromItem( item ) );
            writer.newLine();
        }
        writer.close();
        
        return true;
    }

    @Override
    public List<String> listAllDateContainingData() throws Exception {
        final int STANDARD_ITEM_FILE_NAME_LENGTH = 14;
        
        File f = new File( ITEM_CSV_FILE_PATH );
        String[] fileNameList = f.list();
        List<String> allDateList = new ArrayList<String>();
        
        if( fileNameList == null || fileNameList.length <= 0 ) {
            return allDateList;
        }
        
        for( int i = 0; i < fileNameList.length; i++ ) {
            if( fileNameList[ i ] != null &&
                    fileNameList[ i ].length() == STANDARD_ITEM_FILE_NAME_LENGTH &&
                    Character.isDigit( fileNameList[ i ].charAt( 0 ) ) && 
                    Character.isDigit( fileNameList[ i ].charAt( 1 ) ) && 
                    Character.isDigit( fileNameList[ i ].charAt( 2 ) ) && 
                    Character.isDigit( fileNameList[ i ].charAt( 3 ) ) && 
                    fileNameList[ i ].charAt( 4 ) == '.' && 
                    Character.isDigit( fileNameList[ i ].charAt( 5 ) ) && 
                    Character.isDigit( fileNameList[ i ].charAt( 6 ) ) && 
                    fileNameList[ i ].charAt( 7 ) == '.' && 
                    Character.isDigit( fileNameList[ i ].charAt( 8 ) ) && 
                    Character.isDigit( fileNameList[ i ].charAt( 9 ) ) && 
                    fileNameList[ i ].charAt( 10 ) == '.' && 
                    Character.toLowerCase( fileNameList[ i ].charAt( 11 ) ) == 'c' && 
                    Character.toLowerCase( fileNameList[ i ].charAt( 12 ) ) == 's' && 
                    Character.toLowerCase( fileNameList[ i ].charAt( 13 ) ) == 'v' ) {
                allDateList.add( fileNameList[ i ].substring( 0, 10 ) );
            }
        }
        
        return allDateList;
    }

    @Override
    public int checkItemDataVersion( Integer year, Integer month, Integer day ) throws Exception {
        String csvFilePath = ITEM_CSV_FILE_PATH + 
                ItemUtil.getItemCsvFileNameFromItem( year, month, day );
        int returnCode = 0;
        
        if( !checkIfCsvFileExists( csvFilePath ) ) {
            return Contants.SUCCESS;
        }
        
        BufferedReader bufReader = new BufferedReader( new InputStreamReader( 
                new FileInputStream( new File( csvFilePath ) ),
                FILE_CHARSET 
            ) 
        );
        
        // read attribute titles
        String attributeTitle = bufReader.readLine();
        if( attributeTitle == null ) {
            returnCode = Contants.ERROR_EMPTY_FILE;
        } else if( !attributeTitle.equals( ITEM_CSV_FILE_ATTR_STRING ) ) {
            returnCode = Contants.ERROR_VERSION_OUT_OF_DATE;
        } else {
            returnCode = Contants.SUCCESS;
        }
        
        bufReader.close();
        
        return returnCode;
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

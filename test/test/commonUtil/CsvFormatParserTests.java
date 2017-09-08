package test.commonUtil;

import commonUtil.CsvFormatParser;

import junit.framework.TestCase;

public class CsvFormatParserTests extends TestCase {

    public void testParseFromTuple() {
        String input = "123,test,\"hello world\",\"double-quote\"\"\",,\",comma\"";
        String[] output = CsvFormatParser.parseFromTuple( input );
        assertEquals( 6, output.length );
        try {
            assertEquals( "123", output[ 0 ] );
            assertEquals( "test", output[ 1 ] );
            assertEquals( "hello world", output[ 2 ] );
            assertEquals( "double-quote\"", output[ 3 ] );
            assertEquals( "", output[ 4 ] );
            assertEquals( ",comma", output[ 5 ] );
        } catch( ArrayIndexOutOfBoundsException e ) {
            assertTrue( e.getMessage(), false );
        }
    }

    public void testToCsvDataString() {
        String input1 = "test";
        String input2 = "test, test";
        String input3 = "test\"123\"";
        String output1 = CsvFormatParser.toCsvData( input1 );
        String output2 = CsvFormatParser.toCsvData( input2 );
        String output3 = CsvFormatParser.toCsvData( input3 );
        assertEquals( "\"test\"", output1 );
        assertEquals( "\"test, test\"", output2 );
        assertEquals( "\"test\"\"123\"\"\"", output3 );
    }
    
    public void testMergeCsvDataToATuple() {
        String[] input = { "\"test\"", "123", "C", "", "\"test\"\"123\"\"\"" };
        String output = CsvFormatParser.mergeCsvDataToATuple( input );
        assertEquals( "\"test\",123,C,,\"test\"\"123\"\"\"", output );
    }
    
    public void testCheckSpecialCharacter() {
        String input1 = "123test";
        String input2 = "123" + System.lineSeparator() + "test";
        String input3 = "123&<test>";
        assertFalse( CsvFormatParser.checkSpecialCharacter( input1 ) );
        assertTrue( CsvFormatParser.checkSpecialCharacter( input2 ) );
        assertTrue( CsvFormatParser.checkSpecialCharacter( input3 ) );
    }
    
    public void testSpecialCharacterToHtmlFormat() {
        String input1 = "123test";
        String input2 = "123" + System.lineSeparator() + "test";
        String input3 = "123&<test>";
        String input4 = "123" + System.lineSeparator() + "<test>";
        String input5 = "123" + System.lineSeparator() + "<<test>>&&";
        String input6 = "123\n<<test>>&&";
        String expect1 = "123test";
        String expect2 = "123<br />test";
        String expect3 = "123&amp;&lt;test&gt;";
        String expect4 = "123<br />&lt;test&gt;";
        String expect5 = "123<br />&lt;&lt;test&gt;&gt;&amp;&amp;";
        String expect6 = "123<br />&lt;&lt;test&gt;&gt;&amp;&amp;";
        assertEquals( expect1, CsvFormatParser.specialCharacterToHtmlFormat( input1 ) );
        assertEquals( expect2, CsvFormatParser.specialCharacterToHtmlFormat( input2 ) );
        assertEquals( expect3, CsvFormatParser.specialCharacterToHtmlFormat( input3 ) );
        assertEquals( expect4, CsvFormatParser.specialCharacterToHtmlFormat( input4 ) );
        assertEquals( expect5, CsvFormatParser.specialCharacterToHtmlFormat( input5 ) );
        assertEquals( expect6, CsvFormatParser.specialCharacterToHtmlFormat( input6 ) );
    }
    
    public void testRestoreCharacterFromHtmlFormat() {
        String input1 = "123test";
        String input2 = "123<br />test";
        String input3 = "123&amp;&lt;test&gt;";
        String input4 = "123<br />&lt;test&gt;";
        String input5 = "123<br />&lt;&lt;test&gt;&gt;&amp;&amp;";
        String expect1 = "123test";
        String expect2 = "123" + System.lineSeparator() + "test";
        String expect3 = "123&<test>";
        String expect4 = "123" + System.lineSeparator() + "<test>";
        String expect5 = "123" + System.lineSeparator() + "<<test>>&&";
        assertEquals( expect1, CsvFormatParser.restoreCharacterFromHtmlFormat( input1 ) );
        assertEquals( expect2, CsvFormatParser.restoreCharacterFromHtmlFormat( input2 ) );
        assertEquals( expect3, CsvFormatParser.restoreCharacterFromHtmlFormat( input3 ) );
        assertEquals( expect4, CsvFormatParser.restoreCharacterFromHtmlFormat( input4 ) );
        assertEquals( expect5, CsvFormatParser.restoreCharacterFromHtmlFormat( input5 ) );
    }
}

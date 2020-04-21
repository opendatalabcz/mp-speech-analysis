package test.helper;

import org.junit.Test;

import static helper.StringHelper.getLastNWordsInString;

public class StringHelperTest {
    @Test
    public void getLastNWordsInStringTest() {
        getLastNWordsInString("Místopředseda PSP Lubomír Zaorálek: ", 1);
    }
}

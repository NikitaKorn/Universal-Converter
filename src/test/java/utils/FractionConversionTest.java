package utils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FractionConversionTest {

    @org.junit.jupiter.api.Test
    void simpleConversion() {
        try {
            SimpleAbbreviations.setFileNameAndInit("kontur.csv");
        } catch (IOException e){
            e.printStackTrace();
        }
        FractionConversion fc = new FractionConversion();
        FractionConversion.Response response = fc.conversion("м", "км");
        assertEquals( 0.001D, response.getValue());
        assertEquals(200, response.getResponseCode());
    }

    @org.junit.jupiter.api.Test
    void simpleCompositeConversion() {
        try {
            SimpleAbbreviations.setFileNameAndInit("kontur.csv");
        } catch (IOException e){
            e.printStackTrace();
        }
        FractionConversion fc = new FractionConversion();
        FractionConversion.Response response = fc.conversion("м/с", "км/час");
        assertEquals( 3.6D, response.getValue());
        assertEquals(200, response.getResponseCode());
    }

    @org.junit.jupiter.api.Test
    void conversionWithCoefficient1() {
        try {
            SimpleAbbreviations.setFileNameAndInit("kontur.csv");
        } catch (IOException e){
            e.printStackTrace();
        }
        FractionConversion fc = new FractionConversion();
        FractionConversion.Response response = fc.conversion("1/с", "1/час");
        assertEquals( 3600D, response.getValue());
        assertEquals(200, response.getResponseCode());
    }

    @org.junit.jupiter.api.Test
    void conversionWithCoefficient2() {
        try {
            SimpleAbbreviations.setFileNameAndInit("kontur.csv");
        } catch (IOException e){
            e.printStackTrace();
        }
        FractionConversion fc = new FractionConversion();
        FractionConversion.Response response = fc.conversion("1/с", "2/час");
        assertEquals( 1800D, response.getValue());
        assertEquals(200, response.getResponseCode());
    }

    @org.junit.jupiter.api.Test
    void conversion1() {
        try {
            SimpleAbbreviations.setFileNameAndInit("kontur.csv");
        } catch (IOException e){
            e.printStackTrace();
        }
        FractionConversion fc = new FractionConversion();
        FractionConversion.Response response = fc.conversion("м", "км*с/час");
        assertEquals( 3.6D, response.getValue());
        assertEquals(200, response.getResponseCode());
    }

    @org.junit.jupiter.api.Test
    void conversionWithEmptyString() {
        try {
            SimpleAbbreviations.setFileNameAndInit("kontur.csv");
        } catch (IOException e){
            e.printStackTrace();
        }
        FractionConversion fc = new FractionConversion();
        FractionConversion.Response response = fc.conversion("км/м", "");
        assertEquals( 1000D, response.getValue());
        assertEquals(200, response.getResponseCode());
    }

    @org.junit.jupiter.api.Test
    void incorrectConversion1() {
        try {
            SimpleAbbreviations.setFileNameAndInit("kontur.csv");
        } catch (IOException e){
            e.printStackTrace();
        }
        FractionConversion fc = new FractionConversion();
        FractionConversion.Response response = fc.conversion("м", "км*с/м*час");
        assertEquals( 0D, response.getValue());
        assertEquals(404, response.getResponseCode());
    }

    @org.junit.jupiter.api.Test
    void incorrectConversion2() {
        try {
            SimpleAbbreviations.setFileNameAndInit("kontur.csv");
        } catch (IOException e){
            e.printStackTrace();
        }
        FractionConversion fc = new FractionConversion();
        FractionConversion.Response response = fc.conversion("утки", "км*с/м*час");
        assertEquals( 0D, response.getValue());
        assertEquals(400, response.getResponseCode());
    }
}
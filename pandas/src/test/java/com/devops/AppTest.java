package com.devops;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void sayHelloTest() {
        App app = new App();
        assertEquals("Test de verification de fonctionnement", "Hello world!", app.sayHello());
    }
}

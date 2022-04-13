package com.devops;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class DataFrameTest {
    
    @Test
    public void CreateDataFrameFromData(){
        try {
            ArrayList<String> labels = new ArrayList<String>();
            labels.add("col1");
            labels.add("col2");
            labels.add("col3");
            ArrayList<ArrayList<?>> data = new ArrayList<ArrayList<?>>();
            ArrayList<Integer> colonne1 = new ArrayList<Integer>();
            colonne1.add(1);
            colonne1.add(2);
            colonne1.add(3);
            data.add(colonne1);
            ArrayList<Integer> colonne2 = new ArrayList<Integer>();
            colonne2.add(4);
            colonne2.add(5);
            colonne2.add(6);
            data.add(colonne2);
            ArrayList<Integer> colonne3 = new ArrayList<Integer>();
            colonne3.add(7);
            colonne3.add(8);
            colonne3.add(9);
            data.add(colonne3);
            DataFrame df = new DataFrame(labels, data);
            assertEquals("Obtenir le nombre de ligne",3, df.nbLigne());
            assertEquals("Obtenir le nombre de colonne",3 , df.nbColonne());
            assertEquals("Obtenir la premier colonne", colonne1, df.getColumn("col1"));
            assertEquals("Obtenir la deuxieme colonne", colonne2, df.getColumn("col2"));
            assertEquals("Obtenir la troisieme colonne", colonne3, df.getColumn("col3"));
            System.out.println(df.getColumn("col1").get(0).getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void CreateDataFrameFromCSV(){
        try {
            DataFrame df = new DataFrame("src/test/resources/dataframe.csv");
            assertEquals("Obtenir le nombre de ligne",3, df.nbLigne());
            assertEquals("Obtenir le nombre de colonne",3 , df.nbColonne());
            assertEquals("Obtenir la premier colonne", "1", df.getColumn("col1").get(0));
            assertEquals("Obtenir la deuxieme colonne", "2", df.getColumn("col1").get(1));
            assertEquals("Obtenir la troisieme colonne", "3", df.getColumn("col1").get(2));
            assertEquals("Obtenir la premier colonne", "4", df.getColumn("col2").get(0));
            assertEquals("Obtenir la deuxieme colonne", "5", df.getColumn("col2").get(1));
            assertEquals("Obtenir la troisieme colonne", "6", df.getColumn("col2").get(2));
            assertEquals("Obtenir la premier colonne", "7", df.getColumn("col3").get(0));
            assertEquals("Obtenir la deuxieme colonne", "8", df.getColumn("col3").get(1));
            assertEquals("Obtenir la troisieme colonne", "9", df.getColumn("col3").get(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

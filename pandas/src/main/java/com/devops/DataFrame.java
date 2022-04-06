package com.devops;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.text.TableView.TableRow;

public class DataFrame {
    ArrayList<ArrayList<Object>> dataframe;
    ArrayList<String> labels;



    /**
     * 
     * Génére un DataFrame en prenant une table de String et une table de données
     * 
     * @param label : tableau de String contenant les labels
     * @param table : tableau de tableau contenant les données
     * @throws Exception
     */
    public DataFrame(ArrayList<String> label, ArrayList<Object>[] tableau) throws Exception{
        if (label.size() != tableau.length) 
            throw new Exception("Nombre de label incompatible avec le nombre de colonne");
        labels = new ArrayList<String>(label);

        dataframe = new ArrayList<ArrayList<Object>>();
        // Remplir dataFrame en conservant les types d'entrée
        for (int i = 0; i < tableau.length; i++) {
            Class a = tableau[i].get(0).getClass();

            Type type = a.getGenericSuperclass();
            ArrayList<type> ligne = new ArrayList<Object>(tableau[i]);
            dataframe.add(ligne);
        }//*/

        // Autre méthode
        for (int i = 0; i < tableau.length; i++) {
            if (tableau[i] instanceof ArrayList) {
                if (tableau[i].get(0) instanceof String)
                    dataframe.add(
                        new ArrayList<String>( (ArrayList<String>) tableau[i]) );
            } else {
                throw new Exception("Type des données de la colonne " + i + " incorrect");
            }
        }//*/
    }

    /**
     * 
     * Génére un DataFrame en utilisant un fichier CSV contenant sur la première ligne les labels
     * des colonnes et les données sur les lignes suivantes
     * 
     * @param fileName : nom du fichier CSV contenant les données à utiliser pour générer le DataFrame
     */
    public DataFrame(String fileName){
        try{
            labels = new ArrayList<String>();
            dataframe = new ArrayList<ArrayList<>>();
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            String[] line = scanner.nextLine().split(",");
            for (int i = 0; i < line.length; i++) {
                labels.add(line[i]);
            }
            
            /*
            line = scanner.nextLine().split(",");
            try {
                Integer.parseInt(line[0]);
            } catch (NumberFormatException e) {
                // Ben c'est pas un int
            }//*/
            Pattern numbers = Pattern.compile("[^\p{alpha}]\d+[^\p{alpha}]")
            for (int i = 0; i < line.length; i++){

            }

            do{
                for(int i = 0; i < line.length; i++){
                    dataframe.get(i).add(line[i]);
                }
                line = scanner.nextLine().split(",");}
            while(scanner.hasNextLine());
            scanner.close();
        }   
        catch(Exception e){
            e.printStackTrace();
        }
    }

    
    
}

package com.devops;

import java.io.File;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.TableView.TableRow;

public class DataFrame {
    ArrayList<String> labels;
    ArrayList<ArrayList<?>> dataframe;
    Integer nbLigne = 0;



    /**
     * 
     * Génére un DataFrame en prenant une table de String et une table de données
     * 
     * @param label : tableau de String contenant les labels
     * @param table : tableau de tableau contenant les données
     * @throws Exception
     */
    public DataFrame(ArrayList<String> label, ArrayList<ArrayList<?>> tableau) throws Exception{
        if (label.size() != tableau.size()) 
            throw new Exception("Nombre de label incompatible avec le nombre de colonne du tableau");
        labels = new ArrayList<String>(label);

        dataframe = new ArrayList<ArrayList<?>>();
        // Remplir dataFrame en conservant les types d'entrée
        for (int i = 0; i < tableau.size(); i++) {
            ArrayList<?> ligne = (ArrayList<?>) tableau.get(i).clone();
            if (ligne.size() == 1 || ligne.size() == nbLigne || nbLigne == 0) {
                dataframe.add(ligne);
                if (nbLigne != 1){
                    nbLigne = ligne.size();
                }
            } else {
                throw new Exception("Nombre de ligne incoherent (non constant et different de 1)");
            }
        }
    }


	public int nbLigne() {
        return dataframe.get(0).size();
    }

	public int nbColonne() {
        return labels.size();
    }

	public ArrayList<?> getColumn(String string) {
        return dataframe.get(labels.indexOf(string));
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < labels.size(); i++) {
            s += labels.get(i) + "\t";
        }
        s += "\n";
        for (int i = 0; i <nbLigne; i++) {
            for (int j = 0; j < dataframe.size(); j++) {
                s += dataframe.get(j).get(i) + "\t";
            }
            s += "\n";
        }
        return s;
    }
}

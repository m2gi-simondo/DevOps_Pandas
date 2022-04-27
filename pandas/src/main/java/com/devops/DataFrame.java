package com.devops;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataFrame {
    ArrayList<String> labels;
    ArrayList<ArrayList<?>> dataframe;
    private Integer nbLigne = 0;



    /**
     * 
     * Génére un DataFrame en prenant une table de String et une table de données
     * 
     * @param label : tableau de String contenant les labels
     * @param table : tableau de tableau contenant les données
     * @throws Exception
     */
    public DataFrame(ArrayList<String> label, ArrayList<ArrayList<?>> tableau) throws Exception {
        if (label.size() != tableau.size())
            throw new Exception("Nombre de label (" + label.size() + ") incompatible avec le nombre de colonne du tableau ( " + tableau.size() + ")");
        labels = new ArrayList<String>(label);

        dataframe = new ArrayList<ArrayList<?>>();
        // Remplir dataFrame en conservant les types d'entrée
        for (int i = 0; i < tableau.size(); i++) {
            ArrayList<?> ligne = (ArrayList<?>) tableau.get(i).clone();
            if (ligne.size() == 1 || ligne.size() == nbLigne || nbLigne == 0) {
                dataframe.add(ligne);
                if (nbLigne != 1) {
                    nbLigne = ligne.size();
                }
            } else {
                throw new Exception("Nombre de ligne incoherent (non constant et different de 1)");
            }
        }
    }

    /**
     * 
     * Génére un DataFrame en utilisant un fichier CSV contenant sur la première
     * ligne les labels
     * des colonnes et les données sur les lignes suivantes
     * 
     * @param fileName : nom du fichier CSV contenant les données à utiliser pour
     *                 générer le DataFrame
     */
    public DataFrame(String fileName) {
        Scanner scanner = null;
        try {
            labels = new ArrayList<String>();
            ArrayList<ArrayList<multiType>> tmpDataframe = new ArrayList<ArrayList<multiType>>();
            int nbLabel = 0;
            File file = new File(fileName);
            scanner = new Scanner(file);
            String[] line = scanner.nextLine().split(",");
            nbLabel = line.length;
            for (String label : line) {
                labels.add(label.trim());
                tmpDataframe.add(new ArrayList<multiType>());
            }

            multiType newInput;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine().split(",");
                nbLigne++;
                if (line.length != nbLabel) {
                    throw new Exception("Une ligne ne contient pas le bon nombre d'élément");
                }
                for (int i = 0; i < line.length; i++) {
                    newInput = new multiType(line[i]);
                    tmpDataframe.get(i).add(newInput);
                }
            }
            dataframe = new ArrayList<ArrayList<?>>();
            //on a un tableau temporaire complet, il faut maintenant le convertir pour avoir les bons types.
            for(int i = 0; i < tmpDataframe.size(); i++){
                ArrayList<multiType> colonneCourante = tmpDataframe.get(i);
                switch(colonneCourante.get(0).getMultiType()){
                    case 0:
                        ArrayList<String> tmpStrColonne = new ArrayList<String>();
                        for(int j = 0; j < colonneCourante.size(); j++){
                            multiType item = colonneCourante.get(j);
                            if(item.getMultiType() != 0){
                                throw new Exception("Le type d'un élément de la colonne n'est pas le même que les autres");
                            }
                            tmpStrColonne.add(item.getStr());
                        }
                        dataframe.add(tmpStrColonne);
                        break;
                    case 1:
                        ArrayList<Integer> tmpIntColonne = new ArrayList<Integer>();
                        for(int j = 0; j < colonneCourante.size(); j++){
                            multiType item = colonneCourante.get(j);
                            if(item.getMultiType() != 1){
                                throw new Exception("Le type d'un élément de la colonne n'est pas le même que les autres");
                            }
                            tmpIntColonne.add(item.getInt());
                        }
                        dataframe.add(tmpIntColonne);
                        break;
                    case 2:
                        ArrayList<Float> tmpFloatColonne = new ArrayList<Float>();
                        for(int j = 0; j < colonneCourante.size(); j++){
                            multiType item = colonneCourante.get(j);
                            if(item.getMultiType() != 2){
                                throw new Exception("Le type d'un élément de la colonne n'est pas le même que les autres");
                            }
                            tmpFloatColonne.add(item.getFloat());
                        }
                        dataframe.add(tmpFloatColonne);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if (scanner != null){
                scanner.close();
            }
        }
    }

    /**
     * Crée un DataFrame à partir d'une sélection de colonnes
     * @param labelSelec : tableau des labels des colonnes à sélectionner
     * @return : nouveau DataFrame
     */
    public DataFrame labelSelection(String[] labelsSelec) {
        //TODO gérer les erreurs
        ArrayList<String> newLabels = new ArrayList<String>();
        ArrayList<ArrayList<?>> newDataframe = new ArrayList<ArrayList<?>>();
        for (String iterable_element : labelsSelec) {
            int index = labels.indexOf(iterable_element);
            newLabels.add(iterable_element);
            newDataframe.add(dataframe.get(index));
        }
        try {
            return new DataFrame(newLabels, newDataframe);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Crée un DataFrame à partir d'une colonne
     * 
     * @param index : labels de la colonne à prendre
     * @return : nouveau DataFrame
     */
    public DataFrame labelSelection(String labelSelec) {
        //TODO gérer les erreurs
        ArrayList<String> newLabels = new ArrayList<String>();
        newLabels.add(labelSelec);
        ArrayList<ArrayList<?>> newDataframe = new ArrayList<ArrayList<?>>();
        newDataframe.add(dataframe.get(labels.indexOf(labelSelec)));
        try {
            return new DataFrame(newLabels, newDataframe);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * Crée un DataFrame à partir d'une sélection de lignes
     * @param index : tableau des indices des lignes à prendre
     * @return : nouveau DataFrame
     */
    public DataFrame ligneIndexSelection(Integer[] ligneSelec) {
        //TODO gérer les erreurs
        ArrayList<String> newLabels = (ArrayList<String>) labels.clone();
        ArrayList<ArrayList<?>> newDataframe = new ArrayList<ArrayList<?>>();
        for (int i = 0; i < labels.size(); i++) {
            ArrayList<?> ligne;
            switch (dataframe.get(i).get(0).getClass().getSimpleName()) {
                case "Integer":
                    ArrayList<Integer> ligneInt = new ArrayList<Integer>();
                    for (Integer index : ligneSelec) {
                        ligneInt.add((Integer) dataframe.get(i).get(index-1));
                    }
                    ligne = (ArrayList<?>) ligneInt.clone();
                    break;
                case "String":
                    ArrayList<String> ligneStr = new ArrayList<String>();
                    for (Integer index : ligneSelec) {
                        ligneStr.add((String) dataframe.get(i).get(index-1));
                    }
                    ligne = (ArrayList<?>) ligneStr.clone();
                    break;
                case "Float":
                    ArrayList<Float> ligneFloat = new ArrayList<Float>();
                    for (Integer index : ligneSelec) {
                        ligneFloat.add((Float) dataframe.get(i).get(index-1));
                    }
                    ligne = (ArrayList<?>) ligneFloat.clone();
                    break;
                default:
                // TODO trouver une méthode pour être généraliste
                    ArrayList<Integer> ligneS = new ArrayList<Integer>();
                    ligneS.add(0);
                    ligne = (ArrayList<?>) ligneS.clone();
                    break;
            }
            newDataframe.add(ligne);
        }
        try {
            return new DataFrame(newLabels, newDataframe);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * Crée un DataFrame à partir d'une sélection de lignes
     * @param index : tableau des indices de la ligne à prendre
     * @return : nouveau DataFrame
     */
    public DataFrame ligneIndexSelection(Integer index){
        //TODO gérer les erreurs
        ArrayList<String> newLabels = (ArrayList<String>) labels.clone();
        ArrayList<ArrayList<?>> newDataframe = new ArrayList<ArrayList<?>>();
        for (int i = 0; i < labels.size(); i++) {
            ArrayList<?> ligne;
            switch (dataframe.get(i).get(0).getClass().getSimpleName()) {
                case "Integer":
                    ArrayList<Integer> ligneInt = new ArrayList<Integer>();
                    ligneInt.add((Integer) dataframe.get(i).get(index - 1));
                    ligne = (ArrayList<?>) ligneInt.clone();
                    break;
                case "String":
                    ArrayList<String> ligneStr = new ArrayList<String>();
                    ligneStr.add((String) dataframe.get(i).get(index - 1));
                    ligne = (ArrayList<?>) ligneStr.clone();
                    break;
                case "Float":
                    ArrayList<Float> ligneFloat = new ArrayList<Float>();
                    ligneFloat.add((Float) dataframe.get(i).get(index - 1));
                    ligne = (ArrayList<?>) ligneFloat.clone();
                    break;
                default:
                // TODO trouver une méthode pour être généraliste
                    ArrayList<Integer> ligneS = new ArrayList<Integer>();
                    ligneS.add(0);
                    ligne = (ArrayList<?>) ligneS.clone();
                    break;
            }
            newDataframe.add(ligne);
        }
        try {
            return new DataFrame(newLabels, newDataframe);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    
    /**
    * Fonction qui retourne le nombre de ligne du DataFrame
    * @return le nombre de ligne du DataFrame
    */
	public int nbLigne() {
        return dataframe.get(0).size();
    }

    /**
     * Fonction qui retourne le nombre de colonne du DataFrame
     * 
     * @return le nombre de colonne du DataFrame
     */
    public int nbColonne() {
        return labels.size();
    }

    /**
     * Fonction qui retourne le nom de la colonne à l'indice i
     * 
     * @param i : indice de la colonne
     * @exception IllegalArgumentException si i est hors de la plage de valeurs
     * @return le nom de la colonne à l'indice i
     */
    public ArrayList<?> getColumn(String string) throws IllegalArgumentException {
        int i = labels.indexOf(string);
        if (i == -1) {
            throw new IllegalArgumentException("La colonne n'existe pas");
        }
        return dataframe.get(labels.indexOf(string));
    }

    /**
     * Fonction qui retourne les 10 premières lignes du DataFrame
     * 
     * @return les 10 premières lignes du DataFrame
     */
    public String head() {
        return head(10);
    }

    /**
     * Fonction qui retourne les n premières lignes du DataFrame
     * 
     * @param n : nombre de ligne à retourner
     * @return les n premières lignes du DataFrame
     */
    public String head(int nbl) {
        String s = "";
        for (int i = 0; i < labels.size(); i++) {
            s += labels.get(i) + "\t";
        }
        s += "\n";
        int mini = nbLigne < nbl ? nbLigne : nbl;
        for (int i = 0; i < mini; i++) {
            for (int j = 0; j < dataframe.size(); j++) {
                try {
                    s += dataframe.get(j).get(i) + "\t";
                } catch (Exception e) {
                    s += dataframe.get(j).get(0) + "\t";
                }
            }
            s += "\n";
        }
        return s;
    }

    /**
     * Fonction qui retourne les lignes du DataFrame
     * 
     * @return les lignes composant le DataFrame
     */
    public String toString() {
        return head(Integer.MAX_VALUE);
    }

    /**
     * Fonction qui retourne la moyenne d'une colonne d'un dataframe
    * @param string : nom de la colonne
    * @exception IllegalArgumentException si la colonne n'existe pas
    * @exception IllegalArgumentException si la colonne n'est pas un nombre
    * @return la moyenne d'une colonne d'un dataframe
    */
    public int moyenne(String string) throws IllegalArgumentException {
        int i = labels.indexOf(string);
        if (i == -1) {
            throw new IllegalArgumentException("La colonne n'existe pas");
        }
        
        if (!(dataframe.get(i).get(0) instanceof Integer || dataframe.get(i).get(0) instanceof Float) || dataframe.get(i).get(0) instanceof Double) {
            throw new IllegalArgumentException("La colonne n'est pas de type numérique");
        } 

        int sum = 0;
        int nb = 0;
        for (int j = 0; j < dataframe.get(i).size(); j++) {
            sum += (int) dataframe.get(i).get(j);
            nb++;
        }
        return sum / nb;
    }
    /**
    * Fonction qui retourne la moyenne d'une colonne d'un dataframe
    * @param string : nom de la colonne
    * @exception IllegalArgumentException si la colonne n'existe pas
    * @exception IllegalArgumentException si la colonne n'est pas de type numérique
    * @return la moyenne d'une colonne d'un dataframe
    */
    public int max(String string) throws IllegalArgumentException {
        int i = labels.indexOf(string);
        if (i == -1) {
            throw new IllegalArgumentException("La colonne n'existe pas");
        }
        
        if (!(dataframe.get(i).get(0) instanceof Integer || dataframe.get(i).get(0) instanceof Float) || dataframe.get(i).get(0) instanceof Double) {
            throw new IllegalArgumentException("La colonne n'est pas de type numérique");
        }

        int max = (int) dataframe.get(i).get(0);
        for (int j = 0; j < dataframe.get(i).size(); j++) {
            if ((int) dataframe.get(i).get(j) > max) {
                max = (int) dataframe.get(i).get(j);
            }
        }
        return max;
    }
    
    static public class multiType {
        private int type;
        private String strValue;
        private int intValue;
        private float floatValue;

        public multiType(String input) throws ParseException {
            input = input.trim();
            type = getType(input);
            switch (type) {
                case 0:
                    strValue = input;
                    break;
                case 1:
                    intValue = Integer.parseInt(input);
                    break;
                case 2:
                    floatValue = Float.parseFloat(input);
                    break;
                default:
                    break;
            }
        }

        private int getType(String input) {
            String intRegex = "\\d+";
            String floatRegex = "[+-]?(\\d+([.]\\d*)|[.]\\d+)";
            Pattern intPattern = Pattern.compile(intRegex);
            Pattern floatPattern = Pattern.compile(floatRegex);
            Matcher intMatcher = intPattern.matcher(input);
            Matcher floatMatcher = floatPattern.matcher(input);
            if (floatMatcher.matches()) {
                return 2;
            } else {
                if (intMatcher.matches()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }

        public int getMultiType() {
            return type;
        }

        public String getStr() {
            return strValue;
        }

        public int getInt() {
            return intValue;
        }

        public float getFloat() {
            return floatValue;
        }
    }
}
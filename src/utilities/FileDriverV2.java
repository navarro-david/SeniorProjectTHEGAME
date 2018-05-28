/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author David
 */
public class FileDriverV2 {

    String fileName;

    public FileDriverV2(String name) {
        fileName = name;
    }

    public void changeFile(String name) {
        fileName = name;
    }

    public String[] readFile() {

        String line = null;
        ArrayList<String> lines = new ArrayList<String>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            bufferedReader.close();

        } catch (Exception e) {
            System.out.println(e + "\n\tRead File Method");

        }

        return lines.toArray(new String[lines.size()]);
    }

    public String[][] splitString(String[] list) {
        ArrayList<String[]> matrix = new ArrayList<String[]>();
        //String[][] test = null;
        //test = new String[4][];

        try {

            for (String s : list) {
                matrix.add(s.split(":"));
            }


        } catch (Exception e) {
            System.out.println(e + "\n\tSplit String Method");
        }

        return matrix.toArray(new String[matrix.size()][]);
    }

    public int[][] parseInt2DArray(String[] list) {
        String[][] temp = null;
        int[][] matrix = null;

        int m1length = 0, m2length = 0;

        try {

            String[] temp2 = null;

            temp2 = list[0].split(":");

            for (String s : list) {
                m1length++;
            }
            for (String s : temp2) {
                m2length++;
            }

            //System.out.println(m1length);
            //System.out.println(m2length);

            temp = new String[m1length][m2length];
            matrix = new int[m1length][m2length];


            for (int i = 0; i < temp.length; i++) {
                temp[i] = list[i].split(":");
            }

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    matrix[i][j] = Integer.parseInt(temp[i][j]);
                }
            }
        } catch (Exception e) {
            System.out.println(e + "\n\tParse Int 2D Array Method");
        }


        return matrix;
    }

    public boolean[] parseBooleanArray(String[] list) {
        boolean[] matrix = new boolean[list.length];

        for (int i = 0; i < list.length; i++) {
            matrix[i] = Boolean.parseBoolean(list[i]);
        }

        return matrix;
    }

    public void writeFile(String newString, boolean append) {
        try {

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, append));
            bufferedWriter.newLine();
            bufferedWriter.write(newString);
            bufferedWriter.close();

        } catch (Exception e) {
            System.out.println(e + "\n\tWrite File w/ String Method");
        }
    }

    public void writeFile(String[] newObjects, boolean append) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, append));

            for (String x : newObjects) {
                bufferedWriter.write("" + x);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

        } catch (Exception e) {
            System.out.println(e + "\n\tWrite File w/ String[] Method");
        }
    }

    public void writeFile(Object[] newObjects, boolean append) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, append));

            for (Object x : newObjects) {
                bufferedWriter.write("" + x);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

        } catch (Exception e) {
            System.out.println(e + "\n\tWrite File w/ Object[] Method");
        }
    }

    public void writeFile(boolean[] newObjects, boolean append) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, append));

            for (boolean x : newObjects) {
                bufferedWriter.write("" + x);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

        } catch (Exception e) {
            System.out.println(e + "\n\tWrite File w/ boolean[] Method");
        }
    }
}

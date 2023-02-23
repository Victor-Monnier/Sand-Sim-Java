package data;

import game.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SaveLoad {
    private static String filePath;

    public static void saveEnvironment(Game game, int saveID) {
        System.out.print("Saving environment");
        filePath = System.getProperty("user.dir");
        FileWriter saveWriter = null;
        try {
            saveWriter = new FileWriter(filePath+"/src/data/environments/env_"+saveID+".txt");
            System.out.println("Saving environment to "+filePath+"/src/data/environments/env_"+saveID+".txt");
        } catch(IOException e1) {
            try {
                saveWriter = new FileWriter(filePath+"/data/environments/env_"+saveID+".txt");
                System.out.println("Saving environment to "+filePath+"/data/environments/env_"+saveID+".txt");
            } catch(IOException e2) {
                try {
                    saveWriter = new FileWriter(filePath+"/environments/env_"+saveID+".txt");
                    System.out.println("Saving environment to "+filePath+"/environments/env_"+saveID+".txt");
                } catch(IOException e3) {
                    try {
                        saveWriter = new FileWriter(filePath+"/env_1"+saveID+"txt");
                        System.out.println("Saving environment to "+filePath+"/env_"+saveID+".txt");
                    } catch(IOException e4) {
                        System.out.println("Error occurred while opening save file! Make sure file is being executed in a folder sharing the src folder, data folder, environments folder, or env_1.txt");
                    } 
                }
            }
        }
        if (saveWriter == null)
            return;
        try {
            System.out.print('.');
            saveWriter.write("#Grid of cells, forming an environment");
            saveWriter.write("\nwidth = "+game.grid.length);
            saveWriter.write("\nheight = "+game.grid[0].length);
            for (int row = 0; row < game.grid[0].length; row++) {
                saveWriter.write("\nrow:"+row+" = \t{");
                System.out.print('.');
                for (int col = 0; col < game.grid.length; col++) {
                    saveWriter.write(" "+game.grid[col][row].element.ID);
                }
                saveWriter.write(" }");
            }
        } catch(IOException e) {
            System.out.println("Error occured while writing to save file! Make sure the environment is at least 1x1");
            e.printStackTrace();
        }
        try {
            saveWriter.close();
            System.out.print('.');
        } catch(IOException e) {
            System.out.println("Error occured while closing save file!");
            e.printStackTrace();
        }
        System.out.println("\nDone saving environment");
    }

    public static void loadEnvironment(Game game, int saveID) {
        System.out.print("Saving environment");
        filePath = System.getProperty("user.dir");
        Scanner saveScanner = null;
        try {
            saveScanner = new Scanner(new File(filePath+"/src/data/environments/env_"+saveID+".txt"));
            System.out.println("Loading environment from "+filePath+"/src/data/environments/env_"+saveID+".txt");
        } catch(Exception e1) {
            try {
                saveScanner = new Scanner(new File(filePath+"/data/environments/env_"+saveID+".txt"));
                System.out.println("Loading environment from "+filePath+"/data/environments/env_"+saveID+".txt");
            } catch(Exception e2) {
                try {
                    saveScanner = new Scanner(new File(filePath+"/environments/env_"+saveID+".txt"));
                    System.out.println("Loading environment from "+filePath+"/environments/env_"+saveID+".txt");
                } catch(Exception e3) {
                    try {
                        saveScanner = new Scanner(new File(filePath+"/env_1"+saveID+"txt"));
                        System.out.println("Loading environment from "+filePath+"/env_"+saveID+".txt");
                    } catch(Exception e4) {
                        System.out.println("Error occurred while opening save file! Make sure file is being executed in a folder sharing the src folder, data folder, environments folder, or env_1.txt");
                    } 
                }
            }
        }
        if (saveScanner == null)
            return;
            try {
                int width = 0, height = 0;
                boolean hasCreatedGrid = false;
                while (saveScanner.hasNextLine()) {
                    //Reading input
                    String line = saveScanner.nextLine().trim();
    
                    //Ignores everything past '#' (comments/documentation)
                    if (line.contains("#"))
                        line = line.substring(0, line.indexOf('#'));
    
                    else if (line.startsWith("width")) {
                        width = Integer.parseInt(line.substring(line.indexOf('=')+1).trim());
                    }
                    else if (line.startsWith("height")) {
                        height = Integer.parseInt(line.substring(line.indexOf('=')+1).trim());
                    }
                    else if (line.startsWith("row")) {
                        int rowNumber = Integer.parseInt(line.substring(line.indexOf(':')+1, line.indexOf('=')).trim());
                        String elementRow = line.substring(line.indexOf('{')+1, line.indexOf('}')).trim();
                        //if (rowNumber == 99) System.out.println(elementRow);
                        for (int i = 0; i < elementRow.length()-3; i += 5) {
                            if (rowNumber == 99) System.out.println("Row string length: "+elementRow.length()+" X: "+i/5+" Y:"+rowNumber+" "+elementRow.substring(i, i+4));
                            game.grid[i/5][rowNumber].element = new Element(i%5, rowNumber, elementRow.substring(i, i+4));
                        }
                    }

                    if (!hasCreatedGrid && width != 0 && height != 0) {
                        game.createGrid(width, height);
                        hasCreatedGrid = true;
                    }
                    
                    System.out.print('.');
                }
                
            } catch(Exception e) {
                System.out.println("Error occured while reading save file! Make sure the environment is at least 1x1");
                e.printStackTrace();
            }
        try {
            saveScanner.close();
            System.out.print('.');
        } catch(Exception e) {
            System.out.println("Error occured while closing save file!");
            e.printStackTrace();
        }
        System.out.println("\nDone saving environment");
    }
}

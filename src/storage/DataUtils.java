package storage;

import recipe.Recipe;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataUtils implements Storage {

    private static final String DEFAULT_CSV_PATH = "/Users/byurhanbeyzat/Desktop/kitchen-pal/src/data/data.csv";
    private static final String DEFAULT_CSV_SEPARATOR = "@";

    @Override
    public ArrayList getData() {
        return readFromFile(DEFAULT_CSV_PATH, DEFAULT_CSV_SEPARATOR);
    }

    public void writeLine(ArrayList<Recipe> data) {
        writeToFile(data, DEFAULT_CSV_PATH, DEFAULT_CSV_SEPARATOR);
    }

    // Allows to define custom separator
    public ArrayList<Recipe> readFromFile(String fileName, String separator) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            ArrayList<Recipe> list = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] array = line.split(separator);
                Recipe recipe = new Recipe();
        
                if (array[3].contains("\\n")) {
                    array[3] = array[3].replace("\\n", "\n");
                }

                recipe.setName(array[0]);
                recipe.setAuthor(array[1]);
                recipe.setCategory(array[2]);
                recipe.setRecipeDescription(array[3]);

                list.add(recipe);
            }

            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeToFile(ArrayList<Recipe> data, String fileName, String separator) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            StringBuilder sb = new StringBuilder();

            for (Recipe recipe : data) {
                String descriptionString = recipe.getRecipeDescription();
        
                if (descriptionString.contains("\n")) {
                    descriptionString = descriptionString.replace("\n", "\\n");
                }
                
                sb.append(recipe.getName()).append(separator);
                sb.append(recipe.getAuthor()).append(separator);
                sb.append(recipe.getCategory()).append(separator);
                sb.append(descriptionString);

                sb.append(System.lineSeparator());

                writer.append(sb);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package org.askeroid.utils;

import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LevelLoader {

    public static class LevelList {
        public ArrayList<String> levels;
    }

    public static class LevelData {
        public String level_name;
        public String layout;
    }

    /**
     * Load list of levels from /resources/levels_data/manager.json
     */
    public static LevelList loadLevelList() {
        try (InputStream inputStream = LevelLoader.class.getResourceAsStream("/levels_data/manager.json")) {
            if (inputStream == null) {
                System.err.println("Error: manager.json not found in resources/levels_data/");
                return null;
            }
            InputStreamReader reader = new InputStreamReader(inputStream);
            return new Gson().fromJson(reader, LevelList.class);
        } catch (Exception e) {
            System.err.println("Error loading level list: " + e.getMessage());
            return null;
        }
    }

    /**
     * Load specific level JSON file from /resources/levels_data/
     */
    public static LevelData loadLevelData(String levelPath) {
        String fullPath = levelPath.startsWith("/") ? levelPath : "/" + levelPath;
        try (InputStream inputStream = LevelLoader.class.getResourceAsStream(fullPath)) {
            if (inputStream == null) {
                System.err.println("Error: Level file not found in resources" + fullPath);
                return null;
            }
            InputStreamReader reader = new InputStreamReader(inputStream);
            return new Gson().fromJson(reader, LevelData.class);
        } catch (Exception e) {
            System.err.println("Error loading level data: " + e.getMessage());
            return null;
        }
    }

    /**
     * Parse a layout string into a 2D grid and validate required rows/columns
     */
    public static ArrayList<ArrayList<String>> parseLayout(String layoutString, int requiredRows, int requiredCols) {
        ArrayList<ArrayList<String>> layout = new ArrayList<>();
        String[] rows = layoutString.split("\\|");

        if (rows.length != requiredRows) {
            throw new IllegalArgumentException(
                    "Layout row count mismatch: expected " + requiredRows + " but got " + rows.length
            );
        }

        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            ArrayList<String> rowList = new ArrayList<>();

            for (char c : row.toCharArray()) {
                rowList.add(String.valueOf(c));
            }

            if (rowList.size() != requiredCols) {
                throw new IllegalArgumentException(
                        "Layout column count mismatch at row " + i + ": expected " + requiredCols + " but got " + rowList.size()
                );
            }

            layout.add(rowList);
        }

        return layout;
    }
}

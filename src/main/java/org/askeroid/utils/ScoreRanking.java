package org.askeroid.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.askeroid.score.ScoreManager;
import java.io.*;
import java.util.*;

public class ScoreRanking {
    private static final String FILE_NAME = "score.txt";

    public static void saveScoreToFile(int score) {
        List<Integer> scores = new ArrayList<>();

        // 1. Đọc điểm từ file nếu tồn tại
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {

                    if (line.startsWith("Score: ")) {
                        String numStr = line.substring(7).trim();
                        try {
                            int s = Integer.parseInt(numStr);
                            scores.add(s);
                        } catch (NumberFormatException ignored) {}
                    }
                }
            } catch (IOException e) {
                System.out.println("Đọc file thất bại!");
                e.printStackTrace();
                return; // dừng nếu đọc file lỗi
            }
        }

        // Thêm điểm mới vào list
        scores.add(score);

        // Sắp xếp giảm dần
        scores.sort(Comparator.reverseOrder());

        // Giữ lại 5 điểm cao nhất
        if (scores.size() > 5) {
            scores = scores.subList(0, 5);
        }

        // Ghi lại 5 điểm vào file (ghi đè)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (int s : scores) {
                writer.write("Score: " + s);
                writer.newLine();
            }
            System.out.println("Lưu 5 điểm cao nhất thành công!");
        } catch (IOException e) {
            System.out.println("Ghi file thất bại!");
            e.printStackTrace();
        }
    }
}



package org.askeroid.utils;

import java.io.*;
import java.util.*;

public class ScoreRanking {
    private static final String FILE_NAME = "score.txt";

    public static void saveScoreToFile(int score) {
        List<Integer> scores = new ArrayList<>();

        // Đọc điểm từ file nếu tồn tại
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        int s = Integer.parseInt(line.trim()); // đọc trực tiếp số nguyên trên mỗi dòng
                        scores.add(s);
                    } catch (NumberFormatException ignored) {
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

        // Giữ lại 8 điểm cao nhất
        if (scores.size() > 8) {
            scores = scores.subList(0, 8);
        }

        // Ghi lại 5 điểm vào file (ghi đè)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (int s : scores) {
                writer.write(String.valueOf(s)); // ghi trực tiếp số nguyên
                writer.newLine();
            }
            System.out.println("Lưu 5 điểm cao nhất thành công!");
        } catch (IOException e) {
            System.out.println("Ghi file thất bại!");
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> readScore() {
        ArrayList<Integer> scores = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("File điểm không tồn tại!");
            return scores; // trả về list rỗng
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int s = Integer.parseInt(line.trim()); // đọc trực tiếp số nguyên trên mỗi dòng
                    scores.add(s);
                } catch (NumberFormatException ignored) {
                }
            }
        } catch (IOException e) {
            System.out.println("Đọc file thất bại!");
            e.printStackTrace();
        }

        return scores;
    }
}

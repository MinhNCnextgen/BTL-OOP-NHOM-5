package org.askeroid.utils;

import java.io.*;
import java.util.*;

/**
 * Lớp tiện ích dùng để lưu trữ và đọc danh sách điểm cao của người chơi.
 *
 * Điểm được lưu trong tệp văn bản "score.txt"
 * nằm tại thư mục: ./src/main/resources/score_data/
 *
 * Mỗi dòng trong tệp chứa một điểm số (số nguyên).
 * Khi lưu, lớp sẽ tự động sắp xếp điểm theo thứ tự giảm dần
 * và chỉ giữ lại 8 điểm cao nhất.
 */
public class ScoreRanking {
    private static final String FILE_NAME = "score.txt";

    /**
     * Lưu điểm mới vào tệp điểm, đồng thời cập nhật bảng xếp hạng.
     *
     * Quá trình thực hiện:
     * - Đọc danh sách điểm hiện có trong file (nếu có).
     * - Thêm điểm mới vào danh sách.
     * - Sắp xếp điểm theo thứ tự giảm dần.
     * - Giữ lại tối đa 8 điểm cao nhất.
     * - Ghi đè danh sách mới vào file.
     *
     * @param score điểm số mới cần lưu.
     */
    public static void saveScoreToFile(int score) {
        List<Integer> scores = new ArrayList<>();
        File file = new File("./src/main/resources/score_data/" + FILE_NAME);

        // Đọc điểm từ file nếu tồn tại
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        int s = Integer.parseInt(line.trim());
                        scores.add(s);
                    } catch (NumberFormatException ignored) {
                    }
                }
            } catch (IOException e) {
                System.out.println("Đọc file thất bại!");
                e.printStackTrace();
                return;
            }
        }

        // Thêm điểm mới
        scores.add(score);

        // Sắp xếp giảm dần
        scores.sort(Comparator.reverseOrder());

        // Giữ lại 8 điểm cao nhất
        if (scores.size() > 8) {
            scores = scores.subList(0, 8);
        }

        // Ghi đè vào file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (int s : scores) {
                writer.write(String.valueOf(s));
                writer.newLine();
            }
            System.out.println("Lưu điểm thành công!");
        } catch (IOException e) {
            System.out.println("Ghi file thất bại!");
            e.printStackTrace();
        }
    }

    /**
     * Đọc danh sách điểm từ tệp "score.txt".
     *
     * @return danh sách các điểm số, sắp xếp theo thứ tự trong file.
     *         Nếu file không tồn tại, trả về danh sách rỗng.
     */
    public static ArrayList<Integer> readScore() {
        ArrayList<Integer> scores = new ArrayList<>();
        File file = new File("./src/main/resources/score_data/" + FILE_NAME);

        if (!file.exists()) {
            System.out.println("File điểm không tồn tại!");
            return scores;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int s = Integer.parseInt(line.trim());
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

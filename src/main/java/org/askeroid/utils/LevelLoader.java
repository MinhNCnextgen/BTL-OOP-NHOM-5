package org.askeroid.utils;

import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Lớp tiện ích hỗ trợ tải dữ liệu màn chơi (Level) từ thư mục tài nguyên.
 *
 * Dữ liệu màn chơi được định nghĩa dưới dạng các tệp JSON trong thư mục
 * /resources/levels_data/.
 *
 * Lớp này cung cấp các phương thức để:
 * - Tải danh sách các màn chơi có sẵn từ tệp manager.json
 * - Tải dữ liệu chi tiết của từng màn chơi
 * - Phân tích chuỗi bố cục (layout) thành dạng lưới 2 chiều
 */
public class LevelLoader {

    /**
     * Lớp đại diện cho danh sách các màn chơi (được đọc từ manager.json).
     */
    public static class LevelList {
        /** Danh sách đường dẫn đến các tệp JSON chứa dữ liệu màn chơi. */
        public ArrayList<String> levels;
    }

    /**
     * Lớp chứa thông tin chi tiết của một màn chơi cụ thể.
     *
     * Mỗi màn chơi có tên (level_name) và bố cục (layout)
     * được biểu diễn dưới dạng chuỗi ký tự.
     */
    public static class LevelData {
        /** Tên của màn chơi. */
        public String level_name;

        /** Chuỗi bố cục của màn chơi (layout string). */
        public String layout;
    }

    /**
     * Tải danh sách màn chơi từ tệp /resources/levels_data/manager.json.
     *
     * @return đối tượng LevelList chứa danh sách các màn chơi,
     *         hoặc null nếu xảy ra lỗi khi đọc tệp.
     */
    public static LevelList loadLevelList() {
        try (InputStream inputStream = LevelLoader.class.getResourceAsStream("/levels_data/manager.json")) {
            if (inputStream == null) {
                System.err.println("Lỗi: Không tìm thấy tệp manager.json trong thư mục resources/levels_data/");
                return null;
            }
            InputStreamReader reader = new InputStreamReader(inputStream);
            return new Gson().fromJson(reader, LevelList.class);
        } catch (Exception e) {
            System.err.println("Lỗi khi tải danh sách màn chơi: " + e.getMessage());
            return null;
        }
    }

    /**
     * Tải dữ liệu chi tiết của một màn chơi cụ thể từ thư mục /resources/levels_data/.
     *
     * @param levelPath đường dẫn đến tệp JSON của màn chơi (ví dụ: levels_data/level1.json).
     * @return đối tượng LevelData chứa thông tin màn chơi,
     *         hoặc null nếu tệp không tồn tại hoặc lỗi khi đọc.
     */
    public static LevelData loadLevelData(String levelPath) {
        String fullPath = levelPath.startsWith("/") ? levelPath : "/" + levelPath;
        try (InputStream inputStream = LevelLoader.class.getResourceAsStream(fullPath)) {
            if (inputStream == null) {
                System.err.println("Lỗi: Không tìm thấy tệp màn chơi trong resources" + fullPath);
                return null;
            }
            InputStreamReader reader = new InputStreamReader(inputStream);
            return new Gson().fromJson(reader, LevelData.class);
        } catch (Exception e) {
            System.err.println("Lỗi khi tải dữ liệu màn chơi: " + e.getMessage());
            return null;
        }
    }

    /**
     * Phân tích chuỗi bố cục (layout string) thành lưới 2 chiều các ký tự,
     * đồng thời kiểm tra số hàng và cột yêu cầu.
     *
     * @param layoutString  chuỗi bố cục của màn chơi, các hàng được ngăn cách bằng ký tự '|'.
     * @param requiredRows  số hàng bắt buộc của màn chơi.
     * @param requiredCols  số cột bắt buộc của màn chơi.
     * @return mảng 2 chiều đại diện cho bố cục màn chơi.
     * @throws IllegalArgumentException nếu số hàng hoặc số cột không khớp với yêu cầu.
     */
    public static ArrayList<ArrayList<String>> parseLayout(String layoutString,
                                                           int requiredRows, int requiredCols) {
        ArrayList<ArrayList<String>> layout = new ArrayList<>();
        String[] rows = layoutString.split("\\|");

        if (rows.length != requiredRows) {
            throw new IllegalArgumentException(
                    "Số hàng của layout không khớp: cần " + requiredRows + " nhưng nhận " + rows.length
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
                        "Số cột của layout không khớp tại hàng " + i + ": cần " + requiredCols
                                + " nhưng nhận " + rowList.size()
                );
            }

            layout.add(rowList);
        }

        return layout;
    }
}

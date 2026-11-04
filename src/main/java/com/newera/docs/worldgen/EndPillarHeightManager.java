package com.newera.docs.worldgen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.LevelResource;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntSupplier;

public class EndPillarHeightManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type MAP_TYPE = new TypeToken<Map<String, Integer>>() {}.getType();
    private static final Map<String, Integer> PILLAR_BASE_Y = new ConcurrentHashMap<>();
    private static Path lastSavedPath = null;

    /**
     * Lấy độ cao chân cột (baseY) đã được khóa vĩnh viễn trong save world.
     * Nếu cột chưa từng sinh, gọi groundScanner để quét đỉnh đất cao nhất, lưu vào file rồi trả về.
     */
    public static int getOrComputeBaseY(ServerLevel level, int cx, int cz, IntSupplier groundScanner) {
        String key = cx + "_" + cz;

        // 1. Thử nạp từ file save world nếu bộ nhớ đệm chưa có
        ensureLoaded(level);

        if (PILLAR_BASE_Y.containsKey(key)) {
            return PILLAR_BASE_Y.get(key);
        }

        // 2. Cột sinh lần đầu tiên: quét địa hình tìm điểm cao nhất
        int baseY = groundScanner.getAsInt();
        PILLAR_BASE_Y.put(key, baseY);

        // 3. Lưu ngay vào file save world
        saveToFile(level);

        return baseY;
    }

    /**
     * Trả về chiều cao Bedrock đỉnh cột (baseY + 41) phục vụ EndSpike.getHeight().
     * Phục vụ tia bắn hồi sinh rồng (nhắm vào getHeight() + 1 = baseY + 42).
     */
    public static int getSpikeHeight(int cx, int cz) {
        String key = cx + "_" + cz;
        if (PILLAR_BASE_Y.containsKey(key)) {
            return PILLAR_BASE_Y.get(key) + 41;
        }
        return 58 + 41; // Mặc định 99 nếu chưa có dữ liệu
    }

    private static synchronized void ensureLoaded(ServerLevel level) {
        if (level == null || level.getServer() == null) return;
        try {
            Path saveDir = level.getServer().getWorldPath(LevelResource.DATA);
            Path file = saveDir.resolve("newera_pillars.json");
            lastSavedPath = file;

            if (Files.exists(file)) {
                try (Reader reader = Files.newBufferedReader(file)) {
                    Map<String, Integer> loaded = GSON.fromJson(reader, MAP_TYPE);
                    if (loaded != null) {
                        PILLAR_BASE_Y.putAll(loaded);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static synchronized void saveToFile(ServerLevel level) {
        if (level == null || level.getServer() == null) return;
        try {
            Path saveDir = level.getServer().getWorldPath(LevelResource.DATA);
            Files.createDirectories(saveDir);
            Path file = saveDir.resolve("newera_pillars.json");
            lastSavedPath = file;

            try (Writer writer = Files.newBufferedWriter(file)) {
                GSON.toJson(PILLAR_BASE_Y, MAP_TYPE, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

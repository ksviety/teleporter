package me.ksviety.teleporter;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

public class Config {
    @NotNull
    @Contract("_ -> new")
    public static Config fromFile(File file) throws FileNotFoundException {
        final BufferedReader reader = new BufferedReader(new FileReader(file));

        final String json = reader.lines().collect(Collectors.joining());
        final JSONObject config = new JSONObject(json);

        final int centerX = config.getInt("centerX");
        final int centerZ = config.getInt("centerZ");
        final int size = config.getInt("size");
        final String[] bannedBlocksArray = (String[]) config.getJSONArray("bannedBlocks").toList().toArray();

        final Set<String> bannedBlocks = new HashSet<>(Arrays.asList(bannedBlocksArray));

        return new Config(bannedBlocks, centerX, centerZ, size);
    }

    private final Set<String> bannedBlocks;
    private final int centerX;
    private final int centerZ;
    private final int size;

    private Config(Set<String> bannedBlocks, int centerX, int centerZ, int size) {
        this.bannedBlocks = bannedBlocks;
        this.centerX = centerX;
        this.centerZ = centerZ;
        this.size = size;
    }

    public Collection<String> getBannedBlocks() {
        return bannedBlocks;
    }

    public int getSize() {
        return size;
    }

    public int getCenterZ() {
        return centerZ;
    }

    public int getCenterX() {
        return centerX;
    }
}

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
        final JSONArray bannedBlocksArray = config.getJSONArray("bannedBlocks");
        final int shiftRadius = config.getInt("shiftRadius");

        final Set<String> bannedBlocks = new HashSet<>();

        for (int i = 0; i < bannedBlocksArray.toList().size(); i++) {
            bannedBlocks.add(bannedBlocksArray.getString(i));
        }

        return new Config(bannedBlocks, centerX, centerZ, size, shiftRadius);
    }

    private final Set<String> bannedBlocks;
    private final int centerX;
    private final int centerZ;
    private final int size;
    private final int shiftRadius;

    private Config(Set<String> bannedBlocks, int centerX, int centerZ, int size, int shiftRadius) {
        this.bannedBlocks = bannedBlocks;
        this.centerX = centerX;
        this.centerZ = centerZ;
        this.size = size;
        this.shiftRadius = shiftRadius;
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

    public int getShiftRadius() {
        return shiftRadius;
    }
}

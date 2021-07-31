package me.ksviety.teleporter;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Config {
    @NotNull
    @Contract("_ -> new")
    public static Config fromFile(File file) throws FileNotFoundException {
        final BufferedReader reader = new BufferedReader(new FileReader(file));
        final Set<String> bannedBlocks = new HashSet<>();

        try {
            String block;

            while (true) {
                block = reader.readLine();

                if (block == null)
                    break;

                bannedBlocks.add(block);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalError("Cannot read config file!");
        }

        return new Config(bannedBlocks);
    }

    private final Set<String> bannedBlocks;

    private Config(Set<String> bannedBlocks) {
        this.bannedBlocks = bannedBlocks;
    }

    public Collection<String> getBannedBlocks() {
        return bannedBlocks;
    }
}

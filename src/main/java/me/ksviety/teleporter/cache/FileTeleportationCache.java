package me.ksviety.teleporter.cache;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FileTeleportationCache extends TeleportationCache {
    public static FileTeleportationCache fromFile(File file) throws FileNotFoundException {
        final Set<String> players = new HashSet<>();
        final BufferedReader reader = new BufferedReader(new FileReader(file));

        try {
            String name;

            while (true) {
                name = reader.readLine();

                if (name == null)
                    break;

                players.add(name);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalError("Cannot read from cache file!");
        }

        return new FileTeleportationCache(file, players);
    }

    private final BufferedWriter fileOutput;

    private FileTeleportationCache(File file, Set<String> players) {
        super(players);

        try {
            this.fileOutput = new BufferedWriter(new FileWriter(file, false));
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalError("Cannot write to cache file!");
        }
    }

    @Override
    public void save() {
        try {
            for (String player : getAlreadyTeleportedPlayers()) {
                fileOutput.append(player);
                fileOutput.newLine();
            }

            fileOutput.flush();
            fileOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalError("Cannot write to cache file!");
        }
    }
}

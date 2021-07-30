package me.ksviety.teleporter;

import java.util.Random;

public class RandomPositionProvider implements PositionProvider {
    private final Random random;

    protected RandomPositionProvider(Random random) {
        this.random = random;
    }

    @Override
    public Position provide() {
        return new Position(
                random.nextInt(),
                random.nextInt(),
                random.nextInt()
        );
    }
}

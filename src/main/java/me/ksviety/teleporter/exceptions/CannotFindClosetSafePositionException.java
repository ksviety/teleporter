package me.ksviety.teleporter.exceptions;

public class CannotFindClosetSafePositionException extends RuntimeException {
    public CannotFindClosetSafePositionException() {
        super("Could not find any safe block near the provided position");
    }
}

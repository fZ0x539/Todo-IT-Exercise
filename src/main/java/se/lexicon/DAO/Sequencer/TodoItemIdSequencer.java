package se.lexicon.DAO.Sequencer;

public class TodoItemIdSequencer {
    private static int currentId = 0;

    public static int nextId() {
        return ++currentId;
    }

    public static int getCurrentId() {
        return currentId;
    }

    public static void setCurrentId(int id) { //The purpose of a sequencer is to generate sequential, predictable and unique ID's so no idea why the UML says "setCurrentId(int): void"
        if (id < 0) {
            throw new IllegalArgumentException("ID must be non-negative.");
        }
        currentId = id;
    }
}

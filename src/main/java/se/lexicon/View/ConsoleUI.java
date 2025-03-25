package se.lexicon.View;

public class ConsoleUI {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";

    public static void printSuccess(String message){
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }

    public static void printWarn(String message){
        System.out.println(ANSI_YELLOW + message + ANSI_RESET);
    }

    public static void printError(String message){
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    public static void printInfo(String message){
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }
}


package flashcards;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CardManager cardManager = new CardManager();
        MenuManager menuManager = new MenuManager(cardManager, scanner);

        if (args.length >= 2) {
            if (args[0].equals("-import")) {
                List<Card> importedCards = menuManager.fileManager.importCards(args[1]);
                for (Card card : importedCards) {
                    cardManager.putCard(card);
                }
                System.out.println(importedCards.size() + " cards have been loaded.");
            }
        }
        if (args.length >= 4) {
            if (args[2].equals("-import")) {
                List<Card> importedCards = menuManager.fileManager.importCards(args[3]);
                for (Card card : importedCards) {
                    cardManager.putCard(card);
                }
                System.out.println(importedCards.size() + " cards have been loaded.");
            }
        }
        menuManager.run();
        if (args.length >= 2) {
            if (args[0].equals("-export")) {
                menuManager.fileManager.exportCards(args[1], cardManager.getCards());
                System.out.println(cardManager.size() + " cards have been saved.");
            }
        }
        if (args.length >= 4) {
            if (args[2].equals("-export")) {
                menuManager.fileManager.exportCards(args[3], cardManager.getCards());
                System.out.println(cardManager.size() + " cards have been saved.");
            }
        }
        //cardManager.enterNewCards(scanner);
        //cardManager.askAllCards(scanner);
    }
}
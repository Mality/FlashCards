package flashcards;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MenuManager {

    CardManager cardManager;
    Scanner scanner;
    FileManager fileManager;
    Random random;

    public MenuManager(CardManager cardManager, Scanner scanner) {
        this.cardManager = cardManager;
        this.scanner = scanner;
        fileManager = new FileManager();
        random = new Random();
    }

    public void run() {
        String action;
        do {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            action = scanner.nextLine();
            if (!action.equals("log")) {
                CardManager.log("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
                CardManager.log(action);
            }
        } while (processAction(action));
        System.out.println(CardManager.log("Bye bye!"));
    }

    public boolean processAction(String action) {
        switch (action) {
            case "add":
                add();
                break;

            case "remove":
                remove();
                break;

            case "import":
                importAction();
                break;

            case "export":
                export();
                break;

            case "ask":
                ask();
                break;

            case "log":
                log();
                break;

            case "hardest card":
                printHardesCards();
                break;

            case "reset stats":
                resetStats();
                break;

            case "exit":
                return false;

            default:
                System.out.println(CardManager.log("Unknown command\n"));
                break;
        }
        return true;
    }

    public void resetStats() {
        cardManager.resetStats();
        System.out.println(CardManager.log("Card statistics has been reset.\n"));
    }

    private void add() {
        try {
            String term;
            String definition;
            System.out.println(CardManager.log("The card:"));
            term = CardManager.log(scanner.nextLine());
            if (cardManager.hasTerm(term)) {
                throw new Exception(CardManager.log("The card \"" + term + "\" already exists."));
            }
            System.out.println(CardManager.log("The definition of the card:"));
            definition = CardManager.log(scanner.nextLine());
            if (cardManager.hasDefinition(definition)) {
                throw new Exception(CardManager.log("The definition \"" + definition + "\" already exists."));
            }
            cardManager.putCard(new Flashcard(term, definition));
            System.out.println(CardManager.log("The pair (\"" + term + "\":\"" + definition + "\") has been added."));
        } catch (Exception e) {
            System.out.println(CardManager.log(e.getMessage()));
        }
        System.out.print(CardManager.log("\n"));
    }

    private void printHardesCards() {
        List<Card> hardestCards = cardManager.getHardestCards();
        if (hardestCards.size() == 0) {
            System.out.println(CardManager.log("There are no cards with errors."));
        } else {
            int mistakes = cardManager.getMistakes(hardestCards.get(0).getTerm());
            if (hardestCards.size() == 1) {
                System.out.println(CardManager.log("The hardest card is \"" + hardestCards.get(0).getTerm() + "\". You have " + mistakes + " errors answering it."));
            } else {
                String output = "The hardest cards are \"" + hardestCards.get(0).getTerm() + "\"";
                for (int i = 1; i < hardestCards.size(); i++) {
                    output = output + ", \"" + hardestCards.get(i).getTerm() + "\"";
                }
                output = output + ". You have " + mistakes + " errors answering them.";
                System.out.println(CardManager.log(output));
            }
        }
        System.out.print(CardManager.log("\n"));
    }

    private void log() {
        System.out.println("File name:");
        String fileName = scanner.nextLine();
        fileManager.exportLog(fileName, cardManager.getLog());
        System.out.println("The log has been saved.\n");
    }

    private void remove() {
        String term;
        System.out.println(CardManager.log("The card:"));
        term = CardManager.log(scanner.nextLine());
        cardManager.removeCard(term);
        System.out.print(CardManager.log("\n"));
    }

    private void importAction() {
        System.out.println(CardManager.log("File name:"));
        String fileName = CardManager.log(scanner.nextLine());
        if (!fileManager.canImportFile(fileName)) {
            System.out.println(CardManager.log("File not found."));
        } else {
            List<Card> importedCards = fileManager.importCards(fileName);
            for (Card card : importedCards) {
                cardManager.putCard(card);
            }
            System.out.println(CardManager.log(importedCards.size() + " cards have been loaded."));
        }
        System.out.print(CardManager.log("\n"));
    }

    private void export() {
        System.out.println(CardManager.log("File name:"));
        String fileName = CardManager.log(scanner.nextLine());
        try {
            int count = fileManager.exportCards(fileName, cardManager.getCards());
            System.out.println(CardManager.log(count + " cards have been saved."));
        } catch (Exception e) {
            System.out.println(CardManager.log("Cant save cards"));
        }
        System.out.print(CardManager.log("\n"));
    }

    private void ask() {
        System.out.println(CardManager.log("How many times to ask?"));
        try {
            String s = CardManager.log(scanner.nextLine());
            if (!s.matches("\\d+")) {
                throw new Exception(s + " not a number\n");
            }
            if (cardManager.size() == 0) {
                throw new Exception("Nothing to ask");
            }
            int count = Integer.valueOf(s);
            int lastIndex = -1;
            for (int i = 0; i < count; i++) {
                int randomIndex;
                do {
                    randomIndex = random.nextInt(cardManager.size());
                }  while (cardManager.size() > 1 && lastIndex == randomIndex);
                cardManager.askCard(cardManager.getCard(randomIndex), scanner);
                lastIndex = randomIndex;
            }
        } catch (Exception e) {
            System.out.println(CardManager.log(e.getMessage()));
        }
        System.out.print(CardManager.log("\n"));
    }
}

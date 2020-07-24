package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {

    public boolean canImportFile(String fileName) {
        File file = new File("." + File.separator + fileName);
        return file.isFile() && file.canRead();
    }

    public List<Card> importCards(String fileName) {
        List<Card> importedCards = new ArrayList<>();
        File file = new File("." + File.separator + fileName);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String term = scanner.nextLine();
                String definition = scanner.nextLine();
                int mistakes = Integer.parseInt(scanner.nextLine());
                importedCards.add(new Flashcard(term, definition, mistakes));
            }
        } catch (FileNotFoundException e) {
            System.out.println(CardManager.log("File not found."));
        } catch (Exception e) {
            System.out.println("Unexpected exception in files");
        }
        return importedCards;
    }

    public int exportCards(String fileName, List<Card> cards) {
        File file = new File("." + File.separator + fileName);
        try (FileWriter fileWriter = new FileWriter(file)) {
            for (Card card : cards) {
                fileWriter.write(card.getTerm() + "\n");
                fileWriter.write(card.getDefinition() + "\n");
                fileWriter.write(card.getMistakes() + "\n");
            }
            return cards.size();
        } catch (FileNotFoundException e) {
            //System.out.println(CardManager.log("File not found."));
        } catch (IOException e) {
            //System.out.println(e.getMessage());
        }
        return 0;
    }

    public void exportLog(String fileName, List<String> list) {
        File file = new File("." + File.separator + fileName);
        try (FileWriter fileWriter = new FileWriter(file)) {
            for (String line : list) {
                fileWriter.write(line + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println(CardManager.log("File not found."));
        } catch (IOException e) {
            //System.out.println(e.getMessage());
        }
    }

}

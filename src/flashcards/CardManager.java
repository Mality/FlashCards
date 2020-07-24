package flashcards;

import java.util.*;

class CardManager {
    Map<String, Card> cardByTerm;
    Map<String, Card> cardByDefinition;
    private static final List<String> logList = new ArrayList<>();

    public static String log(String s) {
        logList.add(s);
        return s;
    }

    public CardManager() {
        cardByTerm = new HashMap<>();
        cardByDefinition = new HashMap<>();
    }

    public boolean putCard(Card card) {
        if (hasTerm(card.getTerm())) {
            String lastDefinition = cardByTerm.get(card.getTerm()).getDefinition();
            cardByTerm.remove(card.getTerm());
            cardByDefinition.remove(lastDefinition);
        }
        if (hasDefinition(card.getDefinition())) {
            return false;
        }
        cardByTerm.put(card.getTerm(), card);
        cardByDefinition.put(card.getDefinition(), card);
        return true;
    }

    public int getMistakes(String term) {
        return cardByTerm.get(term).getMistakes();
    }

    public List<String> getLog() {
        return logList;
    }

    public boolean hasTerm(String term) {
        return cardByTerm.containsKey(term);
    }

    public boolean hasDefinition(String definition) {
        return cardByDefinition.containsKey(definition);
    }

    public List<Card> getHardestCards() {
        int mx = 1;
        List<Card> list = new ArrayList<>();
        for (Card card : cardByTerm.values()) {
            if (mx == card.getMistakes()) {
                list.add(card);
            }
            if (mx < card.getMistakes()) {
                list.clear();
                list.add(card);
                mx = card.getMistakes();
            }
        }
        return list;
    }

    public boolean removeCard(String term) {
        if (hasTerm(term)) {
            String definition = cardByTerm.get(term).getDefinition();
            cardByTerm.remove(term);
            cardByDefinition.remove(definition);
            System.out.println(log("The card has been removed."));
            return true;
        } else {
            System.out.println(log("Can't remove \"" + term + "\": there is no such card."));
            return false;
        }
    }

    public Card getCard(int index) {
        int curInd = 0;
        for (Card card : cardByTerm.values()) {
            if (curInd == index) {
                return card;
            }
            curInd++;
        }
        return null;
    }

    public void resetStats() {
        for (String term : cardByTerm.keySet()) {
            String definition = cardByTerm.get(term).getDefinition();
            cardByTerm.get(term).clearMistakes();
            cardByDefinition.get(definition).clearMistakes();
        }
    }

    public void askCard(Card card, Scanner scanner) {
        System.out.println(log("Print the definition of " + "\"" + card.getTerm() + "\":"));
        String answer = log(scanner.nextLine());
        String result = checkAnswer(card, answer);
        if (!result.equals(Flashcard.getCorrectAnswerMessage())) {
            card.addMistake();
        }
        if (!result.equals(Flashcard.getCorrectAnswerMessage()) && cardByDefinition.containsKey(answer)) {
            System.out.println(log("Wrong answer. The correct one is \"" + card.getDefinition() + "\", you've just written the definition of \"" + cardByDefinition.get(answer).getTerm() + "\"."));
        } else {
            System.out.println(log(result));
        }
    }

    public int size() {
        return cardByTerm.size();
    }

    public String checkAnswer(Card card, String answer) {
        return card.checkAnswer(answer);
    }

    public ArrayList<Card> getCards() {
        ArrayList<Card> cards = new ArrayList<>();
        for (Card card : cardByTerm.values()) {
            cards.add(card);
        }
        return cards;
    }
}

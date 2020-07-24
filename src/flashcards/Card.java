package flashcards;

interface Card {

    String checkAnswer(String answer);

    void addMistake();

    void clearMistakes();

    int getMistakes();

    String getTerm();

    String getDefinition();

    void setTerm(String term);

    void setDefinition(String definition);
}

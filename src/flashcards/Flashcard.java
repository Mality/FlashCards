package flashcards;

class Flashcard implements Card {
    private String term;
    private String definition;
    private int mistakes;
    private static final String CORRECT_ANSWER_MESSAGE = "Correct answer.";
    private static final String WRONG_ANSWER_MESSAGE = "Wrong answer. The correct one is ";

    public Flashcard(String term, String definition) {
        this.term = term;
        this.definition = definition;
        mistakes = 0;
    }

    public Flashcard(String term, String definition, int mistakes) {
        this.term = term;
        this.definition = definition;
        this.mistakes = mistakes;
    }

    public Flashcard() {
    }

    @Override
    public String checkAnswer(String userAnswer) {
        if (definition.equals(userAnswer)) {
            return CORRECT_ANSWER_MESSAGE;
        } else {
            return WRONG_ANSWER_MESSAGE + "\"" + definition + "\".";
        }
    }

    @Override
    public void addMistake() {
        mistakes++;
    }

    @Override
    public void clearMistakes() {
        mistakes = 0;
    }

    @Override
    public int getMistakes() {
        return mistakes;
    }

    @Override
    public String getTerm() {
        return term;
    }

    @Override
    public String getDefinition() {
        return definition;
    }

    @Override
    public void setTerm(String term) {
        this.term = term;
    }

    @Override
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public static String getCorrectAnswerMessage() {
        return CORRECT_ANSWER_MESSAGE;
    }

    public static String getWrongAnswerMessage() {
        return WRONG_ANSWER_MESSAGE;
    }
}

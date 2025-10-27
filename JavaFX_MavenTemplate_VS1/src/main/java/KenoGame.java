import java.util.*;

public class KenoGame {
    private final int spotsToPlay;
    private final int drawsToPlay;
    private Set<Integer> selectedNumbers;
    private Set<Integer> currentDrawNumbers;
    private int totalScore;

    public KenoGame(int spotsToPlay, int drawsToPlay) {
        this.spotsToPlay = spotsToPlay;
        this.drawsToPlay = drawsToPlay;
        this.selectedNumbers = new HashSet<>();
        this.totalScore = 0;
    }

    public void selectNumbers(Set<Integer> numbers) {
        if (numbers.size() > spotsToPlay) {
            throw new IllegalArgumentException("Too many numbers selected");
        }
        this.selectedNumbers = new HashSet<>(numbers);

        if(spotsToPlay > numbers.size()) {
            throw new IllegalArgumentException("Too few numbers selected or duplicated");
        }
    }

    public void performRandomPick() {
        selectedNumbers.clear();
        Random rand = new Random();
        while (selectedNumbers.size() < spotsToPlay) {
            int number = rand.nextInt(80) + 1;
            selectedNumbers.add(number);
        }
    }

    public Set<Integer> getSelectedNumbers() {
        return selectedNumbers;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public Set<Integer> performDraw() {
        if (selectedNumbers.isEmpty()) {
            throw new IllegalStateException("No numbers selected");
        }

        currentDrawNumbers = new HashSet<>();
        Random rand = new Random();
        while (currentDrawNumbers.size() < 20) {
            currentDrawNumbers.add(rand.nextInt(80) + 1);
        }

        int hits = countHits();
        totalScore += calculateWin(hits);
        return currentDrawNumbers;
    }

    public int countHits() {
        Set<Integer> hits = new HashSet<>(selectedNumbers);
        hits.retainAll(currentDrawNumbers);
        return hits.size();
    }

    public int calculateWin(int hits) {
        switch (spotsToPlay) {
            case 1: // 1 Spot Game
                if (hits == 1) return 2;
                return 0;

            case 4: // 4 Spot Game
                switch (hits) {
                    case 2: return 1;
                    case 3: return 5;
                    case 4: return 75;
                    default: return 0;
                }

            case 8: // 8 Spot Game
                switch (hits) {
                    case 4: return 2;
                    case 5: return 12;
                    case 6: return 50;
                    case 7: return 750;
                    case 8: return 10000;
                    default: return 0;
                }

            case 10: // 10 Spot Game
                switch (hits) {
                    case 0: return 5;
                    case 5: return 2;
                    case 6: return 15;
                    case 7: return 40;
                    case 8: return 450;
                    case 9: return 4250;
                    case 10: return 100000;
                    default: return 0;
                }

            default:
                return 0; // For other spot games
        }
    }

    public void randomDraws() {
        for (int i = 0; i < drawsToPlay; i++) {
            performDraw();
        }
    }

    public int getSpotsToPlay() {
        return spotsToPlay;
    }

    public int getDrawsToPlay() {
        return drawsToPlay;
    }
}

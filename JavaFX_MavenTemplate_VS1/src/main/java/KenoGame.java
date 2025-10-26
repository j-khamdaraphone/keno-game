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
        while (currentDrawNumbers.size() < spotsToPlay) {
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
        // Keep your original "good" scoring logic:
        if (hits == 0) return 0;
        if (hits == 1) return 2;
        if (hits == 2) return 5;
        if (hits == 3) return 20;
        if (hits == 4) return 100;
        if (hits == 5) return 200;
        if (hits == 6) return 500;
        if (hits == 7) return 1000;
        if (hits == 8) return 2000;
        if (hits == 9) return 5000;
        if (hits == 10) return 10000;
        return 0;
    }

    public int getSpotsToPlay() {
        return spotsToPlay;
    }

    public int getDrawsToPlay() {
        return drawsToPlay;
    }
}

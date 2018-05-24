import static org.junit.jupiter.api.Assertions.*;


class LevensteinDistanceTest {

    @org.junit.jupiter.api.Test
    void main() {

        assertEquals(1, LevensteinDistance.computeLevenshteinDistance("lala", "lalka"));

        assertEquals(1, LevensteinDistance.computeLevenshteinDistance("lala", "lama"));

        assertEquals(2, LevensteinDistance.computeLevenshteinDistance("lala", "lalala"));

        assertEquals(2, LevensteinDistance.computeLevenshteinDistance("lala", "ll"));
    }
}
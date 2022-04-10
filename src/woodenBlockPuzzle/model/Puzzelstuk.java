package woodenBlockPuzzle.model;

/** Stelt een puzzelstuk voor. Een puzzelstuk is een verzameling van vakjes/ coördinaten met een naam een bepaalde score.
 *
 * @author Michel Matthe
 * @version 1.1
 * */
public enum Puzzelstuk {
    VIERKANT2 (4, new int[][] {
            {1,0},{1,1},
            {0,0},{0,1}}),

    VIERKANT3 (9, new int[][] {
            {1, -1}, {1, 0}, {1, 1},
            {0, -1}, {0, 0}, {0, 1},
            {-1, -1}, {-1, 0}, {-1, 1}}),

    T_ONDER (4, new int[][] {
            {0, -1}, {0, 0}, {0, 1},
                    {-1, 0}}),

    T_BOVEN (4, new int[][] {
                    {1, 0},
            {0, -1}, {0, 0}, {0, 1}}),

    T_LINKS (4, new int[][] {
                    {1,0},
            {0,-1},  {0,0},
                    {-1,0}}),

    T_RECHTS (4, new int[][] {
            {1,0},
            {0,0},  {0,1},
            {-1,0}}),

    I2 (2, new int[][] {
            {1,0},
            {0,0}}),

    I2VLAK (2, new int[][] {{0,0}, {0,1}}),

    I3 (3, new int[][] {
            {1,0},
            {0,0},
            {-1,0}}),


    I3VLAK (3, new int[][] {{0,-1}, {0,0}, {0,1}}),

    LKLEIN1(3, new int[][] {
            {1,0},
            {0,0}, {0,1}}),

    LKLEIN2(3, new int[][] {
            {0,0}, {0,1},
            {-1,0}}),

    LKLEIN3(3, new int[][] {
            {0,-1}, {0,0},
                    {-1,0}}),

    LKLEIN4(3, new int[][] {
                    {1,0},
            {0,-1}, {0,0}}),

    LGROOT1(4, new int[][]{
                            {1,1},
            {0,-1}, {0,0}, {0,1},}),

    LGROOT2(4, new int[][]{
            {1,-1},
            {0,-1}, {0,0}, {0,1},}),

    LGROOT3(4, new int[][]{
            {0,-1}, {0,0}, {0,1},
            {-1,-1}}),

    LGROOT4(4, new int[][]{
            {0,-1}, {0,0}, {0,1},
                            {-1,1}}),

    VAK(1, new int[][] {{0,0}});

    int score;
    int[][] coordinaten;

    Puzzelstuk(int score, int[][] coordinaten){
        this.score = score;
        this.coordinaten = coordinaten;
    }

    @Override
    public String toString(){
        return this.name();
    }

    public int[][] getCoordinaten() {
        return coordinaten;
    }
}
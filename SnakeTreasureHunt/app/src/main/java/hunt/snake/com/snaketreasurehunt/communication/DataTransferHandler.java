package hunt.snake.com.snaketreasurehunt.communication;

public class DataTransferHandler {
    // Data transferred when starting the game (GAMESTART_MESSAGE)
    private static int fieldWidth;          // Spielfeldbreite
    private static int fieldHeight;         // Spielfeldhöhe
    private static int numOfOccupiedTiles;  // Zahl der belegten Tiles (= Größe der Arrays unten)
    private static int[] tileXPos;          // X-Positionen aller belegter Tiles
    private static int[] tileYPos;          // Y-Positionen aller belegter Tiles
    private static int[] tileType;          // Typen aller belegter Tiles

    // Data transferred when stitching out (STITCHOUT_MESSAGE)
    private static float tickTime;          // aktuelle Tickzeit (für Synchronisation)
    private static float timestamp;         // Zeitpunkt des Stitchens
    private static int stitchingDirection;  // Richtung des Stitchens (Oben, Rechts, Unten, Links)
    private static int topLeftXPos;         // X-Position des links oberen Tiles
    private static int topLeftYPos;         // Y-Position des links oberen Tiles
    private static int numOfSnakeBodyparts; // Zahl der Körperteile der Schlange (= Größe der Arrays unten)
    private static int[] bodypartXPos;      // X-Positionen aller Schlangen-Körperteile
    private static int[] bodypartYPos;      // Y-Positionen aller Schlangen-Körperteile
    private static boolean[] cornerPart;    // Ist das Körperteil ein Eckteil?
    private static int[] origin;            // "Herkunft" des Körperteils (nur bei Eckteilen)
    private static int[] direction;         // Richtung des Körperteils

    // Data transferred after the snake has eaten and a new gutti is spawned (NEWGUTTI_MESSAGE)
    private static int foodXPos;            // X-Position des Guttis
    private static int foodYPos;            // Y-Position des Guttis

    // Data transferred when the game is over (GAMEOVER_MESSAGE)
    private static int score;               // aktueller Spielstand


    public static int getFieldWidth() {
        return fieldWidth;
    }

    public static void setFieldWidth(int fieldWidth) {
        DataTransferHandler.fieldWidth = fieldWidth;
    }

    public static int getFieldHeight() {
        return fieldHeight;
    }

    public static void setFieldHeight(int fieldHeight) {
        DataTransferHandler.fieldHeight = fieldHeight;
    }

    public static int getNumOfOccupiedTiles() {
        return numOfOccupiedTiles;
    }

    public static void setNumOfOccupiedTiles(int numOfOccupiedTiles) {
        DataTransferHandler.numOfOccupiedTiles = numOfOccupiedTiles;
    }

    public static int[] getTileXPos() {
        return tileXPos;
    }

    public static void setTileXPos(int[] tileXPos) {
        DataTransferHandler.tileXPos = tileXPos;
    }

    public static int[] getTileYPos() {
        return tileYPos;
    }

    public static void setTileYPos(int[] tileYPos) {
        DataTransferHandler.tileYPos = tileYPos;
    }

    public static int[] getTileType() {
        return tileType;
    }

    public static void setTileType(int[] tileType) {
        DataTransferHandler.tileType = tileType;
    }

    public static float getTickTime() {
        return tickTime;
    }

    public static void setTickTime(float tickTime) {
        DataTransferHandler.tickTime = tickTime;
    }

    public static float getTimestamp() {
        return timestamp;
    }

    public static void setTimestamp(float timestamp) {
        DataTransferHandler.timestamp = timestamp;
    }

    public static int getStitchingDirection() {
        return stitchingDirection;
    }

    public static void setStitchingDirection(int stitchingDirection) {
        DataTransferHandler.stitchingDirection = stitchingDirection;
    }

    public static int getTopLeftXPos() {
        return topLeftXPos;
    }

    public static void setTopLeftXPos(int topLeftXPos) {
        DataTransferHandler.topLeftXPos = topLeftXPos;
    }

    public static int getTopLeftYPos() {
        return topLeftYPos;
    }

    public static void setTopLeftYPos(int topLeftYPos) {
        DataTransferHandler.topLeftYPos = topLeftYPos;
    }

    public static int getNumOfSnakeBodyparts() {
        return numOfSnakeBodyparts;
    }

    public static void setNumOfSnakeBodyparts(int numOfSnakeBodyparts) {
        DataTransferHandler.numOfSnakeBodyparts = numOfSnakeBodyparts;
    }

    public static int[] getBodypartXPos() {
        return bodypartXPos;
    }

    public static void setBodypartXPos(int[] bodypartXPos) {
        DataTransferHandler.bodypartXPos = bodypartXPos;
    }

    public static int[] getBodypartYPos() {
        return bodypartYPos;
    }

    public static void setBodypartYPos(int[] bodypartYPos) {
        DataTransferHandler.bodypartYPos = bodypartYPos;
    }

    public static boolean[] getCornerPart() {
        return cornerPart;
    }

    public static void setCornerPart(boolean[] cornerPart) {
        DataTransferHandler.cornerPart = cornerPart;
    }

    public static int[] getOrigin() {
        return origin;
    }

    public static void setOrigin(int[] origin) {
        DataTransferHandler.origin = origin;
    }

    public static int[] getDirection() {
        return direction;
    }

    public static void setDirection(int[] direction) {
        DataTransferHandler.direction = direction;
    }

    public static int getFoodXPos() {
        return foodXPos;
    }

    public static void setFoodXPos(int foodXPos) {
        DataTransferHandler.foodXPos = foodXPos;
    }

    public static int getFoodYPos() {
        return foodYPos;
    }

    public static void setFoodYPos(int foodYPos) {
        DataTransferHandler.foodYPos = foodYPos;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        DataTransferHandler.score = score;
    }
}

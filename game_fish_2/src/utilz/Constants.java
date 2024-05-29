package utilz;

public class Constants {
    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
    public static class PlayerConstants {
        public static final int REST_R = 0;
        public static final int REST_L = 1;
        public static final int SWIM_R = 2;
        public static final int SWIM_L = 3;
        // tra ve so hinh anh
        public static int GetSpriteAmount(int player_action){
            return 6;
        }
    }
}

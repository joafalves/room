package room.game;

public enum Characters {

    REGULAR_MERLIN_MINION(0),
    REGULAR_MORDRED_MINION(1),
    PERCIVAL(2),
    MORGANA(3),
    MERLIN(4),
    ASSASSIN(5);

    private int value = 0;

    private Characters(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
    
    public static String getRoleName(int value) {
        switch (value) {
            case 0:
                return "Servant of Merlin";
            case 1:
                return "Servant of Mordred";
            case 2:
                return "Percival";
            case 3:
                return "Morgana";
            case 4:
                return "Merlin";
            case 5:
                return "Assassin";
            default:
                return "";
        }
    }
}

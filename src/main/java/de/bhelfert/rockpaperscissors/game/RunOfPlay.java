package de.bhelfert.rockpaperscissors.game;

public class RunOfPlay {

    private Enum<?> itemPlayer1;
    private Enum<?> itemPlayer2;
    private GameResult gameResult;
    private String gameResultReason;

    public Enum<?> getItemPlayer1() {
        return itemPlayer1;
    }

    public void setItemPlayer1(Enum<?> itemPlayer1) {
        this.itemPlayer1 = itemPlayer1;
    }

    public Enum<?> getItemPlayer2() {
        return itemPlayer2;
    }

    public void setItemPlayer2(Enum<?> itemPlayer2) {
        this.itemPlayer2 = itemPlayer2;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResultReason(String gameResultReason) {
        this.gameResultReason = gameResultReason;
    }

    public String getGameResultReason() {
        return gameResultReason;
    }

    public boolean isTie() {
        return gameResult == GameResult.Tie;
    }

    public boolean hasPlayer1Won() {
        return gameResult == GameResult.Player1Wins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RunOfPlay runOfPlay = (RunOfPlay) o;
        if (itemPlayer1 != null ? !itemPlayer1.equals(runOfPlay.itemPlayer1) : runOfPlay.itemPlayer1 != null)
            return false;
        if (itemPlayer2 != null ? !itemPlayer2.equals(runOfPlay.itemPlayer2) : runOfPlay.itemPlayer2 != null)
            return false;
        if (gameResult != runOfPlay.gameResult) return false;
        return !(gameResultReason != null ? !gameResultReason.equals(runOfPlay.gameResultReason) : runOfPlay.gameResultReason != null);

    }

    @Override
    public int hashCode() {
        int result = itemPlayer1 != null ? itemPlayer1.hashCode() : 0;
        result = 31 * result + (itemPlayer2 != null ? itemPlayer2.hashCode() : 0);
        result = 31 * result + (gameResult != null ? gameResult.hashCode() : 0);
        result = 31 * result + (gameResultReason != null ? gameResultReason.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RunOfPlay{" +
                "itemPlayer1=" + itemPlayer1 +
                ", itemPlayer2=" + itemPlayer2 +
                ", gameResult=" + gameResult +
                ", gameResultReason='" + gameResultReason + '\'' +
                '}';
    }
}

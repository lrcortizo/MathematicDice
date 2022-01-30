package com.lrcortizo.android.mathematicdice.core;

import java.io.Serializable;

import lombok.Data;

@Data
public class Game implements Serializable {

    final String player1;
    final String player2;
    Dice dice;
    int target;
    String result1;
    String result2;

    public Game(final String player1, final String player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void setDices(final Dice d, final int target) {
        this.dice = d;
        this.target = target;
    }

    public String winner() {
        String toRet = "";
        double r1 = Double.parseDouble(result1);
        double r2 = Double.parseDouble(result2);
        if (result1 == null && result2 == null) {
            toRet = "Draw";
        } else if (result1 == null) {
            toRet = player2;
        } else if (result2 == null) {
            toRet = player1;
        } else if (r1 == r2) {
            toRet = "Draw";
        } else if (r1 < target && r2 < target) {
            if (r1 > r2) {
                toRet = player1;
            } else {
                toRet = player2;
            }
        } else if (r1 > target && r2 > target) {
            if (r1 < r2) {
                toRet = player1;
            } else {
                toRet = player2;
            }
        } else if (r1 > target && r2 < target) {
            if ((r1 - target) == (target - r2)) {
                toRet = "Draw";
            } else if ((r1 - target) < (target - r2)) {
                toRet = player1;
            } else if ((r1 - target) > (target - r2)) {
                toRet = player2;
            }
        } else if (r1 < target && r2 > target) {
            if ((r2 - target) == (target - r1)) {
                toRet = "Draw";
            } else if ((r2 - target) < (target - r1)) {
                toRet = player2;
            } else if ((r2 - target) > (target - r1)) {
                toRet = player1;
            }
        }
        return toRet;
    }

    public String getResult1() {
        return result1;
    }

    public void setResult1(String r) {
        result1 = r;
    }

    public String getResult2() {
        return result2;
    }

    public void setResult2(String r) {
        result2 = r;
    }

    public Dice getDice() {
        return dice;
    }

    public int getTarget() {
        return target;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }


}

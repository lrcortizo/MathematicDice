package com.lrcortizo.android.mathematicdice.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import lombok.Value;
import lombok.experimental.NonFinal;

@Value
public class Dice implements Serializable {

    int sideNumber;

    @NonFinal
    List<String> diceThrows;

    public Dice(final int sideNumber) {
        this.sideNumber = sideNumber;
        this.diceThrows = new ArrayList<>();
    }

    public void throwDice() {
        IntStream.range(0, sideNumber).forEach(i -> {
            int result = (int) (Math.random() * this.sideNumber + 1);
            this.diceThrows.add(String.valueOf(result));
        });
    }
}

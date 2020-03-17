package edu.touro.mco364;

import java.awt.*;

public enum CellState {
    NONE,
    BLACK,
    WHITE;

    public String toString() {
        return this.equals(CellState.NONE) ? " " : this.equals(CellState.BLACK) ? "●" : "○";
    }

    public Color getColor() {
        return this.equals(CellState.NONE) ? Color.decode("#485d3f") : this.equals(CellState.BLACK) ? Color.BLACK : Color.WHITE;
    }
}

package de.lukaspellny.labyreplay.api;

public final class RoundStats {

    private int kills;
    private int beds;
    private int deaths;
    private int hits;

    public void recordKill() {
        this.kills++;
    }

    public void recordBedBreak() {
        this.beds++;
    }

    public void recordDeath() {
        this.deaths++;
    }

    public void recordHit() {
        this.hits++;
    }

    public int kills() {
        return this.kills;
    }

    public int beds() {
        return this.beds;
    }

    public int deaths() {
        return this.deaths;
    }

    public int hits() {
        return this.hits;
    }

    public void reset() {
        this.kills = 0;
        this.beds = 0;
        this.deaths = 0;
        this.hits = 0;
    }
}

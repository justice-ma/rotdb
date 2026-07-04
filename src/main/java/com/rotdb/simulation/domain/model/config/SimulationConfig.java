package com.rotdb.simulation.domain.model.config;

public class SimulationConfig {
    private ProcMode procMode;
    private Long randomSeed;

    public static SimulationConfig defaults() {
        SimulationConfig config = new SimulationConfig();
        config.setProcMode(ProcMode.FORCED);
        config.setRandomSeed(null);
        return config;
    }

    public ProcMode getProcMode() {
        return procMode;
    }

    public void setProcMode(ProcMode procMode) {
        this.procMode = procMode;
    }

    public Long getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(Long randomSeed) {
        this.randomSeed = randomSeed;
    }
}

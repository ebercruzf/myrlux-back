package com.ebercruz.myrluxb.deepseek.api.model;

public class OllamaRequest {
    private String model;
    private String prompt;
    private boolean stream;
    private double temperature;

    // Constructor por defecto necesario para la serialización
    public OllamaRequest() {}

    // Constructor completo
    public OllamaRequest(String model, String prompt, boolean stream, double temperature) {
        this.model = model;
        this.prompt = prompt;
        this.stream = stream;
        this.temperature = temperature;
    }

    // Getters y setters necesarios para la serialización JSON
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public boolean isStream() { return stream; }
    public void setStream(boolean stream) { this.stream = stream; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
}

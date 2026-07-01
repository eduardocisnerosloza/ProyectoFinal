package c2_negocio;

public class ResultadoEvaluacion {
    private int puntaje;
    private String observaciones;

    public ResultadoEvaluacion(int puntaje, String observaciones) {
        this.puntaje = puntaje;
        this.observaciones = observaciones;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getPorcentajeCompatibilidad() {
        return puntaje;
    }

    public String getObservaciones() {
        return observaciones;
    }
}

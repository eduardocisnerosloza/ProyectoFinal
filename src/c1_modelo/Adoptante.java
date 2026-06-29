package c1_modelo;

public class Adoptante extends Persona {
    private double presupuestoMensual;
    private int horasFueraCasa;
    private boolean experienciaPrevia;

    public Adoptante(String cedula, String nombre, double presupuestoMensual, int horasFueraCasa, boolean experienciaPrevia) {
        super(cedula, nombre);
        this.presupuestoMensual = presupuestoMensual;
        this.horasFueraCasa = horasFueraCasa;
        this.experienciaPrevia = experienciaPrevia;
    }

    public double getPresupuestoMensual() { return presupuestoMensual; }
    public int getHorasFueraCasa() { return horasFueraCasa; }
    public boolean tieneExperiencia() { return experienciaPrevia; }

    @Override
    public String toString() {
        return getNombre() + " (C.I: " + getCedula() + ")";
    }
}
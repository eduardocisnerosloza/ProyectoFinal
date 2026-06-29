package c1_modelo;

public class Adoptante extends Persona {
    private double presupuestoMensual;
    private int horasFueraCasa;
    private boolean experienciaPrevia;
    private boolean vetado;

    public Adoptante(String cedula, String nombre, double presupuestoMensual, int horasFueraCasa, boolean experienciaPrevia) {
        super(cedula, nombre);
        this.presupuestoMensual = presupuestoMensual;
        this.horasFueraCasa = horasFueraCasa;
        this.experienciaPrevia = experienciaPrevia;
        this.vetado = false;
    }

    public double getPresupuestoMensual() { return presupuestoMensual; }
    public int getHorasFueraCasa() { return horasFueraCasa; }
    public boolean tieneExperiencia() { return experienciaPrevia; }
    public boolean isVetado() { return vetado; }
    public void setVetado(boolean vetado) { this.vetado = vetado; }

    @Override
    public String toString() {
        String alerta = vetado ? " [VETADO]" : "";
        return getNombre() + " (C.I: " + getCedula() + ")" + alerta;
    }
}
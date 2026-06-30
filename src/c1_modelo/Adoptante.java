package c1_modelo;

public class Adoptante extends Persona {
    private String tipoVivienda;
    private double areaMetros;
    private double presupuestoMensual;
    private int horasFueraCasa;
    private boolean experienciaPrevia;
    private boolean vetado;

    public Adoptante(String cedula, String nombre, String telefono, String tipoVivienda, double areaMetros, double presupuestoMensual, int horasFueraCasa, boolean experienciaPrevia) {
        super(cedula, nombre, telefono);
        this.tipoVivienda = tipoVivienda;
        this.areaMetros = areaMetros;
        this.presupuestoMensual = presupuestoMensual;
        this.horasFueraCasa = horasFueraCasa;
        this.experienciaPrevia = experienciaPrevia;
        this.vetado = false;
    }

    public String getTipoVivienda() { return tipoVivienda; }
    public double getAreaMetros() { return areaMetros; }
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
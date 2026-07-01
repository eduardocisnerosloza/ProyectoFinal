package c1_modelo;

public class Adoptante extends Persona {
    private TipoVivienda tipoVivienda;
    private boolean viviendaPropia;
    private int minutosPaseoDiario;
    private double presupuestoMensual;
    private int horasSoloAlDia;
    private boolean experienciaPrevia;
    private boolean vetado;

    public Adoptante(String cedula, String nombre, String telefono, TipoVivienda tipoVivienda, boolean viviendaPropia, int minutosPaseoDiario, double presupuestoMensual, int horasSoloAlDia, boolean experienciaPrevia) {
        super(cedula, nombre, telefono);
        this.tipoVivienda = tipoVivienda;
        this.viviendaPropia = viviendaPropia;
        this.minutosPaseoDiario = minutosPaseoDiario;
        this.presupuestoMensual = presupuestoMensual;
        this.horasSoloAlDia = horasSoloAlDia;
        this.experienciaPrevia = experienciaPrevia;
        this.vetado = false;
    }

    public TipoVivienda getTipoVivienda() {
        return tipoVivienda;
    }

    public boolean isViviendaPropia() {
        return viviendaPropia;
    }

    public int getMinutosPaseoDiario() {
        return minutosPaseoDiario;
    }

    public double getPresupuestoMensual() {
        return presupuestoMensual;
    }

    public int getHorasSoloAlDia() {
        return horasSoloAlDia;
    }

    public boolean tieneExperiencia() {
        return experienciaPrevia;
    }

    public boolean isVetado() {
        return vetado;
    }

    public void setVetado(boolean vetado) {
        this.vetado = vetado;
    }

    @Override
    public String toString() {
        String alerta = vetado ? " [VETADO]" : "";
        return getNombre() + " (C.I: " + getCedula() + ")" + alerta;
    }
}

package c1_modelo;

// Subclase que deriva de Persona
public class Adoptante extends Persona {
    private double presupuestoMensual;
    private int horasFueraCasa;
    private boolean experienciaPrevia;

    public Adoptante(String cedula, String nombre, double presupuestoMensual, int horasFueraCasa, boolean experienciaPrevia) {
        // Invocación obligatoria al constructor de la superclase
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
        // Usamos getCedula() y getNombre() que son heredados de Persona
        return "Adoptante [" + getCedula() + "] " + getNombre() + " | Presupuesto: $" + presupuestoMensual + " | Horas fuera: " + horasFueraCasa;
    }
}
package c1_modelo;

public class Perro {
    private String id;
    private String nombre;
    private int nivelEnergia;
    private boolean condicionMedicaEspecial;
    private String estado;

    public Perro(String id, String nombre, int nivelEnergia, boolean condicionMedicaEspecial) {
        this.id = id;
        this.nombre = nombre;
        this.nivelEnergia = nivelEnergia;
        this.condicionMedicaEspecial = condicionMedicaEspecial;
        this.estado = "En Cuarentena";
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getNivelEnergia() { return nivelEnergia; }
    public boolean tieneCondicionMedica() { return condicionMedicaEspecial; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return nombre + " (ID: " + id + ") - Energia: " + nivelEnergia + " - Estado: " + estado;
    }
}
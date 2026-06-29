package c1_modelo;

public abstract class Animal {
    private String id;
    private String nombre;
    private int nivelEnergia;
    private boolean condicionMedicaEspecial;
    private String estado;

    public Animal(String id, String nombre, int nivelEnergia, boolean condicionMedicaEspecial) {
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

    public abstract String getTipo();

    @Override
    public String toString() {
        return nombre + " (" + getTipo() + " - ID: " + id + ") - Energia: " + nivelEnergia + " - Estado: " + estado;
    }
}
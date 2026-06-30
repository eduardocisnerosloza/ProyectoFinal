package c1_modelo;

public abstract class Animal {
    private String id;
    private String nombre;
    private String raza;
    private int edad;
    private String tamano;
    private int nivelEnergia;
    private boolean condicionMedicaEspecial;
    private int criticidadMedica;
    private String estado;
    private Adoptante adoptante;

    public Animal(String id, String nombre, String raza, int edad, String tamano, int nivelEnergia, boolean condicionMedicaEspecial, int criticidadMedica) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.tamano = tamano;
        this.nivelEnergia = nivelEnergia;
        this.condicionMedicaEspecial = condicionMedicaEspecial;
        this.criticidadMedica = criticidadMedica;
        this.estado = "En Cuarentena";
        this.adoptante = null;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getRaza() { return raza; }
    public int getEdad() { return edad; }
    public String getTamano() { return tamano; }
    public int getNivelEnergia() { return nivelEnergia; }
    public boolean tieneCondicionMedica() { return condicionMedicaEspecial; }
    public int getCriticidadMedica() { return criticidadMedica; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Adoptante getAdoptante() { return adoptante; }
    public void setAdoptante(Adoptante adoptante) { this.adoptante = adoptante; }

    public String calcularPrioridad() {
        double ip = (criticidadMedica * 0.7) + (edad * 0.3);
        if (ip >= 4.0) {
            return "Critico";
        } else if (ip >= 2.0) {
            return "Moderado";
        } else {
            return "Estable";
        }
    }

    public abstract String getTipo();

    @Override
    public String toString() {
        return nombre + " (" + getTipo() + " " + raza + " - ID: " + id + ") - Prioridad: " + calcularPrioridad();
    }
}
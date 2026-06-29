package c1_modelo;

public class Persona {
    // Atributos privados que se heredarán indirectamente
    private String cedula;
    private String nombre;

    // Constructor de la superclase
    public Persona(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }

    // Métodos públicos heredados por las subclases
    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }
}

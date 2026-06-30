package c1_modelo;

public class Perro extends Animal {
    public Perro(String id, String nombre, String raza, int edad, String tamano, int nivelEnergia, boolean condicionMedicaEspecial, int criticidadMedica) {
        super(id, nombre, raza, edad, tamano, nivelEnergia, condicionMedicaEspecial, criticidadMedica);
    }

    @Override
    public String getTipo() {
        return "Perro";
    }
}
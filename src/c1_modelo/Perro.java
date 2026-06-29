package c1_modelo;

public class Perro extends Animal {
    public Perro(String id, String nombre, int nivelEnergia, boolean condicionMedicaEspecial) {
        super(id, nombre, nivelEnergia, condicionMedicaEspecial);
    }

    @Override
    public String getTipo() {
        return "Perro";
    }
}
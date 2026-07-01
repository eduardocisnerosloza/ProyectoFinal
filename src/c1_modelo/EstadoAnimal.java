package c1_modelo;

public enum EstadoAnimal {
    DISPONIBLE("Disponible"),
    ADOPTADO("Adoptado"),
    CUARENTENA("En Cuarentena");

    private final String texto;

    EstadoAnimal(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}

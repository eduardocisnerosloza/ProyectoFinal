package c1_modelo;

public enum TamanoAnimal {
    PEQUENO("Pequeño"),
    MEDIANO("Mediano"),
    GRANDE("Grande");

    private final String texto;

    TamanoAnimal(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}

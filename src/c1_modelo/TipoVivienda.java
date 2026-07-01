package c1_modelo;

public enum TipoVivienda {
    CASA("Casa"),
    DEPARTAMENTO("Departamento");

    private final String texto;

    TipoVivienda(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}

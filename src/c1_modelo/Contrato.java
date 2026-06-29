package c1_modelo;

public class Contrato {
    private String idContrato;
    private String fecha;
    private int porcentajeCompatibilidad;
    private Adoptante adoptante;
    private Animal animal;

    public Contrato(String idContrato, String fecha, Adoptante adoptante, Animal animal, int porcentajeCompatibilidad) {
        this.idContrato = idContrato;
        this.fecha = fecha;
        this.adoptante = adoptante;
        this.animal = animal;
        this.porcentajeCompatibilidad = porcentajeCompatibilidad;
    }

    public String generarContrato() {
        return "ID Contrato: " + idContrato + "\n" +
                "Fecha: " + fecha + "\n" +
                "Adoptante: " + adoptante.getNombre() + " (C.I: " + adoptante.getCedula() + ")\n" +
                "Animal Adoptado: " + animal.getNombre() + " (ID: " + animal.getId() + ")\n" +
                "Nivel de Compatibilidad Validado: " + porcentajeCompatibilidad + "%";
    }
}
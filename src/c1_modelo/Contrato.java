package c1_modelo;

public class Contrato {
    private String idContrato;
    private String fecha;
    private int porcentajeCompatibilidad;
    private Adoptante adoptante;
    private Perro perro;

    public Contrato(String idContrato, String fecha, Adoptante adoptante, Perro perro, int porcentajeCompatibilidad) {
        this.idContrato = idContrato;
        this.fecha = fecha;
        this.adoptante = adoptante;
        this.perro = perro;
        this.porcentajeCompatibilidad = porcentajeCompatibilidad;
    }

    public String generarContrato() {
        return "ID Contrato: " + idContrato + "\n" +
                "Fecha: " + fecha + "\n" +
                "Adoptante: " + adoptante.getNombre() + " (C.I: " + adoptante.getCedula() + ")\n" +
                "Perro Adoptado: " + perro.getNombre() + " (ID: " + perro.getId() + ")\n" +
                "Nivel de Compatibilidad Validado: " + porcentajeCompatibilidad + "%";
    }
}
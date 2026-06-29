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

        public void generarContrato() {

            System.out.println("ID Contrato: " + idContrato);
            System.out.println("Fecha: " + fecha);
            System.out.println("Adoptante: " + adoptante.getNombre() + " (C.I: " + adoptante.getCedula() + ")");
            System.out.println("Perro Adoptado: " + perro.getNombre() + " (ID: " + perro.getId() + ")");
            System.out.println("Nivel de Compatibilidad Validado: " + porcentajeCompatibilidad + "%");


        }
    }


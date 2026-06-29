package c1_modelo;

    public class Perro {
        private String id;
        private String nombre;
        private int nivelEnergia; // 1 al 5
        private boolean condicionMedicaEspecial;
        private String estado; // "En Cuarentena", "Disponible", "Adoptado"

        public Perro(String id, String nombre, int nivelEnergia, boolean condicionMedicaEspecial) {
            this.id = id;
            this.nombre = nombre;
            this.nivelEnergia = nivelEnergia;
            this.condicionMedicaEspecial = condicionMedicaEspecial;
            this.estado = "En Cuarentena";
        }

        public String getId() { return id; }
        public String getNombre() { return nombre; }
        public int getNivelEnergia() { return nivelEnergia; }
        public boolean tieneCondicionMedica() { return condicionMedicaEspecial; }
        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }

        @Override
        public String toString() {
            return "Perro [" + id + "] " + nombre + " | Energía: " + nivelEnergia + " | Médico: " + condicionMedicaEspecial + " | Estado: " + estado;
        }
    }


package c2_negocio;

import c1_modelo.Adoptante;
import c1_modelo.Perro;

public class GestorEmparejamiento {

    public int calcularMatch(Adoptante adoptante, Perro perro) {
        int puntaje = 100;

        // Condición médica vs Presupuesto bajo
        if (perro.tieneCondicionMedica() && adoptante.getPresupuestoMensual() < 50.0) {
            System.out.println(" MATCH FALLIDO: El perro requiere cuidados médicos y el presupuesto es insuficiente.");
            return 0;
        }

        // Perro hiperactivo vs Mucho tiempo solo
        if (perro.getNivelEnergia() >= 4 && adoptante.getHorasFueraCasa() > 8) {
            System.out.println(" MATCH FALLIDO: El perro tiene mucha energía para el tiempo que pasará solo.");
            return 0;
        }

        // Ponderación de variables si pasa los filtros
        if (!adoptante.tieneExperiencia() && perro.getNivelEnergia() > 3) {
            puntaje -= 30; // Penalización por falta de experiencia con perros enérgicos
        }

        // Penalización media por tiempo fuera
        if (adoptante.getHorasFueraCasa() > 5 && adoptante.getHorasFueraCasa() <= 8) {
            puntaje -= 15;
        }

        return puntaje;
    }
}


package c2_negocio;

import c1_modelo.Adoptante;
import c1_modelo.Animal;

public class GestorEmparejamiento {

    public int calcularMatch(Adoptante adoptante, Animal animal) {
        int puntaje = 100;

        if (animal.tieneCondicionMedica() && adoptante.getPresupuestoMensual() < 50.0) {
            System.out.println(" MATCH FALLIDO: El animal requiere cuidados medicos y el presupuesto es insuficiente.");
            return 0;
        }

        if (animal.getNivelEnergia() >= 4 && adoptante.getHorasFueraCasa() > 8) {
            System.out.println(" MATCH FALLIDO: El animal tiene mucha energia para el tiempo que pasara solo.");
            return 0;
        }

        if (animal.tieneCondicionMedica() && adoptante.getHorasFueraCasa() > 6) {
            System.out.println(" MATCH FALLIDO: El animal requiere atencion medica y pasara demasiado tiempo solo.");
            return 0;
        }

        if (!adoptante.tieneExperiencia() && animal.getNivelEnergia() > 3) {
            puntaje -= 30;
        }

        if (adoptante.getHorasFueraCasa() > 8) {
            puntaje -= 30;
        } else if (adoptante.getHorasFueraCasa() > 5 && adoptante.getHorasFueraCasa() <= 8) {
            puntaje -= 15;
        }

        return puntaje;
    }
}
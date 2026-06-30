package c2_negocio;

import c1_modelo.Adoptante;
import c1_modelo.Animal;

public class GestorEmparejamiento {

    public int calcularMatch(Adoptante adoptante, Animal animal) {
        if (adoptante.isVetado()) {
            return 0;
        }

        if (adoptante.getTipoVivienda().equals("Departamento") && adoptante.getAreaMetros() < 50.0 && animal.getTamano().equals("Grande")) {
            return 0;
        }

        int puntaje = 100;

        if (animal.tieneCondicionMedica() && adoptante.getPresupuestoMensual() < 100.0) {
            return 0;
        }

        if (animal.getNivelEnergia() >= 4 && adoptante.getHorasFueraCasa() > 8) {
            return 0;
        }

        if (animal.tieneCondicionMedica() && adoptante.getHorasFueraCasa() > 6) {
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
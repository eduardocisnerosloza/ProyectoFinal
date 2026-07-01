package c2_negocio;

import c1_modelo.Adoptante;
import c1_modelo.Animal;
import c1_modelo.EstadoAnimal;
import c1_modelo.TamanoAnimal;
import c1_modelo.TipoVivienda;

public class GestorEmparejamiento {

    public ResultadoEvaluacion calcularMatch(Adoptante adoptante, Animal animal) {
        StringBuilder observaciones = new StringBuilder();

        if (adoptante.isVetado()) {
            observaciones.append("- El adoptante se encuentra vetado por una devolución anterior.\n");
            return new ResultadoEvaluacion(0, observaciones.toString());
        }

        if (animal.getEstado() != EstadoAnimal.DISPONIBLE) {
            observaciones.append("- El perro no está disponible para adopción. Estado actual: ")
                    .append(animal.getEstado()).append(".\n");
            return new ResultadoEvaluacion(0, observaciones.toString());
        }

        if (animal.getNivelEnergia() >= 4 && adoptante.getMinutosPaseoDiario() < 30) {
            observaciones.append("- El perro tiene energía alta y el paseo diario ofrecido es menor a 30 minutos.\n");
            return new ResultadoEvaluacion(0, observaciones.toString());
        }

        if (animal.tieneCondicionMedica() && adoptante.getPresupuestoMensual() < 100.0) {
            observaciones.append("- El perro tiene condición médica especial y el presupuesto mensual es insuficiente.\n");
            return new ResultadoEvaluacion(0, observaciones.toString());
        }

        if (adoptante.getHorasSoloAlDia() >= 10) {
            observaciones.append("- El perro pasaría 10 horas o más solo al día.\n");
            return new ResultadoEvaluacion(0, observaciones.toString());
        }

        int puntaje = 100;

        if (!adoptante.isViviendaPropia()) {
            puntaje -= 8;
            observaciones.append("- La vivienda no es propia; se aplica una penalización moderada por estabilidad habitacional.\n");
        }

        puntaje = evaluarPaseo(adoptante, animal, puntaje, observaciones);
        puntaje = evaluarVivienda(adoptante, animal, puntaje, observaciones);
        puntaje = evaluarHorasSolo(adoptante, puntaje, observaciones);
        puntaje = evaluarExperiencia(adoptante, animal, puntaje, observaciones);
        puntaje = evaluarPresupuesto(adoptante, animal, puntaje, observaciones);

        puntaje = limitarPuntaje(puntaje);

        if (observaciones.length() == 0) {
            observaciones.append("- No se detectaron factores importantes de riesgo en la compatibilidad.\n");
        }

        return new ResultadoEvaluacion(puntaje, observaciones.toString());
    }

    private int evaluarPaseo(Adoptante adoptante, Animal animal, int puntaje, StringBuilder observaciones) {
        int minutos = adoptante.getMinutosPaseoDiario();
        int energia = animal.getNivelEnergia();

        if (energia >= 4) {
            if (minutos < 60) {
                puntaje -= 30;
                observaciones.append("- El perro tiene energía alta y el paseo diario es menor a 60 minutos.\n");
            } else if (minutos < 90) {
                puntaje -= 10;
                observaciones.append("- El perro tiene energía alta; el paseo es aceptable, pero podría ser mayor.\n");
            } else {
                puntaje += 5;
                observaciones.append("- El tiempo de paseo es muy adecuado para un perro de energía alta.\n");
            }
        } else if (energia == 3) {
            if (minutos < 45) {
                puntaje -= 20;
                observaciones.append("- El perro tiene energía media y el paseo diario es limitado.\n");
            } else if (minutos >= 60) {
                puntaje += 5;
                observaciones.append("- El tiempo de paseo es adecuado para un perro de energía media.\n");
            }
        } else {
            if (minutos < 30) {
                puntaje -= 10;
                observaciones.append("- Aunque el perro tiene energía baja, el paseo diario es muy reducido.\n");
            } else if (minutos >= 45) {
                puntaje += 5;
                observaciones.append("- El paseo diario es adecuado para un perro de energía baja o moderada.\n");
            }
        }

        return puntaje;
    }

    private int evaluarVivienda(Adoptante adoptante, Animal animal, int puntaje, StringBuilder observaciones) {
        if (adoptante.getTipoVivienda() == TipoVivienda.DEPARTAMENTO) {
            if (animal.getTamano() == TamanoAnimal.GRANDE) {
                if (adoptante.getMinutosPaseoDiario() >= 90) {
                    puntaje -= 5;
                    observaciones.append("- Vive en departamento con un perro grande, pero el paseo diario compensa parcialmente el espacio.\n");
                } else if (adoptante.getMinutosPaseoDiario() >= 60) {
                    puntaje -= 10;
                    observaciones.append("- Vive en departamento con un perro grande; se recomienda reforzar rutina de paseo y enriquecimiento.\n");
                } else {
                    puntaje -= 20;
                    observaciones.append("- Vive en departamento con un perro grande y el paseo diario no compensa suficientemente.\n");
                }
            } else if (animal.getTamano() == TamanoAnimal.MEDIANO) {
                puntaje -= 5;
                observaciones.append("- Vive en departamento con un perro mediano; la penalización es leve.\n");
            }
        }

        return puntaje;
    }

    private int evaluarHorasSolo(Adoptante adoptante, int puntaje, StringBuilder observaciones) {
        int horas = adoptante.getHorasSoloAlDia();

        if (horas > 8) {
            puntaje -= 20;
            observaciones.append("- El perro pasaría más de 8 horas solo al día.\n");
        } else if (horas > 6) {
            puntaje -= 10;
            observaciones.append("- El perro pasaría más de 6 horas solo al día.\n");
        } else if (horas <= 4) {
            puntaje += 5;
            observaciones.append("- El perro pasaría pocas horas solo al día.\n");
        }

        return puntaje;
    }

    private int evaluarExperiencia(Adoptante adoptante, Animal animal, int puntaje, StringBuilder observaciones) {
        if (!adoptante.tieneExperiencia() && animal.getNivelEnergia() >= 4) {
            puntaje -= 15;
            observaciones.append("- El adoptante no tiene experiencia previa y el perro es de energía alta.\n");
        } else if (!adoptante.tieneExperiencia() && animal.getTamano() == TamanoAnimal.GRANDE) {
            puntaje -= 10;
            observaciones.append("- El adoptante no tiene experiencia previa con perros y el perro es grande.\n");
        }

        return puntaje;
    }

    private int evaluarPresupuesto(Adoptante adoptante, Animal animal, int puntaje, StringBuilder observaciones) {
        double presupuesto = adoptante.getPresupuestoMensual();

        if (animal.tieneCondicionMedica()) {
            if (animal.getCriticidadMedica() >= 4 && presupuesto < 150.0) {
                puntaje -= 20;
                observaciones.append("- La condición médica es de alta criticidad y el presupuesto es ajustado.\n");
            } else if (presupuesto >= 200.0) {
                puntaje += 5;
                observaciones.append("- El presupuesto mensual es favorable para cubrir una condición médica especial.\n");
            }
        } else if (presupuesto < 40.0) {
            puntaje -= 10;
            observaciones.append("- El presupuesto mensual es bajo para cubrir gastos básicos del perro.\n");
        }

        return puntaje;
    }

    private int limitarPuntaje(int puntaje) {
        if (puntaje < 0) {
            return 0;
        }
        if (puntaje > 100) {
            return 100;
        }
        return puntaje;
    }
}

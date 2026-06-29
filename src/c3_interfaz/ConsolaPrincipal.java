package c3_interfaz;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import c1_modelo.Adoptante;
import c1_modelo.Perro;
import c1_modelo.Contrato;
import c2_negocio.GestorEmparejamiento;

public class ConsolaPrincipal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorEmparejamiento gestorMatch = new GestorEmparejamiento();

        System.out.println(" SISTEMA DE GESTIÓN DE REFUGIO ");

        // 1. Registro de Perro
        System.out.println("\n Registro de Perro ");
        System.out.print("Ingrese ID del perro: ");
        String idPerro = scanner.nextLine();
        System.out.print("Ingrese nombre del perro: ");
        String nombrePerro = scanner.nextLine();
        System.out.print("Nivel de energía (1-5): ");
        int energia = scanner.nextInt();
        System.out.print("¿Tiene condición médica especial? (true/false): ");
        boolean condicion = scanner.nextBoolean();
        scanner.nextLine();

        Perro perro1 = new Perro(idPerro, nombrePerro, energia, condicion);
        System.out.println(" Perro registrado: " + perro1.toString());

        // 2. Registro de Adoptante
        System.out.println("\n Registro de Adoptante ");
        System.out.print("Ingrese Cédula: ");
        String cedula = scanner.nextLine();
        System.out.print("Ingrese Nombre: ");
        String nombreAdoptante = scanner.nextLine();
        System.out.print("Presupuesto mensual para la mascota ($): ");
        double presupuesto = scanner.nextDouble();
        System.out.print("Horas fuera de casa al día: ");
        int horas = scanner.nextInt();
        System.out.print("¿Tiene experiencia previa con perros? (true/false): ");
        boolean experiencia = scanner.nextBoolean();

        Adoptante adoptante1 = new Adoptante(cedula, nombreAdoptante, presupuesto, horas, experiencia);
        System.out.println("  Adoptante registrado: " + adoptante1.toString());

        // 3. Ejecutar algoritmo de Match
        System.out.println("\n Evaluando Compatibilidad ");
        int porcentajeMatch = gestorMatch.calcularMatch(adoptante1, perro1);

        System.out.println("Porcentaje de compatibilidad: " + porcentajeMatch + "%");

        if (porcentajeMatch >= 70) {
            System.out.println(" ADOPCIÓN VIABLE: El perfil es apto para este perro.");
            perro1.setEstado("Adoptado");

            // Generar fecha actual para el contrato
            String fechaHoy = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

            // Instanciar y generar el contrato
            Contrato contrato = new Contrato("CTR-1001", fechaHoy, adoptante1, perro1, porcentajeMatch);
            contrato.generarContrato();

        } else if (porcentajeMatch > 0) {
            System.out.println(" MATCH BAJO: Se recomienda buscar otras opciones de perros en el refugio.");
        }

        scanner.close();
    }
}


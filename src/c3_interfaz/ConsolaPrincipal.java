package c3_interfaz;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import c1_modelo.Adoptante;
import c1_modelo.Perro;
import c1_modelo.Contrato;
import c2_negocio.GestorEmparejamiento;

public class ConsolaPrincipal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorEmparejamiento gestorMatch = new GestorEmparejamiento();
        ArrayList<Perro> listaPerros = new ArrayList<>();
        ArrayList<Adoptante> listaAdoptantes = new ArrayList<>();

        listaPerros.add(new Perro("P-001", "Canelo", 2, true));
        listaPerros.add(new Perro("P-002", "Quijote", 5, false));

        int opcion = 0;

        while (opcion != 4) {
            System.out.println("\n--- SISTEMA DE GESTION DE REFUGIO ---");
            System.out.println("1. Registrar Perro");
            System.out.println("2. Registrar Adoptante");
            System.out.println("3. Evaluar Compatibilidad");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opcion no valida. Intente de nuevo.");
                continue;
            }

            if (opcion == 1) {
                System.out.print("Ingrese ID del perro: ");
                String idPerro = scanner.nextLine();

                boolean idExiste = false;
                for (Perro p : listaPerros) {
                    if (p.getId().equals(idPerro)) {
                        idExiste = true;
                        break;
                    }
                }

                if (idExiste) {
                    System.out.println("Error: Ya existe un perro registrado con el ID " + idPerro);
                } else {
                    System.out.print("Ingrese nombre del perro: ");
                    String nombrePerro = scanner.nextLine();
                    int energia = 0;
                    boolean condicion = false;

                    while (true) {
                        try {
                            System.out.print("Nivel de energia (1-5): ");
                            energia = Integer.parseInt(scanner.nextLine());
                            System.out.print("Tiene condicion medica especial? (true/false): ");
                            condicion = Boolean.parseBoolean(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Formato invalido. Intente de nuevo.");
                        }
                    }
                    Perro nuevoPerro = new Perro(idPerro, nombrePerro, energia, condicion);
                    listaPerros.add(nuevoPerro);
                    System.out.println("Perro registrado exitosamente.");
                }

            } else if (opcion == 2) {
                System.out.print("Ingrese Cedula: ");
                String cedula = scanner.nextLine();

                boolean cedulaExiste = false;
                for (Adoptante a : listaAdoptantes) {
                    if (a.getCedula().equals(cedula)) {
                        cedulaExiste = true;
                        break;
                    }
                }

                if (cedulaExiste) {
                    System.out.println("Error: Ya existe un adoptante registrado con la Cedula " + cedula);
                } else {
                    System.out.print("Ingrese Nombre: ");
                    String nombreAdoptante = scanner.nextLine();
                    double presupuesto = 0.0;
                    int horas = 0;
                    boolean experiencia = false;

                    while (true) {
                        try {
                            System.out.print("Presupuesto mensual ($): ");
                            presupuesto = Double.parseDouble(scanner.nextLine());
                            System.out.print("Horas fuera de casa al dia: ");
                            horas = Integer.parseInt(scanner.nextLine());
                            System.out.print("Tiene experiencia previa? (true/false): ");
                            experiencia = Boolean.parseBoolean(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Formato invalido. Intente de nuevo.");
                        }
                    }
                    Adoptante nuevoAdoptante = new Adoptante(cedula, nombreAdoptante, presupuesto, horas, experiencia);
                    listaAdoptantes.add(nuevoAdoptante);
                    System.out.println("Adoptante registrado exitosamente.");
                }

            } else if (opcion == 3) {
                if (listaPerros.isEmpty() || listaAdoptantes.isEmpty()) {
                    System.out.println("Debe registrar al menos un perro y un adoptante antes de evaluar.");
                    continue;
                }

                System.out.println("\nPerros disponibles:");
                for (int i = 0; i < listaPerros.size(); i++) {
                    System.out.println(i + ". " + listaPerros.get(i).toString());
                }
                System.out.print("Seleccione el numero del perro: ");
                int indexPerro = Integer.parseInt(scanner.nextLine());

                System.out.println("\nAdoptantes registrados:");
                for (int i = 0; i < listaAdoptantes.size(); i++) {
                    System.out.println(i + ". " + listaAdoptantes.get(i).toString());
                }
                System.out.print("Seleccione el numero del adoptante: ");
                int indexAdoptante = Integer.parseInt(scanner.nextLine());

                Perro perroSeleccionado = listaPerros.get(indexPerro);
                Adoptante adoptanteSeleccionado = listaAdoptantes.get(indexAdoptante);

                int porcentajeMatch = gestorMatch.calcularMatch(adoptanteSeleccionado, perroSeleccionado);
                System.out.println("\nPorcentaje de compatibilidad: " + porcentajeMatch + "%");

                if (porcentajeMatch >= 70) {
                    System.out.println("ADOPCION VIABLE: El perfil es apto para este perro.");
                    perroSeleccionado.setEstado("Adoptado");
                    String fechaHoy = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                    Contrato contrato = new Contrato("CTR-" + System.currentTimeMillis(), fechaHoy, adoptanteSeleccionado, perroSeleccionado, porcentajeMatch);
                    System.out.println("\n--- CONTRATO GENERADO ---");
                    System.out.println(contrato.generarContrato());
                } else if (porcentajeMatch > 0) {
                    System.out.println("MATCH BAJO: Se recomienda buscar otras opciones en el refugio.");
                }
            }
        }
        scanner.close();
    }
}
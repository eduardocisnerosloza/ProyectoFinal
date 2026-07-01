package c3_interfaz;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import c1_modelo.Adoptante;
import c1_modelo.Animal;
import c1_modelo.Contrato;
import c1_modelo.EstadoAnimal;
import c1_modelo.Perro;
import c1_modelo.TamanoAnimal;
import c1_modelo.TipoVivienda;
import c2_negocio.GestorEmparejamiento;
import c2_negocio.ResultadoEvaluacion;

public class VentanaPrincipal extends JFrame {

    private ArrayList<Animal> listaAnimales;
    private ArrayList<Adoptante> listaAdoptantes;
    private GestorEmparejamiento gestorMatch;

    public VentanaPrincipal() {
        listaAnimales = new ArrayList<>();
        listaAdoptantes = new ArrayList<>();
        gestorMatch = new GestorEmparejamiento();

        cargarDatosIniciales();

        setTitle("Sistema de Gestión de Refugio");
        setSize(450, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));

        JButton btnRegistrarPerro = new JButton("Registrar Perro");
        JButton btnRegistrarAdoptante = new JButton("Registrar Adoptante");
        JButton btnEvaluar = new JButton("Evaluar Compatibilidad");
        JButton btnRanking = new JButton("Ranking de Adoptantes por Perro");
        JButton btnDevolucion = new JButton("Registrar Devolución");
        JButton btnSalir = new JButton("Salir");

        add(btnRegistrarPerro);
        add(btnRegistrarAdoptante);
        add(btnEvaluar);
        add(btnRanking);
        add(btnDevolucion);
        add(btnSalir);

        btnRegistrarPerro.addActionListener(e -> registrarPerro());
        btnRegistrarAdoptante.addActionListener(e -> registrarAdoptante());
        btnEvaluar.addActionListener(e -> evaluarCompatibilidad());
        btnRanking.addActionListener(e -> mostrarRankingAdoptantes());
        btnDevolucion.addActionListener(e -> registrarDevolucion());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void cargarDatosIniciales() {
        Perro perro1 = new Perro("P-001", "Canelo", "Pitbull", 3, TamanoAnimal.MEDIANO, 2, true, 4);
        perro1.setEstado(EstadoAnimal.DISPONIBLE);

        Perro perro2 = new Perro("P-002", "Quijote", "Pitbull", 4, TamanoAnimal.GRANDE, 5, false, 0);
        perro2.setEstado(EstadoAnimal.DISPONIBLE);

        listaAnimales.add(perro1);
        listaAnimales.add(perro2);
    }

    private void registrarPerro() {
        JTextField txtId = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtRaza = new JTextField();
        JTextField txtEdad = new JTextField();
        JComboBox<TamanoAnimal> cmbTamano = new JComboBox<>(TamanoAnimal.values());
        JTextField txtEnergia = new JTextField();
        JCheckBox chkCondicion = new JCheckBox("¿Tiene condición médica especial?");
        JTextField txtCriticidad = new JTextField("0");

        Object[] message = {
                "ID del perro:", txtId,
                "Nombre:", txtNombre,
                "Raza:", txtRaza,
                "Edad (años):", txtEdad,
                "Tamaño:", cmbTamano,
                "Nivel de energía (1-5):", txtEnergia,
                chkCondicion,
                "Criticidad médica (1-5 si tiene condición; 0 si no tiene):", txtCriticidad
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Registrar Perro", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String id = txtId.getText().trim();

                if (id.isEmpty() || !id.matches("[A-Za-z0-9\\-]+")) {
                    mostrarError("El ID no puede estar vacío y solo debe contener letras, números o guiones.");
                    return;
                }

                for (Animal a : listaAnimales) {
                    if (a.getId().equalsIgnoreCase(id)) {
                        mostrarError("Ya existe un animal con el ID " + id + ".");
                        return;
                    }
                }

                String nombre = txtNombre.getText().trim();
                String raza = txtRaza.getText().trim();

                if (!textoSoloLetras(nombre)) {
                    mostrarError("El nombre del perro no puede estar vacío y solo debe contener letras o espacios.");
                    return;
                }

                if (!textoSoloLetras(raza)) {
                    mostrarError("La raza no puede estar vacía y solo debe contener letras o espacios.");
                    return;
                }

                int edad = leerEntero(txtEdad, "edad", 0, 25);
                TamanoAnimal tamano = (TamanoAnimal) cmbTamano.getSelectedItem();
                int energia = leerEntero(txtEnergia, "nivel de energía", 1, 5);
                boolean condicion = chkCondicion.isSelected();
                int criticidad;

                if (condicion) {
                    criticidad = leerEntero(txtCriticidad, "criticidad médica", 1, 5);
                } else {
                    criticidad = 0;
                }

                Perro nuevoPerro = new Perro(id, nombre, raza, edad, tamano, energia, condicion, criticidad);
                nuevoPerro.setEstado(EstadoAnimal.DISPONIBLE);
                listaAnimales.add(nuevoPerro);

                JOptionPane.showMessageDialog(this,
                        "Perro registrado exitosamente.\n" +
                                "Estado inicial: " + nuevoPerro.getEstado() + ".\n" +
                                "Total de perros registrados: " + listaAnimales.size() + ".\n" +
                                "Perros disponibles: " + obtenerAnimalesPorEstado(EstadoAnimal.DISPONIBLE).size() + ".");

            } catch (NumberFormatException ex) {
                mostrarError(ex.getMessage());
            }
        }
    }

    private void registrarAdoptante() {
        JTextField txtCedula = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtTelefono = new JTextField();
        JComboBox<TipoVivienda> cmbVivienda = new JComboBox<>(TipoVivienda.values());
        JCheckBox chkPropia = new JCheckBox("¿La vivienda es propia?");
        JTextField txtPaseo = new JTextField();
        JTextField txtPresupuesto = new JTextField();
        JTextField txtHorasSolo = new JTextField();
        JCheckBox chkExperiencia = new JCheckBox("¿Tiene experiencia previa con perros?");

        Object[] message = {
                "Cédula:", txtCedula,
                "Nombre:", txtNombre,
                "Teléfono:", txtTelefono,
                "Tipo de vivienda:", cmbVivienda,
                chkPropia,
                "Minutos de paseo efectivo diario:", txtPaseo,
                "Presupuesto mensual para el perro ($):", txtPresupuesto,
                "Horas que el perro estará solo al día:", txtHorasSolo,
                chkExperiencia
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Registrar Adoptante", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String cedula = txtCedula.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String nombre = txtNombre.getText().trim();

                if (!cedula.matches("\\d{10}")) {
                    mostrarError("La cédula debe contener exactamente 10 dígitos.");
                    return;
                }

                if (!telefono.matches("\\d{7,10}")) {
                    mostrarError("El teléfono debe contener entre 7 y 10 dígitos.");
                    return;
                }

                if (!textoSoloLetras(nombre)) {
                    mostrarError("El nombre no puede estar vacío y solo debe contener letras o espacios.");
                    return;
                }

                for (Adoptante a : listaAdoptantes) {
                    if (a.getCedula().equals(cedula)) {
                        mostrarError("Ya existe un adoptante con la cédula " + cedula + ".");
                        return;
                    }
                }

                TipoVivienda tipoVivienda = (TipoVivienda) cmbVivienda.getSelectedItem();
                boolean viviendaPropia = chkPropia.isSelected();
                int minutosPaseo = leerEntero(txtPaseo, "minutos de paseo", 0, 300);
                double presupuesto = leerDouble(txtPresupuesto, "presupuesto mensual", 0, 5000);
                int horasSolo = leerEntero(txtHorasSolo, "horas que el perro estará solo", 0, 24);
                boolean experiencia = chkExperiencia.isSelected();

                Adoptante nuevoAdoptante = new Adoptante(cedula, nombre, telefono, tipoVivienda, viviendaPropia, minutosPaseo, presupuesto, horasSolo, experiencia);
                listaAdoptantes.add(nuevoAdoptante);

                JOptionPane.showMessageDialog(this,
                        "Adoptante registrado exitosamente.\n" +
                                "Total de adoptantes registrados: " + listaAdoptantes.size() + ".");

            } catch (NumberFormatException ex) {
                mostrarError(ex.getMessage());
            }
        }
    }

    private void evaluarCompatibilidad() {
        ArrayList<Animal> disponibles = obtenerAnimalesPorEstado(EstadoAnimal.DISPONIBLE);

        if (listaAnimales.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay perros registrados en el sistema.");
            return;
        }

        if (disponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Hay perros registrados, pero ninguno está disponible para adopción.\n" +
                            "Revise si están en cuarentena o adoptados.\n\n" +
                            generarResumenRegistros());
            return;
        }

        if (listaAdoptantes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay adoptantes registrados en el sistema.\n\n" +
                            generarResumenRegistros());
            return;
        }

        JComboBox<Animal> comboAnimales = new JComboBox<>(disponibles.toArray(new Animal[0]));
        JComboBox<Adoptante> comboAdoptantes = new JComboBox<>(listaAdoptantes.toArray(new Adoptante[0]));

        Object[] message = {
                "Seleccione el perro:", comboAnimales,
                "Seleccione el adoptante:", comboAdoptantes
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Evaluar Compatibilidad", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Animal animalSel = (Animal) comboAnimales.getSelectedItem();
            Adoptante adoptanteSel = (Adoptante) comboAdoptantes.getSelectedItem();

            ResultadoEvaluacion resultado = gestorMatch.calcularMatch(adoptanteSel, animalSel);
            int puntaje = resultado.getPuntaje();

            String evaluacion = construirTextoEvaluacion(resultado);

            if (puntaje >= 70) {
                evaluacion += "\nADOPCIÓN VIABLE: El perfil es apto.\n\n¿Desea confirmar la adopción?";
                int confirmacion = JOptionPane.showConfirmDialog(this, evaluacion, "Confirmar Adopción", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    animalSel.setEstado(EstadoAnimal.ADOPTADO);
                    animalSel.setAdoptante(adoptanteSel);

                    String fechaHoy = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                    Contrato contrato = new Contrato("CTR-" + System.currentTimeMillis(), fechaHoy, adoptanteSel, animalSel, puntaje);

                    JOptionPane.showMessageDialog(this, "--- CONTRATO GENERADO ---\n" + contrato.generarContrato());
                } else {
                    JOptionPane.showMessageDialog(this, "Adopción cancelada por el usuario.");
                }

            } else if (puntaje > 0) {
                evaluacion += "\nMATCH BAJO: Se recomienda buscar otras opciones o mejorar las condiciones del adoptante.";
                JOptionPane.showMessageDialog(this, evaluacion);
            } else {
                evaluacion += "\nMATCH FALLIDO: No se cumplen los requisitos mínimos.";
                JOptionPane.showMessageDialog(this, evaluacion);
            }
        }
    }

    private void mostrarRankingAdoptantes() {
        ArrayList<Animal> disponibles = obtenerAnimalesPorEstado(EstadoAnimal.DISPONIBLE);

        if (listaAnimales.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay perros registrados en el sistema.");
            return;
        }

        if (disponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Hay perros registrados, pero ninguno está disponible para adopción.\n" +
                            "Revise si están en cuarentena o adoptados.\n\n" +
                            generarResumenRegistros());
            return;
        }

        if (listaAdoptantes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay adoptantes registrados en el sistema.\n\n" +
                            generarResumenRegistros());
            return;
        }

        JComboBox<Animal> comboAnimales = new JComboBox<>(disponibles.toArray(new Animal[0]));

        Object[] message = {
                "Seleccione el perro para comparar adoptantes:", comboAnimales
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Ranking de Adoptantes", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Animal animalSel = (Animal) comboAnimales.getSelectedItem();
            ArrayList<Adoptante> candidatos = new ArrayList<>(listaAdoptantes);

            candidatos.sort(Comparator.comparingInt((Adoptante a) -> gestorMatch.calcularMatch(a, animalSel).getPuntaje()).reversed());

            StringBuilder ranking = new StringBuilder();
            ranking.append("Ranking para: ").append(animalSel.getNombre()).append("\n\n");

            for (int i = 0; i < candidatos.size(); i++) {
                Adoptante candidato = candidatos.get(i);
                ResultadoEvaluacion resultado = gestorMatch.calcularMatch(candidato, animalSel);

                ranking.append(i + 1).append(". ")
                        .append(candidato.getNombre())
                        .append(" - Puntaje exacto: ")
                        .append(resultado.getPuntaje()).append("/100")
                        .append(" - Compatibilidad: ")
                        .append(resultado.getPorcentajeCompatibilidad()).append("%")
                        .append("\n");
            }

            JOptionPane.showMessageDialog(this, ranking.toString());
        }
    }

    private void registrarDevolucion() {
        ArrayList<Animal> adoptados = obtenerAnimalesPorEstado(EstadoAnimal.ADOPTADO);

        if (adoptados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay perros adoptados para devolver.");
            return;
        }

        JComboBox<Animal> comboAnimales = new JComboBox<>(adoptados.toArray(new Animal[0]));

        Object[] message = {
                "Seleccione el perro a devolver:", comboAnimales
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Registrar Devolución", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Animal animalSel = (Animal) comboAnimales.getSelectedItem();
            Adoptante adoptanteSel = animalSel.getAdoptante();

            animalSel.setEstado(EstadoAnimal.CUARENTENA);
            animalSel.setAdoptante(null);

            if (adoptanteSel != null) {
                adoptanteSel.setVetado(true);

                JOptionPane.showMessageDialog(this,
                        "Devolución procesada.\n" +
                                "El perro ingresó a cuarentena.\n" +
                                "El adoptante " + adoptanteSel.getNombre() + " fue VETADO.");
            } else {
                JOptionPane.showMessageDialog(this, "Devolución procesada.\nEl perro ingresó a cuarentena.");
            }
        }
    }

    private ArrayList<Animal> obtenerAnimalesPorEstado(EstadoAnimal estado) {
        ArrayList<Animal> animalesFiltrados = new ArrayList<>();

        for (Animal a : listaAnimales) {
            if (a.getEstado() == estado) {
                animalesFiltrados.add(a);
            }
        }

        return animalesFiltrados;
    }

    private String construirTextoEvaluacion(ResultadoEvaluacion resultado) {
        return "Porcentaje de compatibilidad: " + resultado.getPorcentajeCompatibilidad() + "%\n" +
                "Puntaje exacto: " + resultado.getPuntaje() + "/100\n\n" +
                "Observaciones:\n" + resultado.getObservaciones();
    }

    private String generarResumenRegistros() {
        return "Resumen actual del sistema:\n" +
                "- Total de perros registrados: " + listaAnimales.size() + "\n" +
                "- Perros disponibles: " + obtenerAnimalesPorEstado(EstadoAnimal.DISPONIBLE).size() + "\n" +
                "- Perros adoptados: " + obtenerAnimalesPorEstado(EstadoAnimal.ADOPTADO).size() + "\n" +
                "- Perros en cuarentena: " + obtenerAnimalesPorEstado(EstadoAnimal.CUARENTENA).size() + "\n" +
                "- Adoptantes registrados: " + listaAdoptantes.size();
    }

    private int leerEntero(JTextField campo, String nombreCampo, int minimo, int maximo) throws NumberFormatException {
        String texto = campo.getText().trim();

        if (texto.isEmpty()) {
            throw new NumberFormatException("El campo " + nombreCampo + " no puede estar vacío.");
        }

        int valor;

        try {
            valor = Integer.parseInt(texto);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("El campo " + nombreCampo + " debe ser un número entero válido.");
        }

        if (valor < minimo || valor > maximo) {
            throw new NumberFormatException("El campo " + nombreCampo + " debe estar entre " + minimo + " y " + maximo + ".");
        }

        return valor;
    }

    private double leerDouble(JTextField campo, String nombreCampo, double minimo, double maximo) throws NumberFormatException {
        String texto = campo.getText().trim().replace(',', '.');

        if (texto.isEmpty()) {
            throw new NumberFormatException("El campo " + nombreCampo + " no puede estar vacío.");
        }

        double valor;

        try {
            valor = Double.parseDouble(texto);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("El campo " + nombreCampo + " debe ser un número válido.");
        }

        if (valor < minimo || valor > maximo) {
            throw new NumberFormatException("El campo " + nombreCampo + " debe estar entre " + minimo + " y " + maximo + ".");
        }

        return valor;
    }

    private boolean textoSoloLetras(String texto) {
        return texto != null && texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]{2,}");
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, "Error: " + mensaje);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}

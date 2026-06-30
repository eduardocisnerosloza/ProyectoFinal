package c3_interfaz;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import c1_modelo.Adoptante;
import c1_modelo.Animal;
import c1_modelo.Perro;
import c1_modelo.Contrato;
import c2_negocio.GestorEmparejamiento;

public class VentanaPrincipal extends JFrame {

    private ArrayList<Animal> listaAnimales;
    private ArrayList<Adoptante> listaAdoptantes;
    private GestorEmparejamiento gestorMatch;

    public VentanaPrincipal() {
        listaAnimales = new ArrayList<>();
        listaAdoptantes = new ArrayList<>();
        gestorMatch = new GestorEmparejamiento();

        listaAnimales.add(new Perro("P-001", "Canelo", "Pitbull", 3, "Mediano", 2, true, 4));
        listaAnimales.add(new Perro("P-002", "Quijote", "Pitbull", 4, "Grande", 5, false, 0));

        setTitle("Sistema de Gestion de Refugio");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnRegistrarPerro = new JButton("Registrar Perro");
        JButton btnRegistrarAdoptante = new JButton("Registrar Adoptante");
        JButton btnEvaluar = new JButton("Evaluar Compatibilidad");
        JButton btnDevolucion = new JButton("Registrar Devolucion");
        JButton btnSalir = new JButton("Salir");

        add(btnRegistrarPerro);
        add(btnRegistrarAdoptante);
        add(btnEvaluar);
        add(btnDevolucion);
        add(btnSalir);

        btnRegistrarPerro.addActionListener(e -> registrarPerro());
        btnRegistrarAdoptante.addActionListener(e -> registrarAdoptante());
        btnEvaluar.addActionListener(e -> evaluarCompatibilidad());
        btnDevolucion.addActionListener(e -> registrarDevolucion());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void registrarPerro() {
        JTextField txtId = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtRaza = new JTextField();
        JTextField txtEdad = new JTextField();
        JComboBox<String> cmbTamano = new JComboBox<>(new String[]{"Pequeño", "Mediano", "Grande"});
        JTextField txtEnergia = new JTextField();
        JCheckBox chkCondicion = new JCheckBox("Tiene condicion medica especial?");
        JTextField txtCriticidad = new JTextField("0");

        Object[] message = {
                "ID del perro:", txtId,
                "Nombre:", txtNombre,
                "Raza:", txtRaza,
                "Edad (años):", txtEdad,
                "Tamaño:", cmbTamano,
                "Nivel de energia (1-5):", txtEnergia,
                chkCondicion,
                "Criticidad medica (1-5) (0 si no tiene):", txtCriticidad
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Registrar Perro", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String id = txtId.getText().trim();
                for (Animal a : listaAnimales) {
                    if (a.getId().equals(id)) {
                        JOptionPane.showMessageDialog(this, "Error: Ya existe un animal con el ID " + id);
                        return;
                    }
                }

                String nombre = txtNombre.getText().trim();
                String raza = txtRaza.getText().trim();
                if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+") || !raza.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                    JOptionPane.showMessageDialog(this, "Error: Nombre y raza solo deben contener letras.");
                    return;
                }

                int edad = Integer.parseInt(txtEdad.getText());
                String tamano = cmbTamano.getSelectedItem().toString();
                int energia = Integer.parseInt(txtEnergia.getText());
                if (energia < 1 || energia > 5) {
                    JOptionPane.showMessageDialog(this, "Error: La energia debe estar entre 1 y 5.");
                    return;
                }

                boolean condicion = chkCondicion.isSelected();
                int criticidad = Integer.parseInt(txtCriticidad.getText());

                listaAnimales.add(new Perro(id, nombre, raza, edad, tamano, energia, condicion, criticidad));
                JOptionPane.showMessageDialog(this, "Perro registrado exitosamente.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Formato invalido en los campos numericos.");
            }
        }
    }

    private void registrarAdoptante() {
        JTextField txtCedula = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtTelefono = new JTextField();
        JComboBox<String> cmbVivienda = new JComboBox<>(new String[]{"Casa", "Departamento"});
        JTextField txtArea = new JTextField();
        JTextField txtPresupuesto = new JTextField();
        JTextField txtHoras = new JTextField();
        JCheckBox chkExperiencia = new JCheckBox("Tiene experiencia previa?");

        Object[] message = {
                "Cedula:", txtCedula,
                "Nombre:", txtNombre,
                "Telefono:", txtTelefono,
                "Tipo de Vivienda:", cmbVivienda,
                "Area disponible (m2):", txtArea,
                "Presupuesto mensual ($):", txtPresupuesto,
                "Horas fuera de casa al dia:", txtHoras,
                chkExperiencia
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Registrar Adoptante", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String cedula = txtCedula.getText().trim();
                String telefono = txtTelefono.getText().trim();
                if (!cedula.matches("\\d+") || !telefono.matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "Error: Cedula y telefono solo deben contener numeros.");
                    return;
                }

                for (Adoptante a : listaAdoptantes) {
                    if (a.getCedula().equals(cedula)) {
                        JOptionPane.showMessageDialog(this, "Error: Ya existe un adoptante con la Cedula " + cedula);
                        return;
                    }
                }

                String nombre = txtNombre.getText().trim();
                if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                    JOptionPane.showMessageDialog(this, "Error: El nombre solo debe contener letras.");
                    return;
                }

                String tipoVivienda = cmbVivienda.getSelectedItem().toString();
                double areaMetros = Double.parseDouble(txtArea.getText());
                double presupuesto = Double.parseDouble(txtPresupuesto.getText());
                int horas = Integer.parseInt(txtHoras.getText());

                if (areaMetros < 0 || presupuesto < 0 || horas < 0) {
                    JOptionPane.showMessageDialog(this, "Error: Los valores numericos no pueden ser negativos.");
                    return;
                }

                boolean experiencia = chkExperiencia.isSelected();

                listaAdoptantes.add(new Adoptante(cedula, nombre, telefono, tipoVivienda, areaMetros, presupuesto, horas, experiencia));
                JOptionPane.showMessageDialog(this, "Adoptante registrado exitosamente.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Formato invalido en los numeros.");
            }
        }
    }

    private void evaluarCompatibilidad() {
        ArrayList<Animal> disponibles = new ArrayList<>();
        for (Animal a : listaAnimales) {
            if (!a.getEstado().equals("Adoptado")) {
                disponibles.add(a);
            }
        }

        if (disponibles.isEmpty() || listaAdoptantes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe registrar al menos un animal disponible y un adoptante.");
            return;
        }

        JComboBox<Animal> comboAnimales = new JComboBox<>(disponibles.toArray(new Animal[0]));
        JComboBox<Adoptante> comboAdoptantes = new JComboBox<>(listaAdoptantes.toArray(new Adoptante[0]));

        Object[] message = {
                "Seleccione el animal:", comboAnimales,
                "Seleccione el adoptante:", comboAdoptantes
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Evaluar Compatibilidad", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Animal animalSel = (Animal) comboAnimales.getSelectedItem();
            Adoptante adoptanteSel = (Adoptante) comboAdoptantes.getSelectedItem();

            int porcentaje = gestorMatch.calcularMatch(adoptanteSel, animalSel);

            String evaluacion = "Porcentaje de compatibilidad: " + porcentaje + "%\n\n";

            if (porcentaje >= 70) {
                evaluacion += "ADOPCION VIABLE: El perfil es apto.\n\n¿Desea confirmar la adopcion?";
                int confirmacion = JOptionPane.showConfirmDialog(this, evaluacion, "Confirmar Adopcion", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    animalSel.setEstado("Adoptado");
                    animalSel.setAdoptante(adoptanteSel);
                    String fechaHoy = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                    Contrato contrato = new Contrato("CTR-" + System.currentTimeMillis(), fechaHoy, adoptanteSel, animalSel, porcentaje);
                    JOptionPane.showMessageDialog(this, "--- CONTRATO GENERADO ---\n" + contrato.generarContrato());
                } else {
                    JOptionPane.showMessageDialog(this, "Adopcion cancelada por el usuario.");
                }
            } else if (porcentaje > 0) {
                evaluacion += "MATCH BAJO: Se recomienda buscar otras opciones.";
                JOptionPane.showMessageDialog(this, evaluacion);
            } else {
                evaluacion += "MATCH FALLIDO: No se cumplen los requisitos minimos, restriccion de espacio, o el adoptante esta vetado.";
                JOptionPane.showMessageDialog(this, evaluacion);
            }
        }
    }

    private void registrarDevolucion() {
        ArrayList<Animal> adoptados = new ArrayList<>();
        for (Animal a : listaAnimales) {
            if (a.getEstado().equals("Adoptado")) {
                adoptados.add(a);
            }
        }

        if (adoptados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay animales adoptados para devolver.");
            return;
        }

        JComboBox<Animal> comboAnimales = new JComboBox<>(adoptados.toArray(new Animal[0]));

        Object[] message = {
                "Seleccione el animal a devolver:", comboAnimales
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Registrar Devolucion", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Animal animalSel = (Animal) comboAnimales.getSelectedItem();
            Adoptante adoptanteSel = animalSel.getAdoptante();

            animalSel.setEstado("En Cuarentena");
            animalSel.setAdoptante(null);

            if (adoptanteSel != null) {
                adoptanteSel.setVetado(true);
                JOptionPane.showMessageDialog(this, "Devolucion procesada.\nEl animal ingreso a cuarentena.\nEl adoptante " + adoptanteSel.getNombre() + " fue VETADO.");
            } else {
                JOptionPane.showMessageDialog(this, "Devolucion procesada.\nEl animal ingreso a cuarentena.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}
package co.edu.uniquindio.poo;

import Model.*;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Empresa empresa = new Empresa("Mensajería Express S.A.S", "900123456-7", "3001234567");

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            System.out.println();
            switch (opcion) {
                case 1 -> registrarNacional();
                case 2 -> registrarInternacional();
                case 3 -> registrarExpres();
                case 4 -> listarEnvios();
                case 5 -> buscarEnvio();
                case 6 -> eliminarEnvio();
                case 7 -> mostrarResumen();
                case 0 -> System.out.println("Hasta pronto.");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }


    private static void mostrarMenu() {
        System.out.println("\n===== " + empresa.getNombre() + " =====");
        System.out.println("1. Registrar envío nacional");
        System.out.println("2. Registrar envío internacional");
        System.out.println("3. Registrar envío exprés");
        System.out.println("4. Listar envíos");
        System.out.println("5. Buscar envío por código");
        System.out.println("6. Eliminar envío");
        System.out.println("7. Resumen (cantidades y total)");
        System.out.println("0. Salir");
    }


    private static void registrarNacional() {
        String codigo = leerCodigoNuevo();
        if (codigo == null) return;
        String destinatario = leerTexto("Destinatario: ");
        double peso = leerPeso();
        String ciudad = leerTexto("Ciudad de destino: ");
        boolean domicilio = leerBoolean("¿Requiere domicilio? (s/n): ");

        Envio envio = new EnvioNacional(codigo, destinatario, peso, empresa, ciudad, domicilio);
        System.out.println(empresa.registrarEnvio(envio));
        System.out.printf("Costo: $%,.0f%n", envio.calcularCosto());
    }

    private static void registrarInternacional() {
        String codigo = leerCodigoNuevo();
        if (codigo == null) return;
        String destinatario = leerTexto("Destinatario: ");
        double peso = leerPeso();
        String pais = leerTexto("País de destino: ");
        boolean seguro = leerBoolean("¿Incluye seguro? (s/n): ");

        Envio envio = new EnvioInternacional(codigo, destinatario, peso, empresa, pais, seguro);
        System.out.println(empresa.registrarEnvio(envio));
        System.out.printf("Costo: $%,.0f%n", envio.calcularCosto());
    }

    private static void registrarExpres() {
        String codigo = leerCodigoNuevo();
        if (codigo == null) return;
        String destinatario = leerTexto("Destinatario: ");
        double peso = leerPeso();
        String ciudad = leerTexto("Ciudad de destino: ");
        boolean domicilio = leerBoolean("¿Requiere domicilio? (s/n): ");
        int horas = leerEnteroPositivo("Horas máximas de entrega: ");

        Envio envio = new EnvioExpres(codigo, destinatario, peso, empresa, ciudad, domicilio, horas);
        System.out.println(empresa.registrarEnvio(envio));
        System.out.printf("Costo: $%,.0f%n", envio.calcularCosto());
    }



    private static void listarEnvios() {
        if (empresa.getListaEnvios().isEmpty()) {
            System.out.println("No hay envíos registrados.");
            return;
        }
        for (Envio envio : empresa.getListaEnvios()) {
            imprimirEnvio(envio);
        }
    }

    private static void buscarEnvio() {
        String codigo = leerTexto("Código a buscar: ");
        empresa.buscarEnvio(codigo).ifPresentOrElse(
                Main::imprimirEnvio,
                () -> System.out.println("No existe un envío con ese código."));
    }

    private static void eliminarEnvio() {
        String codigo = leerTexto("Código a eliminar: ");
        System.out.println(empresa.eliminarEnvio(codigo));
    }

    private static void mostrarResumen() {
        System.out.println("Envíos nacionales (incluye exprés): " + empresa.contarPorTipo(EnvioNacional.class));
        System.out.println("Envíos exprés: " + empresa.contarPorTipo(EnvioExpres.class));
        System.out.println("Envíos internacionales: " + empresa.contarPorTipo(EnvioInternacional.class));
        System.out.printf("Total de costos: $%,.0f%n", empresa.calcularTotalRecaudado());
    }

    private static void imprimirEnvio(Envio envio) {
        System.out.printf("Código: %s | Destinatario: %s | Peso: %.1f kg | %s | Costo: $%,.0f%n",
                envio.getCodigo(), envio.getDestinatario(), envio.getPeso(),
                describirEnvio(envio), envio.calcularCosto());
    }

    private static String describirEnvio(Envio envio) {
        if (envio instanceof EnvioExpres exp) {
            return "Exprés a " + exp.getCiudadDestino()
                    + " | Domicilio: " + (exp.getDomicilio() ? "Sí" : "No")
                    + " | Máx. " + exp.getHoraMaxima() + " h";
        } else if (envio instanceof EnvioNacional nac) {
            return "Nacional a " + nac.getCiudadDestino()
                    + " | Domicilio: " + (nac.getDomicilio() ? "Sí" : "No");
        } else if (envio instanceof EnvioInternacional inter) {
            return "Internacional a " + inter.getPaisDestino()
                    + " | Seguro: " + (inter.isSeguro() ? "Sí" : "No");
        }
        return "";
    }



    private static String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Este campo no puede estar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    private static String leerCodigoNuevo() {
        String codigo = leerTexto("Código del envío: ");
        if (empresa.buscarEnvio(codigo).isPresent()) {
            System.out.println("El envío ya existe.");
            return null;
        }
        return codigo;
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private static int leerEnteroPositivo(String mensaje) {
        int valor;
        do {
            valor = leerEntero(mensaje);
            if (valor <= 0) {
                System.out.println("Debe ser mayor que cero.");
            }
        } while (valor <= 0);
        return valor;
    }

    private static double leerPeso() {
        while (true) {
            System.out.print("Peso (kg): ");
            try {
                double peso = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
                if (peso > 0) {
                    return peso;
                }
                System.out.println("El peso debe ser mayor que cero.");
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido (ej: 2.5).");
            }
        }
    }

    private static boolean leerBoolean(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (respuesta.equals("si") || respuesta.equals("si") || respuesta.equals("sí")) {
                return true;
            }
            if (respuesta.equals("no") || respuesta.equals("no")) {
                return false;
            }
            System.out.println("Responda con si o no.");
        }
    }
}
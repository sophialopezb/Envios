package Model;

import java.util.*;

public class Empresa {

    private final String nombre;
    private final String nit;
    private String telefono;

    private ArrayList<Envio> listaEnvios = new ArrayList<>();

    public Empresa(String nombre, String nit, String telefono){
        this.nombre = nombre;
        this.nit = nit;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNit() {
        return nit;
    }

    public String getTelefono() {
        return telefono;
    }

    public List<Envio> getListaEnvios() {
        return Collections.unmodifiableList(listaEnvios);
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setListaEnvios(ArrayList<Envio> listaEnvios) {
        this.listaEnvios = listaEnvios;
    }

    // CRUD

    public String registrarEnvio(Envio envio) {
        if (buscarEnvio(envio.getCodigo()).isPresent()) {
            return "El envío ya existe";
        }
        listaEnvios.add(envio);
        return "Envío registrado con éxito";
    }

    public String eliminarEnvio(String codigo) {
        Optional<Envio> envio = buscarEnvio(codigo);
        if (envio.isEmpty()) {
            return "El envío no existe";
        }
        listaEnvios.remove(envio.get());
        return "Envío eliminado con éxito";
    }

    // Lógica

    public Optional<Envio> buscarEnvio(String codigo) {
        return listaEnvios.stream()
                .filter(envio -> envio.getCodigo().equals(codigo))
                .findFirst();
    }

    public double calcularTotalRecaudado() {
        return listaEnvios.stream().mapToDouble(Envio::calcularCosto).sum();
    }

    public <T extends Envio> List<T> listarPorTipo(Class<T> tipo) {
        return listaEnvios.stream()
                .filter(tipo::isInstance)
                .map(tipo::cast)
                .toList();
    }

    public long contarPorTipo(Class<? extends Envio> tipo) {
        return listaEnvios.stream().filter(tipo::isInstance).count();
    }

}

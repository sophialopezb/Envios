package Model;

public abstract class Envio {

    private final String codigo;
    private String destinatario;
    protected double peso;
    private Empresa ownedByEmpresa;

    public Envio(String codigo, String destinatario, double peso, Empresa ownedByEmpresa){

        this.codigo=codigo;
        this.destinatario=destinatario;
        this.peso=peso;
        this.ownedByEmpresa=ownedByEmpresa;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public double getPeso() {
        return peso;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public abstract double calcularCosto();


}

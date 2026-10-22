package Model;

public class EnvioNacional extends Envio{

    private static final double COSTO_FIJO = 6000;
    private static final double COSTO_POR_KG = 2000;
    private static final double COSTO_DOMICILIO = 3000;

    private String ciudadDestino;
    private boolean domicilio;

    public EnvioNacional(String codigo, String destinatario, double peso, Empresa ownedByEmpresa, String ciudadDestino, boolean domicilio) {
        super(codigo, destinatario, peso, ownedByEmpresa);
        this.ciudadDestino = ciudadDestino;
        this.domicilio = domicilio;
    }

    public String getCiudadDestino(){
        return ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino){
        this.ciudadDestino=ciudadDestino;
    }

    public boolean getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(boolean domicilio) {
        this.domicilio = domicilio;
    }


    @Override
    public double calcularCosto() {
        double costoTotal = COSTO_FIJO + (peso * COSTO_POR_KG);
        if (domicilio) {
            costoTotal += COSTO_DOMICILIO;
        }
        return costoTotal;
    }
}

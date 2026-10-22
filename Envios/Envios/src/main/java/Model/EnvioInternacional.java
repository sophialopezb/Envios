package Model;

public class EnvioInternacional extends Envio{
    private static final double COSTO_FIJO = 15000;
    private static final double COSTO_POR_KG = 9000;
    private static final double COSTO_SEGURO = 20000;

    private String paisDestino;
    private boolean seguro;

    public EnvioInternacional(String codigo, String destinatario, double peso,Empresa ownedByEmpresa, String paisDestino, boolean seguro){
        super(codigo,destinatario,peso,ownedByEmpresa);
        this.paisDestino=paisDestino;
        this.seguro=seguro;
    }

    public boolean isSeguro() {
        return seguro;
    }

    public String getPaisDestino() {
        return paisDestino;
    }

    public void setPaisDestino(String paisDestino) {
        this.paisDestino = paisDestino;
    }

    public void setSeguro(boolean seguro) {
        this.seguro = seguro;
    }

    @Override
    public double calcularCosto() {
        double costoTotal = COSTO_FIJO + (peso * COSTO_POR_KG);
        if (seguro) {
            costoTotal += COSTO_SEGURO;
        }
        return costoTotal;
    }
}

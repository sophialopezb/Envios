package Model;

public class EnvioExpres extends EnvioNacional{

    private static final double RECARGO_PORCENTAJE = 1.30;
    private static final double RECARGO_URGENTE = 5000;
    private static final double LIMITE_URGENTE_HORAS = 24;
    private int horaMaxima;

    public EnvioExpres(String codigo, String destinatario, double peso, Empresa ownedByEmpresa, String ciudadDestino, boolean domicilio, int horaMaxima){
        super(codigo,destinatario,peso,ownedByEmpresa,ciudadDestino,domicilio);
        this.horaMaxima=horaMaxima;
    }

    public int getHoraMaxima() {
        return horaMaxima;
    }

    public void setHoraMaxima(int horaMaxima) {
        this.horaMaxima = horaMaxima;
    }

    @Override
    public double calcularCosto() {
        double costoTotal = super.calcularCosto() * RECARGO_PORCENTAJE;
        if (horaMaxima <= LIMITE_URGENTE_HORAS) {
            costoTotal += RECARGO_URGENTE;
        }
        return costoTotal;
    }


}

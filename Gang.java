public class Gang {
    private final String code;
    private final double capacityHours;
    private final boolean certificatedRefrigeration;
    private boolean ocuped;

    public Gang(String code, double capacityHours, boolean certificatedRefrigeration){
        if(code == null || code.trim().isEmpty())
            throw new IllegalArgumentException("El codigo de la cuadrilla no puede estar vacia nunca.");

        if(!Double.isFinite(capacityHours) || capacityHours <= 0)
            throw new IllegalArgumentException("La capacidad debe ser positivo o mayor a 0.");
        this.code = code.trim();
        this.capacityHours = capacityHours;
        this.certificatedRefrigeration = certificatedRefrigeration;
        this.ocuped = false;
    }
    void ocuped(){
        if(ocuped)
            throw new IllegalStateException("La cuadrilla con el codigo " + code + "ya tiene una jornada sin cerrar. ");
        ocuped = true;
    }
    void liberar(){ocuped = false;}

    public String getCode(){return code;}
    public double getCapacityHours(){return capacityHours;}
    public boolean isCertificatedRefrigeration(){return certificatedRefrigeration;}
    public boolean isLibre(){return !ocuped;}

    @Override 
    public String toString(){
        return String.format(java.util.Locale.US, "[%s] Capacidad: %.2f h | Certificada en refrigeración: %s | %s", code , capacityHours, certificatedRefrigeration ? "si" : "no" , ocuped ? "con jornada abierta" : "libre");
    }

    
}

public class Diagnostic extends OrdenS {
    public static final double MAX_HORAS = 1.5;
    private static final double COSTO_METROPOLITANA = 150.0;
    private static final double COSTO_PERIFERICA = 225.0;

    public Diagnostic(String code, String costumer, String direction, double estimedHours, Zone zone){
        super(code, costumer, direction, estimedHours, zone);
        if(estimedHours > MAX_HORAS)
            throw new IllegalArgumentException("Un diagnostico no se puede superar las siguientes horas maximas: " + MAX_HORAS);

    }
    @Override public String getCategory(){return "Diagnostico";}
    @Override 
    public double calculateCoste(){
        return getZone().esMetropolitana() ? COSTO_METROPOLITANA : COSTO_PERIFERICA;
    }
    @Override public int getAtentionDays(){return getZone().esMetropolitana() ? 1 : 2;}
    @Override public int getMaxofVisits(){return 3;}
    
}

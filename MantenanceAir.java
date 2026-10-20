public class MantenanceAir extends OrdenS {
    public static final int BTUmin = 9000;
    public static final int BTUmax = 60000;
    public static final double TARIFA_BY_HORA = 110.0;
    public static final double CHARGE_GAS = 80.0;

    private final int btu;

    public MantenanceAir(String code, String costumer, String direction, double hoursEstimated,Zone zone, int btu){
        super(code, costumer,direction,hoursEstimated,zone);
        if(!zone.esMetropolitana())
            throw new IllegalArgumentException("El mantenimiento del aire acondicionado se acepta en: Zona Metropolitana");
        if(btu < BTUmin || btu > BTUmax)
            throw new IllegalArgumentException("Los BTU deben estas en el rango de " + BTUmin + "y" + BTUmax + ".");
        this.btu=btu; 
    }
    public int getBtu(){return btu;}

    @Override public String getCategory(){return "Mantenimiento de aire acondicionado";}

    @Override 
    public double calculateCoste(){
        return getZone().getTarifBase() + TARIFA_BY_HORA * getEstimedhours() + CHARGE_GAS;
    }
    @Override public int getAtentionDays(){return 2;}
    @Override public int getMaxofVisits(){return 1;}

    @Override 
    public boolean isCompatibleWith(Gang gang){
        return gang.isCertificatedRefrigeration();
    }
    
}

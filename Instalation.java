public class Instalation extends OrdenS {
    public static final double HORAS_BY_PUNTO = 0.75;
    public static final double TARIFA_BY_HORA = 90.0;


    private final int points;

    public Instalation(String code, String costumer, String direction, double estimedHours,Zone zone, int points){
        super(code, costumer,direction,estimedHours,zone);
        if(points <= 0)
            throw new IllegalArgumentException("La cantidad de sus puntos debe ser un numero entero positivo o mayor a 0.");
        this.points=points;
    }
    public int getPoints(){return points;}
    public double getHoursbyPoints(){return points + HORAS_BY_PUNTO;}

    public double getHoursFactures(){
        return Math.max(getEstimedhours(), getHoursbyPoints());

    }
    @Override public String getCategory(){return "Instalación";}

    @Override 
    public double calculateCoste(){
        double hoursCobrad = Math.ceil(getHoursFactures() - EPS);
        return getZone().getTarifBase()+ TARIFA_BY_HORA*hoursCobrad;
    }
    @Override public int getAtentionDays(){return getZone().esMetropolitana() ? 3 : 5;}
    @Override public int getMaxofVisits(){return 3;}
    
}

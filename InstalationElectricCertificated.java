public class InstalationElectricCertificated extends Instalation {
    public static final double COST_CERTIFICADED = 120.0;
    public static final double PERCENT_GARANTIA = 0.05;
    public static final int DIA_ADICIONALES = 2;

    private final double valueofMaterials;

    public InstalationElectricCertificated(String code, String costumer,String direction,double hoursEstimed, Zone zone, int points, double valueofMaterials){
        super(code, costumer,direction,hoursEstimed,zone,points);
        if(!Double.isFinite(valueofMaterials) || valueofMaterials <= 0)
            throw new IllegalArgumentException("El valor de los materiales debe ser un positivo o mayor a 0.");
        this.valueofMaterials = valueofMaterials;
    }
    public double getValueofMaterial(){return valueofMaterials;}
    @Override public String getCategory(){return "Instalación electrica totalmente certificada";}

    @Override 
    public double calculateCoste(){
        return super.calculateCoste() + COST_CERTIFICADED + PERCENT_GARANTIA * valueofMaterials;
    }
    @Override public int getAtentionDays(){return super.getAtentionDays() + DIA_ADICIONALES;}
    @Override public int getMaxofVisits(){return 2;}
    
}

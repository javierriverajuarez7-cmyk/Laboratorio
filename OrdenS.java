public abstract class OrdenS{
    public static final double additional_CV = 40.0;
    protected static final double EPS = 1e-9;

    private final String code;
    private final String costumer;
    private final String direction;
    private final double hoursEstimated;
    private final Zone zone;
    private Orderstate state;
    private int visitDone;


    protected OrdenS(String code, String costumer, String direction, double hoursEstimated,Zone zone){
        if(code == null || code.trim().isEmpty())
            throw new IllegalArgumentException("Porfavor ingrese codigo. ");
        if(costumer== null || costumer.trim().isEmpty())
            throw new IllegalArgumentException("Porfavor ingrese nombre.");
        if(direction == null || direction.trim().isEmpty())
            throw new IllegalArgumentException("La dirección debe ser ingresada.");
        if(!Double.isFinite(hoursEstimated) || hoursEstimated <= 0)
            throw new IllegalArgumentException("Horas deben ser positivas, porfavor agrege las horas correctamente.");

        if(zone == null)
            throw new IllegalArgumentException("Zona no registrada, agregela.");


        this.code = code.trim();
        this.costumer = costumer.trim();
        this.direction = direction.trim();
        this.hoursEstimated = hoursEstimated;
        this.zone = zone;
        this.state = Orderstate.PENDIENTE;
        this.visitDone =0;


        
    }
    public abstract String getCategory();
    public abstract double calculateCoste();
    public abstract int getAtentionDays();
    public abstract int getMaxofVisits();

    public boolean isCompatibleWith(Gang gang){return true;}

    void asignedMark(){
        if(state != Orderstate.PENDIENTE)
            throw new IllegalStateException("La orden" + code + "no esta pendiente || estado:" + state + ".");
        state = Orderstate.ASIGNADA;
    }
    void retiredMark(){
        if(state != Orderstate.ASIGNADA)
            throw new IllegalStateException("La orden" + code + "no esta asignada || estado:" + state + ".");
        state = Orderstate.PENDIENTE;

    }
    double startVisit(){
        if(state != Orderstate.ASIGNADA)
            throw new IllegalStateException("La orden" + code + " no esta asignada||estado" + state + ".");
        visitDone++;
        state = Orderstate.EN_ATENCION;
        return visitDone > 1 ? additional_CV : 0.0;
    }
    void registerResults(ResultVisit result){
        if(state != Orderstate.EN_ATENCION)
            throw new IllegalStateException("La orden" + code + "no esta en atención || estado" + state + ".");
        if (result == ResultVisit.COMPLETADA){
            state = Orderstate.COMPLETADA;
        } else if (visitDone >= getMaxofVisits()){
            state = Orderstate.CANCELADA;

        }else{
            state = Orderstate.PENDIENTE;
        }
    }

    public String getCode(){return code;}
    public String getCostumer(){return costumer;}
    public String getDirection(){return direction;}
    public double getEstimedhours(){return hoursEstimated;}
    public Zone getZone(){return zone;}
    public Orderstate getState(){return state;}
    public int getVisitDone(){return visitDone;}

    public String cotization(){
        return String.format(java.util.Locale.US, "%s | Costo: Q%.2f | Tiempo estimado: %d dias habiles", getCategory(),calculateCoste(),getAtentionDays());
    }
    @Override 
    public String toString(){
        return String.format(java.util.Locale.US,"[%s] %s | Cliente: %s | Dir: %s | %.2f h | Zona: %s | Estado: %s | Visitas: %d/%d" , code,getCategory(),costumer,direction,hoursEstimated,zone,state,visitDone,getMaxofVisits());
    }






}
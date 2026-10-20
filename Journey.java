import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class Journey {
    private final String code;
    private final Gang gang;
    private final List<OrdenS> ordens = new ArrayList<>();
    private final Set<OrdenS> withResult = new HashSet<>();
    private JourneyState state;

    Journey(String code, Gang gang){
        gang.ocuped();
        this.code = code;
        this.gang = gang;
        this.state = JourneyState.EN_PREPARACION;
    }
    private void demandState(JourneyState waiting){
        if(state != waiting)
            throw new IllegalStateException("La jornada" + code + "esta" + state + ";la operación requiero lo siguiente" + waiting+".");
    }
    public void asignar(OrdenS orden){
        demandState(JourneyState.EN_PREPARACION);
        if (orden.getState() != Orderstate.PENDIENTE)
            throw new IllegalStateException("La orden" + orden.getCode() + "no puede asignarse || estado: " + orden.getState() + ".");
        if (!orden.isCompatibleWith(gang))
            throw new IllegalArgumentException("La orden" + orden.getCode()+"|" + orden.getCategory()+"| no es compatible con la cuadrilla" + gang.getCode() + ".");
        double total = getAsignedHours() + orden.getEstimedhours();
        if(total > gang.getCapacityHours() + 1e-9)
            throw new IllegalArgumentException(String.format(java.util.Locale.US, "Excede la capacidad de la cuadrilla %s (%.2f h) : quedaria en %.2f h." , gang.getCode() , gang.getCapacityHours(), total));
        orden.asignedMark();
        ordens.add(orden);

    }
    public void retirar(String CodeOrden){
        demandState(JourneyState.EN_PREPARACION);
        OrdenS orden = buscar(CodeOrden);
        if(orden == null)
            throw new IllegalArgumentException("La orden" + CodeOrden + "no se encuentra en la jornada" + code + ".");
        orden.retiredMark();
        ordens.remove(orden);
        
    }
    public void start(RegisterofIncomes incomes){
        demandState(JourneyState.EN_PREPARACION);
        if(ordens.isEmpty())
            throw new IllegalStateException("No se puede iniciar con una jornada vacia.");
        for(OrdenS orden : ordens){
            double cargo = orden.startVisit();
            if(cargo >0) incomes.registerAditionalVisits(orden.getCategory(),cargo);
        }
        state = JourneyState.EN_ATENCION;
    }
    public void registerResults(String CodeOrden, ResultVisit result ){
        demandState(JourneyState.EN_ATENCION);
        OrdenS orden = buscar(CodeOrden);
        if(orden == null)
            throw new IllegalArgumentException("La orden" + CodeOrden + "no esta en la jornada" + code + ".");
        if(withResult.contains(orden))
            throw new IllegalStateException("La orden" + CodeOrden + "ya tiene resultado en alguna jornada.");
        orden.registerResults(result);
        withResult.add(orden);
        if(withResult.size() == ordens.size()){
            state = JourneyState.CERRADA;
            gang.liberar();
        }
        

    }
    private OrdenS buscar(String CodeOrden){
        for(OrdenS o : ordens)
            if(o.getCode().equals(CodeOrden.trim())) return o;
        return null;
    }
    public double getAsignedHours(){
        double sum = 0;
        for(OrdenS o : ordens) sum += o.getEstimedhours();
        return sum;
    }
    public double getCapacityAvailable(){
        return gang.getCapacityHours() - getAsignedHours();
    }
    public List<OrdenS> getOrdenwithoutReuslts(){
        List<OrdenS> pendientes = new ArrayList<>();
        for(OrdenS o : ordens) if(!withResult.contains(o)) pendientes.add(o);
        return pendientes;
    }
    public String getCode(){return code;}
    public Gang getGang(){return gang;}
    public JourneyState getState(){return state;}
    public List<OrdenS> getOrdens(){return new ArrayList<>(ordens);}

    @Override 
    public String toString(){
        return String.format(java.util.Locale.US, "Jornada %s | Cuadrilla: %s | Estado: %s | Horas asignadas: %.2f | Capacidad disponible: %.2f | Ordenes: %d" , code, gang.getCode(),state,getAsignedHours(),getCapacityAvailable(),ordens.size());
    }


    
}

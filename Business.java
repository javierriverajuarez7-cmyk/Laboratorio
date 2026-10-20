import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class Business {
    private final Map<String, OrdenS> ordens = new LinkedHashMap<>();
    private final Map<String, Gang> gangs = new LinkedHashMap<>();
    private final Map<String, Journey> journeys = new LinkedHashMap<>();
    private final RegisterofIncomes incomes = new RegisterofIncomes();
    private int countofJourneys = 0;

    public boolean haveOrder(String code){return ordens.containsKey(code.trim());}

    public void registerOrder(OrdenS orden){
        addOrdenwithoutCollection(orden);
        incomes.registerCollection(orden.getCategory(),orden.calculateCoste());
    }
    public void addOrdenwithoutCollection(OrdenS orden){
        if(ordens.containsKey(orden.getCode()))
            throw new IllegalArgumentException("Ya existe un orden con el codigo " + orden.getCode()+".");
        ordens.put(orden.getCode(),orden);
    }
    public OrdenS foundOrden(String code){
        OrdenS o = ordens.get(code.trim());
        if(o == null) throw new IllegalArgumentException("No existe orden con codigo " + code + ".");
        return o;
    }
    public Collection<OrdenS> getOrdens(){return ordens.values();}

    public void registerGang(Gang c){
        if(gangs.containsKey(c.getCode()))
            throw new IllegalArgumentException("Ya existe cuadrilla con el siguiente codigo: " + c.getCode() + ".");
        gangs.put(c.getCode(), c);
    }
    public Gang foundGang(String code){
        Gang c = gangs.get(code.trim());
        if(c == null) throw new IllegalArgumentException("No existe la cuadrilla buscada: "+ code + ".");
        return c; 
    }
    public Collection<Gang> getGangs(){return gangs.values();}

    public Journey createJourney(String codeGang){
        Gang c = foundGang(codeGang);
        Journey j = new Journey("J-" + (countofJourneys + 1),c);

        countofJourneys++;
        journeys.put(j.getCode(),j);
        return j;
    }
    public Journey foundJourney(String code){
        Journey j = journeys.get(code.trim());
        if(j == null) throw new IllegalArgumentException("No existe la jornada" + code +".");
        return j;
    }
    public void asignOrden(String codJourney, String codOrden){
        foundJourney(codJourney).asignar(foundOrden(codOrden));
    }
    public void removeOrden(String codJourney, String codOrden){
        foundJourney(codJourney).retirar(codOrden);
    }
    public void startJourney(String codJourney){
        foundJourney(codJourney).start(incomes);
    }
    public void registerResults(String codJourney, String codOrden,ResultVisit r){
        foundJourney(codJourney).registerResults(codOrden, r);
    }

    public Map<String, Map<Orderstate, Integer>> countByCategoryAndState() {
    Map<String, Map<Orderstate, Integer>> r = new LinkedHashMap<>();
    for (OrdenS o : ordens.values()) {
        r.computeIfAbsent(o.getCategory(), k -> new EnumMap<>(Orderstate.class))
         .merge(o.getState(), 1, Integer::sum);
    }
    return r;
}
    public  RegisterofIncomes getIncomes(){return incomes;}

    public List<Journey> getJourneysOpen(){
        List<Journey> opens = new ArrayList<>();
        for(Journey j : journeys.values())
            if(j.getState() != JourneyState.CERRADA) opens.add(j);
        return opens;
    }



    
}

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class RegisterofIncomes {
    private final Map<String, Double> byRegister = new LinkedHashMap<>();
    private final Map<String, Double> byVisits = new LinkedHashMap<>();

    void registerCollection(String category, double monto){
        byRegister.merge(category,monto,Double::sum);
    }
    void registerAditionalVisits(String category, double monto){
        byVisits.merge(category,monto,Double::sum);
    }
    public double getTotalRegistro(){return sum(byRegister);}
    public double getTotalVisists(){return sum(byVisits);}
    public double getTotal(){return getTotalRegistro() + getTotalVisists();}

    public double getCategoryIncomes(String category){
        return byRegister.getOrDefault(category, 0.0) + byVisits.getOrDefault(category, 0.0);
    }
    public Set<String> getCategory(){
        Set<String> cats = new LinkedHashSet<>(byRegister.keySet());
        cats.addAll(byVisits.keySet());
        return cats;
    }
    private static double sum(Map<String,Double>m){
        double s = 0;
        for(double v : m.values()) s += v;
        return s;
    }
    
}

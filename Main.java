import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final Business businnes = new Business();

    public static void main(String[] args){
        chargeInitialD();
        boolean out = false;
        while(!out){
            try{
                shownMenu();
                int op = leerEntero("Opción: ");
                switch (op){
                    case 1: quoteandRegister();break;
                    case 2: registerGang();break;
                    case 3: createJourney();break;
                    case 4: asignedOrder();break;
                    case 5: retiredOrder();break;
                    case 6: startJourney();break;
                    case 7: registerResult();break;
                    case 8: businnes.getOrdens().forEach(System.out::println);;break;
                    case 9: businnes.getGangs().forEach(System.out::println);;break;
                    case 10: reports(); break;
                    case 0: out = true; System.out.println("Hasta la proxima");break;
                    default: System.out.println("Opcion invalida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e){
                System.out.println("Operación no correcta" + e.getMessage());
            } catch (NoSuchElementException e){
                System.out.println("Fin de la entrada"); out = true;
            }
        }
    }
    private static void chargeInitialD(){
        businnes.addOrdenwithoutCollection(new Diagnostic("D-001" , "Ermina Lopez" , "Zona 7" , 1.0,Zone.METROPOLITANA));
        businnes.addOrdenwithoutCollection(new Diagnostic("D-002" , "Henandez Luis" , "Mixco" , 1.5,Zone.PERIFERICA));
        businnes.addOrdenwithoutCollection(new Instalation("I-001" , "Hernesta Miguela","Zona 15",2.5,Zone.METROPOLITANA, 5));
        businnes.addOrdenwithoutCollection(new Instalation("I-002" , "Carlos Juarez","Villa Nueva",4.2,Zone.PERIFERICA,3));
        businnes.addOrdenwithoutCollection(new InstalationElectricCertificated("E-001" , "Sofia Amilia","Zona 14",2.5,Zone.METROPOLITANA,5,2000));
        businnes.addOrdenwithoutCollection(new InstalationElectricCertificated("E-002" , "Jorge Santi","San miguel Petapa",3.0,Zone.PERIFERICA,4,1500));
        businnes.addOrdenwithoutCollection(new MantenanceAir("A-001","Elena Castiloo","Zona 9",2.0,Zone.METROPOLITANA,12000));
        businnes.addOrdenwithoutCollection(new MantenanceAir("A-002","Santiago Pozuelos","Zona 4",3.0,Zone.METROPOLITANA,24000));

        businnes.registerGang(new Gang("C-01" , 8.0, false));
        businnes.registerGang(new Gang("C-02" , 6.0, true));
        businnes.registerGang(new Gang("C-03" , 4.0, false));

        

    }
    private static void shownMenu(){
        System.out.println("\n===== TechiH =====");
        System.out.println("1. Cotizar o registrar orden");
        System.out.println("2. Registrar cuadrilla");
        System.out.println("3. Crear una jornada");
        System.out.println("4. Asignar el orden a una jornada");
        System.out.println("5. Retirar el orden a una jornada");
        System.out.println("6. Iniciar una jornada");
        System.out.println("7. Consultado de una visita");
        System.out.println("8. Consultar ordenes");
        System.out.println("9 Consultar cuadrillas");
        System.out.println("10. Reportes!!!");
        System.out.println("0. Salir??");

    }
    private static void quoteandRegister(){
        OrdenS orden = leerOrden();
        System.out.println("Cotización -->" + orden.cotization());
        if(leerTexto("El cliente acepta? (s/n)").equalsIgnoreCase("s")){
            businnes.registerOrder(orden);
            System.out.println("Orden registrado y tambien cobrada" + money(orden.calculateCoste()));
        }else{
            System.out.println("Cotización descartad; no se a registrado nada por el momento.");
        }
        
    }
    private static OrdenS leerOrden(){
        System.out.println("Categoria: 1) Diagnostico 2) Instalacion 3) Instalacion Electrica certificada 4) Mantenimiento A/C");
        int cat = leerEntero("Categoria: ");
        if(cat < 1 || cat >4 ) 
            throw new IllegalArgumentException("Categoria invalida.");
        String code = leerTexto("Codigo :");
        if(!code.isEmpty() && businnes.haveOrder(code)) 
            throw new IllegalArgumentException("Existe una orden conn el siguiente codigo " + code + ".");

        String costumer = leerTexto("Cliente : ");
        String direction = leerTexto("Direccion : ");
        double hours = leerDouble("Horas estimadas: ");
        Zone zone = leerZone();
        switch (cat){
            case 1:
                return new Diagnostic(code, costumer,direction,hours,zone);
            case 2:
                return new Instalation(code, costumer,direction,hours,zone,leerEntero("Puntos a instalar: "));
            case 3:{
                int points = leerEntero("Puntos a insatalar: ");
                double materials = leerDouble("Valor declarado de materiales (Q): ");
                return new InstalationElectricCertificated(code, costumer,direction,hours,zone,points,materials);
            }
            default:
                return new MantenanceAir(code,costumer,direction,hours,zone,leerEntero("Capacidad (BTU):"));

                
        }
    }
    private static void registerGang(){
        String code = leerTexto("Codigo de la cuadrilla: ");
        double capacity = leerDouble("Capacidad maxima(horas): ");
        boolean cert = leerTexto("Esta certificada en refrigeración (s/n): ").equalsIgnoreCase("s");
        businnes.registerGang(new Gang(code, capacity,cert));
        System.out.println("Cuadrilla registrada.");
    }
    private static void createJourney(){
        Journey j = businnes.createJourney(leerTexto("Codigo de la cuadrilla: "));
        System.out.println("Jornada creada: " + j);
    }
    private static void asignedOrder(){
        businnes.asignOrden(leerTexto("Codigo de jornada: "),leerTexto("Codigo de orden: "));
        System.out.println("Orden asignada. ");
    }
    private static void retiredOrder(){
        businnes.removeOrden(leerTexto("Codigo de jornada: "), leerTexto("Codigo de orden: "));
        System.out.println("Orden retirada; vuelve a queder pendiente, agrege otra.");
    }
    private static void startJourney(){
        businnes.startJourney(leerTexto("Codigo de jornada: "));
        System.out.println("Jornada iniciada. ");
    }
    private static void registerResult(){
        String codj = leerTexto("Codigo de jornada: ");
        Journey j = businnes.foundJourney(codj);
        System.out.println("Ordenes sin ningun resultado: ");
        j.getOrdenwithoutReuslts().forEach(o -> System.out.println(" " + o));
        String cod0 = leerTexto("Codigo de orden: ");
        int r = leerEntero("Resultado: 1) Completada 2) Visita fallida: ");
        if(r != 1 && r != 2) throw new IllegalArgumentException("Resultado invalido.");
        businnes.registerResults(codj, cod0, r == 1 ? ResultVisit.COMPLETADA : ResultVisit.FALLIDA);
        System.out.println("Resultado registrado. Estado de la jornada: " + j.getState());
    }
    private static void reports(){
        System.out.println("\n ---- Ordenes por categoria y su estado --- ");
        for(Map.Entry<String, Map<Orderstate, Integer>> e : businnes.countByCategoryAndState().entrySet()){
            StringBuilder sb = new StringBuilder(e.getKey() + ":");
            for(Orderstate est : Orderstate.values()) 
                sb.append(" ").append(est).append("=").append(e.getValue().getOrDefault(est,0));
            System.out.println(sb);
        }
        RegisterofIncomes ing = businnes.getIncomes();
        System.out.println("\n --- Ingresos ---");
        System.out.println("Por registro: " + money(ing.getTotalRegistro()));
        System.out.println("Por visitas adicionales: " + money(ing.getTotalVisists()));
        System.out.println("Total: " + money(ing.getTotal()));
        for (String cat : ing.getCategory())
            System.out.println(" " + cat + ": " + money(ing.getCategoryIncomes(cat)));
        System.out.println("\n---- Jornadas no cerradas ---");
        List<Journey> opens = businnes.getJourneysOpen();
        if(opens.isEmpty()) System.out.println("(Ninguna)");
        opens.forEach(System.out::println);

        
    }
      private static String leerTexto(String prompt) {
        System.out.print(prompt);
        return in.nextLine().trim();
    }
 
    private static int leerEntero(String prompt) {
        while (true) {
            try { return Integer.parseInt(leerTexto(prompt)); }
            catch (NumberFormatException e) { System.out.println("Entrada inválida: ingrese un número entero."); }
        }
    }
 
    private static double leerDouble(String prompt) {
        while (true) {
            try { return Double.parseDouble(leerTexto(prompt).replace(',', '.')); }
            catch (NumberFormatException e) { System.out.println("Entrada inválida: ingrese un número."); }
        }
    }
 
    private static Zone leerZone() {
        while (true) {
            int z = leerEntero("Zona (1 = metropolitana, 2 = periférica): ");
            if (z == 1) return Zone.METROPOLITANA;
            if (z == 2) return Zone.PERIFERICA;
            System.out.println("Zona no válida.");
        }
    }
 
    private static String money(double monto) { return String.format(Locale.US, "Q%.2f", monto); }
    

    
}

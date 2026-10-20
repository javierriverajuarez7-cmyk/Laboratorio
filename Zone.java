public enum Zone {
    METROPOLITANA("Metropolitana" , 100.0),
    PERIFERICA("Periferica", 175.0);

    private final String name;
    private final double tarifBase;

    Zone(String name, double tarifBase){
        this.name = name;
        this.tarifBase = tarifBase;
    }
    public double getTarifBase(){return tarifBase;}
    public boolean esMetropolitana(){return this == METROPOLITANA;}

    @Override 
    public String toString(){ return name;}
    
}

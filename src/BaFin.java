public enum BaFin {

    INSTANCE;

    public boolean liquiditaetPruefen(Bank bank){
        return bank.liquiditaetPruefen();
    }

}
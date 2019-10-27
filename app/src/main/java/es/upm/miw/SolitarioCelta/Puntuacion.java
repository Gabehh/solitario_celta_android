package es.upm.miw.SolitarioCelta;

public class Puntuacion {
    private int id;
    private String name;
    private String date;
    private int fichas;

    public Puntuacion(Puntuacion puntuacion){
        this.id = puntuacion.getId();
        this.name = puntuacion.getName();
        this.fichas = puntuacion.getFichas();
        this.date = puntuacion.getDate();
    }

    public Puntuacion(String name, String date, int fichas){
        this.name = name;
        this.fichas = fichas;
        this.date = date;
    }

    public Puntuacion(){
    }

    public int getId(){return this.id;}
    public void setid(int id){this.id = id;}
    public String getName(){return this.name;}
    public void setName(String name){this.name = name;}
    public String getDate(){return this.date;}
    public void setDate(String date){this.date = date;}
    public int getFichas(){return this.fichas;}
    public void setFichas(int fichas){this.fichas = fichas;}

}

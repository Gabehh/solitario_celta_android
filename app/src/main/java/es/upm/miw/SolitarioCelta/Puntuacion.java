package es.upm.miw.SolitarioCelta;

public class Puntuacion {
    private int id;
    private String name;
    private String date;
    private int fichas;
    private String chronometer;

    public Puntuacion(Puntuacion puntuacion){
        this.id = puntuacion.getId();
        this.name = puntuacion.getName();
        this.fichas = puntuacion.getFichas();
        this.date = puntuacion.getDate();
        this.chronometer = puntuacion.getChronometer();
    }

    public Puntuacion(String name, String date, int fichas, String chronometer){
        this.name = name;
        this.fichas = fichas;
        this.date = date;
        this.chronometer = chronometer;
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
    public String getChronometer(){return this.chronometer;}
    public void setChronometer(String chronometer){this.chronometer = chronometer;}


}

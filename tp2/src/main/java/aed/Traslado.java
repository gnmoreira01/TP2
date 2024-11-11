package aed;

public class Traslado {
    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;
    int antiguedad;
    int pos_heap_ganancia;
    int pos_heap_antiguedad;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
        this.antiguedad = null;
    }

    public int ganancia (){
        return gananciaNeta;
    }

    public int id (){
        return id;
    }

    public int origen (){
        return origen;
    }

    public int destino (){
        return destino;
    }

    public int timestamp () {
        return timestamp;
    }

    public int antiguedad (){
        return antiguedad;
    }

    public añadir_antiguedad (int i){
        this.antiguedad = i;
    }

    public añadir_pos_heap_antig
}

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
        //this.antiguedad = null;
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

    public void añadir_antiguedad (int i){
        this.antiguedad = i;
    }

    public void añadir_pos_heap_ganancia(int i){
        this.pos_heap_ganancia = i;
    }

    public void añadir_pos_heap_antiguedad(int i){
        this.pos_heap_ganancia = i;
    }
}

package aed;

public class Traslado {
    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;
    int indice;
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

    public void negar_timestamp(){
        timestamp = (-1) * timestamp;
    }

    public int indice (){
        return indice;
    }

    public int pos_heap_ganancia(){
        return pos_heap_ganancia;
    }

    public int pos_heap_antiguedad(){
        return pos_heap_antiguedad;
    }

    public void añadir_indice (int i){
        this.indice = i;
    }

    public void añadir_pos_heap_ganancia(int i){
        this.pos_heap_ganancia = i;
    }

    public void añadir_pos_heap_antiguedad(int i){
        this.pos_heap_ganancia = i;
    }
}

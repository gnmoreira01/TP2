package aed;

public class Traslado {
    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;
    int indice;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
        this.indice = null;
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

    public int indice (){
        return indice;
    }

    public añadir_indice_vectortraslados (int i){
        this.indice = i;
    }
}

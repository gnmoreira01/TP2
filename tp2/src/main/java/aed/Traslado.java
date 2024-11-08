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

    public añadir_indice_vectortraslados (int i){
        this.indice = i;
    }
}

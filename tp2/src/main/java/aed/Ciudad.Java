package aed;

public class Ciudad {
    private int ganancia;
    private int perdida;
    private int superavit;
    private int pos_heap;
    private int id;

    public Ciudad (int i){
        ganancia = 0;
        perdida = 0;
        superavit = 0;
        pos_heap = i;
        id = i;
    }

    public void actualizar_superavit (){
        superavit = ganancia - perdida;
    }

    public void modificar_posicion_heap (int g){
        pos_heap = g;
    }

    public void modificar_id (int g){
        id = g;
    }

    public void sumar_ganancia (int g){
        ganancia = ganancia + g;
    }

    public void sumar_perdida (int g){
        perdida = perdida + g;
    }

    public int superavit(){
        return superavit;
    }

    public int ganancia(){
        return ganancia;
    }
    
    public int perdida(){
        return perdida; 
    }

    public int posicion_heap(){
        return pos_heap;
    }

    public int id(){
        return id;
    }

}
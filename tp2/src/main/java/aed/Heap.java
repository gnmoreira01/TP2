import java.util.ArrayList;

public class Heap<T> {
    private ArrayList arr;
    private int longitud;
    private int tipo;
    /* tipo = 0 equivale a max-heap por ganancia, i = 1 min-heap por antiguedad e
    i = 2 max-heap de ciudades por superávit */

    public Heap (int i){
        arr = new ArrayList<T>();
        tipo = i;
    }

    public añadir (T elem){
        if (arr.size() == 0);
            arr.add(elem);
        else{
            
        }
    }

    private T hijoizquierdo (int i){
        return arr.get(2*i + 1);
    }

    private T hijoderecho (int i){
        return arr.get(2*i+2);
    }

    private void heapify_aux (int i){
        if (2*i+2 < longitud){
            T hijoizq = this.hijoizquierdo(i);
            T hijoder = this.hijoderecho(i);
            if (tipo == 2){
                if (esMayorQueLosHijos(arr.get(i).get(3), i, i)){
                    return;
                }
                else{

                }
            }
            else{
                if (tipo == 0){
                    if (!esMayorQueLosHijos(this.get(i).ganancia(), hijoder.ganancia(), hijoizq.ganancia())){
                        if (arr.hijoizquierdo(i).ganancia() > arr.hijoderecho(i).ganancia){
                            this.swap(i, 2*i+1);
                        }
                        else{
                            this.swap(i,2*i+2);
                        }
                    }
                }
                if (tipo == 1){
                    if (esMayorQueLosHijos(this.get(i).ganancia(), hijoder.ganancia(), hijoizq.ganancia())){
                        if (arr.hijoizquierdo(i).indice() < arr.hijoderecho(i).indice()){
                            this.swap(i, 2*i+1);
                        }
                        else{
                            this.swap(i,2*i+2);
                        }
                    }
                }
            }
        }
        else if (2*i+1 < longitud){

        }
    }

    private void swap (int i, int k){
        int elem_i = arr.get(i);
        int elem_k = arr.get(k);
        arr.set(i,elem_k);
        arr.set(k,elem_i);
    }

    private boolean esMayorQueLosHijos (int padre, int hijoizquierdo, int hijoderecho){
        if (padre > hijoizquierdo && padre > Hijo.derecho){
            return true;
        }
        else {
            return false;
        }
    }
}

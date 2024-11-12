package aed;
import java.util.ArrayList;

public class Heap<T> {
    private ArrayList<T> arr;
    private int longitud;
    private int tipo;
    /* tipo = 0 equivale a max-heap por ganancia, i = 1 min-heap por antiguedad e
    i = 2 max-heap de ciudades por superávit */

    public Heap (int i){
        arr = new ArrayList<T>();
        tipo = i;
    }

    public Heap (ArrayList<T> array, int t){
        longitud = array.size();
        tipo = t;
        arr = array;
        for (int i = longitud - 1; i > -1; i--){
            heapify_aux(i);
            // Falta agregar las posiciones en el heap para cada pedido,
        }
    }

    public void encolar (T elem){
        if (arr.size() == 0){
            arr.add(elem);
            longitud += 1;
        }  
        else{
            arr.add(elem);
            longitud+=1;
            double a = Math.floor((longitud-2)/2);
            int indice_padre = (int) a;
            int k = longitud - 1;
            while ((this.valor_i(indice_padre) <= this.valor_i(k)) && k!= 0){
                if((this.valor_i(indice_padre) < this.valor_i(k)) || menorSegunElSegundoCriterio(k,indice_padre)){
                    int ind_pad = (int) indice_padre;
                    this.swap(ind_pad,k);
                    k = ind_pad;
                    indice_padre = (int) Math.floor((k-1)/2);
                }
                else{
                    return;
                }
            }
        }
    }

    public void desencolar(){
        eliminarPorIndice(0);
    }

    private void eliminarPorIndice(int pos){
        swap(pos,longitud-1);
        longitud--;
        if(longitud > 0){
            int actual = pos;
            int valorAntesDelHeapify = valor_i(pos);
            while(actual*2+1 < longitud){
                heapify_aux(actual);
                if(valorAntesDelHeapify == valor_i(actual)){
                    return;
                }
                else if(valorAntesDelHeapify == valor_i(actual*2+1)){
                    actual = actual*2+1;
                }
                else{
                    actual = actual*2+2;
                }
            }
        }
    }
    
    private int valor_i (int i){
        if (tipo == 0){
            ArrayList<Traslado> a = (ArrayList<Traslado>) arr;
            return a.get(i).ganancia();
        }
        else{
            if (tipo == 1){
                ArrayList<Traslado> a = (ArrayList<Traslado>) arr;
                return a.get(i).antiguedad();
            }
            else{
                ArrayList<ArrayList<Integer>> a = (ArrayList <ArrayList<Integer>>) arr;
                return a.get(i).get(2);
            }
        }
    }
    
    private int valorSegundoCriterio_i (int i){
        if (tipo == 0){
            ArrayList<Traslado> a = (ArrayList<Traslado>) arr;
            return a.get(i).id();
        }
        else{
            return i;
        }
    }

    private int valor_hijo_izquierdo (int i){
        return this.valor_i(2*i+1);
    }

    private int valor_hijo_derecho (int i){
        return this.valor_i (2*i+2);
    }

    private void heapify_aux (int i){
        if (2*i+2 < longitud){
            int padre = this.valor_i(i);
            int hijoizq = this.valor_hijo_izquierdo(i);
            int hijoder = this.valor_hijo_derecho(i);
            if (!esMayorQueLosHijos(padre, hijoizq, hijoder)){
                if (hijoizq > hijoder){
                    this.swap(i, 2*i+1);
                }
                else{
                    this.swap(i,2*i+2);
                }
            }
        }
        else if (2*i+1 < longitud){
            int padre = this.valor_i(i);
            int hijoizq = this.valor_hijo_izquierdo(i);
            if (padre < hijoizq){
                this.swap(i, 2*i+1);
            }
        }
    }

    private void swap (int i, int k){
        T elem_i = arr.get(i);
        T elem_k = arr.get(k);
        if (tipo == 0){
            Traslado ielem = (Traslado) elem_i;
            Traslado kelem = (Traslado) elem_i;
            ielem.añadir_pos_heap_ganancia(k);
            kelem.añadir_pos_heap_ganancia(i);
        }
        else if (tipo == 1){
            Traslado ielem = (Traslado) elem_i;
            Traslado kelem = (Traslado) elem_i;
            ielem.añadir_pos_heap_antiguedad(k);
            kelem.añadir_pos_heap_antiguedad(i);
        }
        else{
            ArrayList<Integer> ielem = (ArrayList<Integer>) elem_i;
            ArrayList<Integer> kelem = (ArrayList<Integer>) elem_k;
            ielem.set(3,k);
            kelem.set(3,i);
        }
        arr.set(i,elem_k);
        arr.set(k,elem_i);
    }

    private boolean esMayorQueLosHijos (int padre, int hijoizquierdo, int hijoderecho){
        if (padre > hijoizquierdo && padre > hijoderecho){
            return true;
        }
        else {
            return false;
        }
    }
}

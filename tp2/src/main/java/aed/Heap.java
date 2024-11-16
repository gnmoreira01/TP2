package aed;
import java.sql.Array;
import java.util.ArrayList;

public class Heap<T> {
    private ArrayList<T> atributo_array ;
    private int longitud;
    private int tipo;
    /* tipo = 0 equivale a max-heap por ganancia, i = 1 min-heap por antiguedad e
    i = 2 max-heap de ciudades por superávit */


    // El tema de actualizar los otros heaps al despachar lo solucionamos haciendo eliminar y encolar, solo queda 2 log n en vez de log n, lo cual es lo mismo en el O().
    public Heap (int i){
        atributo_array = new ArrayList<T>();
        tipo = i;
    }

    public Heap (ArrayList<T> array, int t){
        longitud = array.size();
        tipo = t;
        atributo_array = array;
        for (int i = longitud - 1; i > -1; i--){
            if (tipo == 0){
                ArrayList<Traslado> a = (ArrayList<Traslado>) atributo_array;
                a.get(i).cambiar_pos_heap_ganancia(i);
            }  
            else if (tipo == 1){
                ArrayList<Traslado> a = (ArrayList<Traslado>) atributo_array;
                a.get(i).cambiar_pos_heap_antiguedad(i);
            }
            else{
                ArrayList<ArrayList<Integer>> a = (ArrayList<ArrayList<Integer>>) atributo_array;
                a.get(i).set(3,i);
            }
            heapify(i);
        }
    }

    public int longitud(){
        return longitud;
    }

    public void encolar (T elem){
        if (atributo_array.size() == 0){
            atributo_array.add(elem);
            longitud += 1;
        }  
        else{
            atributo_array.add(elem);
            longitud += 1;
            double a = Math.floor((longitud-2)/2);
            int indice_padre = (int) a;
            int k = longitud - 1;
            while ((this.valor_i(indice_padre) <= this.valor_i(k)) && k!= 0){
                if((this.valor_i(indice_padre) < this.valor_i(k)) || menorSegunElSegundoCriterio(k,indice_padre)){
                    this.swap(indice_padre,k);
                    k = indice_padre;
                    indice_padre = (int) Math.floor((k-1)/2);
                }
                else{
                    return;
                }
            }
        }
    }
    public T desencolar(){
        return eliminarPorIndice(0);
    }

    public T eliminarPorIndice(int pos){
        swap(pos,longitud-1);
        T eliminado = atributo_array.get(longitud-1);
        if(tipo!=2){
            atributo_array.remove(longitud-1);
        }
        longitud--;
        if(longitud > 0 && pos < longitud){
            heapify(pos);
        }
        return eliminado;
    }

    public int consultarIDdelMax(){
        return valorSegundoCriterio_i(0);
    }
    
    private int valor_i (int i){
        if (tipo == 0){
            ArrayList<Traslado> a = (ArrayList<Traslado>) atributo_array;
            return a.get(i).ganancia();
        }
        else{
            if (tipo == 1){
                ArrayList<Traslado> a = (ArrayList<Traslado>) atributo_array;
                return a.get(i).timestamp();
            }
            else{
                ArrayList<ArrayList<Integer>> a = (ArrayList <ArrayList<Integer>>) atributo_array;
                return a.get(i).get(2);
            }
        }
    }
    
    private int valorSegundoCriterio_i (int i){
        if (tipo == 0){
            ArrayList<Traslado> a = (ArrayList<Traslado>) atributo_array;
            return a.get(i).id();
        }
        else if(tipo== 1){
            ArrayList<Traslado> a = (ArrayList<Traslado>) atributo_array;
            return a.get(i).id();
            //Nota: En realidad no nos interesa que haya un criterio de desmpate para translado más antiguo, ya que los timestamp nunca pueden ser iguales. 
            //Pero, como tambien la usamos para devolver los IDS a la hora de despachar, utilizamos el ID como "segundo criterio".
        }    
        else{
            ArrayList<ArrayList<Integer>> a = (ArrayList <ArrayList<Integer>>) atributo_array;
            return a.get(i).get(4);
        }
    }

    private boolean menorSegunElSegundoCriterio(int i, int j){
        if(valorSegundoCriterio_i(i) < valorSegundoCriterio_i(j)){
            return true;
        }
        else{
            return false;
        }
    }
    
    private int valor_hijo_izquierdo (int i){
        return this.valor_i(2*i+1);
    }

    private int valor_hijo_derecho (int i){
        return this.valor_i (2*i+2);
    }

    private void heapify (int pos){
        int actual = pos;
        int valorAntesDelHeapify = valor_i(pos);
        int segundoValorAntesDelHeapify = valorSegundoCriterio_i(pos); 
        while(actual*2+1 < longitud){
            heapify_aux(actual);
            if((valorAntesDelHeapify == valor_i(actual)) && (segundoValorAntesDelHeapify == valorSegundoCriterio_i(actual))){
                return;
            }
            else if((valorAntesDelHeapify == valor_i(actual*2+1)) && (segundoValorAntesDelHeapify == valorSegundoCriterio_i(actual*2+1))){
                actual = actual*2+1;
            }
            else{
                actual = actual*2+2;
            }
        }
    }

    private void heapify_aux (int i){
        if (2*i+2 < longitud){
            int padre = this.valor_i(i);
            int hijoIzq = 2*i+1;
            int hijoDer = 2*i+2;
            int valorHijoIzq = this.valor_hijo_izquierdo(i);
            int valorHijoDer = this.valor_hijo_derecho(i);            
            if (!esMayorQueLosHijos(padre, valorHijoIzq, valorHijoDer)){
                if (valorHijoIzq > valorHijoDer || ((valorHijoIzq == valorHijoDer) && menorSegunElSegundoCriterio(hijoIzq, hijoDer))){
                    if((valorHijoIzq > padre) || (((valorHijoIzq == padre) && menorSegunElSegundoCriterio(hijoIzq, i)))){
                        this.swap(i, 2*i+1);
                    }
                }
                else{
                    if((valorHijoDer > padre) || (((valorHijoDer == padre) && menorSegunElSegundoCriterio(hijoDer, i)))){
                        this.swap(i,2*i+2);
                    }
                }
            }
        }
        else if (2*i+1 < longitud){
            int padre = this.valor_i(i);
            int hijoIzq = 2*i+1;
            int valorHijoIzq = this.valor_hijo_izquierdo(i);
            if ((padre < valorHijoIzq) || ((padre == valorHijoIzq) && (menorSegunElSegundoCriterio(hijoIzq, i)))){
                this.swap(i, 2*i+1);
            }
        }
    }

    private void swap (int i, int k){
        T elem_i = atributo_array.get(i);
        T elem_k = atributo_array.get(k);
        if (tipo == 0){
            Traslado ielem = (Traslado) elem_i;
            Traslado kelem = (Traslado) elem_i;
            ielem.cambiar_pos_heap_ganancia(k);
            kelem.cambiar_pos_heap_ganancia(i);
        }
        else if (tipo == 1){
            Traslado ielem = (Traslado) elem_i;
            Traslado kelem = (Traslado) elem_i;
            ielem.cambiar_pos_heap_antiguedad(k);
            kelem.cambiar_pos_heap_antiguedad(i);
        }
        else{
            ArrayList<Integer> ielem = (ArrayList<Integer>) elem_i;
            ArrayList<Integer> kelem = (ArrayList<Integer>) elem_k;
            ielem.set(3,k);
            kelem.set(3,i);
        }
        atributo_array.set(i,elem_k);
        atributo_array.set(k,elem_i);
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

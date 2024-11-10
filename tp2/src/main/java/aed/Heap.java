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

    public encolar (T elem){
        if (arr.size() == 0){
            arr.add(elem);
            longitud += 1;
        }  
        else{
            arr.add(elem);
            longitud+=1;
            double indice_padre = Math.floor((longitud-2)/2);
            int k = longitud - 1;
            while ((this.valor_i(indice_padre) < this.valor_i(longitud-1)) && () ){
                this.swap(indice_padre,longitud-1);
            }
        }
    }

    public desencolar(){
        swap(0,longitud-1);
        longitud--;
        if(longitud > 0){
            int actual = 0;
            int valorAntesDelHeapify = valor_i(actual);
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

    private T hijoizquierdo (int i){
        return arr.get(2*i + 1);
    }

    private int valor_i (int i){
        if (tipo == 0){
            return arr.get(i).ganancia();
        }
        else{
            if (tipo == 1){
                return arr.get(i).antiguedad();
            }
            else{
                return arr.get(i).get(2);
            }
        }
    }

    private int valor_hijo_izquierdo (int i){
        if (tipo == 0){
            return this.hijoizquierdo(i).ganancia();
        }
        else{
            if (tipo == 1){
                return this.hijoizquierdo(i).antiguedad();
            }
            else{
                return this.hijoizquierdo(i).get(2);
            }
        }
    }

    private int valor_hijo_derecho (int i){
        if (tipo == 0){
            return this.hijoderecho(i).ganancia();
        }
        else{
            if (tipo == 1){
                return this.hijoderecho(i).antiguedad();
            }
            else{
                return this.hijoderecho(i).get(2);
            }
        }
    }

    private T hijoderecho (int i){
        return arr.get(2*i+2);
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
            if (padre < hijoizq){
                this.swap(i, 2*i+1);
            }
        }
    }

    private void swap (int i, int k){
        int elem_i = arr.get(i);
        int elem_k = arr.get(k);
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

package aed;

import java.util.ArrayList;

public class BestEffort {
    private Heap<Traslado> heap_pedidos_por_ganancia;
    private Heap<Traslado> heap_pedidos_por_antiguedad;
    private ArrayList<Traslado> vector_traslados;
    private Heap<ArrayList<Integer>> heap_ciudades_mayor_superavit;
    private int cantidad_pedidos_despachados;
    private int ganancia_global;
    private ArrayList<ArrayList<Integer>> estadisticas_ciudades;
    private ArrayList<Integer> ciudades_mayor_ganancia;
    private ArrayList<Integer> ciudades_mayor_perdida;

    /* estadisticas_ciudades es un vector de vectores con 5 posiciones, las cuales son ganancia, pérdida, superávit, posicion en el heap de
     * superávit e ID
     */

    public BestEffort(int cantCiudades, Traslado[] traslados){
        cantidad_pedidos_despachados = 0;
        ganancia_global = 0;
        ciudades_mayor_ganancia = new ArrayList<Integer> ();
        ciudades_mayor_perdida = new ArrayList<Integer>();
        for (int i = 0; i < traslados.length; i++){
            traslados[i].añadir_indice(i);
            traslados[i].negar_timestamp(); //Esto es solo para poder usar el heap de antiguedad como9 un max-heap.
            vector_traslados.add(traslados[i]);
        }
        heap_pedidos_por_ganancia = new Heap<Traslado>(vector_traslados, 0);
        heap_pedidos_por_antiguedad = new Heap <Traslado> (vector_traslados, 1);
        estadisticas_ciudades = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < cantCiudades; i++){
           ArrayList<Integer> ciudad = new ArrayList<Integer>(); 
           ciudad.add(0);
           ciudad.add(0); 
           ciudad.add(0); 
           ciudad.add(i);
           ciudad.add(i);
           estadisticas_ciudades.add(ciudad);
           ciudades_mayor_ganancia.add(i);
           ciudades_mayor_perdida.add(i);
        }
        heap_ciudades_mayor_superavit = new Heap<ArrayList<Integer>>(estadisticas_ciudades,2);
    }

    public void aumentarElemArrayList (ArrayList<ArrayList<Integer>> array, int ciudad, int atributoDeCiudad, int valor){
        ArrayList<Integer> elem_ciudad = array.get(ciudad);
        int valor_viejo = elem_ciudad.get(atributoDeCiudad);
        elem_ciudad.set(atributoDeCiudad, valor_viejo + valor);

    }

    public void registrarTraslados(Traslado[] nuevos_traslados){
        if (nuevos_traslados.length != 0){
            for (int i = 0; i < nuevos_traslados.length; i++){
                int indice = vector_traslados.size();
                nuevos_traslados[i].añadir_indice(indice);
                vector_traslados.add(nuevos_traslados[i]);
                heap_pedidos_por_ganancia.encolar(nuevos_traslados[i]);
                heap_pedidos_por_antiguedad.encolar(nuevos_traslados[i]);
            }
        }
    }
    
    public int[] despacharMasRedituables(int n){
        if(n > heap_pedidos_por_ganancia.longitud()){
            n = heap_pedidos_por_ganancia.longitud();
        }
        int [] ids = new int[n];
        if (n == 0){
            return ids;
        }
        else{
            ids = despacho(heap_pedidos_por_ganancia,n,0);   
            return ids;
        }
    }

    public int[] despacharMasAntiguos(int n){
        if(n > heap_pedidos_por_ganancia.longitud()){
            n = heap_pedidos_por_ganancia.longitud();
        }
        int [] ids = new int[n];
        if (n == 0){
            return ids;
        }
        else{
            ids = despacho(heap_pedidos_por_ganancia,n,1);   
            return ids;
        }
    }

    public int [] despacho(Heap<Traslado> heapDespachado,int cantDespachos, int tipo){
        int [] ids = new int[cantDespachos];
        for (int i = 0; i < cantDespachos; i++){
            Traslado pedido = heapDespachado.desencolar();
            int ganancia_pedido = pedido.ganancia();
            int perdida_pedido = ganancia_pedido;
            int ciudad_origen = pedido.origen();
            int ciudad_destino = pedido.destino();
            aumentarElemArrayList(estadisticas_ciudades,ciudad_origen,0,ganancia_pedido);
            aumentarElemArrayList(estadisticas_ciudades,ciudad_origen,2,ganancia_pedido);
            aumentarElemArrayList(estadisticas_ciudades,ciudad_destino,1,perdida_pedido);
            aumentarElemArrayList(estadisticas_ciudades,ciudad_destino,2,(-perdida_pedido));
            if (comparacionConElMaximo(ciudad_origen,0,ciudades_mayor_ganancia) == 0){
                ciudades_mayor_ganancia.add(ciudad_origen);
            }
            else if (comparacionConElMaximo(ciudad_origen,0,ciudades_mayor_ganancia) > 0)
                ciudades_mayor_ganancia = new ArrayList<Integer>();
                //se supero el maximo, se elimina todos los IDs anteriormente guardados.
                ciudades_mayor_ganancia.add(ciudad_origen);
            if (comparacionConElMaximo(ciudad_destino,1,ciudades_mayor_perdida) == 0){
                ciudades_mayor_perdida.add(ciudad_destino);
            }
            else if (comparacionConElMaximo(ciudad_destino,1,ciudades_mayor_perdida) > 0){
                ciudades_mayor_perdida = new ArrayList<Integer>();
                //se supero el maximo, se elimina todos los IDs anteriormente guardados.
                ciudades_mayor_perdida.add(ciudad_origen);
            }

            //Actualio las variables goblales y guardo el ID del pedido recién desencolado.

            cantidad_pedidos_despachados+= 1;
            ganancia_global+= ganancia_pedido;
            ids[i] = pedido.id();

            //Si es del tipo 0 es el heap por ganancia, asi que me falta actualizar el heap antiguedad.
            //Para ello eliminare por indice en dicho heap. Misma lógica para el tipo 1, pero al revés.
            if(tipo == 0){
                heap_pedidos_por_antiguedad.eliminarPorIndice(pedido.pos_heap_antiguedad);
            }

            else{
                heap_pedidos_por_ganancia.eliminarPorIndice(pedido.pos_heap_ganancia);
            }

            //Tengo que "actualizar" el heap Superavit, ya que han cambiado los valores de dos nodos.
            //Para ello elimino en el heap dichos valores y los vuelvo a encoar (Para que la comparación se realice con el valor modificado)

            heap_ciudades_mayor_superavit.eliminarPorIndice((estadisticas_ciudades.get(ciudad_destino)).get(3));
            heap_ciudades_mayor_superavit.eliminarPorIndice((estadisticas_ciudades.get(ciudad_origen)).get(3));
            heap_ciudades_mayor_superavit.encolar(estadisticas_ciudades.get(ciudad_destino));
            heap_ciudades_mayor_superavit.encolar(estadisticas_ciudades.get(ciudad_origen));
        }
        return ids;
    }
    
    private int comparacionConElMaximo (int c, int atributo, ArrayList<Integer> arr){
        ArrayList <Integer> ciudad = estadisticas_ciudades.get(c);
        ArrayList <Integer> ciudad_referencia = estadisticas_ciudades.get(arr.get(0));
        return (ciudad.get(atributo).compareTo(ciudad_referencia.get(atributo)));
    }
    
    public int ciudadConMayorSuperavit(){
        return heap_ciudades_mayor_superavit.consultarIDdelMax();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return ciudades_mayor_ganancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return ciudades_mayor_perdida;
    }

    public int gananciaPromedioPorTraslado(){ 
        return ganancia_global/cantidad_pedidos_despachados;
    }
    
}

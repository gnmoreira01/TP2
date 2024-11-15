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

        public void modificarArrayList (ArrayList<ArrayList<Integer>> array, int ciudad, int atributoDeCiudad, int valor){
            array.get(ciudad).set(atributoDeCiudad, valor);
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
        int [] ids = new int[n];
        if (n==0 || vector_traslados.size()==0){
            return ids;
        }
        else{
            for (int i = 0; i<n;i++){
                int id_pedido = heap_pedidos_por_ganancia.desencolar();
                ids[i] = id_pedido;
            }
            heap_pedidos_por_antiguedad.eliminarPorIndice(n)
        }
    }

    public int[] despacharMasAntiguos(int n){
        // Implementar
        return null;
    }

    public int ciudadConMayorSuperavit(){
        // Implementar
        return 0;
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

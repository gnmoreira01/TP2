package aed;

import java.util.ArrayList;

public class BestEffort {
    private Heap<Traslado> heap_pedidos_por_ganancia;
    private Heap<Traslado> heap_pedidos_por_antiguedad;
    private ArrayList<Traslado> vector_traslados;
    private Heap<ArrayList<Integer>> heap_ciudades_mayor_superavit;
    private int cantidad_pedidos_despachados;
    private int ganancia_global;
    private ArrayList<Integer> estadisticas_ciudades;
    private ArrayList<ArrayList<Integer>> ciudades_mayor_ganancia;
    private ArrayList<ArrayList<Integer>> ciudades_mayor_perdida;

    /* estadisticas_ciudades es un vector de vectores con 4 posiciones, las cuales son ganancia, pérdida, superávit y posicion en el heap de
     * superávit.
     */


    public BestEffort(int cantCiudades, Traslado[] traslados){
        cantidad_pedidos_despachados = 0;
        ganancia_global = 0;
        ciudades_mayor_ganancia = new ArrayList<ArrayList<Integer>> ();
        ciudades_mayor_perdida = new ArrayList<ArrayList<Integer>>();
        }

    public void registrarTraslados(Traslado[] traslados){
        // Implementar
    }

    public int[] despacharMasRedituables(int n){
        // Implementar
        return null;
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
        // Implementar
        return null;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        // Implementar
        return null;
    }

    public int gananciaPromedioPorTraslado(){
        // Implementar
        return 0;
    }
    
}

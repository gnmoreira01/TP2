package aed;

import java.util.ArrayList;
import Heap;

public class BestEffort {
    private Heap<Traslado> heap_pedidos_por_ganancia;
    private Heap<Traslado> heap_pedidos_por_antiguedad;
    private ArrayList vector_traslados;
    private Heap heap_ciudades_mayor_superavit;
    private int cantidad_pedidos_despachados;
    private int ganancia_global;
    private ArrayList estadisticas_ciudades;
    private ArrayList ciudades_mayor_ganancia;
    private ArrayList ciudades_mayor_pérdida;

    /* estadisticas_ciudades es un vector de vectores con 4 posiciones, las cuales son ganancia, pérdida, superávit y posicion en el heap de
     * superávit.
     */


    public BestEffort(int cantCiudades, Traslado[] traslados){
        int i = 0;
        while (i < traslados.length){
            heap_pedidos_por_antiguedad.add(traslados[i]);
            heap_pedidos_por_ganancia.add(traslados[i]);
            vector_traslados.add({traslados[i],null});
        }

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

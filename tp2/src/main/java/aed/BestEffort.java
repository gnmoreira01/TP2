package aed;

import java.util.ArrayList;

public class BestEffort {
    private Heap<Traslado> heap_pedidos_por_ganancia;
    private Heap<Traslado> heap_pedidos_por_antiguedad;
    private ArrayList<Traslado> vector_traslados;
    private Heap<ArrayList<Integer>> heap_ciudades_mayor_superavit;
    private int cantidad_pedidos_despachados;
    private int ganancia_global;
    private int [] [] estadisticas_ciudades;
    private ArrayList<Integer> ciudades_mayor_ganancia;
    private ArrayList<Integer> ciudades_mayor_perdida;

    /* estadisticas_ciudades es un vector de vectores con 4 posiciones, las cuales son ganancia, pérdida, superávit y posicion en el heap de
     * superávit.
     */


    public BestEffort(int cantCiudades, Traslado[] traslados){
        cantidad_pedidos_despachados = 0;
        ganancia_global = 0;
        ciudades_mayor_ganancia = new ArrayList<Integer> ();
        ciudades_mayor_perdida = new ArrayList<Integer>();
        for (int i = 0; i < traslados.length; i++){
            traslados[i].añadir_indice(i);
            traslados[i].negar_timestamp(); //Esto es solo para convertirlo en un max-heap más facilmente.
            vector_traslados.add(traslados[i]);
        }
        heap_pedidos_por_ganancia = new Heap<Traslado>(vector_traslados, 0);
        heap_pedidos_por_antiguedad = new Heap <Traslado> (vector_traslados, 1);
        estadisticas_ciudades = new int[cantCiudades][4];
        for (int i = 0; i < cantCiudades; i++){
            estadisticas_ciudades[i][0] = i;
            estadisticas_ciudades[i][1] = 0;
            estadisticas_ciudades[i][2] = 0;
            estadisticas_ciudades[i][3] = i;
           ciudades_mayor_ganancia.add(i);
           ciudades_mayor_perdida.add(i);
        }
        heap_ciudades_mayor_superavit(estadisticas_ciudades);


        heap_ciudades_mayor_superavit = new Heap <ArrayList<Integer>>(2);
        }

    public void registrarTraslados(Traslado[] traslados){
        for (int i = 0; i < traslados.length; i++){
            vector_traslados.add(traslados[i]);
            heap_pedidos_por_ganancia.encolar(traslados[i]);
            
        }
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
        if (cantidad_pedidos_despachados == 0){
            return 
        }
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

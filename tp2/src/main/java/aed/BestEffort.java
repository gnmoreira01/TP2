package aed;

import java.util.ArrayList;

public class BestEffort {
    private Heap<Traslado> heap_pedidos_por_ganancia;
    private Heap<Traslado> heap_pedidos_por_antiguedad;
    private Heap<Ciudad> heap_ciudades_mayor_superavit;
    private int cantidad_pedidos_despachados;
    private int ganancia_global;
    private ArrayList<Ciudad> estadisticas_ciudades;
    private ArrayList<Integer> ciudades_mayor_ganancia;
    private ArrayList<Integer> ciudades_mayor_perdida;
    private boolean [] esta_en_mayor_ganancia;
    private boolean [] esta_en_mayor_perdida;

    /* estadisticas_ciudades es un vector de vectores con 5 posiciones, las cuales son: Ganancia, pérdida, superávit, posicion en el heap de
     * superávit e ID.
     */

    public BestEffort(int cantCiudades, Traslado[] traslados){
        cantidad_pedidos_despachados = 0;
        ganancia_global = 0;
        ciudades_mayor_ganancia = new ArrayList<Integer> ();
        ciudades_mayor_perdida = new ArrayList<Integer>();
        ArrayList<Traslado> vectorTraslados = new ArrayList<Traslado>();
        for (int i = 0; i < traslados.length; i++){
            vectorTraslados.add(traslados[i]);
            traslados[i].negar_timestamp(); //Esto es poder usar el heap de antiguedad cómo un max-heap.
            traslados[i].cambiar_pos_heap_antiguedad(i);
            traslados[i].cambiar_pos_heap_ganancia(i); 
        }
        //Registramos todos los traslados en un bucle que va de 0 al tamaño de la secuencia, en definitiva, eso es O(|T|). 
        heap_pedidos_por_ganancia = new Heap<Traslado>(vectorTraslados, 0);
        heap_pedidos_por_antiguedad = new Heap <Traslado> (vectorTraslados,1);
        //Ambos heaps se crean utilizando el algoritmo de heapify sobre la secuencia anteriormente formada, por lo cual tienen complejidad O(|T|). 
        estadisticas_ciudades = new ArrayList<Ciudad>();
        esta_en_mayor_ganancia = new boolean[cantCiudades];
        esta_en_mayor_perdida = new boolean[cantCiudades];
        for (int i = 0; i < cantCiudades; i++){
           Ciudad ciudad = new Ciudad(i); 
           estadisticas_ciudades.add(ciudad);
           ciudades_mayor_ganancia.add(i);
           ciudades_mayor_perdida.add(i);
        }
        //Misma lógica que el primer bucle, pero esta vez la complejidad está atada al tamaño de ciudades. O(|C|)
        heap_ciudades_mayor_superavit = new Heap<Ciudad>(estadisticas_ciudades,2);
        //Misma lógica que los dos heaps anteriores, con la diferencia que este se crea sobre la secuecnia estadisticas_ciudades. O(|C|)
    }

    // Conclusión: La complejidad de crear el sistema es O(|T|+|C|)

    public void registrarTraslados(Traslado[] nuevos_traslados){
        if (nuevos_traslados.length != 0){
            for (int i = 0; i < nuevos_traslados.length; i++){
                nuevos_traslados[i].negar_timestamp();
                nuevos_traslados[i].cambiar_pos_heap_antiguedad(heap_pedidos_por_antiguedad.longitud());
                nuevos_traslados[i].cambiar_pos_heap_ganancia(heap_pedidos_por_ganancia.longitud()); 
                heap_pedidos_por_ganancia.encolar(nuevos_traslados[i]);
                heap_pedidos_por_antiguedad.encolar(nuevos_traslados[i]);
            }
        }
        //El bucle va de 0 a |T|, y dentro realiza la negación de un elemento de la lista ( O(1) ) y la operación encolar, la cual tiene costo O(log n).
        //Conclusión: La complejidad de registrar traslados es O(|T|log(|T|))
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
        //El peor caso en cuanto a complejidad es el de la rama else, que tiene complejidad O(n(log(|C|)+log(|T|))).
    }

    public int[] despacharMasAntiguos(int n){
        if(n > heap_pedidos_por_antiguedad.longitud()){
            n = heap_pedidos_por_antiguedad.longitud();
        }
        int [] ids = new int[n];
        if (n == 0){
            return ids;
        }
        else{
            ids = despacho(heap_pedidos_por_antiguedad,n,1);   
            return ids;
        }
        //El peor caso en cuanto a complejidad es el de la rama else, que tiene complejidad O(n(log(|C|)+log(|T|))).
    }
    
    public int ciudadConMayorSuperavit(){
        return heap_ciudades_mayor_superavit.consultarIDdelMax();
        //Es ver un atributo de la primera posición. O(1).
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return ciudades_mayor_ganancia;
        //Simplemente devuelve un atributo: O(1).
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return ciudades_mayor_perdida;
        //Simplemente devuelve un atributo: O(1).
    }

    public int gananciaPromedioPorTraslado(){ 
        return ganancia_global/cantidad_pedidos_despachados;
        //Son todas operaciones elementales: O(1).
    }
    
    public void aumentarElemArrayList (ArrayList<ArrayList<Integer>> array, int ciudad, int atributoDeCiudad, int valor){
        ArrayList<Integer> elem_ciudad = array.get(ciudad);
        int valor_viejo = elem_ciudad.get(atributoDeCiudad);
        elem_ciudad.set(atributoDeCiudad, valor_viejo + valor);

    }

    public int [] despacho(Heap<Traslado> heapDespachado,int cantDespachos, int tipo){
        int [] ids = new int[cantDespachos];
        //Para no dificultar la lectura, aclararemos la complejidad de las operaciones que estén por encima de O(1). 
        for (int i = 0; i < cantDespachos; i++){
            Traslado pedido = heapDespachado.desencolar(); //O(log(|T|)), pues heap despachado puede ser el de mayor antiguedad o mayor ganancia.
            int ganancia_pedido = pedido.ganancia();
            int perdida_pedido = ganancia_pedido;
            int ciudad_origen = pedido.origen();
            int ciudad_destino = pedido.destino();
            int maximaGanancia = estadisticas_ciudades.get(ciudades_mayor_ganancia.get(0)).ganancia();
            int maximaPerdida = estadisticas_ciudades.get(ciudades_mayor_perdida.get(0)).perdida();
            estadisticas_ciudades.get(ciudad_origen).sumar_ganancia(ganancia_pedido);
            estadisticas_ciudades.get(ciudad_origen).actualizar_superavit();
            estadisticas_ciudades.get(ciudad_destino).sumar_perdida(perdida_pedido);
            estadisticas_ciudades.get(ciudad_destino).actualizar_superavit(); // ACLARAR POR QUÉ ************************************************************************ O(1).
            if (maximaGanancia == estadisticas_ciudades.get(ciudad_origen).ganancia()){
                if (!esta_en_mayor_ganancia[ciudad_origen]){
                    ciudades_mayor_ganancia.add(ciudad_origen);
                    esta_en_mayor_ganancia[ciudad_origen] = true;
                }
                //Si luego del despacho, se empató el máximo, se agrega a las ciudades con mayor ganancia. 
                //¿Por qué hacemos el pertenece? Para que no haya duplicados.
                //¿Cómo lo hacemos en O(1)? Con un array de booleanos.  
            }
            else if (estadisticas_ciudades.get(ciudad_origen).ganancia()> maximaGanancia){
                ArrayList<Integer> nuevo_Ciudades = new ArrayList<Integer>();
                esta_en_mayor_ganancia = new boolean[estadisticas_ciudades.size()];
                nuevo_Ciudades.add(ciudad_origen);
                ciudades_mayor_ganancia = nuevo_Ciudades;
                esta_en_mayor_ganancia [ciudad_origen] = true;
                //Como se supero el máximo, se elimina todos los IDs anteriormente guardados y se "reinicia" el array de booleanos.
            }   
            if (maximaPerdida == estadisticas_ciudades.get(ciudad_destino).perdida()){
                if (!esta_en_mayor_perdida[ciudad_destino]){
                    ciudades_mayor_perdida.add(ciudad_destino);
                    esta_en_mayor_perdida[ciudad_destino] = true;
                }
                //Misma lógica del primer if.
            }
            else if (estadisticas_ciudades.get(ciudad_destino).perdida() > maximaPerdida){
                ArrayList<Integer> nuevo_Ciudades = new ArrayList<Integer>();
                esta_en_mayor_perdida = new boolean[estadisticas_ciudades.size()];
                nuevo_Ciudades.add(ciudad_destino);
                ciudades_mayor_perdida = nuevo_Ciudades;
                esta_en_mayor_perdida [ciudad_destino] = true;
                //Misma lógica del else if.
            }
            cantidad_pedidos_despachados+= 1;
            ganancia_global+= ganancia_pedido;
            ids[i] = pedido.id();

            //Actualizo las variables goblales y guardo el ID del pedido recién desencolado.

            if(tipo == 0){
                heap_pedidos_por_antiguedad.eliminarPorIndice(pedido.pos_heap_antiguedad);
            }

            else{
                heap_pedidos_por_ganancia.eliminarPorIndice(pedido.pos_heap_ganancia);
            }

            //Si es del tipo 0 es el heap por ganancia, asi que me falta actualizar el heap antiguedad, y viceversa para el tipo 1.
            //Para ello eliminaré por indice en el heap "contrario" ¿Pero cómo sé la posición en dicho heap? Porque la tengo guardada!
            //En ambos casos, eliminar por indice es O(log(|T|))

            heap_ciudades_mayor_superavit.eliminarPorIndice((estadisticas_ciudades.get(ciudad_destino)).posicion_heap());
            heap_ciudades_mayor_superavit.encolar(estadisticas_ciudades.get(ciudad_destino));
            heap_ciudades_mayor_superavit.eliminarPorIndice((estadisticas_ciudades.get(ciudad_origen)).posicion_heap());
            heap_ciudades_mayor_superavit.encolar(estadisticas_ciudades.get(ciudad_origen));

            //Tengo que "actualizar" el heap superavit, ya que han cambiado los valores de dos elementos.
            //Para ello elimino en el heap dichos valores y los vuelvo a encolar (Para que la comparación se realice con el valor modificado).
            //Encolar y eliminar por indice son O(log(|C|))
        }
        //El for realizado va de 0 a cantDespachos, y el bloque interno tiene complejidad O(log(|C|)+log(|T|)).
        //Conclusión: O(cantDespachos(log(|C|)+log(|T|)))
        return ids;
    }
}

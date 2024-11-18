package aed;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;

public class HeapTests {
    int tipo;
    Traslado listaTraslados[];
    ArrayList<Traslado> arrlist_listaTraslados;
    ArrayList<Traslado> arrlist_listaTraslados_ganancia;
    ArrayList<Traslado> arrlist_listaTraslados_antiguedad;

    @BeforeEach
    void init(){
        listaTraslados = new Traslado[] {
                                            new Traslado(1, 0, 1, 100, -10),
                                            new Traslado(2, 0, 1, 400, -20),
                                            new Traslado(3, 3, 4, 500, -50),
                                            new Traslado(4, 4, 3, 500, -11),
                                            new Traslado(5, 1, 0, 1000, -40),
                                            new Traslado(6, 1, 0, 1000, -41),
                                            new Traslado(7, 6, 3, 2000, -42)
                                        };

        Traslado [] listaTraslados_por_ganancia = new Traslado [] {
            new Traslado(7, 6, 3, 2000, -42),
            new Traslado(5, 1, 0, 1000, -40), 
            new Traslado(6, 1, 0, 1000, -41),
            new Traslado(4, 4, 3, 500, -11),
            new Traslado(2, 0, 1, 400, -20),
            new Traslado(1, 0, 1, 100, -10),
            new Traslado(3, 3, 4, 500, -50),
        };

        Traslado [] listaTraslados_por_antiguedad = new Traslado []{
            // Los timestamps están negados solo porque la clase heap los usa así para actuar como Max-Heap.
            new Traslado(1, 0, 1, 100, -10),
            new Traslado(4, 4, 3, 500, -11),
            new Traslado(2, 0, 1, 400, -20),
            new Traslado(5, 1, 0, 1000, -40), 
            new Traslado(6, 1, 0, 1000, -41),
            new Traslado(7, 6, 3, 2000, -42),
            new Traslado(3, 3, 4, 500, -50),
        };

        arrlist_listaTraslados = new ArrayList<Traslado> ();
        arrlist_listaTraslados_antiguedad = new ArrayList<Traslado> ();
        arrlist_listaTraslados_ganancia = new ArrayList<Traslado> ();
        for (int i = 0; i < listaTraslados.length; i++){
            arrlist_listaTraslados.add(listaTraslados[i]);
            arrlist_listaTraslados_ganancia.add(listaTraslados_por_ganancia[i]);
            arrlist_listaTraslados_antiguedad.add(listaTraslados_por_antiguedad[i]);
        }
    }

    void assertSetEquals(ArrayList<Traslado> s1, ArrayList<Traslado> s2) {
        assertEquals(s1.size(), s2.size());
        for (Traslado e1 : s1) {
            boolean encontrado = false;
            for (Traslado e2 : s2) {
                if (e1.id == e2.id && e1.origen() == e2.origen() && e1.destino() == e2.destino() && e1.ganancia() == e2.ganancia() && e1.timestamp() == e2.timestamp()) encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " +  e1 + " en el arreglo " + s2.toString());
        }
    }

    @Test
    void test_constructor_heap_ganancia (){
        Heap<Traslado> h = new Heap<Traslado>(arrlist_listaTraslados,0);
        assertSetEquals(h.array(), arrlist_listaTraslados_ganancia);
    }

    @Test
    void test_constructor_heap_antiguedad (){
        Heap<Traslado> h = new Heap<Traslado> (this.arrlist_listaTraslados, 1);
        assertSetEquals(h.array(), arrlist_listaTraslados_antiguedad);
    }

    @Test
    void test_constructor_stress_empates(){
        Traslado [] traslados = new Traslado[100];
        int [] ref_arr = new int[]{99,93,98,76,92,97,60,67,75,83,91,96,51,55,59,62,66,70,74,78,82,86,90,94,95,48,50,28,54,56,58,31,45,63,65,35,69,71,73,39,77,79,81,43,85,87,89,47,61,46,29,5,23,22,49,4,25,12,53,11,27,26,57,0,16,9,36,6,30,21,64,3,32,15,68,14,34,33,72,2,20,17,44,8,38,37,80,7,40,19,84,18,42,41,88,1,24,13,52,10};
        ArrayList<Traslado> referencia = new ArrayList<Traslado>();
        ArrayList<Traslado> constructor = new ArrayList<Traslado>();
        for (int i = 0; i < 100; i++){
            traslados[i] = new Traslado(i, 0, 0, 0, 0);
            constructor.add(traslados[i]);
            Traslado a = new Traslado(ref_arr[i],0,0,0,0);
            referencia.add(a);
        }
        Heap<Traslado> heap = new Heap<Traslado>(constructor, 0);
        assertSetEquals(heap.array(), referencia);
    }

    @Test
    void test_desencolar (){
        Heap<Traslado> heap = new Heap<Traslado>(arrlist_listaTraslados,1);
        heap.desencolar();
        heap.desencolar();
        arrlist_listaTraslados_antiguedad = new ArrayList<>();
        Traslado[] nuevo_orden = new Traslado[]{
            new Traslado(2, 0, 1, 400, -20),
            new Traslado(5, 1, 0, 1000, -40), 
            new Traslado(7, 6, 3, 2000, -42),
            new Traslado(3, 3, 4, 500, -50),
            new Traslado(6, 1, 0, 1000, -41)
        };
        for (Traslado e : nuevo_orden){
            arrlist_listaTraslados_antiguedad.add(e);
        }
        assertSetEquals(heap.array(), arrlist_listaTraslados_antiguedad);
        heap.desencolar();
        Traslado[] nuevo_orden2 = new Traslado[]{
            new Traslado(5, 1, 0, 1000, -40), 
            new Traslado(6, 1, 0, 1000, -41),
            new Traslado(7, 6, 3, 2000, -42),
            new Traslado(3, 3, 4, 500, -50)
            };
        arrlist_listaTraslados_antiguedad = new ArrayList<>();
        for (Traslado e : nuevo_orden2){
            arrlist_listaTraslados_antiguedad.add(e);
        }
        assertSetEquals(heap.array(), arrlist_listaTraslados_antiguedad);
        heap.desencolar();
        heap.desencolar();
        heap.desencolar();
        Traslado[] nuevo_orden3 = new Traslado[]{new Traslado(3, 3, 4, 500, -50)};
        arrlist_listaTraslados_antiguedad = new ArrayList<>();
        for (Traslado e : nuevo_orden3){
            arrlist_listaTraslados_antiguedad.add(e);
        }
        assertSetEquals(heap.array(), arrlist_listaTraslados_antiguedad);
    }
}
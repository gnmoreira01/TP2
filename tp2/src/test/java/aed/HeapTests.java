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


}
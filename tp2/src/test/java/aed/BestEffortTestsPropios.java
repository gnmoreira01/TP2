package aed;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class BestEffortTestsPropios {

    int cantCiudades;
    Traslado[] listaTrasladosLarga;
    ArrayList<Integer> actual;


    @BeforeEach
    void init(){
        cantCiudades = 11;
        listaTrasladosLarga = new Traslado[] {
                                            new Traslado(8, 0, 1, 900, 10),
                                            new Traslado(6, 2, 5, 800, 20),
                                            new Traslado(12, 8, 7, 1200, 50),
                                            new Traslado(67, 2, 0, 600, 11),
                                            new Traslado(75, 3, 0, 1800, 40),
                                            new Traslado(44, 9, 7, 1300, 41),
                                            new Traslado(11, 3, 9, 1300, 42),
                                            new Traslado(1000,10,8,100,232),
                                            new Traslado(1111,8,2,300,909),
                                            new Traslado(92,2,8,1700,3),
                                        };
    }

    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2) encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " +  e1 + " en el arreglo " + s2.toString());
        }
    }

    @Test
    void noHayTraslados(){
        Traslado[] pruebaVacio = {};
        BestEffort sis = new BestEffort(6, pruebaVacio);
        
        sis.despacharMasRedituables(8);
        sis.despacharMasAntiguos(2);

        assertSetEquals(new ArrayList<>(Arrays.asList(0,1,2,3,4,5)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0,1,2,3,4,5)), sis.ciudadesConMayorPerdida());
        assertEquals(0, sis.ciudadConMayorSuperavit());
    }
    
    @Test
    void unPingPongEntreDosCiudades(){

        Traslado[] trasladosEntre0y1 = new Traslado[] {
            new Traslado(8, 0, 1, 900, 10),
            new Traslado(6, 1, 0, 900, 20),
            new Traslado(12, 0, 1, 1200, 50),
            new Traslado(67, 1, 0, 1800, 11),
            new Traslado(75, 0, 1, 600, 40),
        };

        BestEffort sis = new BestEffort(2, trasladosEntre0y1);
        
        sis.despacharMasAntiguos(2);
        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(0, 1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 1)), sis.ciudadesConMayorPerdida());
        assertEquals(0, sis.ciudadConMayorSuperavit());
    }
    
    @Test
    void registrarTrasladosTest(){
        Traslado[] pruebaVacio = {};
        Traslado[] muyRedituable = new Traslado[]{
            new Traslado(112, 8, 5, 10000, 43),
        };
        Traslado[] muyAntiguo = new Traslado[]{
            new Traslado(113, 5, 8, 1000, 1),
        };
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTrasladosLarga);

        sis.registrarTraslados(pruebaVacio);
        sis.registrarTraslados(muyRedituable);
        sis.registrarTraslados(muyAntiguo);

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(8)), sis.ciudadesConMayorPerdida());
        assertEquals(5, sis.ciudadConMayorSuperavit());
        
        sis.despacharMasRedituables(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(8)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida());
        assertEquals(8, sis.ciudadConMayorSuperavit());

        assertEquals(5500, sis.gananciaPromedioPorTraslado());
        
    }
    
    @Test
    void despacharMasAntiguosYRedituablesTest(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTrasladosLarga);

        sis.despacharMasAntiguos(0);
        sis.despacharMasRedituables(20052004);

        assertSetEquals(new ArrayList<>(Arrays.asList(2,3)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(7)), sis.ciudadesConMayorPerdida());

        assertEquals(3, sis.ciudadConMayorSuperavit());
        assertEquals(1000, sis.gananciaPromedioPorTraslado());
    }
    
    @Test
    void unaSolaCiudad(){
        Traslado[] pruebaVacio = {};
        BestEffort sis = new BestEffort(1, pruebaVacio);

        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorPerdida());
        assertEquals(0, sis.ciudadConMayorSuperavit());

    }

    @Test
    void actualizacionDelSuperhabitTest(){
        BestEffort sis = new BestEffort(this.cantCiudades, listaTrasladosLarga);

        sis.despacharMasRedituables(1);
        assertEquals(3, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(3);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasRedituables(3);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(3);
        assertEquals(3, sis.ciudadConMayorSuperavit());

    }

    @Test
    void deportivoEmpate(){

        Traslado[] gananciasIguales = new Traslado[]{
            new Traslado(1, 8, 5, 1000, 2),
            new Traslado(2, 5, 2, 1000, 10),
            new Traslado(3, 2, 8, 1000, 3),
            new Traslado(4, 8, 5, 1000, 4),
            new Traslado(5, 5, 2, 1000, 1),
            new Traslado(6, 2, 8, 1000, 8),
        };

        BestEffort sis = new BestEffort(9, gananciasIguales);

        sis.despacharMasRedituables(1);
        assertEquals(8, sis.ciudadConMayorSuperavit());
        assertSetEquals(new ArrayList<>(Arrays.asList(8)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertEquals(8, sis.ciudadConMayorSuperavit());
        assertSetEquals(new ArrayList<>(Arrays.asList(8,5)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5,2)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertEquals(0, sis.ciudadConMayorSuperavit());
        assertSetEquals(new ArrayList<>(Arrays.asList(8,5,2)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5,2,8)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(3);
        assertEquals(0, sis.ciudadConMayorSuperavit());
        assertSetEquals(new ArrayList<>(Arrays.asList(8,5,2)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5,2,8)), sis.ciudadesConMayorPerdida());

    }

}

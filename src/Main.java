import java.time.*;
import java.util.*;

public class Main {
    boolean debug = false;  // Activar esto para ver cuando se generan puntos.

    void main() {
        pruebaAlgoritmos();
    }

    public Punto[] Alg1(Punto[] S) {
        ArrayList<Punto> pareto = new ArrayList<>();

        for (Punto p : S) {
            boolean dominado = false;

            for (Punto q : S) {
                if (p.equals(q)) {
                    continue;
                }

                if (comprobarDominio(q, p)) {
                    dominado = true;
                    break;
                }
            }

            if (!dominado) {
                pareto.add(p);
            }
        }
        return (pareto.toArray(new Punto[0]));
    }

    private Punto[] Alg2Arreglo(Punto[] S) {
        ArrayList<Punto> pareto = new ArrayList<>();

        for (Punto p : S) {
            boolean dominado = false;
            int i = 0;

            while (i < pareto.size() && !dominado) {
                Punto q = pareto.get(i);

                if (comprobarDominio(q, p)) {
                    dominado = true;
                } else if (comprobarDominio(p, q)) {
                    pareto.remove(i);
                } else {
                    i++;
                }
            }

            if (!dominado) {
                pareto.add(p);
            }
        }
        return (pareto.toArray(new Punto[0]));
    }

    private Punto[] Alg2MiEstructura(Punto[] S) {
        LinkedList<Punto> pareto = new LinkedList<>();

        for (Punto p : S) {
            boolean dominado = false;
            ListIterator<Punto> it = pareto.listIterator();

            while (it.hasNext() && !dominado) {
                Punto q = it.next();

                if (comprobarDominio(q, p)) {
                    dominado = true;
                } else if (comprobarDominio(p, q)) {
                    it.remove();
                }
            }

            if (!dominado) {
                pareto.addLast(p);
            }
        }
        return (pareto.toArray(new Punto[0]));
    }

    private boolean comprobarDominio(Punto p, Punto q) {
        if (p.getX() < q.getX()) {
            return p.getY() <= q.getY();
        }

        if (p.getX() == q.getX()) {
            return p.getY() < q.getY();
        }
        return false;
    }

    // Métodos extra para comprobar cosas.
    void printArreglo(Punto[] P) {
        if (P.length == 0) {
            System.out.print("[]");
            return;
        }

        System.out.print("[");

        for (int i = 0; i < (P.length - 1); i++) {
            System.out.print("{" + P[i].getX() + "," + P[i].getY() + "}" + "; ");
        }

        System.out.print("{" + P[P.length - 1].getX() + "," + P[P.length - 1].getY() + "}]");
    }

    private void pruebaAlgoritmos() {
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Prueba de Algoritmos Pareto ---\n");

        System.out.println("1. Prueba con valores fijos.");
        System.out.println("2. Prueba con valores aleatorios.");

        System.out.print("\n> ");

        int eleccion = sc.nextInt();

        ArrayList<Punto> puntos = new ArrayList<>();

        if (eleccion == 1) {
            System.out.println(" == Escribe el par de numeros de la forma \"x,y\"");
            System.out.println(" == Escribe \"0\" para dejar de leer pares.");

            String[] valor;

            do {
                System.out.print("> ");

                try {
                    valor = sc.next().split(",");

                    if (valor.length == 2) {
                        puntos.add(new Punto(Integer.parseInt(valor[0]), Integer.parseInt(valor[1])));
                    } else {
                        if (valor[0].equals("0")) {
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Error: Un valor ingresado no es válido.");
                }

            } while (true);

        } else {
            System.out.println("Escribe el valor máximo que pueden tomar x e y.");
            System.out.println("Ambos valores son iguales.");

            System.out.print(" > ");
            int dim = sc.nextInt();

            System.out.println("\n == Escribe la cantidad de puntos que seran generados.");
            int cant = sc.nextInt();

            for (int i = 0; i < cant; i++) {
                Punto p = new Punto((int) (Math.random() * dim), (int) (Math.random() * dim));

                if (!puntos.contains(p)) {
                    puntos.add(p);

                    if (debug) {
                        System.out.println(" >> " + i);
                    }
                } else {
                    System.out.println("Se ha generado un punto repetido.");
                    i--;
                }
            }
        }

        Punto[] arregloPuntos = puntos.toArray(new Punto[0]);
        Punto[] pareto;

        System.out.println("\n\n ======================================= ");
        System.out.println(" == Comenzando prueba de algoritmos: ");
        System.out.println(" ======================================= ");

        long inicio1 = System.nanoTime();
        pareto = Alg1(arregloPuntos);
        long final1 = System.nanoTime();

        System.out.print(" == Pareto Alg1: ");
        printArreglo(pareto);

        long inicio2 = System.nanoTime();
        pareto = Alg2Arreglo(arregloPuntos);
        long final2 = System.nanoTime();

        System.out.println();
        System.out.print(" == Pareto Algoritmo2: ");
        printArreglo(pareto);

        long inicio3 = System.nanoTime();
        pareto = Alg2MiEstructura(arregloPuntos);
        long final3 = System.nanoTime();

        System.out.println();
        System.out.print(" == Pareto Algoritmo2MiEstructura: ");
        printArreglo(pareto);

        System.out.println();
        System.out.println();

        long duracion1 = final1 - inicio1;
        long duracion2 = final2 - inicio2;
        long duracion3 = final3 - inicio3;

        double ms1 = duracion1 / 1_000_000.0;
        double ms2 = duracion2 / 1_000_000.0;
        double ms3 = duracion3 / 1_000_000.0;

        System.out.println("== Resultados: Para un arreglo de " + arregloPuntos.length + " elementos ==");

        System.out.printf(" >> El Alg1 tardó:                %.4f ms%n", ms1);
        System.out.printf(" >> El Alg2Arreglo tardó:         %.4f ms%n", ms2);
        System.out.printf(" >> El Alg2MiEstructura tardó:    %.4f ms%n", ms3);
    }
}
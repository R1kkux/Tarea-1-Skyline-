import java.time.*;
import java.util.*;

public class Main {
    boolean debug = false;

    void main() {
        pruebaAlgoritmos();
    }

    public void Alg1(Punto[] S) {
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

        System.out.println("Pareto (Alg1): ");
        printArreglo(pareto.toArray(new Punto[0]));
    }

    private void Alg2Arreglo(Punto[] S) {
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

        System.out.println("Pareto (Alg2Arreglo): ");
        printArreglo(pareto.toArray(new Punto[0]));
    }

    private void Alg2MiEstructura(Punto[] S) {
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

        System.out.println("Pareto (Alg2MiEstructura): ");
        printArreglo(pareto.toArray(new Punto[0]));
    }

    private boolean comprobarDominio(Punto p, Punto q) {
        if (debug) {
            System.out.println("Comparando {" + p.getX() + "," + p.getY() + "} con {" + q.getX() + "," + q.getY() + "}");
        }

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
        System.out.println("\t -- Imprimiendo arreglo...\n");

        if (P.length == 0) {
            System.out.println("[]");
            return;
        }

        System.out.print("[");

        for (int i = 0; i < (P.length - 1); i++) {
            System.out.print("{" + P[i].getX() + "," + P[i].getY() + "}" + "; ");
        }

        System.out.println("{" + P[P.length - 1].getX() + "," + P[P.length - 1].getY() + "}]");
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

            System.out.print("\n> ");
            int dim = sc.nextInt();

            System.out.println(" == Escribe la cantidad de puntos que seran generados.");
            int cant = sc.nextInt();

            for (int i = 0; i < cant; i++) {
                Punto p = new Punto((int) (Math.random() * dim), (int) (Math.random() * dim));

                if (!puntos.contains(p)) {
                    puntos.add(p);
                    // System.out.println(" >> Nuevo punto: {" + p.getX() + "," + p.getY() + "}");
                } else {
                    System.out.println("Se ha generado un punto repetido.");
                    i--;
                }
            }
        }

        System.out.print("\n == Presiona 1 para imprimir todos los puntos generados: ");
        eleccion = sc.nextInt();

        if (eleccion == 1) {
            printArreglo(puntos.toArray(new Punto[0]));
        }

        Punto[] arregloPuntos = puntos.toArray(new Punto[0]);

        System.out.println("\n\n ======================================= ");
        System.out.println(" == Comenzando prueba de Algoritmo 1: ");
        System.out.println(" ======================================= ");

        LocalTime inicio1 =  LocalTime.now();
        Alg1(arregloPuntos);
        LocalTime final1 = LocalTime.now();

        System.out.println();

        System.out.println("\n\t = El algoritmo 1 tardó " +Math.abs(final1.getHour() - inicio1.getHour()) +":" +Math.abs(final1.getMinute() - inicio1.getMinute()) + ":" +Math.abs(final1.getSecond() - inicio1.getSecond()) +"." +Math.abs(final1.getNano() - inicio1.getNano()) +" ==");

        System.out.print("\nEscribe \"0\" para continuar: > ");
        int st = sc.nextInt();

        System.out.println();

        System.out.println("\n\n ======================================= ");
        System.out.println(" == Comenzando prueba de Alg2Arreglo: ");
        System.out.println(" ======================================= ");
        LocalTime inicio2 =  LocalTime.now();
        Alg2Arreglo(arregloPuntos);
        LocalTime final2 =  LocalTime.now();

        System.out.println();

        System.out.println("\n\t = El algoritmo 2 tardó " +Math.abs(final2.getHour() - inicio2.getHour()) +":" +Math.abs(final2.getMinute() - inicio2.getMinute()) + ":" +Math.abs(final2.getSecond() - inicio2.getSecond()) +"." +Math.abs(final2.getNano() - inicio2.getNano()) +" ==");

        System.out.print("\nEscribe \"0\" para continuar: > ");
        st = sc.nextInt();

        System.out.println();

        System.out.println("\n\n ======================================= ");
        System.out.println(" == Comenzando prueba de Alg2MiEstructura: ");
        System.out.println(" ======================================= ");
        LocalTime inicio3 =  LocalTime.now();
        Alg2MiEstructura(arregloPuntos);
        LocalTime final3 =  LocalTime.now();

        System.out.println();

        System.out.println("\n >> Para un arreglo de puntos de " +arregloPuntos.length +" elementos (formato: HH:mm:ss.ms):");
        System.out.println("\t = Alg1 tardó " +Math.abs(final1.getHour() - inicio1.getHour()) +":" +Math.abs(final1.getMinute() - inicio1.getMinute()) + ":" +Math.abs(final1.getSecond() - inicio1.getSecond()) +"." +Math.abs(final1.getNano() - inicio1.getNano()) +" ==");
        System.out.println("\t = algoritmo2Arreg tardó " +Math.abs(final2.getHour() - inicio2.getHour()) +":" +Math.abs(final2.getMinute() - inicio2.getMinute()) + ":" +Math.abs(final2.getSecond() - inicio2.getSecond()) +"." +Math.abs(final2.getNano() - inicio2.getNano()) +" ==");
        System.out.println("\t = algoritmo2MiEstructura tardó " +Math.abs(final3.getHour() - inicio3.getHour()) +":" +Math.abs(final3.getMinute() - inicio3.getMinute()) + ":" +Math.abs(final3.getSecond() - inicio3.getSecond()) +"." +Math.abs(final3.getNano() - inicio3.getNano()) +" ==");

    }
}
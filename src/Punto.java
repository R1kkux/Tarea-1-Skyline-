public class Punto {
    int x, y;

    Punto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Punto p) {
        return  this.x == p.getX() && this.y == p.getY();
    }
}

package bean;

public class Point {
    public long x;
    public long y;

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        Point p = (Point) obj;
        return this.x == p.x && this.y == p.y;
    }

    @Override
    public int hashCode() {
        return (int) (this.x * this.x + this.y + this.y);
    }
}

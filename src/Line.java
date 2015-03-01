interface ILine{
        public String getResult();
    }
public class Line implements ILine {
    int x1, y1;
    int x2, y2;

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public Line(String st) {
        st.trim();
        this.x1 = Integer.valueOf(st.substring(0, st.indexOf(',')));
        st = st.substring(st.indexOf(',') + 1);
        this.y1 = Integer.valueOf(st.substring(0, st.indexOf(',')));
        st = st.substring(st.indexOf(',') + 1);
        this.x2 = Integer.valueOf(st.substring(0, st.indexOf(',')));
        st = st.substring(st.indexOf(',') + 1);
        this.y2 = Integer.valueOf(st);
    }

    private float coofK() {
        return (y2 - y1) / (x2 - x1);
    }

    private float coofB() {
        return (x1 * (y2 - y1) + y1 * (x2 - x1)) / (x2 - x1);
    }

    public String getResult() {
        String s = "y = ";
        if (x1 == x2 && y1 != y2)
            s = "x = " + String.valueOf(x1);
        else if (y1 == y2 && x1 != x2)
            s = "y = " + String.valueOf(y1);
        else if (y1 == y2 && x1 == x2)
            s = "Это точка с координатами (" + String.valueOf(x1) + "," + String.valueOf(y1) + ")";
        else if (coofB() > 0)
            s += String.valueOf(coofK()) + "x" + "-" + String.valueOf(coofB());
        else
            s += String.valueOf(coofK()) + "x" + "+" + String.valueOf(coofB());
        return s;
    }
}

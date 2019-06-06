public class Obj {
    boolean near, interacted;
    int posl,posr,arrowY;
    String dialogue;

    public Obj(int posl, int posr, int arrowY, String dialogue) {
        this.posl = posl;
        this.posr = posr;
        this.arrowY = arrowY;
        this.dialogue = dialogue;
    }
}

public class Obj {
    boolean near, interacted;
    int posl,posr,arrowY;
    String dialogue, objName;

    public Obj(String objName, int posl, int posr, int arrowY, String dialogue) {
        this.objName = objName;
        this.posl = posl;
        this.posr = posr;
        this.arrowY = arrowY;
        this.dialogue = dialogue;
    }
}

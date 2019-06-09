public class Obj {
    boolean near, interacted;
    int posl,posr,arrowY;
    String dialogue, dialogue2, objName;

    public Obj(String objName, int posl, int posr, int arrowY, String dialogue) {
        this.objName = objName;
        this.posl = posl;
        this.posr = posr;
        this.arrowY = arrowY;
        this.dialogue = dialogue;
    }

    public Obj(String objName, int posl, int posr, int arrowY, String dialogue, String dialogue2) {
        this.objName = objName;
        this.posl = posl;
        this.posr = posr;
        this.arrowY = arrowY;
        this.dialogue = dialogue;
        this.dialogue2 = dialogue2;
    }
}

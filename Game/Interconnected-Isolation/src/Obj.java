/**
 * Represents an interactive object.
 *
 * @author Christopher Trevisan
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.9 - Chris Trevisan
 * <br>Date:2019/06/06
 * <br>Time Spent: 30 minutes
 * <br>Added first constructor.
 * </p>
 * <p>
 * 1.9 - Julia Xie
 * <br>Date: 2019/06/06
 * <br>Time Spent: 10 minutes
 * <br>Modified first constructor.
 * </p>
 * <p>
 * 1.11 - Julia Xie
 * <br>Date: 2019/06/08
 * <br>Time Spent: 30 minutes
 * <br>Added second constructor.
 * </p>
 * @since 1.9
 */
public class Obj {
    /**
     * Whether the player is near the object or has interacted
     */
    private boolean near, interacted;
    /**
     * The x coordinates of the player and the y coordinates of the arrows
     */
    private int posl, posr, arrowY;
    /**
     * The dialogue and name of the object
     */
    private String dialogue, dialogue2, objName;

    /**
     * Constructor for Obj class.
     * Sets all of the variables using the given parameters.
     * <p>
     * 1.9 - Julia Xie
     * <br>Added objName as a parameter.
     * </p>
     *
     * @param objName  the name of the object
     * @param posl     the left x coordinate of the object
     * @param posr     the right x coordinate of the object
     * @param arrowY   the y coordinate of the arrow
     * @param dialogue the dialogue that is said when the object is interacted with
     * @since 1.9
     */
    public Obj(String objName, int posl, int posr, int arrowY, String dialogue) {
        this.objName = objName;
        this.posl = posl;
        this.posr = posr;
        this.arrowY = arrowY;
        this.dialogue = dialogue;
    }

    /**
     * Constructor for Obj class.
     * Sets all of the variables using the given parameters.
     *
     * @param objName   the name of the object
     * @param posl      the left x coordinate of the object
     * @param posr      the right x coordinate of the object
     * @param arrowY    the y coordinate of the arrow
     * @param dialogue  the dialogue that is said when the object is interacted with
     * @param dialogue2 the dialogue that is said when the object is interacted with for the second time
     * @since 1.9
     */
    public Obj(String objName, int posl, int posr, int arrowY, String dialogue, String dialogue2) {
        this.objName = objName;
        this.posl = posl;
        this.posr = posr;
        this.arrowY = arrowY;
        this.dialogue = dialogue;
        this.dialogue2 = dialogue2;
    }

    /**
     * @return whether or not the player is near the object
     * @since 1.11
     */
    public boolean isNear() {
        return near;
    }

    /**
     * @return whether or not the player has interacted with the object
     * @since 1.11
     */
    public boolean isInteracted() {
        return interacted;
    }

    /**
     * @return the y coordinate of the arrow
     * @since 1.11
     */
    public int getArrowY() {
        return arrowY;
    }

    /**
     * @return the x coordinate of the left side of the object
     * @since 1.11
     */
    public int getPosl() {
        return posl;
    }

    /**
     * @return the x coordinate of the right side of the object
     * @since 1.11
     */
    public int getPosr() {
        return posr;
    }

    /**
     * @return the dialogue which appears when interacted with
     * @since 1.11
     */
    public String getDialogue() {
        return dialogue;
    }

    /**
     * @return the dialogue which appears when interacted with the second time
     * @since 1.11
     */
    public String getDialogue2() {
        return dialogue2;
    }

    /**
     * @return the name of the object
     * @since 1.11
     */
    public String getObjName() {
        return objName;
    }

    /**
     * @param interacted if the player has interacted with object
     * @since 1.11
     */
    public void setInteracted(boolean interacted) {
        this.interacted = interacted;
    }

    /**
     * @param near if the player is near the object
     * @since 1.11
     */
    public void setNear(boolean near) {
        this.near = near;
    }
}

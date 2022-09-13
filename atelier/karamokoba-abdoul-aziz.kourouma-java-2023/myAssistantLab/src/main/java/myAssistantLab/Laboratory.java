package myAssistantLab;

import java.util.ArrayList;

public class Laboratory {
    private ArrayList<Assistant> assistants;

    public Laboratory() {
        this.assistants = new ArrayList<>();
    }

    /**
     * Adds all the Assistants inside `assistants` into `this.assistants`.
     * An Assistant must not be present more than once.
     * The `?` symbol is called a wildcard. The method will accept any List
     * containing elements of type Assistant or one of its subtypes.
     * @return whether `this.assistants` has been updated.
     */
    public boolean addAssistants(ArrayList<? extends Assistant> assistants) {
        int res = 0;
        for (var assistant : assistants)
        {
            int i = 0;
            for (;i < this.assistants.size();i++) {
                var x = this.assistants.get(i);
                if (x.equals(assistant)) {
                    break;
                }
            }
            if (i == this.assistants.size()) {
                this.assistants.add(assistant);
                res++;
            }
        }
        return res != 0;
    }

    /**
     * Adds an `assistant` into `this.assistants`, if it is not already inside.
     * @return whether `this.assistants` has been updated.
     */
    public boolean addAssistant(Assistant assistant) {
        for (var x : this.assistants) {
            if (x.login.equals(assistant.login)){
                return false;
            }
        }
        this.assistants.add(assistant);
        return true;
    }

    /**
     * Removes an `assistant` from `this.assistants`.
     * @return whether `this.assistants` has been updated.
     */
    public boolean removeAssistant(final String name) {
        for (var x : this.assistants) {
            if (x.login.equals(name)){
                this.assistants.remove(x);
                return true;
            }
        }
        return false;
    }

    /**
     * Prints the information of all Assistants in `this.assistants`, in order.
     * You must *not* add an extra new line.
     */
    public void print() { for (var x : this.assistants) { x.print(); } }
}

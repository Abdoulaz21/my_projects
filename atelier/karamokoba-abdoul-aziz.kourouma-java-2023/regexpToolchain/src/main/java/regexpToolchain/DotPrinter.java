package regexpToolchain;

public class DotPrinter {

    public String print(Automaton automaton) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("digraph G {\nrankdir=\"LR\";\n\nnode [shape = diamond];\n");

        State init = automaton.getInitialState();
        stringBuilder.append(init.getLabel() + "\n\nnode [shape = doublecircle];\n");

        State fin = automaton.newFinalState();
        stringBuilder.append(fin.getLabel() + "\n\nnode [shape = circle];\n");

        var list = automaton.getTransitions();

        list.forEach(x -> stringBuilder.append(x.getSrc().getLabel() + " -> " + x.getDst().getLabel() + " [label = \"" + x.label() + "\"];\n"));

        stringBuilder.append("}");

        return stringBuilder.toString();
    }
}

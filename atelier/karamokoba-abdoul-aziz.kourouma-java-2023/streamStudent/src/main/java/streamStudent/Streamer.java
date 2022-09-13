package streamStudent;

import java.util.Optional;
import java.util.stream.Stream;

public class Streamer {

    private boolean isValid(streamStudent.Pair<Integer, String> stud) {
        if (stud.getKey() < 0 || stud.getKey() > 100)
            return false;

        long count = stud.getValue().chars().filter(ch -> ch == '.').count();
        long count2 = stud.getValue().chars().filter(ch -> ch == '_').count();
        if ((count2 == 1 && count > 0) || (count2 == 0 && count == 0) || (count2 > 1 || count > 1)) {
            return false;
        }
        return true;
    }

    public Stream<streamStudent.Pair<Integer, String>>
    validator(Stream<streamStudent.Pair<Integer, String>> stream) {
        return stream.filter(this::isValid);
    }

    public Stream<streamStudent.Pair<Integer, String>>
    orderGrade(Stream<streamStudent.Pair<Integer, String>> stream) {
        return stream.sorted((o1, o2) -> {
            if (o1.getKey().equals(o2.getKey())) {
                return o1.getValue().compareTo(o2.getValue());
            }
            return o1.getKey().compareTo(o2.getKey());
        });
    }

    public Stream<streamStudent.Pair<Integer, String>>
    lowercase(Stream<streamStudent.Pair<Integer, String>> stream) {
        return stream.map(x -> {
            if (x.getValue().toLowerCase().equals(x.getValue()))
                return x;
            else
                return new Pair<>(x.getKey() / 2, x.getValue().toLowerCase());
        });
    }

    public Optional<Pair<Integer, String>>
    headOfTheClass(Stream<streamStudent.Pair<Integer, String>> stream) {
        return stream.max((o1, o2) -> {
            if (o1.getKey().equals(o2.getKey())) {
                return o2.getValue().compareTo(o1.getValue());
            }
            return o1.getKey().compareTo(o2.getKey());
        });
    }

    public Stream<streamStudent.Pair<Integer, String>>
    quickFix(Stream<streamStudent.Pair<Integer, String>> stream) {
        return stream.map(x -> {
            if (x.getValue().toLowerCase().startsWith("ma") ||
                    (x.getValue().toLowerCase().charAt(0) == 'l' && x.getValue().toLowerCase().charAt(x.getValue().length() - 1) == 'x')) {
                if (x.getKey() * 2 > 100)
                    return new Pair<>(100, x.getValue());
                else
                    return new Pair<>(x.getKey() * 2, x.getValue());
            }
            return x;
        });
    }

    public Stream<streamStudent.Pair<Integer, String>>
    encryption(Stream<streamStudent.Pair<Integer, String>> stream) {
        return stream.map(x -> new Pair<>(x.getKey(), x.getValue().substring(x.getValue().length() / 2) + x.getValue().substring(0, x.getValue().length() / 2)));
    }
}

package vn.com.multiplechoice.dao.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import vn.com.multiplechoice.dao.model.paging.enums.Direction;

public final class EmployeeComparators {

    static class Key {
        String name;
        Direction dir;

        public Key() {
        }

        public Key(String name, Direction dir) {
            super();
            this.name = name;
            this.dir = dir;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Direction getDir() {
            return dir;
        }

        public void setDir(Direction dir) {
            this.dir = dir;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((dir == null) ? 0 : dir.hashCode());
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Key other = (Key) obj;
            if (dir != other.dir)
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            return true;
        }

    }

    public static Map<Key, Comparator<Employee>> getMap() {
        return map;
    }

    public static void setMap(Map<Key, Comparator<Employee>> map) {
        EmployeeComparators.map = map;
    }

    static Map<Key, Comparator<Employee>> map = new HashMap<>();

    static {
        map.put(new Key("name", Direction.asc), Comparator.comparing(Employee::getName));
        map.put(new Key("name", Direction.desc), Comparator.comparing(Employee::getName).reversed());

        map.put(new Key("start_date", Direction.asc), Comparator.comparing(Employee::getStartDate));
        map.put(new Key("start_date", Direction.desc), Comparator.comparing(Employee::getStartDate).reversed());

        map.put(new Key("position", Direction.asc), Comparator.comparing(Employee::getPosition));
        map.put(new Key("position", Direction.desc), Comparator.comparing(Employee::getPosition).reversed());

        map.put(new Key("salary", Direction.asc), Comparator.comparing(Employee::getSalary));
        map.put(new Key("salary", Direction.desc), Comparator.comparing(Employee::getSalary).reversed());

        map.put(new Key("office", Direction.asc), Comparator.comparing(Employee::getOffice));
        map.put(new Key("office", Direction.desc), Comparator.comparing(Employee::getOffice).reversed());

        map.put(new Key("extn", Direction.asc), Comparator.comparing(Employee::getExtn));
        map.put(new Key("extn", Direction.desc), Comparator.comparing(Employee::getExtn).reversed());
    }

    public static Comparator<Employee> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private EmployeeComparators() {
    }
}

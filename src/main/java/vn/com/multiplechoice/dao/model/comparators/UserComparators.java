package vn.com.multiplechoice.dao.model.comparators;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.paging.enums.Direction;

public final class UserComparators {

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

    public static Map<Key, Comparator<User>> getMap() {
        return map;
    }

    public static void setMap(Map<Key, Comparator<User>> map) {
        UserComparators.map = map;
    }

    static Map<Key, Comparator<User>> map = new HashMap<>();

    static {
        map.put(new Key("id", Direction.asc), Comparator.comparing(User::getId));
        map.put(new Key("id", Direction.desc), Comparator.comparing(User::getId).reversed());
        
        map.put(new Key("firstname", Direction.asc), Comparator.comparing(User::getFirstname));
        map.put(new Key("firstname", Direction.desc), Comparator.comparing(User::getFirstname).reversed());
        
        map.put(new Key("lastname", Direction.asc), Comparator.comparing(User::getLastname));
        map.put(new Key("lastname", Direction.desc), Comparator.comparing(User::getLastname).reversed());
        
        map.put(new Key("username", Direction.asc), Comparator.comparing(User::getUsername));
        map.put(new Key("username", Direction.desc), Comparator.comparing(User::getUsername).reversed());

        map.put(new Key("birthday", Direction.asc), Comparator.comparing(User::getBirthday));
        map.put(new Key("birthday", Direction.desc), Comparator.comparing(User::getBirthday).reversed());

        map.put(new Key("email", Direction.asc), Comparator.comparing(User::getEmail));
        map.put(new Key("email", Direction.desc), Comparator.comparing(User::getEmail).reversed());

        map.put(new Key("role", Direction.asc), Comparator.comparing(User::getRole));
        map.put(new Key("role", Direction.desc), Comparator.comparing(User::getRole).reversed());

        map.put(new Key("status", Direction.asc), Comparator.comparing(User::getStatus));
        map.put(new Key("status", Direction.desc), Comparator.comparing(User::getStatus).reversed());

        map.put(new Key("phone_number", Direction.asc), Comparator.comparing(User::getPhoneNumber));
        map.put(new Key("phone_number", Direction.desc), Comparator.comparing(User::getPhoneNumber).reversed());
    }

    public static Comparator<User> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private UserComparators() {
    }
}

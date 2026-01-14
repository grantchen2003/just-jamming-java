package io.github.grantchen2003.store;

import java.util.HashMap;
import java.util.Optional;

public class InMemoryStore implements Store {
    final HashMap<String, String> data = new HashMap<>();

    @Override
    public Optional<String> getValue(String key) {
        return Optional.ofNullable(this.data.get(key));
    }

    @Override
    public void put(String key, String value) {
        this.data.put(key, value);
    }

    @Override
    public Optional<String> removeKey(String key) {
        return Optional.ofNullable(this.data.remove(key));
    }
}

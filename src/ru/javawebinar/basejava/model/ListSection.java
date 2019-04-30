package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {

    private final List<String> items;

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }


    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "OOO items");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "items=" + items +
                '}';
    }
}

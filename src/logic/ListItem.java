package logic;
import java.util.*;
public class ListItem implements Comparable<ListItem>{
    private List<Integer> list;
    private Integer value;
    public ListItem(List<Integer> list, Integer value){
        this.list = list;
        this.value = value;
    }

    public List<Integer> getList() {
        return list;
    }

    public Integer getValue() {
        return value;
    }
    @Override
    public int compareTo(ListItem other){
        return other.value.compareTo(this.value);
    }
}

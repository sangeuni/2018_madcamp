package org.weibeld.example.tabs;

public class NameListItem {
    private String name;
    private int weight;

    public String getName(){
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public NameListItem(String name, int weight){
        this.name = name;
        this.weight = weight;
    }
}

////////////////////////////////////////////////////////////////////
// [TOMMASO] [POPPI] [1201270]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

public class MenuItem {
    private itemType item;
    private String name;
    private double price;

    public MenuItem(itemType tipo, String nome, double prezzo) {
        this.item = tipo;
        this.name = nome;
        this.price = prezzo;
    }

    public String getName() {
        return name;
    }

    public itemType getType() {
        return item;
    }

    public double getPrice() {
        return price;
    }
}
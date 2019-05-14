package com.jerry.pubcount;

import java.util.ArrayList;
import java.util.UUID;

import phoneapp.AllContacts;
import phoneapp.Contact;

import android.content.Context;

public class AllItems {

    private static AllItems allItems;

    private Context applicationContext;

    private static ArrayList<Item> itemsList;

    private AllItems(Context apllicationContext) {

        this.applicationContext = apllicationContext;

        itemsList = new ArrayList<Item>();

        itemsList.add(new Item("Podlužan 11°", 28.3f));
        itemsList.add(new Item("Starobrno 11°", 35f));
        itemsList.add(new Item("Jahodový džus", 350f));
        itemsList.add(new Item("plzeň", 35));
        itemsList.add(new Item("voda", 50));
        itemsList.add(new Item("Kuřecí křidélka", 129));

    }

    public static AllItems get(Context context) {

        if (allItems == null) {
            // getApplicationContext returns the global Application object
            // This Context is global to every part of the application
            allItems = new AllItems(context.getApplicationContext());
        }


        return allItems;

    }

    public static void saveItemsList() {

    }

    public ArrayList<Item> getItemsList() {
        return itemsList;

    }

    public Item getItem(UUID id) {

        for (Item theItem : itemsList) {
            if (theItem.getIdNumber().equals(id)) {
                return theItem;
            }
        }
        return null;
    }

    public static void addItem(Item item) {
        itemsList.add(item);
    }

    public static void removeItem(Item item) {
        itemsList.remove(item);
    }
}

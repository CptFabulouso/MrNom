package com.jerry.pubcount;

import java.util.ArrayList;
import java.util.UUID;

import android.util.Log;

public class Receipt {

    private ArrayList<Item> receiptItems;
    StringBuilder itemsBuilder = new StringBuilder();

    public Receipt() {
        receiptItems = new ArrayList<Item>();
    }

    public Receipt(String items) {
        receiptItems = new ArrayList<Item>();
        String name = null;
        String price = null;
        String count = null;
        int phase = 0;
        int start = 0;
        int end = 0;


        try {
            for (char c : items.toCharArray()) {
                //Log.d("PUBCOUNT","char: " + c + ", compare: " + (c == '|'));
                if (phase == 0) {
                    if (c == '|') {
                        name = items.substring(start, end);
                        Log.d("PUBCOUNT", "start: " + start + ", end: " + end + ", name: " + name);
                        end++;
                        start = end;
                        phase++;
                        Log.d("PUBCOUNT", "new start: " + start + ", new end: " + end);
                        continue;
                    }
                }
                if (phase == 1) {
                    if (c == '|') {
                        price = items.substring(start, end);
                        Log.d("PUBCOUNT", "start: " + start + ", end: " + end + ", price: " + price);
                        end++;
                        start = end;
                        phase++;
                        Log.d("PUBCOUNT", "new start: " + start + ", new count: " + end);
                        continue;
                    }
                }
                if (phase == 2) {
                    if (c == '|') {
                        count = items.substring(start, end);
                        Log.d("PUBCOUNT", "start: " + start + ", end: " + end + ", count: " + count);
                        end++;
                        start = end;
                        phase = 0;
                        Log.d("PUBCOUNT", "new start: " + start + ", new end: " + end);
                        try {
                            Item newItem = new Item(name, Float.parseFloat(price));
                            newItem.setCount(Integer.parseInt(count));
                            addNewItem(newItem);
                            continue;
                        } catch (NumberFormatException e) {

                            Log.d("PUBCOUNT", "numFormatExc" + price);
                        }

                    }
                }
                end++;
            }
        } catch (StringIndexOutOfBoundsException e) {

            Log.d("PUBCOUNT", items + ", start " + start + ", end " + end + ", name: " + name + ", price: " + price + ", count" + count);
        }
    }

    public String save() {
        itemsBuilder.setLength(0);
        for (int i = 0; i < receiptItems.size(); i++) {
            itemsBuilder.append(receiptItems.get(i).getName());
            itemsBuilder.append("|");
            itemsBuilder.append(receiptItems.get(i).getPrice());
            itemsBuilder.append("|");
            itemsBuilder.append(receiptItems.get(i).getCount());
            itemsBuilder.append("|");
        }
        return itemsBuilder.toString();
    }

    public void addNewItem(Item item) {
        receiptItems.add(item);
    }

    public void addItem(UUID idNumber) {
        for (int i = 0; i < receiptItems.size(); i++) {
            if (receiptItems.get(i).getIdNumber() == idNumber) {
                receiptItems.get(i).addCount();
            }
        }
    }

    public void addItem(Item item) {
        for (int i = 0; i < receiptItems.size(); i++) {
            if (receiptItems.get(i).equals(item)) {
                receiptItems.get(i).addCount();
            }
        }
    }

    public void removeItem(UUID idNumber) {
        for (int i = 0; i < receiptItems.size(); i++) {
            if (receiptItems.get(i).getIdNumber() == idNumber) {
                receiptItems.get(i).removeCount();
                if (receiptItems.get(i).getCount() <= 0) {
                    receiptItems.remove(i);
                }
            }
        }
    }

    public float getTotal() {
        float total = 0;
        for (int i = 0; i < receiptItems.size(); i++) {
            total += (receiptItems.get(i).getCount() * receiptItems.get(i).getPrice());

        }
        return total;
    }

    public void removeItem(Item item) {
        for (int i = 0; i < receiptItems.size(); i++) {
            if (receiptItems.get(i).equals(item)) {
                receiptItems.get(i).removeCount();
                if (receiptItems.get(i).getCount() <= 0) {
                    receiptItems.remove(i);
                }
            }
        }
    }

    public ArrayList<Item> getReceiptItemsList() {

        return receiptItems;

    }


}

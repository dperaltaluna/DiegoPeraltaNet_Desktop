package net.diegoperalta.diegoperaltanet_desktop;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class PageBlogEntries {
    public static ArrayList<ArrayList<BlogEntry>> pages = new ArrayList<>();
    public static ArrayList<BlogEntry> entries;
    public static int numLastPage = 0;
    public static boolean isLastPage = false;
    public static int indexEntrySelected;
    public static ArrayList<String> linkEntries;

    public PageBlogEntries () {
        entries = new ArrayList<BlogEntry>();
    }

    public void fillPageBlogEntries (ArrayList<String> titles, ArrayList<String> paragraphs) {
        for(int i = 0; i < titles.size(); i++) {
            BlogEntry entry = new BlogEntry(titles.get(i), paragraphs.get(i));
            addEntry(entry);
        }
    }

    public ArrayList<BlogEntry> getEntries() {
        return entries;
    }

    public void addEntry(BlogEntry entry) {
        entries.add(entry);
    }

    public ArrayList<ArrayList<BlogEntry>> getPages() {
        return pages;
    }

    public ArrayList<BlogEntry> getPage(int index) {
        return pages.get(index);
    }

    public void addEntriesToPage(int index, ArrayList<BlogEntry> entries) {
        pages.add(index, entries);
    }

    public void printPage() {
        for (int i = 0; i < pages.size(); i++) {
            for (int j = 0; j < pages.get(i).size(); j++) {
                System.out.println(pages.get(i).get(j).getTitle() + " ");
            }
            System.out.println("\n");
        }
        System.out.println("Fin página");
    }

}

package net.diegoperalta.diegoperaltanet_desktop;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Diego
 */
public class BlogEntry {
    private String title;
    private String paragraph;
    private String content;

    public BlogEntry (String title, String paragraph) {
        this.title = title;
        this.paragraph = paragraph;
    }

    public String getTitle() {
        return title;
    }

    public String getParagraph() {
        return paragraph;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

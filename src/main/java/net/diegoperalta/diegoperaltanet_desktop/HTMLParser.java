package net.diegoperalta.diegoperaltanet_desktop;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public class HTMLParser {

    Document doc;
    Document docEntry;
    Elements titles;
    Elements paragraphs;
    Elements links;
    Elements entryContents;
    ArrayList<String> titlesClean = new ArrayList<>();
    ArrayList<String> paragraphsClean = new ArrayList<>();
    ArrayList<String> linksClean = new ArrayList<>();
    ArrayList<String> contentsClean = new ArrayList<>();

    public void parseDoc() {
        try {
            doc = Jsoup.connect("http://diegoperalta.net/category/blog/")
                    .method(Connection.Method.GET)
                    .header("Connection", "close")
                    .userAgent("Jsoup client")
                    //.maxBodySize(1)
                    .timeout(10000)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .referrer("<div class=\"older-post no-post-thumbnail\">")
                    .get();

        } catch (HttpStatusException e) {
            PageBlogEntries.isLastPage = true;
            BlogSceneController.numPage--;
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parsePodcastDoc() {
        try {
            doc = Jsoup.connect("http://diegoperalta.net/category/podcast/")
                    .method(Connection.Method.GET)
                    .header("Connection", "close")
                    .userAgent("Jsoup client")
                    //.maxBodySize(1)
                    .timeout(10000)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .referrer("<div class=\"older-post no-post-thumbnail\">")
                    .get();

        } catch (HttpStatusException e) {
            PageBlogEntries.isLastPage = true;
            BlogSceneController.numPage--;
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void parseDoc(int index) {
        String strNumPage = ""+index;
        try {
            doc = Jsoup
                    .connect("http://diegoperalta.net/category/blog/page/"+strNumPage)
                    .method(Connection.Method.GET)
                    .header("Connection", "close")
                    .userAgent("Jsoup client")
                    //.maxBodySize(1)
                    .timeout(10000)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .referrer("<div class=\"older-post no-post-thumbnail\">")
                    //.data("class", "older-post no-post-thumbnail")
                    .get();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (HttpStatusException e) {
            PageBlogEntries.isLastPage = true;
            //numLastPage = index--;
            PageBlogEntries.numLastPage = index--;
            BlogSceneController.numPage--;
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parsePodcastDoc(int index) {
        String strNumPage = ""+index;
        try {
            doc = Jsoup
                    .connect("http://diegoperalta.net/category/podcast/page/"+strNumPage)
                    .method(Connection.Method.GET)
                    .header("Connection", "close")
                    .userAgent("Jsoup client")
                    //.maxBodySize(1)
                    .timeout(10000)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .referrer("<div class=\"older-post no-post-thumbnail\">")
                    //.data("class", "older-post no-post-thumbnail")
                    .get();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (HttpStatusException e) {
            PageBlogEntries.isLastPage = true;
            //numLastPage = index--;
            PageBlogEntries.numLastPage = index--;
            BlogSceneController.numPage--;
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseEntry(String entryLink) {
        try {
            docEntry = Jsoup.connect(entryLink)
                    .method(Connection.Method.GET)
                    .header("Connection", "close")
                    .userAgent("Jsoup client")
                    //.maxBodySize(1)
                    .timeout(10000)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .referrer("<div class=\"single-post-content cf\">")
                    .get();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (HttpStatusException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> parseTitles() {
        //titles = doc.getElementsByClass("entry-title");
        titles = doc.body()
                .removeAttr("head")
                .removeAttr("style")
                .removeAttr("h2")
                .removeAttr("script")
                .removeAttr("link")
                .removeAttr("a")
                .removeAttr("meta")
                .removeAttr("li")
                .removeAttr("a")
                .after("</head>")
                .getElementsByClass("entry-title");
        //.tagName("title");

        for (Element title : titles) {
            String articleTitle = title.text();
            //System.out.println(articleTitle);
            titlesClean.add(articleTitle);
        }

//        for (int i = 0; i < titlesClean.size(); i++) {
//            System.out.println(titlesClean.get(i).toString());
//        }

        return titlesClean;
    }

    public ArrayList<String> parseParagraphs() {
        paragraphs = doc.body()
                .removeAttr("head")
                .removeAttr("style")
                .removeAttr("h2")
                .removeAttr("script")
                .removeAttr("link")
                .removeAttr("meta")
                .removeAttr("li")
                .removeAttr("a")
                .getElementsByClass("older-post no-post-thumbnail")
                .select("p:not(.post-meta)")
                .select("p:not(.continue-reading)");

        for (Element paragraph : paragraphs) {
            String paragraphExtract = paragraph.text();
            //System.out.println(paragraphExtract);
            paragraphsClean.add(paragraphExtract);
        }
        return paragraphsClean;
    }

    public ArrayList<String> parseLinks() {
        links = doc.body().removeAttr("meta").getElementsByClass("entry-title")
                .select("a[href]");
        for (Element link : links) {
            String strLink = link.attr("href");
            //System.out.println(strLink);
            linksClean.add(strLink);
        }
        return linksClean;
    }

    public ArrayList<String> parseTextEntry() {
        entryContents = docEntry.body()
                .getElementsByClass("tve-tl-cnt-wrap")
                .select("p");
        for (Element entryContent : entryContents) {
            String strContent = entryContent.text();
            contentsClean.add(strContent);
        }
        return contentsClean;
    }
}

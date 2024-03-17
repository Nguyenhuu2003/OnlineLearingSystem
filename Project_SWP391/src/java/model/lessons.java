/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author MH
 */
public class lessons {
    private int lesson_id;
    private String lesson_name;
    private int chapter_id;
    private String content;
    private int status;
    private int type;

    public lessons(int lesson_id, String lesson_name, int chapter_id, String content, int status) {
        this.lesson_id = lesson_id;
        this.lesson_name = lesson_name;
        this.chapter_id = chapter_id;
        this.content = content;
        this.status = status;
    }

    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
    
    

    public lessons(int lesson_id, String lesson_name, int chapter_id, String content) {
        this.lesson_id = lesson_id;
        this.lesson_name = lesson_name;
        this.chapter_id = chapter_id;
        this.content = content;
    }

    public lessons() {
    }

    public int getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(int lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getLesson_name() {
        return lesson_name;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "lessons{" + "lesson_id=" + lesson_id + ", lesson_name=" + lesson_name + ", chapter_id=" + chapter_id + ", content=" + content + ", status=" + status + '}';
    }

    
    private int is_archived;

    public int getIs_archived() {
        return is_archived;
    }

    public void setIs_archived(int is_archived) {
        this.is_archived = is_archived;
    }
    
    
}

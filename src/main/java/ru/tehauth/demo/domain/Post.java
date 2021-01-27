package ru.tehauth.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Post {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    private String title;
    @Column(length = 5000)
    private String anons;
    @Column(length = 5000)
    private String full_text;
    private String img;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime localDate;
    private boolean status = true;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Views> views;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        StringBuffer anonsClear = new StringBuffer(this.anons);

        while (anonsClear.indexOf("[") != -1 || anonsClear.indexOf("]") != -1) {
            try {
                anonsClear.delete(anonsClear.indexOf("[") - 1, anonsClear.indexOf("]") + 1);
            } catch (Exception e) {
                break;
            }

        }
        return anonsClear.toString();
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFull_text() {
        StringBuffer anonsClear = new StringBuffer(this.full_text);

        while (anonsClear.indexOf("[") != -1 || anonsClear.indexOf("]") != -1) {
            try {
                anonsClear.delete(anonsClear.indexOf("[") - 1, anonsClear.indexOf("]") + 1);
            } catch (Exception e) {
                break;
            }
        }
        return anonsClear.toString();
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public Set<Views> getViews() {
        return views;
    }

    public void setViews(Set<Views> views) {
        this.views = views;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean statys) {
        this.status = statys;
    }

    public String getImg() {
        if (img == null){
            return "static/img/null.png";
        }
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public LocalDateTime getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDateTime localDate) {
        this.localDate = localDate;
    }

}

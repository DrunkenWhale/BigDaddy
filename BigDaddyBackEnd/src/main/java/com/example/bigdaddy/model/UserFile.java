package com.example.bigdaddy.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "file")
public class UserFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String fileUrl;

    @ManyToOne
    private User host;

    @Column
    private long fileSize; // 文件大小
    public UserFile() {
    }

    public UserFile(String name, String fileUrl, long fileSize, User host){
        this.name = name;
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
        this.host = host;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }


    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }
}

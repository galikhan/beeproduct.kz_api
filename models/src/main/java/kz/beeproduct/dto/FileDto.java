package kz.beeproduct.dto;

import kz.beeproduct.model.tables.records.FileRecord;

import java.time.LocalDateTime;

public class FileDto {

    public Long id;
    public Long container;
    public String filename;
    public String filepath;
    public LocalDateTime modified;
    public LocalDateTime created;

    public FileDto() {
    }

    public FileDto(FileRecord record) {
        this.id = record.getId_();
        this.container = record.getContainer_();
        this.filename = record.getFilename_();
        this.filepath = record.getFilepath_();
        this.modified = record.getModified_();
        this.created = record.getCreated_();
    }
}


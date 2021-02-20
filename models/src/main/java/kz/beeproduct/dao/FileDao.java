package kz.beeproduct.dao;

import kz.beeproduct.dto.FileDto;

public interface FileDao {

    FileDto create(FileDto file);

    FileDto findByContainer(Long container);

}

package kz.beeproduct.dao.impl;

import kz.beeproduct.dao.FileDao;
import kz.beeproduct.dto.FileDto;
import kz.beeproduct.model.Sequences;
import kz.beeproduct.model.tables.records.FileRecord;
import org.jooq.DSLContext;
import org.jooq.Result;

import java.time.LocalDateTime;

import static kz.beeproduct.model.tables.File.FILE;

public class FileDaoImpl implements FileDao {

    private DSLContext ctx;

    public FileDaoImpl(DSLContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public FileDto create(FileDto file) {

        FileRecord record = ctx.newRecord(FILE);
        record.setContainer_(file.container);
        record.setId_(ctx.nextval(Sequences.HONEYBEE_SEQ));
        record.setModified_(LocalDateTime.now());
        record.setFilename_(file.filename);
        record.setFilepath_(file.filepath);
        record.setRemoved_(false);
        record.insert();

        return new FileDto(record);
    }

    @Override
    public FileDto findByContainer(Long container) {
        Result<FileRecord> records = ctx.selectFrom(FILE).where(FILE.CONTAINER_.eq(container)).fetch();
        if(records.size() > 0) {
            return new FileDto(records.get(0));
        }
        return null;
    }


}

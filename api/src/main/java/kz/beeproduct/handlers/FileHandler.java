package kz.beeproduct.handlers;

import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import kz.beeproduct.dao.FileDao;
import kz.beeproduct.dao.ProductDao;
import kz.beeproduct.dao.impl.FileDaoImpl;
import kz.beeproduct.dao.impl.ProductDaoImpl;
import kz.beeproduct.dto.FileDto;
import kz.beeproduct.dto.ProductDto;
import kz.beeproduct.helper.FileUploadHelper;
import kz.beeproduct.utils.DbUtils;
import kz.beeproduct.utils.ResponseUtils;
import kz.beeproduct.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import static kz.beeproduct.utils.ResponseUtils.processResult;

public class FileHandler {

    public String directoryPath;

    private Logger log = LogManager.getLogger(FileHandler.class);

    private FileUploadHelper fileUploadHelper;

    public FileHandler(String directoryPath) {
        this.directoryPath = directoryPath;
        fileUploadHelper = new FileUploadHelper(this.directoryPath);
    }

    public void uploadedFile(RoutingContext routingContext) {

        Long container = Long.parseLong(routingContext.pathParam("container"));

        if (routingContext.fileUploads().size() > 0) {

            Optional<FileUpload> fileUploadOptional = routingContext.fileUploads().stream().findFirst();
            if (fileUploadOptional.isPresent()) {
                FileUpload fileUpload = fileUploadOptional.get();
                String filename = fileUpload.fileName();
//                log.info("name {}", fileUpload.name());
//                log.info("filename {}", fileUpload.fileName());
//                log.info("uploadedfilename {}", fileUpload.uploadedFileName());
//                log.info("contenttype {}", fileUpload.contentType());
//                log.info("size {}", fileUpload.size());
//                log.info("cancel {}", fileUpload.cancel());
                File file = new File(fileUpload.uploadedFileName());

                try {

                    fileUploadHelper.plainSaveFile(file, filename);

                    DbUtils.blocking(ctx -> {

                        FileDao fileDao = new FileDaoImpl(ctx);
                        FileDto fileDto = new FileDto();
                        fileDto.filepath = directoryPath;
                        fileDto.filename = filename;
                        fileDto.container = container;
                        return fileDao.create(fileDto);

                    }, result -> {

                        if(result.succeeded()) {

                            DbUtils.blocking(ctx -> {
                                ProductDao productDao = new ProductDaoImpl(ctx);
                                ProductDto productDto = productDao.findById(container);
                                productDto.avatar = result.result().filename;
                                productDao.update(productDto);
                                return result.result();
                            }, success -> processResult(result, routingContext), routingContext.vertx());
                       }

                    }, routingContext.vertx());

                } catch (IOException e) {
                    log.error("error", e);
                }
            }
        }
    }


    public void findByContainer(RoutingContext routingContext) {

        Long container = Long.parseLong(routingContext.pathParam("container"));

        DbUtils.blocking(ctx -> {

            FileDao fileDao = new FileDaoImpl(ctx);
            return fileDao.findByContainer(container);

        }, result -> processResult(result, routingContext), routingContext.vertx());

    }
}

package cn.edu.sjtu.ist.irp.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dyanjun
 * @date 2021/12/17 0:51
 */
@Data
public class PhotoFile {
    String id;

    String url;

    MultipartFile file;
}

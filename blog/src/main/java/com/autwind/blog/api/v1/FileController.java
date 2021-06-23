package com.autwind.blog.api.v1;


import com.autwind.blog.dto.Result.Result;
import com.autwind.blog.exception.http.NotFoundException;
import com.autwind.blog.utils.CosUtil;
import com.autwind.blog.vo.FileVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file){
        var vo = new FileVO();
        var result = new Result();
        if (file == null) {
            throw new NotFoundException(10002);
        }
        vo.setFileName(file.getName());
        String filePath = CosUtil.uploadfile(file);
        vo.setUrl(filePath);

        result.setData(vo);
        return result;
    }
}

package utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public class FileUpUtil {
    public static boolean fileUp(MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {

            String contextPath = request.getContextPath();//"/SpringMvcFileUpload"
            String servletPath = request.getServletPath();//"/gotoAction"
            String scheme = request.getScheme();//"http"


            String storePath = "E:\\project\\SpringMvcFileUpload\\WebRoot\\images";//存放我们上传的文件路径

            String fileName = file.getOriginalFilename();

            File filepath = new File(storePath, fileName);

            if (!filepath.getParentFile().exists()) {

                filepath.getParentFile().mkdirs();//如果目录不存在，创建目录

            }

            try {
                file.transferTo(new File(storePath + File.separator + fileName));//把文件写入目标文件地址
                return true;
            } catch (Exception e) {
                return false;
            }
        }else {
            return false;
        }

    }
}

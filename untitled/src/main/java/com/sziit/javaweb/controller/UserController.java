package com.sziit.javaweb.controller;

import com.sziit.javaweb.entity.User;
import com.sziit.javaweb.service.UserService;
import com.sziit.javaweb.service.UserServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService = new UserServiceImpl();
    private static final String STORAGE_PATH = "D:\\userpic\\";

    @PostMapping("/uploadAvatar")
    public Map<String, Object> uploadAvatar(
            @RequestParam("file") MultipartFile file,
            @RequestParam("username") String username) {

        Map<String, Object> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("status", "error");
            response.put("message", "选择的文件不能为空");
            return response;
        }

        File directory = new File(STORAGE_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String suffix = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFileName = UUID.randomUUID().toString() + suffix;

        File targetFile = new File(directory, newFileName);

        try {
            file.transferTo(targetFile);

            String avatarUrl = "uploads/" + newFileName;

            User user = new com.sziit.javaweb.dao.UserDaoImpl().getUserByName(username);
            if (user != null) {
                user.setAvatar(avatarUrl);
                userService.updateUser(user);

                response.put("status", "success");
                response.put("avatarUrl", avatarUrl);
                response.put("message", "头像上传并更新成功");
            } else {
                response.put("status", "error");
                response.put("message", "绑定的用户不存在");
            }

        } catch (IOException e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "文件写入物理磁盘异常: " + e.getMessage());
        }

        return response;
    }
}
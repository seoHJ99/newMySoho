package com.study.springboot.other.ckEditor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;


    @PostMapping("/imgUpload")
    @ResponseBody
    public ResponseEntity<FileResponse> imgUpload(
            @RequestPart(value = "upload", required = false) MultipartFile fileload) throws Exception {

        return new ResponseEntity<>(FileResponse.builder().
                uploaded(true).
                url(mainService.upload(fileload)).
                build(), HttpStatus.OK);
    }
    @PostMapping("/submit")
    public String submit(@RequestParam String editorData, Model model){
        model.addAttribute("editorData", editorData);
        return "display";
    }
}

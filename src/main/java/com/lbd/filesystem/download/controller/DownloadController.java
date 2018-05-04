package com.lbd.filesystem.download.controller;

import com.lbd.filesystem.download.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/download")
public class DownloadController {

    private DownloadService downloadServiceImpl;

    @Autowired
    public DownloadController(DownloadService downloadServiceImpl) {
        this.downloadServiceImpl = downloadServiceImpl;
    }

    @RequestMapping("/downloadFiles")
    public void downloadFiles(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        this.downloadServiceImpl.downloadFiles(response, request, session);
    }

    @RequestMapping("/downloadFile")
    public void downloadfile(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
    	this.downloadServiceImpl.downloadFile(response, request, session);
    }
}
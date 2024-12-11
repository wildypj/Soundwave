package com.wildy.Soundwave.music;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MusicRequest {
    private String title;
    private String genre;
    private String contributors;
    private MultipartFile file;
    private MultipartFile coverArt;
    private String artistEmail;
}

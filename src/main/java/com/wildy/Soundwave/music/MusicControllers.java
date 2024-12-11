package com.wildy.Soundwave.music;

import com.wildy.Soundwave.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/music")
//@RequiredArgsConstructor
public class MusicControllers {

    private final MusicService musicService;
    private final UserService userService;

    public MusicControllers(MusicService musicService, UserService userService) {
        this.musicService = musicService;
        this.userService = userService;
    }
    // Endpoint for artist to upload their music
    @PostMapping("/upload")
    @PreAuthorize("hasRole('ARTIST')")  // Only ARTIST role can upload
    public ResponseEntity<Music> uploadMusic(@RequestBody MusicRequest musicRequest) throws IOException {

        // Check if the artist exists
        if (!userService.getUserByEmail(musicRequest.getArtistEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Music music = Music.builder()
                .title(musicRequest.getTitle())
                .genre(musicRequest.getGenre())
                .contributors(musicRequest.getContributors())
                .fileData(musicRequest.getFile().getBytes())  // File data
                .coverArt(musicRequest.getCoverArt().getBytes()) // Cover art data
                .build();

        Music savedMusic = musicService.saveMusic(music, musicRequest.getArtistEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMusic);
    }

    // Endpoint for artists to get all their music
    @GetMapping("/myMusic")
    @PreAuthorize("hasRole('ARTIST')")  // Only ARTIST role can access
    public ResponseEntity<List<Music>> getMyMusic(@RequestParam("artistEmail") String artistEmail) {
        List<Music> musicList = musicService.getMusicByArtist(artistEmail);
        return ResponseEntity.ok(musicList);
    }

    // Endpoint for artists to update their music
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ARTIST')")  // Only ARTIST role can update their music
    public ResponseEntity<Music> updateMusic(
            @PathVariable("id") Long id,
            @RequestBody MusicRequest musicRequest) throws IOException {

        // Check if the artist owns the music
        Music music = musicService.getMusicById(id);
        if (music == null || !music.getArtist().getEmail().equals(musicRequest.getArtistEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        // Update music fields
        if (musicRequest.getTitle() != null) music.setTitle(musicRequest.getTitle());
        if (musicRequest.getGenre() != null) music.setGenre(musicRequest.getGenre());
        if (musicRequest.getContributors() != null) music.setContributors(musicRequest.getContributors());
        if (musicRequest.getFile() != null) music.setFileData(musicRequest.getFile().getBytes());
        if (musicRequest.getCoverArt() != null) music.setCoverArt(musicRequest.getCoverArt().getBytes());

        Music updatedMusic = musicService.saveMusic(music, musicRequest.getArtistEmail());
        return ResponseEntity.ok(updatedMusic);
    }

    // Endpoint for artists to delete their music
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ARTIST')")  // Only ARTIST role can delete their music
    public ResponseEntity<Void> deleteMusic(@PathVariable("id") Long id, @RequestParam("artistEmail") String artistEmail) {
        Music music = musicService.getMusicById(id);
        if (music == null || !music.getArtist().getEmail().equals(artistEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        musicService.deleteMusic(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint for regular users to get all music (READ only)
    @GetMapping("/all")
    public ResponseEntity<List<Music>> getAllMusic() {
        List<Music> musicList = musicService.getAllMusic();
        return ResponseEntity.ok(musicList);
    }
}

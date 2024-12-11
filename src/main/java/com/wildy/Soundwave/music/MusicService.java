package com.wildy.Soundwave.music;

import com.wildy.Soundwave.artist.Artist;
import com.wildy.Soundwave.artist.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
//@RequiredArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;
    private final ArtistRepository artistRepository;

    public MusicService(MusicRepository musicRepository, ArtistRepository artistRepository) {
        this.musicRepository = musicRepository;
        this.artistRepository = artistRepository;
    }

    public Music saveMusic(Music music, String artistEmail) throws IOException {
        Artist artist = artistRepository.findByEmail(artistEmail)
                .orElseThrow(() -> new IllegalArgumentException("Artist not found"));
        music.setArtist(artist);
        return musicRepository.save(music);
    }

    public List<Music> getMusicByArtist(String artistEmail) {
        Artist artist = artistRepository.findByEmail(artistEmail)
                .orElseThrow(() -> new IllegalArgumentException("Artist not found"));
        return musicRepository.findByArtist(artist);
    }

    public List<Music> getAllMusic() {
        return musicRepository.findAll();
    }

    public Music getMusicById(Long id) {
       return musicRepository.findById(id).orElse(null);
    }

    public void deleteMusic(Long id) {
        Music music = musicRepository.findById(id).orElse(null);
        if (music != null) {
            musicRepository.delete(music);
        }
    }
}

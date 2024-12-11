package com.wildy.Soundwave.music;

import com.wildy.Soundwave.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    List<Music> findByArtist(Artist artist);
}

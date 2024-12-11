package com.wildy.Soundwave.artist;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository {
    Optional<Artist> findByEmail(String email);
}

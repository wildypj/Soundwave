//package com.wildy.Soundwave.artist;
//
//import com.wildy.Soundwave.music.Music;
//import com.wildy.Soundwave.user.User;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.List;
//
//@EqualsAndHashCode(callSuper = true)
//@Entity
////@Getter
////@Setter
////@Data  // Lombok generates getters, setters, equals(), hashCode(), and toString()
////@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Artist extends User {
//
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private Long id;
//    @Setter
//    @Getter
//    private String stageName;
//    @Setter
//    @Getter
//    private String bio;
//
//    @Setter
//    @Getter
//    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
//    private List<Music> musicList;
//}
//    @Override
//    public Long getId() {
//        return id;
//    }
//
//    @Override
//    public void setId(Long id) {
//        this.id = id;
//    }


package com.wildy.Soundwave.artist;

import com.wildy.Soundwave.music.Music;
import com.wildy.Soundwave.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Artist extends User {

//    @Setter
//    @Getter
    private String stageName;
//
//    @Setter
//    @Getter
    private String bio;

//    @Setter
//    @Getter
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<Music> musicList;
}


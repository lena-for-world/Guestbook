package projectB.guestbook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.RequestMapping;

@Entity
@Getter
@RequiredArgsConstructor
public class Post {


    public static Post makeNewPost(String name, String content, String dateTime) {
        Post post = new Post();
        post.name = name;
        post.content = content;
        post.liked = 0;
        post.hate = 0;
        post.dateTime = dateTime;
        return post;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    @Size(min = 1, message = "1글자 이상의 내용이 있어야 합니다")
    private String content;
    private int liked;
    private int hate;
    private String dateTime;

}

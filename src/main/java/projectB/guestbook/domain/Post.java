package projectB.guestbook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Post {

    public Post(Long id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.liked = 0;
        this.hate = 0;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    private String name;
    @Size(min=1, message="1글자 이상의 내용이 있어야 합니다")
    private String content;
    private int liked;
    private int hate;
    private String dateTime;

    // 정적 팩터리 메서드로 엔티티 생성 -- setter 만들지 않기

}

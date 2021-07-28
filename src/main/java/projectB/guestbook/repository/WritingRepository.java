package projectB.guestbook.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import projectB.guestbook.domain.Post;

@Repository
@Transactional
@RequiredArgsConstructor
public class WritingRepository {

    @PersistenceContext private final EntityManager em;

    // 모든 방명록 출력
    public List<Post> findAllPosts() {
        em.flush();
        em.clear();
        return em.createQuery("select p from Post p", Post.class)
            .getResultList();
    }

    public List<Post> findPost(Post post) {
        return em.createQuery("select p from Post p where p.name = :name")
                .setParameter("name", post.getName())
                .getResultList();
    }

    // 방명록 저장
    public void save(Post post) {
        em.persist(post);
    }

    public void plusLike(String name) {
        em.createQuery("update Post p set p.liked = p.liked+1 where p.name =:name")
            .setParameter("name", name)
            .executeUpdate();
    }
    public void plusHate(String name) {
        em.createQuery("update Post p set p.hate = p.hate+1 where p.name =:name")
            .setParameter("name", name)
            .executeUpdate();
    }

    public boolean checkHateCount(String name) {
        List<Post> list = em.createQuery("select p from Post p where p.name = :name")
            .setParameter("name", name)
            .getResultList();
        if(list.isEmpty() == true) throw new IndexOutOfBoundsException("빨리 눌러서 발생하는 삭제 에러");
        int check = list.get(0).getHate() + 1;
        return check >= 10;
    }

    public void deletePost(String name) {
        em.createQuery("delete from Post p where p.name = :name")
            .setParameter("name", name)
            .executeUpdate();
    }

}

package projectB.guestbook.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import projectB.guestbook.domain.Post;

@Repository
@Transactional
@RequiredArgsConstructor
public class WritingRepository {

    @PersistenceContext
    private final EntityManager em;

    // 모든 방명록 출력
    public List<Post> findAllPosts() {
        return em.createQuery("select p from Post p", Post.class)
            .getResultList();
    }

    public List<Post> findPost(Long id) {
        return em.createQuery("select p from Post p where p.id = :id")
            .setParameter("id", id)
            .getResultList();
    }

    public List<Post> findPostByName(String name) {
        return em.createQuery("select p from Post p where p.name = :name")
            .setParameter("name", name)
            .getResultList();
    }

    // 방명록 저장
    public void save(Post post) {
        em.persist(post);
    }

    /*em.createQuery("delete from Post p where p.name = :name")
                .setParameter("name", name)
                .executeUpdate();
            em.flush();
            em.detach(post.get(0));*/
    public void deletePost(Long id) {
        List<Post> post = findPost(id);
        em.remove(post.get(0));
    }

}

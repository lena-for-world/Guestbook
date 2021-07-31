package projectB.guestbook.repository;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import projectB.guestbook.domain.Post;

@Repository
@Transactional
@RequiredArgsConstructor
public class ReactingRepository {

    private final WritingRepository writingRepository;
    private final EntityManager em;

    public void plusLike(Long id) {
        List<Post> list = writingRepository.findPost(id);
        Post post = list.get(0);
        em.createQuery("update Post p set p.liked = p.liked+1 where p.name =:name and p.id = :id")
            .setParameter("name", post.getName())
            .setParameter("id", post.getId())
            .executeUpdate();
        em.flush();
        em.detach(post);
    }

    public void plusHate(Long id) {
        List<Post> list = writingRepository.findPost(id);
        Post post = list.get(0);
        em.createQuery("update Post p set p.hate = p.hate+1 where p.name =:name and p.id = :id")
            .setParameter("name", post.getName())
            .setParameter("id", post.getId())
            .executeUpdate();
        em.flush();
        em.detach(post);
    }

    public boolean checkHateCount(Long id) {
        List<Post> list = em.createQuery("select p from Post p where p.id = :id")
            .setParameter("id", id)
            .getResultList();
        if (list.isEmpty() == true) {
            throw new IndexOutOfBoundsException("빨리 눌러서 발생하는 삭제 에러");
        }
        int check = list.get(0).getHate() + 1;
        return check >= 10;
    }
}

package projectB.guestbook.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import projectB.guestbook.domain.Post;
import projectB.guestbook.service.WritingService;

@Controller
@RequiredArgsConstructor
public class WritingController {

    private final WritingService writingService;

    @GetMapping("/guestbooks")
    public String main(Model model) {
        model.addAttribute("posts", writingService.getPosts());
        return "main";
    }

    @PostMapping("/guestbooks/write")
    public String write(@Valid Post post, Model model, BindingResult br) {
        try {
            if (br.hasErrors()) {
                List<ObjectError> list = br.getAllErrors();
                for (ObjectError err : list) {
                    System.out.println("br err = " + err);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            writingService.save(post);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            model.addAttribute("duplicateName", e.getMessage());
            model.addAttribute("posts", writingService.getPosts());
            return "main";
        }
        return "redirect:/guestbooks"; // redirect
    }

    @GetMapping("/guestbooks/pushPlusLike")
    public String pushPlusLike(String name) {
        // writing서비스에서 좋아요 수 업데이트
        writingService.plusLike(name);
        return "redirect:/guestbooks";
    }

    @GetMapping("/guestbooks/pushPlusHate")
    public String pushPlusHate(String name) {
        // writingService에서 싫어요 수 업데이트
        try {
            writingService.plusHate(name);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();

        }
        return "redirect:/guestbooks";
    }
}

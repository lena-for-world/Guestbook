package projectB.guestbook.controller;

import java.time.DateTimeException;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public String write(@Valid Post post, Model model, RedirectAttributes re, BindingResult br) {
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
        } catch (IllegalArgumentException | DateTimeException e) {
            if (e instanceof IllegalArgumentException) {
                e.printStackTrace();
                model.addAttribute("duplicateName", e.getMessage());
                model.addAttribute("posts", writingService.getPosts());
            } else {
                ((DateTimeException) e).printStackTrace();
                model.addAttribute("lessThanOneMinute", ((DateTimeException) e).getMessage());
                model.addAttribute("posts", writingService.getPosts());
            }
            return "main";
        }
        return "redirect:/guestbooks"; // redirect
    }

}

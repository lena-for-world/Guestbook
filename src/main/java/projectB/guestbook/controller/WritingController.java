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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectB.guestbook.service.WritingService;

@Controller
@RequiredArgsConstructor
public class WritingController {

    private final WritingService writingService;

    @GetMapping("/guestbooks")
    public String main(Model model) {
        model.addAttribute("postForm", new PostForm());
        model.addAttribute("posts", writingService.getPosts());
        return "main";
    }

    @PostMapping("/guestbooks/write")
    public String write(@Valid PostForm form, Model model, RedirectAttributes ra, BindingResult br) {
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
            writingService.save(form);
        } catch (DateTimeException e) {
            e.printStackTrace();
            ra.addFlashAttribute("msg", e.getMessage());
            return "redirect:/guestbooks";
        }
        return "redirect:/guestbooks";
    }

}

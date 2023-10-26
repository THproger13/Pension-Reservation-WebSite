package com.example.reservation.controller;

import com.example.reservation.dto.ReviewDTO;
import com.example.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/review")
    public String review(Model model,
                         @RequestParam(value="page", required = false, defaultValue = "1") int page,
                         @RequestParam(value="type", required = false, defaultValue = "reviewTitle") String type,
                         @RequestParam(value="q", required = false, defaultValue = "") String q) {
        Page<ReviewDTO> reviewDTOList = reviewService.findAll(page, type, q);

        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < reviewDTOList.getTotalPages()) ? startPage + blockLimit - 1 : reviewDTOList.getTotalPages();

        model.addAttribute("reviewList", reviewDTOList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("q", q);

        return "boardPages/review";
    }

    @GetMapping("/review/save")
    public String reviewSave() {
        return "boardPages/reviewSave";
    }

    @PostMapping("/review/save")
    public String reviewSave(@ModelAttribute ReviewDTO reviewDTO) throws IOException {
        Long id = reviewService.save(reviewDTO);
        System.out.println(" 컨트롤러 id = " + id);
        return "redirect:/board/review";
    }

    @GetMapping("/review/{id}")
    public String reviewDetail(@PathVariable("id") Long id, Model model) throws IOException {
        System.out.println("id" + id);
        ReviewDTO reviewDTO = reviewService.findById(id);
        System.out.println(reviewDTO);
        model.addAttribute("review", reviewDTO);
        return "/boardPages/reviewDetail";
    }

}
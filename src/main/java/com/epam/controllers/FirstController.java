package com.epam.controllers;

import com.epam.dao.BookDAO;
import com.epam.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/store")
public class FirstController {

public final BookDAO bookDAO;

    public FirstController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping("/mainPage")
    public String mainPage(Model model) {
    model.addAttribute("bookList", bookDAO.index());
    return "store/mainPage";
    }
    @GetMapping("category/{genre}")
    public String categories(Model model, @PathVariable("genre") String genre){
    model.addAttribute("categories", bookDAO.genre(genre));
    return "store/category";
    }
    @GetMapping("/{id}")
    public String one(@PathVariable("id") int id, Model model){
    model.addAttribute("bookinfo", bookDAO.show(id));
    return "store/individual";
    }
    @GetMapping("/register")
    public String reg(Model model){
    model.addAttribute("newBook", new Book());
    return "store/register";
    }
    @PostMapping("/register")
    public String regPost(@ModelAttribute ("newBook") Book book){
    bookDAO.create(book);
    return "redirect:/store/mainPage";
    }
    @GetMapping("/{id}/edit")
    public String updPage(Model model, @PathVariable("id") int id){
    model.addAttribute("updateBook", bookDAO.show(id));
    return "store/edit";
    }
    @PatchMapping("/{id}")
    public String upd(@ModelAttribute("updateBook") Book book, @PathVariable("id") int id){
    bookDAO.update(id, book);
    return "redirect:/store/mainPage";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
    bookDAO.delete(id);
    return "redirect:/store/mainPage";
    }
}

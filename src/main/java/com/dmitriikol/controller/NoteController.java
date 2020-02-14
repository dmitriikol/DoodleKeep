package com.dmitriikol.controller;

import com.dmitriikol.model.Note;
import com.dmitriikol.model.User;
import com.dmitriikol.repository.NoteRepository;
import com.dmitriikol.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class NoteController {

    @Autowired
    NoteRepository noteRepo;
    @Autowired
    UserRepository userRepo;

    private static Logger log = Logger.getLogger(NoteController.class);

    @GetMapping("/keep/{id}")
    public String keep(@PathVariable Long id, Model model) {
        log.info("user logged in id: " + id);
        model.addAttribute("notes", noteRepo.findAllByUserId(id));
        model.addAttribute("id_user", id);
        return "keep";
    }

    @PostMapping("/addNote")
    public String addNote(@RequestParam String title,
                          @RequestParam String text,
                          @RequestParam Long id,
                          Model model) {
        User user = userRepo.findById(id).get();
        Note note = new Note(title,text);
        log.info("user " + user.getId() + " add new note: " + note.getId());
        user.addNote(note);
        noteRepo.save(note);
        model.addAttribute("notes", noteRepo.findAllByUserId(id));
        model.addAttribute("id_user", id);
        return "redirect:/keep/" + id;
    }

    @PostMapping("/deleteNote")
    public String deleteNote(@RequestParam String userID,
                             @RequestParam String noteID,
                             Model model) {
        Note note = noteRepo.findById(Long.valueOf(noteID)).get();
        User user = userRepo.findById(Long.valueOf(userID)).get();
        user.deleteNote(note);
        noteRepo.delete(note);
        log.info("user " + user.getId() + " delete note: " + note.getId());
        model.addAttribute("notes", noteRepo.findAllByUserId(Long.valueOf(userID)));
        model.addAttribute("id_user", Long.valueOf(userID));
        return "redirect:/keep/" + Long.valueOf(userID);
    }

    @GetMapping("/updateNote")
    public String showUpdateForm(@RequestParam String userID,
                                 @RequestParam String noteID,
                                 Model model) {
        model.addAttribute("id_user", userID);
        model.addAttribute("id_note", noteID);
        return "update-note.html";
    }

    @PostMapping("/updateNote")
    public String updateNote(@RequestParam String title,
                             @RequestParam String text,
                             @RequestParam String userID,
                             @RequestParam String noteID,
                             Model model) {

        Note note = noteRepo.findById(Long.valueOf(noteID)).get();
        note.setTitle(title);
        note.setText(text);
        noteRepo.save(note);
        log.info("user " + userID + " update note: " + note.getId());
        model.addAttribute("id_user", Long.valueOf(userID));
        model.addAttribute("notes", noteRepo.findAllByUserId(Long.valueOf(userID)));
        return "redirect:/keep/" + Long.valueOf(userID);
    }

}
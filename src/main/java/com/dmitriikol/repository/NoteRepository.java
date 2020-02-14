package com.dmitriikol.repository;

import com.dmitriikol.model.Note;
import com.dmitriikol.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoteRepository extends CrudRepository<Note, Long> {

     List<Note> findAllByUserId(Long id);

}
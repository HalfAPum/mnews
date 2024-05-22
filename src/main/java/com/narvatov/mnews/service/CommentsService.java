package com.narvatov.mnews.service;

import com.narvatov.mnews.dao.CommentsDao;
import com.narvatov.mnews.dto.request.comment.CreateComment;
import com.narvatov.mnews.model.Comment;
import com.narvatov.mnews.dto.request.comment.UpdateComment;
import com.narvatov.mnews.model.user.Role;
import com.narvatov.mnews.model.user.User;
import com.narvatov.mnews.service.auth.UserService;
import com.narvatov.mnews.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {

    @Autowired
    private CommentsDao dao;
    @Autowired
    private UserService userService;

    public String add(String authHeader, CreateComment createComment) {
        User author = userService.extractUserFromAuthHeader(authHeader);

        dao.save(new Comment(createComment, author));

        return ServerResponse.getCreatedMessage(createComment);
    }

    public String update(String authHeader, UpdateComment updateComment) {
        User editor = userService.extractUserFromAuthHeader(authHeader);

        if (!verifyEditorIsAuthor(editor, updateComment.getId())) return "Editor id " + editor.getId() + " doesn't correspond to original author id.";

        dao.save(new Comment(updateComment, editor));

        return ServerResponse.getUpdatedMessage(updateComment);
    }

    private boolean verifyEditorIsAuthor(User editor, int commentId) {
        Comment comment = dao.getReferenceById(commentId);

        return editor.getId() == comment.getAuthor().getId();
    }


    public String delete(String authHeader, int id) {
        User editor = userService.extractUserFromAuthHeader(authHeader);

        if (!(verifyEditorIsAuthor(editor, id) || editor.getRole() == Role.ROLE_ADMIN)) {
            return "Editor is not admin. And as user it's id " + editor.getId() + " doesn't correspond to original author id.";
        }

        dao.deleteById(id);

        return ServerResponse.getDeletedMessage(id);
    }

}
